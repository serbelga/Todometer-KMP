/*
 * Copyright 2024 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sergiobelda.todometer.common.resources

import cafe.adriel.lyricist.LyricistStrings

@Suppress("ktlint:standard:max-line-length")
@LyricistStrings(languageTag = Locales.EN, default = true)
internal val EnTodometerStrings =
    TodometerStrings(
        about = "About",
        add = "Add",
        addElement = "Add element",
        addElementOptional = "Add element",
        addTask = "Add task",
        addTaskList = "Add task list",
        back = "Back",
        cancel = "Cancel",
        cannotDeleteThisTaskList = "Can\'t delete the default task list",
        cannotEditThisTaskList = "Can\'t edit the default task list",
        checkTask = "Check task",
        checklist = "Checklist",
        chooseTag = "Choose a Tag",
        chooseTheme = "Choose theme",
        clear = "Clear",
        completedTasks = { d -> "Completed ($d)" },
        congratulations = "Congratulations!",
        darkTheme = "Dark",
        dateTime = "Date and time",
        defaultTaskListName = "My tasks",
        deleteTask = "Delete task",
        deleteTaskList = "Delete task list",
        deleteTaskListQuestion = "All tasks in this task list will be deleted. Do you want to continue?",
        deleteTaskQuestion = "Do you want to delete this task?",
        deleteTasks = "Delete tasks",
        deleteTasksQuestion = "Do you want to delete these tasks?",
        description = "Description",
        discardTaskAlertDialogBody = "Are you sure? If you exit the current process without saving, you will lose the entered information.",
        discardTaskAlertDialogTitle = "Discard task",
        editTask = "Edit task",
        editTaskList = "Edit task list",
        enterDateTime = "Enter date and time",
        enterDescription = "Enter a Description",
        enterTaskListName = "Enter a Task list name",
        enterTaskName = "Enter a Task name",
        fieldNotEmpty = "Field must not be empty",
        followSystem = "Follow system",
        github = "GitHub",
        lightTheme = "Light",
        menu = "Menu",
        more = "More",
        name = "Name",
        noDescription = "No description provided",
        noPendingTasks = "You have no tasks to do",
        noTaskLists = "There\'s any task list",
        noTasks = "You don\'t have added any task",
        notPinnedTask = "Not pinned task",
        ok = "OK",
        openSourceLicenses = "Open-source licenses",
        optional = "Optional",
        others = "Others",
        pinned = "Pinned",
        pinnedTask = "Pinned task",
        privacyPolicy = "Privacy policy",
        privacyPolicyDeviceAndNetworkAbuse = "Device and network abuse",
        privacyPolicyDeviceAndNetworkAbuseBody = "The system does not block or interfere with another application that displays advertisements. It does not provide or provide instructions on how to hack services, software or hardware, or bypass security protections. You do not access or use a service or API in a way that violates its terms of service.",
        privacyPolicyPermissions = "Permissions",
        privacyPolicyPermissionsBody = "The system does not require the user to grant any location or call and messaging permissions.",
        privacyPolicyPublic = "Public",
        privacyPolicyPublicBody = "The system can be used by people over 3 years old. It does not contain violent material, sexual material, or offensive language. Does not contain ads. Sensitive information for minors is not collected.",
        privacyPolicyUserData = "User data",
        privacyPolicyUserDataBody = "The Todometer application does not store any user\'s data on any server since it is an application that runs in a local environment without the need for a connection. No user data is collected, transferred, or disclosed.",
        save = "Save",
        selectTime = "Select time",
        selected = "Selected",
        selectedTasks = { d -> "$d selected" },
        settings = "Settings",
        task = "Task",
        taskList = "Task list",
        taskListNameInput = "Task list name",
        taskLists = "Task lists",
        taskTitleInput = "Task name",
        tasks = "Tasks",
        theme = "Theme",
        title = "Title",
        uncheckTask = "Uncheck task",
        youHaveCompletedAllTasks = "You have completed all tasks",
        youHaveNotAnyTaskList = "You haven\'t any task list",
    )
