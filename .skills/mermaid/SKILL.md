---
name: dependency-graph-to-mermaid
description: Generates, updates, or batch-synchronizes Mermaid module dependency diagrams in README.md files by inspecting Gradle build files (build.gradle.kts) and settings.gradle.kts directly.
metadata:
  author: todometer
  version: "4.0"
---

# Module Dependency Graph to Mermaid Skill

Use this skill to generate, update, or batch-synchronize Mermaid module dependency diagrams directly in module `README.md` files by inspecting Gradle build scripts (`build.gradle.kts`) and project declarations in `settings.gradle.kts` without relying on SVG files.

## Standard Palette & Class Definitions

All Mermaid diagrams in the codebase must use these centralized `classDef` definitions at the end of the diagram block:

```mermaid
classDef android-application fill:#CAFFBF,stroke:#000,stroke-width:1px,color:#000;
classDef android-feature fill:#FFD6A5,stroke:#000,stroke-width:1px,color:#000;
classDef android-library fill:#9BF6FF,stroke:#000,stroke-width:1px,color:#000;
classDef android-test fill:#A0C4FF,stroke:#000,stroke-width:1px,color:#000;
classDef jvm-library fill:#BDB2FF,stroke:#000,stroke-width:1px,color:#000;
classDef kmp-library fill:#A280FF,stroke:#000,stroke-width:1px,color:#000;
classDef unknown fill:#FFADAD,stroke:#000,stroke-width:1px,color:#000;
```

---

## Workflow for Single Module

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

   - **KMP Library** (`:::kmp-library`, `#A280FF`): Modules applying `kotlin("multiplatform")` (e.g. `:common:*`, `:app-common:*`, `:app-feature:*`, `:app:shared`).
   - **Android Application** (`:::android-application`, `#CAFFBF`): Single-platform Android application modules (`:app:android`, `:wearapp:wearos`).
   - **JVM App / Library** (`:::jvm-library`, `#BDB2FF`): Desktop app and JVM modules (`:app:desktop`).
   - **Android Feature** (`:::android-feature`, `#FFD6A5`): Pure Android feature modules (non-KMP).
   - **Android Library** (`:::android-library`, `#9BF6FF`): Android-specific libraries (`:common-android:*`).
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

---

## Batch Operations / Syncing All Modules

When asked to **generate, update, or synchronize Mermaid diagrams across the entire project**:

1. **Iterate All Project Modules**:
   - Read `settings.gradle.kts` to obtain all included project paths.
   - For each project path, map it to its filesystem directory (e.g., `:app-feature:home` $\rightarrow$ `app-feature/home`).

2. **Process Each Module**:
   - Execute Steps 2–7 for each module.
   - If the module's `README.md` file does not exist, create it with the module header and the generated Mermaid diagram block.
   - If `README.md` exists, replace its old diagram (`<img src="...">` or previous ````mermaid```` block) with the new diagram block.

3. **Style-Only Sync (Bulk `classDef` Update)**:
   - If only the `classDef` definitions change, locate all `README.md` files in the repository.
   - Replace the `classDef` lines in all ````mermaid```` blocks with the updated standard palette definitions from this Skill.
