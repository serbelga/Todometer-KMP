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

data class TodometerStrings(
    val about: String,
    val add: String,
    val addElement: String,
    val addElementOptional: String,
    val addTask: String,
    val addTaskList: String,
    val appName: String = "Todometer",
    val back: String,
    val cancel: String,
    val cannotDeleteThisTaskList: String,
    val cannotEditThisTaskList: String,
    val checkTask: String,
    val checklist: String,
    val chooseTag: String,
    val chooseTheme: String,
    val clear: String,
    val completedTasks: (d: Int) -> String,
    val congratulations: String,
    val darkTheme: String,
    val dateTime: String,
    val defaultTaskListName: String,
    val deleteTask: String,
    val deleteTaskList: String,
    val deleteTaskListQuestion: String,
    val deleteTaskQuestion: String,
    val deleteTasks: String,
    val deleteTasksQuestion: String,
    val description: String,
    val discardTaskAlertDialogBody: String,
    val discardTaskAlertDialogTitle: String,
    val editTask: String,
    val editTaskList: String,
    val enterDateTime: String,
    val enterDescription: String,
    val enterTaskListName: String,
    val enterTaskName: String,
    val fieldNotEmpty: String,
    val followSystem: String,
    val github: String = "GitHub",
    val lightTheme: String,
    val menu: String,
    val more: String,
    val name: String,
    val noDescription: String,
    val noPendingTasks: String,
    val noTaskLists: String,
    val noTasks: String,
    val notPinnedTask: String,
    val ok: String,
    val openSourceLicenses: String,
    val optional: String,
    val others: String,
    val pinned: String,
    val pinnedTask: String,
    val privacyPolicy: String,
    val privacyPolicyDeviceAndNetworkAbuse: String,
    val privacyPolicyDeviceAndNetworkAbuseBody: String,
    val privacyPolicyPermissions: String,
    val privacyPolicyPermissionsBody: String,
    val privacyPolicyPublic: String,
    val privacyPolicyPublicBody: String,
    val privacyPolicyUserData: String,
    val privacyPolicyUserDataBody: String,
    val save: String,
    val selectTime: String,
    val selected: String,
    val selectedTasks: (d: Int) -> String,
    val settings: String,
    val task: String,
    val taskList: String,
    val taskListNameInput: String,
    val taskLists: String,
    val taskTitleInput: String,
    val tasks: String,
    val theme: String,
    val title: String,
    val uncheckTask: String,
    val youHaveCompletedAllTasks: String,
    val youHaveNotAnyTaskList: String,
)

internal val Strings: Map<String, TodometerStrings> =
    mapOf(
        Locales.CA to CaTodometerStrings,
        Locales.EN to EnTodometerStrings,
        Locales.ES to EsTodometerStrings,
    )
