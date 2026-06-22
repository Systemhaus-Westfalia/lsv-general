#!/bin/bash
# =============================================================================
#  adempiere-vue Release
#
#  Creates a GitHub release for adempiere-vue and updates the corresponding
#  image version in adempiere-ui-gateway/env_template.env.
#
#  Assumes: commits have already been pushed to develop.
#
#  Version source: if no GitHub release exists yet, the current version is
#  read from VUE_UI_IMAGE in adempiere-ui-gateway/env_template.env.
#
#  Usage:
#    ./release-adempiere-vue.sh "<release notes>"
#    ./release-adempiere-vue.sh --dry-run "<release notes>"
#    ./release-adempiere-vue.sh -n
# =============================================================================

# ── Configurable polling interval ─────────────────────────────────────────────
POLL_INTERVAL=30    # seconds between CI/CD status checks

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
    echo "  adempiere-vue Release"
    echo ""
    echo "  Creates a GitHub release and updates adempiere-ui-gateway/env_template.env."
    echo "  Assumes: commits are already pushed to develop."
    echo ""
    echo "  Usage:"
    echo "    $SCRIPT \"<release notes>\"           real run — creates release, pushes ui-gateway"
    echo "    $SCRIPT --dry-run \"<release notes>\"  preview only — no changes made"
    echo "    $SCRIPT -n                           dry-run with a default notes placeholder"
    echo ""
    echo "  POLL_INTERVAL  Shell variable (default: 300s). Set at the top of the script."
    echo ""
    exit 1
fi
[ -z "$RELEASE_NOTES" ] && RELEASE_NOTES="(dry run — no release will be created)"

# ── Repository paths ──────────────────────────────────────────────────────────
BASE_DIR="/data2/entwicklung/westfaliaRepository_2022-06"
VUE_DIR="$BASE_DIR/adempiere-vue_SHW"
GW_DIR="$BASE_DIR/adempiere-ui-gateway_SHW"

VUE_REPO="Systemhaus-Westfalia/adempiere-vue"
VUE_BRANCH="develop"

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

# ── Dry-run helpers ────────────────────────────────────────────────────────────
runcmd() {
    if $DRY_RUN; then
        echo -e "    ${YELLOW}[DRY-RUN]${NC} $*"
        return 0
    fi
    "$@"
}

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
# Increment the last numeric component: 0.0.10 → 0.0.11
increment_patch() {
    local v="$1"
    local prefix last
    prefix=$(echo "$v" | sed 's/\.[0-9]*$//')
    last=$(echo "$v"  | grep -oE '[0-9]+$')
    echo "${prefix}.$((last + 1))"
}

# ── GitHub Actions helpers ─────────────────────────────────────────────────────
_latest_run_id() {
    local repo="$1" workflow="$2" branch="$3"
    local args=()
    [ -n "$branch" ] && args+=("--branch" "$branch")
    gh run list --repo "$repo" --workflow "$workflow" "${args[@]}" \
        --limit 1 --json databaseId --jq '.[0].databaseId' 2>/dev/null || echo "0"
}

snapshot_before() {
    local repo="$1" workflow="$2" branch="${3:-}"
    _latest_run_id "$repo" "$workflow" "$branch"
}

