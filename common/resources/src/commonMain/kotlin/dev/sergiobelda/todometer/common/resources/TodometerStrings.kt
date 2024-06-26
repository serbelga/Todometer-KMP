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
    val cannot_delete_this_task_list: String,
    val cannot_edit_this_task_list: String,
    val checkTask: String,
    val checklist: String,
    val choose_tag: String,
    val choose_theme: String,
    val clear: String,
    val completed_tasks: (d: Int) -> String,
    val congratulations: String,
    val dark_theme: String,
    val date_time: String,
    val default_task_list_name: String,
    val delete_task: String,
    val delete_task_list: String,
    val delete_task_list_question: String,
    val delete_task_question: String,
    val delete_tasks: String,
    val delete_tasks_question: String,
    val description: String,
    val discard_task_alert_dialog_body: String,
    val discard_task_alert_dialog_title: String,
    val edit_task: String,
    val edit_task_list: String,
    val enter_date_time: String,
    val enter_description: String,
    val enter_task_list_name: String,
    val enter_task_name: String,
    val field_not_empty: String,
    val follow_system: String,
    val github: String = "GitHub",
    val light_theme: String,
    val menu: String,
    val more: String,
    val name: String,
    val no_description: String,
    val no_pending_tasks: String,
    val no_task_lists: String,
    val no_tasks: String,
    val not_pinned_task: String,
    val ok: String,
    val open_source_licenses: String,
    val optional: String,
    val others: String,
    val pinned: String,
    val pinned_task: String,
    val privacy_policy: String,
    val privacy_policy_device_and_network_abuse: String,
    val privacy_policy_device_and_network_abuse_body: String,
    val privacy_policy_permissions: String,
    val privacy_policy_permissions_body: String,
    val privacy_policy_public: String,
    val privacy_policy_public_body: String,
    val privacy_policy_user_data: String,
    val privacy_policy_user_data_body: String,
    val save: String,
    val select_time: String,
    val selected: String,
    val selected_tasks: (d: Int) -> String,
    val settings: String,
    val task: String,
    val task_list: String,
    val task_list_name_input: String,
    val task_lists: String,
    val task_title_input: String,
    val tasks: String,
    val theme: String,
    val title: String,
    val uncheck_task: String,
    val you_have_completed_all_tasks: String,
    val you_have_not_any_task_list: String
)

internal val Strings: Map<String, TodometerStrings> = mapOf(
    Locales.CA to CaTodometerStrings,
    Locales.EN to EnTodometerStrings,
    Locales.ES to EsTodometerStrings
)
