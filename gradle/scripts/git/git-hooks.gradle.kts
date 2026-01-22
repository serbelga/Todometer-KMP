tasks.register("copyPreCommitGitHook", Copy::class) {
    from(File(rootProject.rootDir, "gradle/scripts/git/hooks/pre-commit"))
    into { File(rootProject.rootDir, ".git/hooks") }
    filePermissions { unix("774") }
}