wait_for_workflow() {
    local repo="$1" workflow="$2" before_id="${3:-0}" branch="${4:-}"

    if $DRY_RUN; then
        echo -e "    ${YELLOW}[DRY-RUN]${NC} Would wait for $workflow to complete in $repo"
        return 0
    fi

    local args=()
    [ -n "$branch" ] && args+=("--branch" "$branch")

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

wait_for_current_workflow() {
    local repo="$1" workflow="$2" branch="$3"

    if $DRY_RUN; then
        echo -e "    ${YELLOW}[DRY-RUN]${NC} Would wait for current $workflow run on $repo/$branch"
        return 0
    fi

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

# ── Prerequisites ──────────────────────────────────────────────────────────────
check_prerequisites() {
    local missing=()
    for cmd in gh git jq curl; do
        command -v "$cmd" &>/dev/null || missing+=("$cmd")
    done
    [ ${#missing[@]} -gt 0 ] && die "Missing required tools: ${missing[*]}"
    gh auth status &>/dev/null || die "gh CLI is not authenticated — run: gh auth login"
}

# =============================================================================
# MAIN
# =============================================================================
check_prerequisites

echo ""
echo -e "${BOLD}═════════════════════════════════════════════════════════════${NC}"
if $DRY_RUN; then
echo -e "${BOLD}${YELLOW}  adempiere-vue Release  [DRY-RUN — no changes made]${NC}"
else
echo -e "${BOLD}  adempiere-vue Release${NC}"
fi
echo    "  Started : $(ts)"
echo    "  Notes   : $RELEASE_NOTES"
echo -e "${BOLD}═════════════════════════════════════════════════════════════${NC}"


# ─────────────────────────────────────────────────────────────────────────────
# STEP 1 — adempiere-vue: wait for CI, create release, wait for publish
# ─────────────────────────────────────────────────────────────────────────────
step_start "Step 1/5  adempiere-vue — wait for CI"
cd "$VUE_DIR" || die "Cannot enter $VUE_DIR"
git checkout "$VUE_BRANCH" -q && git pull -q && git fetch --tags -q

# Try GitHub releases first; fall back to reading VUE_UI_IMAGE from env_template.env
VUE_CURRENT=$(gh release list --repo "$VUE_REPO" --limit 1 --json tagName --jq '.[0].tagName' 2>/dev/null)
if [ -z "$VUE_CURRENT" ]; then
    ENV_FILE_GW="$GW_DIR/docker-compose/env_template.env"
    VUE_CURRENT=$(grep -oP 'VUE_UI_IMAGE="marcalwestf/adempiere-vue:\K[^"]+' "$ENV_FILE_GW" 2>/dev/null)
    [ -z "$VUE_CURRENT" ] && die "Could not determine current adempiere-vue version (no GitHub release and VUE_UI_IMAGE not found)"
    echo "    No GitHub release found — using version from env_template.env: $VUE_CURRENT"
fi

# Count commits ahead only when a matching git tag exists
VUE_GIT_TAG=$(git tag -l "$VUE_CURRENT" 2>/dev/null | head -1)
if [ -n "$VUE_GIT_TAG" ]; then
    COMMITS_AHEAD=$(git rev-list "${VUE_CURRENT}..HEAD" --count 2>/dev/null)
    if [ "$COMMITS_AHEAD" -eq 0 ]; then
        echo ""
        echo -e "  ℹ️   adempiere-vue is up to date — no new commits since tag ${VUE_CURRENT}."
        echo ""
        exit 0
    fi
    echo "    $COMMITS_AHEAD commit(s) ahead of last release ($VUE_CURRENT)"
else
    echo "    No prior git tag found for $VUE_CURRENT — skipping commit count check"
fi

wait_for_current_workflow "$VUE_REPO" "ci.yml" "$VUE_BRANCH"
step_end "adempiere-vue CI passed"

step_start "Step 2/5  adempiere-vue — create release"
VUE_NEW=$(increment_patch "$VUE_CURRENT")
echo    "    $VUE_CURRENT  →  $VUE_NEW"
BEFORE_VUE_PUBLISH=$(snapshot_before "$VUE_REPO" "publish.yml")
runcmd gh release create "$VUE_NEW" \
    --repo  "$VUE_REPO" \
    --target "$VUE_BRANCH" \
    --title "$VUE_NEW" \
    --notes "$RELEASE_NOTES" \
    || die "Failed to create release $VUE_NEW for adempiere-vue"
step_end "adempiere-vue release $VUE_NEW created"

step_start "Step 3/5  adempiere-vue — wait for publish"
wait_for_workflow "$VUE_REPO" "publish.yml" "$BEFORE_VUE_PUBLISH"
$DRY_RUN || { git pull -q && git fetch --tags -q; }
step_end "adempiere-vue publish completed"

step_start "Step 4/5  adempiere-vue — verify Docker image"
verify_docker_image "marcalwestf/adempiere-vue" "${VUE_NEW}"
step_end "adempiere-vue Docker image ${VUE_NEW} verified"


# ─────────────────────────────────────────────────────────────────────────────
# STEP 2 — adempiere-ui-gateway: update vue image version, commit, push
# ─────────────────────────────────────────────────────────────────────────────
step_start "Step 5/5  adempiere-ui-gateway — update vue image version"
cd "$GW_DIR" || die "Cannot enter $GW_DIR"
git checkout adempiere-trunk -q && git pull -q

ENV_FILE="docker-compose/env_template.env"

sed_and_verify \
    "s|VUE_UI_IMAGE=\"marcalwestf/adempiere-vue:[^\"]*\"|VUE_UI_IMAGE=\"marcalwestf/adempiere-vue:${VUE_NEW}\"|" \
    "$ENV_FILE" \
    "VUE_UI_IMAGE=\"marcalwestf/adempiere-vue:${VUE_NEW}\"" \
    "sed did not update VUE_UI_IMAGE in $ENV_FILE"

echo "    Updated $ENV_FILE:"
echo "      VUE_UI_IMAGE → marcalwestf/adempiere-vue:${VUE_NEW}"

runcmd git add "$ENV_FILE"
runcmd git commit -m "Update adempiere-vue image to ${VUE_NEW}"
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
echo -e "${BOLD}${YELLOW}  adempiere-vue Release Complete  [DRY-RUN]${NC}"
else
echo -e "${BOLD}  adempiere-vue Release Complete${NC}"
fi
echo    "  Finished : $(ts)"
printf  "  Duration : %dm %02ds\n" "$TOTAL_MINS" "$TOTAL_SECS"
echo    "  ─────────────────────────────────────────────────────────"
echo -e "  ${GREEN}${OK}  adempiere-vue         $VUE_CURRENT  →  $VUE_NEW${NC}"
echo -e "  ${GREEN}${OK}  adempiere-ui-gateway  $( $DRY_RUN && echo "would push to" || echo "pushed to") adempiere-trunk${NC}"
echo -e "${BOLD}═════════════════════════════════════════════════════════════${NC}"
echo ""
exit 0
