# ADempiere LSV

El Salvador localisation library for ADempiere.

Provides electronic invoicing (DTE documents per Ministerio de Hacienda
specifications), provider abstractions for plugging in different e-invoicing
services, domain model interfaces, and ADempiere setup classes.

## Requirements

- [JDK 17 or later](https://adoptium.net/)
- [Gradle 8.0.1 or later](https://gradle.org/install/)

---

## Package structure

| Package | Contents |
|---|---|
| `org.shw.lsv.einvoice` | Electronic invoicing — document types (Factura, Crédito Fiscal, Nota de Crédito/Débito, Retención, Anulación, Contingencia, …), factories, ADempiere process classes, utility classes |
| `org.shw.lsv.util.support` | Provider abstraction interfaces: `IDeclarationProvider`, `IDeclarationDocument` |
| `org.shw.lsv.util.support.provider` | Provider implementations: `SVMinHacienda`, `Findex`, `ElectronicInvoice`, and related helpers |
| `org.shw.lsv.setup` | ADempiere setup/deploy classes |
| `org.adempiere.core.domains.models` | Domain model interfaces (`I_E_*` series) generated from the El Salvador dictionary |

---

## How to add this library

The artifact is published to GitHub Packages under the
`Systemhaus-Westfalia/adempiere-shw` registry.
A GitHub personal access token with `read:packages` scope is required.

Add to `~/.gradle/gradle.properties`:

```properties
deployUsername=YOUR_GITHUB_USERNAME
deployToken=YOUR_GITHUB_PAT_WITH_READ_PACKAGES_SCOPE
```

Add the repository and dependency to your `build.gradle`:

```groovy
repositories {
    maven {
        url = "https://maven.pkg.github.com/Systemhaus-Westfalia/adempiere-shw"
        credentials {
            username = findProperty("deployUsername") ?: System.getenv("GITHUB_DEPLOY_USER")
            password = findProperty("deployToken")    ?: System.getenv("GITHUB_DEPLOY_TOKEN")
        }
    }
}

dependencies {
    implementation 'com.shw:lsv-general:1.0.53'
}
```

---

## Role in the SHW Customization Stack

`lsv-general` is the bottom layer of the SHW customization stack. Releasing a
new version triggers a chain of dependent releases that ends with updated Docker
image references in `adempiere-ui-gateway`.

```
lsv-general
    └── adempiere-shw
            ├── adempiere-shw-zk              ─┐
            ├── adempiere-grpc-server          ├─► adempiere-ui-gateway
            └── adempiere-processors-service  ─┘
```

### Automation scripts

Three shell scripts in the `scripts/` directory automate the release chain.
They require `gh`, `git`, `jq`, and `curl`, and an authenticated `gh` CLI
(`gh auth login`).

| Script | Purpose |
|---|---|
| `stack-update.sh` | Full chain: starting from a new `lsv-general` release, propagates versions through every dependent repository up to `adempiere-ui-gateway` |
| `release-adempiere-grpc-server.sh` | Single step: creates a release for `adempiere-grpc-server` and updates its image tag in `adempiere-ui-gateway` |
| `release-adempiere-vue.sh` | Single step: creates a release for `adempiere-vue` and updates its image tag in `adempiere-ui-gateway` |

**Usage**

```bash
# Full chain — real run
./scripts/stack-update.sh "Fix report formatting in SHW invoice layout"

# Full chain — preview only, no changes made
./scripts/stack-update.sh --dry-run "Fix report formatting in SHW invoice layout"

# Full chain — dry-run with default placeholder notes
./scripts/stack-update.sh -n

# Single step — grpc-server only
./scripts/release-adempiere-grpc-server.sh "Fix gRPC handler for invoice posting"

# Single step — vue only
./scripts/release-adempiere-vue.sh "Update invoice print layout"
```

**Options**

- `--dry-run` / `-n` — preview mode: prints every action that would be taken
  but makes no changes (no commits, no pushes, no GitHub releases). Read-only
  GitHub API calls still run so version transitions are shown accurately.
- `POLL_INTERVAL` — shell variable at the top of each script (default: 30 s).
  Controls how often CI/CD workflow status is checked.
