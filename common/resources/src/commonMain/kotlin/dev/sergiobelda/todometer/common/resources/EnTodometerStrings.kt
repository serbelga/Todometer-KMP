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

@LyricistStrings(languageTag = Locales.EN, default = true)
internal val EnTodometerStrings = TodometerStrings(
    about = "About",
    add = "Add",
    addElement = "Add element",
    addElementOptional = "Add element",
    addTask = "Add task",
    addTaskList = "Add task list",
    back = "Back",
    cancel = "Cancel",
    cannot_delete_this_task_list = "Can\'t delete the default task list",
    cannot_edit_this_task_list = "Can\'t edit the default task list",
    checkTask = "Check task",
    checklist = "Checklist",
    choose_tag = "Choose a Tag",
    choose_theme = "Choose theme",
    clear = "Clear",
    completed_tasks = { d -> "Completed ($d)" },
    congratulations = "Congratulations!",
    dark_theme = "Dark",
    date_time = "Date and time",
    default_task_list_name = "My tasks",
    delete_task = "Delete task",
    delete_task_list = "Delete task list",
    delete_task_list_question = "All tasks in this task list will be deleted. Do you want to continue?",
    delete_task_question = "Do you want to delete this task?",
    delete_tasks = "Delete tasks",
    delete_tasks_question = "Do you want to delete these tasks?",
    description = "Description",
    discard_task_alert_dialog_body = "Are you sure? If you exit the current process without saving, you will lose the entered information.",
    discard_task_alert_dialog_title = "Discard task",
    edit_task = "Edit task",
    edit_task_list = "Edit task list",
    enter_date_time = "Enter date and time",
    enter_description = "Enter a Description",
    enter_task_list_name = "Enter a Task list name",
    enter_task_name = "Enter a Task name",
    field_not_empty = "Field must not be empty",
    follow_system = "Follow system",
    github = "GitHub",
    light_theme = "Light",
    menu = "Menu",
    more = "More",
    name = "Name",
    no_description = "No description provided",
    no_pending_tasks = "You have no tasks to do",
    no_task_lists = "There\'s any task list",
    no_tasks = "You don\'t have added any task",
    not_pinned_task = "Not pinned task",
    ok = "OK",
    open_source_licenses = "Open-source licenses",
    optional = "Optional",
    others = "Others",
    pinned = "Pinned",
    pinned_task = "Pinned task",
    privacy_policy = "Privacy policy",
    privacy_policy_device_and_network_abuse = "Device and network abuse",
    privacy_policy_device_and_network_abuse_body = "The system does not block or interfere with another application that displays advertisements. It does not provide or provide instructions on how to hack services, software or hardware, or bypass security protections. You do not access or use a service or API in a way that violates its terms of service.",
    privacy_policy_permissions = "Permissions",
    privacy_policy_permissions_body = "The system does not require the user to grant any location or call and messaging permissions.",
    privacy_policy_public = "Public",
    privacy_policy_public_body = "The system can be used by people over 3 years old. It does not contain violent material, sexual material, or offensive language. Does not contain ads. Sensitive information for minors is not collected.",
    privacy_policy_user_data = "User data",
    privacy_policy_user_data_body = "The Todometer application does not store any user\'s data on any server since it is an application that runs in a local environment without the need for a connection. No user data is collected, transferred, or disclosed.",
    save = "Save",
    select_time = "Select time",
    selected = "Selected",
    selected_tasks = { d -> "$d selected" },
    settings = "Settings",
    task = "Task",
    task_list = "Task list",
    task_list_name_input = "Task list name",
    task_lists = "Task lists",
    task_title_input = "Task name",
    tasks = "Tasks",
    theme = "Theme",
    title = "Title",
    uncheck_task = "Uncheck task",
    you_have_completed_all_tasks = "You have completed all tasks",
    you_have_not_any_task_list = "You haven\'t any task list"
)
