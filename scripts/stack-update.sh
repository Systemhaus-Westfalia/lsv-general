#!/bin/bash
# =============================================================================
#  SHW Customization Stack Update
#
#  Automates the full release chain from lsv-general to adempiere-ui-gateway.
#  Assumes: the customization commits have already been pushed to lsv-general/master.
#
#  Usage:
#    ./stack-update.sh "<release notes>"          — full run
#    ./stack-update.sh --dry-run "<release notes>" — preview only, no changes
#    ./stack-update.sh -n                          — dry-run with default notes
#
#  Example: ./stack-update.sh "Fix report formatting in SHW invoice layout"
# =============================================================================

# ── Configurable polling interval ─────────────────────────────────────────────
POLL_INTERVAL=30   # seconds between CI/CD status checks (default: 5 minutes)

# ── Argument parsing ───────────────────────────────────────────────────────────
DRY_RUN=false
RELEASE_NOTES=""

while [[ $# -gt 0 ]]; do
    case "$1" in
        --dry-run|-n) DRY_RUN=true ; shift ;;
        *)            RELEASE_NOTES="$1" ; shift ;;
    esac
done

if ! $DRY_RUN && [ -z "$RELEASE_NOTES" ]; then
    SCRIPT=$(basename "$0")
    echo ""
    echo "  SHW Customization Stack Update"
    echo ""
    echo "  Automates the full release chain from lsv-general to adempiere-ui-gateway."
    echo "  Assumes: customization commits have already been pushed to lsv-general/master."
    echo ""
    echo "  Chain:"
    echo "    lsv-general → adempiere-shw → adempiere-shw-zk        ┐"
    echo "                               → adempiere-grpc-server     ├→ adempiere-ui-gateway"
    echo "                               → adempiere-processors-service ┘"
    echo ""
    echo "  Usage:"
    echo "    $SCRIPT \"<release notes>\"           real run — commits, pushes, creates releases"
    echo "    $SCRIPT --dry-run \"<release notes>\"  preview only — no changes made"
    echo "    $SCRIPT -n                           dry-run with a default notes placeholder"
    echo ""
    echo "  Options:"
    echo "    --dry-run, -n   Skip all writes (sed, git, gh release). Read-only GitHub"
    echo "                    calls still run to show accurate version transitions."
    echo ""
    echo "    POLL_INTERVAL   Shell variable (default: 300s). Set at the top of the"
    echo "                    script to change how often CI/CD status is checked."
    echo ""
    echo "  Examples:"
    echo "    $SCRIPT \"Fix report formatting in SHW invoice layout\""
    echo "    $SCRIPT --dry-run \"Fix report formatting in SHW invoice layout\""
    echo "    $SCRIPT -n"
    echo ""
    exit 1
fi
[ -z "$RELEASE_NOTES" ] && RELEASE_NOTES="(dry run — no release will be created)"

# ── Repository paths ──────────────────────────────────────────────────────────
BASE_DIR="/data2/entwicklung/westfaliaRepository_2022-06"
LSV_DIR="/data2/entwicklung/shw_repositories_2024/lsv-general"
SHW_DIR="$BASE_DIR/adempiere-shw"
ZK_DIR="$BASE_DIR/adempiere-shw-zk"
GRPC_DIR="$BASE_DIR/adempiere-grpc-server_SHW"
PROC_DIR="$BASE_DIR/adempiere-processors-service_SHW"
GW_DIR="$BASE_DIR/adempiere-ui-gateway_SHW"

# ── Colors & icons ─────────────────────────────────────────────────────────────
GREEN='\033[0;32m'; RED='\033[0;31m'; YELLOW='\033[1;33m'
BLUE='\033[0;34m'; BOLD='\033[1m'; NC='\033[0m'
OK="✅"; FAIL="❌"

# ── Timing ─────────────────────────────────────────────────────────────────────
SCRIPT_START=$(date +%s)
STEP_START_TS=0

ts()         { date '+%Y-%m-%d %H:%M:%S'; }
ts_epoch()   { date +%s; }

step_start() {
    STEP_START_TS=$(ts_epoch)
    echo ""
    echo -e "${BLUE}${BOLD}─── $* ─────────────────────────────────────────────────${NC}"
    echo    "    Started : $(ts)"
}

