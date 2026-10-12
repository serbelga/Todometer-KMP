---
name: dependency-graph-to-mermaid
description: Generates or updates Mermaid module dependency diagrams in README.md files by inspecting Gradle build files (build.gradle.kts) and settings.gradle.kts directly.
metadata:
  author: todometer
  version: "3.0"
---

# Module Dependency Graph to Mermaid Skill

Use this skill to generate or update Mermaid module dependency diagrams directly in module `README.md` files by inspecting Gradle build scripts (`build.gradle.kts`) and project declarations in `settings.gradle.kts` without relying on SVG files.

## Workflow

1. **Read `settings.gradle.kts` for Exact Project Paths & Accessor Mapping**:
   - Inspect `settings.gradle.kts` to obtain the list of all included modules and their exact Gradle project paths (e.g., `:app-common:ui`, `:app-feature:home`, `:common:designsystem-resources`, etc.).
   - Note Gradle's Type-Safe Project Accessors naming rule (kebab-case segments like `app-common` become camelCase property accessors like `appCommon`):
     - `projects.appCommon.designsystem` $\rightarrow$ `:app-common:designsystem`
     - `projects.appCommon.ui` $\rightarrow$ `:app-common:ui`
     - `projects.appFeature.home` $\rightarrow$ `:app-feature:home`
     - `projects.common.designsystemResources` $\rightarrow$ `:common:designsystem-resources`
     - `projects.commonAndroid.demoDatabase` $\rightarrow$ `:common-android:demo-database`

2. **Identify Target Module & Read `build.gradle.kts`**:
   - Locate the target module folder (e.g. `app-common/ui`, `common/core`, `app-feature/home`, etc.).
   - Read the module's `build.gradle.kts` file.

3. **Extract Project Dependencies**:
   - Search for project dependencies declared in `dependencies` / `sourceSets` blocks:
     - Type-safe project accessors (e.g. `projects.common.data`, `projects.appCommon.designsystem`, `projects.appFeature.home`).
     - Direct string project references (e.g. `project(":common:data")`, `project(":app-common:designsystem")`).
   - Match type-safe accessors against the declared project paths in `settings.gradle.kts` to get the exact Gradle project path string.
   - Map dependency configuration types to arrow styles:
     - `api(...)` $\rightarrow$ Solid arrow (`-->`)
     - `implementation(...)` $\rightarrow$ Dashed arrow (`-.->`)

4. **Traverse Transitive Project Dependencies**:
   - For each direct project dependency found, inspect its respective `build.gradle.kts` to discover its project dependencies (up to 1-2 levels deep as appropriate for the diagram context).
   - Collect all discovered modules and their inter-dependencies.

5. **Determine Module Category & Style (`classDef`)**:
   Classify each module based on plugins and target platforms:

   - **KMP Library** (`:::kmp-library`, `#A280FF`): Modules applying `kotlin("multiplatform")` (e.g. `:common:*`, `:app-common:*`, `:app-feature:*`).
   - **Android Application** (`:::android-application`, `#CAFFBF`): Single-platform Android/Desktop app modules (`:app:android`, `:app:desktop`, `:wearapp:wearos`).
   - **Android Feature** (`:::android-feature`, `#FFD6A5`): Pure Android feature modules (non-KMP).
   - **Android Library** (`:::android-library`, `#9BF6FF`): Android-specific libraries (`:common-android:*`).
   - **JVM Library** (`:::jvm-library`, `#BDB2FF`): Pure JVM modules.
   - **Android Test** (`:::android-test`, `#A0C4FF`): Test/benchmark modules (`:macrobenchmark`).

6. **Construct the Mermaid Diagram**:
   Format the diagram using the ELK layout configuration, subgraph grouping, and `classDef` rules:

   ```mermaid
   ---
   config:
     layout: elk
     elk:
       nodePlacementStrategy: SIMPLE
   ---
   graph TB
     subgraph <module-short-name>
       direction TB
       :module:path[:module:path]:::kmp-library
       :dep:path[:dep:path]:::kmp-library
     end

     :module:path -.-> :dep:path
     :dep:path --> :subdep:path

   classDef android-application fill:#CAFFBF,stroke:#000,stroke-width:1px,color:#000;
   classDef android-feature fill:#FFD6A5,stroke:#000,stroke-width:1px,color:#000;
   classDef android-library fill:#9BF6FF,stroke:#000,stroke-width:1px,color:#000;
   classDef android-test fill:#A0C4FF,stroke:#000,stroke-width:1px,color:#000;
   classDef jvm-library fill:#BDB2FF,stroke:#000,stroke-width:1px,color:#000;
   classDef kmp-library fill:#A280FF,stroke:#000,stroke-width:1px,color:#000;
   classDef unknown fill:#FFADAD,stroke:#000,stroke-width:1px,color:#000;
   ```

7. **Update the Module's `README.md`**:
   - Write or update the Mermaid code block under the module header in `README.md`.
