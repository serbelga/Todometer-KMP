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

@LyricistStrings(languageTag = Locales.CA)
internal val CaTodometerStrings = TodometerStrings(
    about = "Sobre",
    add = "Afegir",
    addElement = "Afegir element",
    addElementOptional = "Afegir element",
    addTask = "Afegir tasca",
    addTaskList = "Afegir llista de tasques",
    back = "Enrere",
    cancel = "Cancelar",
    cannot_delete_this_task_list = "No es pot eliminar la llista predeterminada",
    cannot_edit_this_task_list = "No es pot editar la llista predeterminada",
    checkTask = "Marcar tasca",
    checklist = "Checklist",
    choose_tag = "Tria una etiqueta",
    choose_theme = "Elegir un tema",
    clear = "Netejar",
    completed_tasks = { d -> "Completades ($d)" },
    congratulations = "Enhorabona!",
    dark_theme = "Obscur",
    date_time = "Data i hora",
    default_task_list_name = "Tasques",
    delete_task = "Eliminar tasca",
    delete_task_list = "Eliminar llista de tasques",
    delete_task_list_question = "S\'eliminaran totes les tasques d\'aquesta llista de tasques. Vols continuar?",
    delete_task_question = "Vols eliminar aquesta tasca?",
    delete_tasks = "Eliminar tasques",
    delete_tasks_question = "Vols eliminar aquestes tasques?",
    description = "Descripció",
    discard_task_alert_dialog_body = "Estàs segur? Si ixes del procés actual sense guardar, perdràs la informació introduïda.",
    discard_task_alert_dialog_title = "Descartar tasca",
    edit_task = "Editar tasca",
    edit_task_list = "Editar llista de tasques",
    enter_date_time = "Introdueix data i hora",
    enter_description = "Introdueix una descripció",
    enter_task_list_name = "Introdueix el nom de la llista de tasques",
    enter_task_name = "Introdueix el nom de la tasca",
    field_not_empty = "El camp no pot quedar buit",
    follow_system = "Seguir sistema",
    light_theme = "Clar",
    menu = "Menú",
    more = "Més",
    name = "Nom",
    no_description = "No hi ha descripció",
    no_pending_tasks = "No tens cap tasca pendent",
    no_task_lists = "No hi han llistes de tasques",
    no_tasks = "No has afegit cap tasca",
    not_pinned_task = "Tasca no fixada",
    ok = "OK",
    open_source_licenses = "Llicències de codi obert",
    optional = "Opcional",
    others = "Altres",
    pinned = "Fixades",
    pinned_task = "Tasca fixada",
    privacy_policy = "Política de privacitat",
    privacy_policy_device_and_network_abuse = "Abús de dispositius i xarxes",
    privacy_policy_device_and_network_abuse_body = "El sistema no bloqueja ni interfereix amb una altra aplicació en la que es mostrin No proporciona ni proporciona instruccions sobre com piratejar serveis, programari o maquinari, o evitar les proteccions de seguretat.",
    privacy_policy_permissions = "Permisos",
    privacy_policy_permissions_body = "El sistema no requereix que l\'usuari concedeixi cap permís d\'ubicació o trucada i missatgeria.",
    privacy_policy_public = "Públic",
    privacy_policy_public_body = "El sistema pot ser utilitzat per persones més grans de 3 anys. No conté material violent, material sexual o llenguatge ofensiu. No conté anuncis. No s\'hi recull informació sensible de menors d\'edat.",
    privacy_policy_user_data = "Dades de l\'usuari",
    privacy_policy_user_data_body = "L\'aplicació Todometer no emmagatzema cap dada d\'usuari a cap servidor ja que és una aplicació que s\'executa en un entorn local sense necessitat de connexió. No es recull, es transfereix o es divulga cap dada de l\'usuari.",
    save = "Guardar",
    select_time = "Seleccionar hora",
    selected = "Seleccionat",
    selected_tasks = { d -> "$d seleccionades" },
    settings = "Configuració",
    task = "Tasca",
    task_list = "Llista de tasques",
    task_list_name_input = "Nom de la llista de tasques",
    task_lists = "Llistes de tasques",
    task_title_input = "Nom de la tasca",
    tasks = "Tasques",
    theme = "Tema",
    title = "Títol",
    uncheck_task = "Desmarcar tasca",
    you_have_completed_all_tasks = "Has completat totes les tasques",
    you_have_not_any_task_list = "No tens cap llista de tasques"
)