step_end() {
    local elapsed=$(( $(ts_epoch) - STEP_START_TS ))
    local mins=$(( elapsed / 60 )) secs=$(( elapsed % 60 ))
    echo    "    Finished: $(ts)"
    printf  "    Duration: %dm %02ds\n" "$mins" "$secs"
    echo -e "    ${GREEN}${OK}  $*${NC}"
}

die() {
    echo -e "${RED}${FAIL}  ERROR [$(ts)]: $*${NC}" >&2
    exit 1
}

# ── Prerequisites ──────────────────────────────────────────────────────────────
check_prerequisites() {
    local missing=()
    for cmd in gh git jq curl; do
        command -v "$cmd" &>/dev/null || missing+=("$cmd")
    done
    [ ${#missing[@]} -gt 0 ] && die "Missing required tools: ${missing[*]}"
    gh auth status &>/dev/null || die "gh CLI is not authenticated — run: gh auth login"
}

# ── Dry-run helpers ────────────────────────────────────────────────────────────

# Execute a command or, in dry-run mode, just print it and return success.
runcmd() {
    if $DRY_RUN; then
        echo -e "    ${YELLOW}[DRY-RUN]${NC} $*"
        return 0
    fi
    "$@"
}

# Apply a sed expression to a file and verify the result, skipping both in dry-run.
# Args: expression  file  verify_pattern  error_message
sed_and_verify() {
    local expr="$1" file="$2" pattern="$3" errmsg="$4"
    if $DRY_RUN; then
        echo -e "    ${YELLOW}[DRY-RUN]${NC} sed -i '$expr' $file"
        return 0
    fi
    sed -i "$expr" "$file"
    grep -q "$pattern" "$file" || die "$errmsg"
}

# ── Version helpers ────────────────────────────────────────────────────────────

# Increment the last numeric component of a version string.
# Examples: 1.0.53 → 1.0.54 | 3.9.4.001-1.1.61 → 3.9.4.001-1.1.62
increment_patch() {
    local v="$1"
    local prefix last
    prefix=$(echo "$v" | sed 's/\.[0-9]*$//')
    last=$(echo "$v"  | grep -oE '[0-9]+$')
    echo "${prefix}.$((last + 1))"
}

# Get the tag name of the latest published GitHub release.
latest_release_tag() {
    local repo="$1"
    gh release list --repo "$repo" --limit 1 --json tagName --jq '.[0].tagName' 2>/dev/null \
        || die "Could not fetch latest release tag from $repo"
}

# ── GitHub Actions helpers ─────────────────────────────────────────────────────

# Return the databaseId of the most recent run of a workflow.
# Optional branch filter (pass "" to omit).
_latest_run_id() {
    local repo="$1" workflow="$2" branch="$3"
    local args=()
    [ -n "$branch" ] && args+=("--branch" "$branch")
    gh run list --repo "$repo" --workflow "$workflow" "${args[@]}" \
        --limit 1 --json databaseId --jq '.[0].databaseId' 2>/dev/null || echo "0"
}

# Snapshot run ID before triggering a workflow.
# Returns the ID so the caller can pass it to wait_for_workflow.
snapshot_before() {
    local repo="$1" workflow="$2" branch="${3:-}"
    _latest_run_id "$repo" "$workflow" "$branch"
}

# Wait until a workflow run with ID > before_id appears, then poll until it succeeds.
# In dry-run mode: prints what would be waited for and returns immediately.
# Args: repo  workflow  before_id  [branch]
wait_for_workflow() {
    local repo="$1" workflow="$2" before_id="${3:-0}" branch="${4:-}"

    if $DRY_RUN; then
        echo -e "    ${YELLOW}[DRY-RUN]${NC} Would wait for $workflow to complete in $repo"
        return 0
    fi

    local args=()
    [ -n "$branch" ] && args+=("--branch" "$branch")

    # Search for the new run (up to 3 min: 12 × 15s)
    local run_id="" attempts=0
    while [ -z "$run_id" ] && [ "$attempts" -lt 12 ]; do
        sleep 15
        local latest
        latest=$(_latest_run_id "$repo" "$workflow" "$branch")
        if [ -n "$latest" ] && [ "$latest" -gt "$before_id" ] 2>/dev/null; then
            run_id="$latest"
        fi
        (( attempts++ )) || true
    done
    [ -z "$run_id" ] && die "No new $workflow run appeared in $repo after $((attempts * 15))s"

    echo "    Run #$run_id — polling every ${POLL_INTERVAL}s"

    while true; do
        local json status conclusion
        json=$(gh run view "$run_id" --repo "$repo" --json status,conclusion 2>/dev/null)
        status=$(echo "$json"     | jq -r '.status')
        conclusion=$(echo "$json" | jq -r '.conclusion')

        if [ "$status" = "completed" ]; then
            [ "$conclusion" = "success" ] && return 0
            die "$workflow run #$run_id in $repo ended with: $conclusion"
        fi

        echo "    $(ts)  $workflow status: $status — next check in ${POLL_INTERVAL}s"
        sleep "$POLL_INTERVAL"
    done
}

# For step 1 only: wait for the CURRENT latest ci.yml run (push already happened externally).
# In dry-run mode: prints what would be waited for and returns immediately.
wait_for_current_workflow() {
    local repo="$1" workflow="$2" branch="$3"

    if $DRY_RUN; then
        echo -e "    ${YELLOW}[DRY-RUN]${NC} Would wait for current $workflow run on $repo/$branch"
        return 0
    fi

    # Give GitHub a moment to register the run
    sleep 20

    local run_id
    run_id=$(_latest_run_id "$repo" "$workflow" "$branch")
    [ -z "$run_id" ] || [ "$run_id" = "0" ] && die "No $workflow run found for $repo/$branch"

    echo "    Run #$run_id — polling every ${POLL_INTERVAL}s"

    while true; do
        local json status conclusion
        json=$(gh run view "$run_id" --repo "$repo" --json status,conclusion 2>/dev/null)
        status=$(echo "$json"     | jq -r '.status')
        conclusion=$(echo "$json" | jq -r '.conclusion')

        if [ "$status" = "completed" ]; then
            [ "$conclusion" = "success" ] && return 0
            die "$workflow run #$run_id in $repo ended with: $conclusion"
        fi

        echo "    $(ts)  $workflow status: $status — next check in ${POLL_INTERVAL}s"
        sleep "$POLL_INTERVAL"
    done
}

# ── Docker Hub verification ────────────────────────────────────────────────────
# In dry-run mode: prints what would be verified and returns immediately.
verify_docker_image() {
    local image="$1" tag="$2"

    if $DRY_RUN; then
        echo -e "    ${YELLOW}[DRY-RUN]${NC} Would verify Docker image ${image}:${tag} on Docker Hub"
        return 0
    fi

    local url="https://hub.docker.com/v2/repositories/${image}/tags/${tag}/"
    local code
    code=$(curl -sf -o /dev/null -w "%{http_code}" --max-time 15 "$url" 2>/dev/null)
    [ "$code" = "200" ] || die "Docker image ${image}:${tag} not found on Docker Hub (HTTP $code)"
    echo -e "    ${GREEN}${OK}  Verified: ${image}:${tag}${NC}"
}

# =============================================================================
# MAIN
# =============================================================================
check_prerequisites

echo ""
echo -e "${BOLD}═════════════════════════════════════════════════════════════${NC}"
if $DRY_RUN; then
echo -e "${BOLD}${YELLOW}  SHW Customization Stack Update  [DRY-RUN — no changes made]${NC}"
else
echo -e "${BOLD}  SHW Customization Stack Update${NC}"
fi
echo    "  Started : $(ts)"
echo    "  Notes   : $RELEASE_NOTES"
echo -e "${BOLD}═════════════════════════════════════════════════════════════${NC}"


# ─────────────────────────────────────────────────────────────────────────────
# STEP 1 — lsv-general: wait for CI, create release, wait for publish
# ─────────────────────────────────────────────────────────────────────────────
step_start "Step 1/6  lsv-general — wait for CI"
cd "$LSV_DIR" || die "Cannot enter $LSV_DIR"
git checkout master -q && git pull -q && git fetch --tags -q

LSV_CURRENT=$(latest_release_tag "Systemhaus-Westfalia/lsv-general")
COMMITS_AHEAD=$(git rev-list "${LSV_CURRENT}..HEAD" --count 2>/dev/null)
[ -z "$COMMITS_AHEAD" ] && die "Could not count commits ahead of $LSV_CURRENT in lsv-general"
if [ "$COMMITS_AHEAD" -eq 0 ]; then
    echo ""
    echo -e "  ℹ️   lsv-general is up to date — no new commits since tag ${LSV_CURRENT}."
    echo    "      If the change was made in a downstream repository, start the chain from there."
    echo ""
    exit 0
fi
echo "    $COMMITS_AHEAD commit(s) ahead of last release ($LSV_CURRENT)"

wait_for_current_workflow \
    "Systemhaus-Westfalia/lsv-general" "ci.yml" "master"
step_end "lsv-general CI passed"

step_start "Step 1/6  lsv-general — create release"
LSV_NEW=$(increment_patch "$LSV_CURRENT")
echo    "    $LSV_CURRENT  →  $LSV_NEW"
BEFORE_LSV_PUBLISH=$(snapshot_before "Systemhaus-Westfalia/lsv-general" "publish.yml")
runcmd gh release create "$LSV_NEW" \
    --repo  Systemhaus-Westfalia/lsv-general \
    --target master \
    --title "$LSV_NEW" \
    --notes "$RELEASE_NOTES" \
    || die "Failed to create release $LSV_NEW for lsv-general"
step_end "lsv-general release $LSV_NEW created"

step_start "Step 1/6  lsv-general — wait for publish"
wait_for_workflow \
    "Systemhaus-Westfalia/lsv-general" "publish.yml" "$BEFORE_LSV_PUBLISH"
step_end "lsv-general Maven artifact $LSV_NEW published"


# ─────────────────────────────────────────────────────────────────────────────
# STEP 2 — adempiere-shw: update dependency, CI, release, publish
# ─────────────────────────────────────────────────────────────────────────────
step_start "Step 2/6  adempiere-shw — update lsv-general version"
cd "$SHW_DIR" || die "Cannot enter $SHW_DIR"
git checkout main -q && git pull -q && git fetch --tags -q

sed_and_verify \
    "s|api \"com.shw:lsv-general:[^\"]*\"|api \"com.shw:lsv-general:${LSV_NEW}\"|" \
    shw_libs/build.gradle \
    "lsv-general:${LSV_NEW}" \
    "sed did not update lsv-general version in adempiere-shw/shw_libs/build.gradle"

BEFORE_SHW_CI=$(snapshot_before "Systemhaus-Westfalia/adempiere-shw" "ci.yml" "main")
runcmd git add shw_libs/build.gradle
runcmd git commit -m "Update lsv-general dependency to ${LSV_NEW}"
runcmd git push origin main || die "Failed to push adempiere-shw"
step_end "adempiere-shw dependency updated and pushed"

step_start "Step 2/6  adempiere-shw — wait for CI"
wait_for_workflow \
    "Systemhaus-Westfalia/adempiere-shw" "ci.yml" "$BEFORE_SHW_CI" "main"
step_end "adempiere-shw CI passed"

step_start "Step 2/6  adempiere-shw — create release"
SHW_CURRENT=$(latest_release_tag "Systemhaus-Westfalia/adempiere-shw")
SHW_NEW=$(increment_patch "$SHW_CURRENT")
echo    "    $SHW_CURRENT  →  $SHW_NEW"
BEFORE_SHW_PUBLISH=$(snapshot_before "Systemhaus-Westfalia/adempiere-shw" "publish.yml")
runcmd gh release create "$SHW_NEW" \
    --repo  Systemhaus-Westfalia/adempiere-shw \
    --target main \
    --title "$SHW_NEW" \
    --notes "$RELEASE_NOTES" \
    || die "Failed to create release $SHW_NEW for adempiere-shw"
step_end "adempiere-shw release $SHW_NEW created"

step_start "Step 2/6  adempiere-shw — wait for publish"
wait_for_workflow \
    "Systemhaus-Westfalia/adempiere-shw" "publish.yml" "$BEFORE_SHW_PUBLISH"
step_end "adempiere-shw Maven artifact $SHW_NEW published"


# ─────────────────────────────────────────────────────────────────────────────
# STEP 3a — adempiere-shw-zk: update dependency, CI, release, publish, verify
# ─────────────────────────────────────────────────────────────────────────────
step_start "Step 3a/6  adempiere-shw-zk — update adempiere-shw version"
cd "$ZK_DIR" || die "Cannot enter $ZK_DIR"
git checkout master -q && git pull -q && git fetch --tags -q

sed_and_verify \
    "s|def adempiereSHWRelease = '[^']*'|def adempiereSHWRelease = '${SHW_NEW}'|" \
    build.gradle \
    "adempiereSHWRelease = '${SHW_NEW}'" \
    "sed did not update adempiere-shw version in adempiere-shw-zk/build.gradle"

BEFORE_ZK_CI=$(snapshot_before "Systemhaus-Westfalia/adempiere-shw-zk" "build.yml" "master")
runcmd git add build.gradle
runcmd git commit -m "Update adempiere-shw dependency to ${SHW_NEW}"
runcmd git push origin master || die "Failed to push adempiere-shw-zk"
step_end "adempiere-shw-zk dependency updated and pushed"

step_start "Step 3a/6  adempiere-shw-zk — wait for CI"
wait_for_workflow \
    "Systemhaus-Westfalia/adempiere-shw-zk" "build.yml" "$BEFORE_ZK_CI" "master"
step_end "adempiere-shw-zk CI passed"

step_start "Step 3a/6  adempiere-shw-zk — create release"
ZK_CURRENT=$(latest_release_tag "Systemhaus-Westfalia/adempiere-shw-zk")
ZK_NEW=$(increment_patch "$ZK_CURRENT")
echo    "    $ZK_CURRENT  →  $ZK_NEW"
BEFORE_ZK_PUBLISH=$(snapshot_before "Systemhaus-Westfalia/adempiere-shw-zk" "publish.yml")
runcmd gh release create "$ZK_NEW" \
    --repo  Systemhaus-Westfalia/adempiere-shw-zk \
    --target master \
    --title "$ZK_NEW" \
    --notes "$RELEASE_NOTES" \
    || die "Failed to create release $ZK_NEW for adempiere-shw-zk"
step_end "adempiere-shw-zk release $ZK_NEW created"

step_start "Step 3a/6  adempiere-shw-zk — wait for publish"
wait_for_workflow \
    "Systemhaus-Westfalia/adempiere-shw-zk" "publish.yml" "$BEFORE_ZK_PUBLISH"
step_end "adempiere-shw-zk publish completed"

step_start "Step 3a/6  adempiere-shw-zk — verify Docker image"
verify_docker_image "marcalwestf/adempiere-shw-zk" "jetty-${ZK_NEW}"
step_end "adempiere-shw-zk Docker image jetty-${ZK_NEW} verified"


# ─────────────────────────────────────────────────────────────────────────────
# STEP 3b — adempiere-grpc-server: update dependency, CI, release, publish, verify
# ─────────────────────────────────────────────────────────────────────────────
step_start "Step 3b/6  adempiere-grpc-server — update adempiere-shw version"
cd "$GRPC_DIR" || die "Cannot enter $GRPC_DIR"
git checkout feature/shw/master -q && git pull -q && git fetch --tags -q

sed_and_verify \
    "s|implementation 'com.shw:adempiere-shw.shw_libs:[^']*'|implementation 'com.shw:adempiere-shw.shw_libs:${SHW_NEW}'|" \
    build.gradle \
    "adempiere-shw.shw_libs:${SHW_NEW}" \
    "sed did not update adempiere-shw version in adempiere-grpc-server/build.gradle"

BEFORE_GRPC_CI=$(snapshot_before "Systemhaus-Westfalia/adempiere-grpc-server" "ci.yml" "feature/shw/master")
runcmd git add build.gradle
runcmd git commit -m "Update adempiere-shw dependency to ${SHW_NEW}"
runcmd git push origin feature/shw/master || die "Failed to push adempiere-grpc-server"
step_end "adempiere-grpc-server dependency updated and pushed"

step_start "Step 3b/6  adempiere-grpc-server — wait for CI"
wait_for_workflow \
    "Systemhaus-Westfalia/adempiere-grpc-server" "ci.yml" "$BEFORE_GRPC_CI" "feature/shw/master"
step_end "adempiere-grpc-server CI passed"

step_start "Step 3b/6  adempiere-grpc-server — create release"
GRPC_CURRENT=$(latest_release_tag "Systemhaus-Westfalia/adempiere-grpc-server")
GRPC_NEW=$(increment_patch "$GRPC_CURRENT")
# The env_template.env var stores only the suffix after "3.9.4.001-shw-"
GRPC_SHORT_NEW="${GRPC_NEW#3.9.4.001-shw-}"
echo    "    $GRPC_CURRENT  →  $GRPC_NEW  (env var: $GRPC_SHORT_NEW)"
BEFORE_GRPC_PUBLISH=$(snapshot_before "Systemhaus-Westfalia/adempiere-grpc-server" "publish.yml")
runcmd gh release create "$GRPC_NEW" \
    --repo  Systemhaus-Westfalia/adempiere-grpc-server \
    --target feature/shw/master \
    --title "$GRPC_NEW" \
    --notes "$RELEASE_NOTES" \
    || die "Failed to create release $GRPC_NEW for adempiere-grpc-server"
step_end "adempiere-grpc-server release $GRPC_NEW created"

step_start "Step 3b/6  adempiere-grpc-server — wait for publish"
wait_for_workflow \
    "Systemhaus-Westfalia/adempiere-grpc-server" "publish.yml" "$BEFORE_GRPC_PUBLISH"
$DRY_RUN || { git pull -q && git fetch --tags -q; }
step_end "adempiere-grpc-server publish completed"

step_start "Step 3b/6  adempiere-grpc-server — verify Docker image"
verify_docker_image "marcalwestf/adempiere-grpc-server" "${GRPC_NEW}"
step_end "adempiere-grpc-server Docker image ${GRPC_NEW} verified"


# ─────────────────────────────────────────────────────────────────────────────
# STEP 3c — adempiere-processors-service: update dependency, CI, release, publish, verify
# ─────────────────────────────────────────────────────────────────────────────
step_start "Step 3c/6  adempiere-processors-service — update adempiere-shw version"
cd "$PROC_DIR" || die "Cannot enter $PROC_DIR"
git checkout feature/shw/customizations -q && git pull -q && git fetch --tags -q

sed_and_verify \
    "s|implementation 'com.shw:adempiere-shw.shw_libs:[^']*'|implementation 'com.shw:adempiere-shw.shw_libs:${SHW_NEW}'|" \
    build.gradle \
    "adempiere-shw.shw_libs:${SHW_NEW}" \
    "sed did not update adempiere-shw version in adempiere-processors-service/build.gradle"

BEFORE_PROC_CI=$(snapshot_before "Systemhaus-Westfalia/adempiere-processors-service" "ci.yml" "feature/shw/customizations")
runcmd git add build.gradle
runcmd git commit -m "Update adempiere-shw dependency to ${SHW_NEW}"
runcmd git push origin feature/shw/customizations || die "Failed to push adempiere-processors-service"
step_end "adempiere-processors-service dependency updated and pushed"

step_start "Step 3c/6  adempiere-processors-service — wait for CI"
wait_for_workflow \
    "Systemhaus-Westfalia/adempiere-processors-service" "ci.yml" "$BEFORE_PROC_CI" "feature/shw/customizations"
step_end "adempiere-processors-service CI passed"

step_start "Step 3c/6  adempiere-processors-service — create release"
PROC_CURRENT=$(latest_release_tag "Systemhaus-Westfalia/adempiere-processors-service")
PROC_NEW=$(increment_patch "$PROC_CURRENT")
echo    "    $PROC_CURRENT  →  $PROC_NEW"
BEFORE_PROC_PUBLISH=$(snapshot_before "Systemhaus-Westfalia/adempiere-processors-service" "publish.yml")
runcmd gh release create "$PROC_NEW" \
    --repo  Systemhaus-Westfalia/adempiere-processors-service \
    --target feature/shw/customizations \
    --title "$PROC_NEW" \
    --notes "$RELEASE_NOTES" \
    || die "Failed to create release $PROC_NEW for adempiere-processors-service"
step_end "adempiere-processors-service release $PROC_NEW created"

step_start "Step 3c/6  adempiere-processors-service — wait for publish"
wait_for_workflow \
    "Systemhaus-Westfalia/adempiere-processors-service" "publish.yml" "$BEFORE_PROC_PUBLISH"
$DRY_RUN || { git pull -q && git fetch --tags -q; }
step_end "adempiere-processors-service publish completed"

step_start "Step 3c/6  adempiere-processors-service — verify Docker image"
verify_docker_image "marcalwestf/adempiere-processors-service" "alpine-${PROC_NEW}"
step_end "adempiere-processors-service Docker image alpine-${PROC_NEW} verified"


# ─────────────────────────────────────────────────────────────────────────────
# STEP 4 — adempiere-ui-gateway: update image versions, commit, push
# ─────────────────────────────────────────────────────────────────────────────
step_start "Step 4/6  adempiere-ui-gateway — update image versions"
cd "$GW_DIR" || die "Cannot enter $GW_DIR"
git checkout adempiere-trunk -q && git pull -q && git fetch --tags -q

ENV_FILE="docker-compose/env_template.env"

# ZK image (line ~130)
sed_and_verify \
    "s|ADEMPIERE_ZK_IMAGE=\"marcalwestf/adempiere-shw-zk:jetty-[^\"]*\"|ADEMPIERE_ZK_IMAGE=\"marcalwestf/adempiere-shw-zk:jetty-${ZK_NEW}\"|" \
    "$ENV_FILE" \
    "adempiere-shw-zk:jetty-${ZK_NEW}" \
    "sed did not update ADEMPIERE_ZK_IMAGE in $ENV_FILE"

# gRPC server short version (line ~232); line 233 derives the full image from this variable
sed_and_verify \
    "s|VUE_BACKEND_GRPC_SERVER_VERSION=\"[^\"]*\"|VUE_BACKEND_GRPC_SERVER_VERSION=\"${GRPC_SHORT_NEW}\"|" \
    "$ENV_FILE" \
    "VUE_BACKEND_GRPC_SERVER_VERSION=\"${GRPC_SHORT_NEW}\"" \
    "sed did not update VUE_BACKEND_GRPC_SERVER_VERSION in $ENV_FILE"

# Processors image (line ~151)
sed_and_verify \
    "s|ADEMPIERE_PROCESSOR_IMAGE=\"marcalwestf/adempiere-processors-service:alpine-[^\"]*\"|ADEMPIERE_PROCESSOR_IMAGE=\"marcalwestf/adempiere-processors-service:alpine-${PROC_NEW}\"|" \
    "$ENV_FILE" \
    "adempiere-processors-service:alpine-${PROC_NEW}" \
    "sed did not update ADEMPIERE_PROCESSOR_IMAGE in $ENV_FILE"

echo "    Updated docker-compose/env_template.env:"
echo "      ADEMPIERE_ZK_IMAGE              → jetty-${ZK_NEW}"
echo "      VUE_BACKEND_GRPC_SERVER_VERSION → ${GRPC_SHORT_NEW}  (image: 3.9.4.001-shw-${GRPC_SHORT_NEW})"
echo "      ADEMPIERE_PROCESSOR_IMAGE       → alpine-${PROC_NEW}"

runcmd git add "$ENV_FILE"
runcmd git commit -m "Update service images: zk=${ZK_NEW}, grpc=${GRPC_NEW}, proc=${PROC_NEW}"
runcmd git push origin adempiere-trunk || die "Failed to push adempiere-ui-gateway"
step_end "adempiere-ui-gateway pushed (adempiere-trunk)"


# =============================================================================
# SUMMARY
# =============================================================================
TOTAL_ELAPSED=$(( $(ts_epoch) - SCRIPT_START ))
TOTAL_MINS=$(( TOTAL_ELAPSED / 60 ))
TOTAL_SECS=$(( TOTAL_ELAPSED % 60 ))

echo ""
echo -e "${BOLD}═════════════════════════════════════════════════════════════${NC}"
if $DRY_RUN; then
echo -e "${BOLD}${YELLOW}  Stack Update Preview Complete  [DRY-RUN — nothing was changed]${NC}"
else
echo -e "${BOLD}  Stack Update Complete${NC}"
fi
echo    "  Finished : $(ts)"
printf  "  Duration : %dm %02ds\n" "$TOTAL_MINS" "$TOTAL_SECS"
echo    "  ─────────────────────────────────────────────────────────"
echo -e "  ${GREEN}${OK}  lsv-general                  $LSV_CURRENT  →  $LSV_NEW${NC}"
echo -e "  ${GREEN}${OK}  adempiere-shw                $SHW_CURRENT  →  $SHW_NEW${NC}"
echo -e "  ${GREEN}${OK}  adempiere-shw-zk             $ZK_CURRENT  →  $ZK_NEW${NC}"
echo -e "  ${GREEN}${OK}  adempiere-grpc-server        $GRPC_CURRENT  →  $GRPC_NEW${NC}"
echo -e "  ${GREEN}${OK}  adempiere-processors-service $PROC_CURRENT  →  $PROC_NEW${NC}"
echo -e "  ${GREEN}${OK}  adempiere-ui-gateway         $(  $DRY_RUN && echo "would push to" || echo "pushed to") adempiere-trunk${NC}"
echo -e "${BOLD}═════════════════════════════════════════════════════════════${NC}"
echo ""
exit 0
