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

@LyricistStrings(languageTag = Locales.ES)
internal val EsTodometerStrings = TodometerStrings(
    about = "Acerca de",
    add = "Añadir",
    addElement = "Añadir elemento",
    addElementOptional = "Añadir elemento",
    addTask = "Añadir tarea",
    addTaskList = "Añadir lista de tareas",
    back = "Atrás",
    cancel = "Cancelar",
    cannot_delete_this_task_list = "No se puede eliminar la lista predeterminada",
    cannot_edit_this_task_list = "No se puede editar la lista predeterminada",
    checkTask = "Marcar tarea",
    checklist = "Checklist",
    choose_tag = "Elige una etiqueta",
    choose_theme = "Elegir un tema",
    clear = "Limpiar",
    completed_tasks = { d -> "Completadas ($d)" },
    congratulations = "Enhorabuena!",
    dark_theme = "Oscuro",
    date_time = "Fecha y hora",
    default_task_list_name = "Mis tareas",
    delete_task = "Eliminar tarea",
    delete_task_list = "Eliminar lista de tareas",
    delete_task_list_question = "Se eliminarán todas las tareas de esta lista de tareas. ¿Desea continuar?",
    delete_task_question = "¿Desea eliminar esta tarea?",
    delete_tasks = "Eliminar tareas",
    delete_tasks_question = "¿Desea eliminar estas tareas?",
    description = "Descripción",
    discard_task_alert_dialog_body = "Estás seguro? Si sales del proceso actual sin guardar, perderás la información introducida.",
    discard_task_alert_dialog_title = "Descartar tarea",
    edit_task = "Editar tarea",
    edit_task_list = "Editar lista de tareas",
    enter_date_time = "Añadir fecha y hora",
    enter_description = "Introduce una descripción",
    enter_task_list_name = "Introduce un nombre para la lista de tareas",
    enter_task_name = "Introduce el nombre de la tarea",
    field_not_empty = "El campo no puede quedar vacío",
    follow_system = "Seguir sistema",
    light_theme = "Claro",
    menu = "Menú",
    more = "Més",
    name = "Nombre",
    no_description = "No hay descripción",
    no_pending_tasks = "No tienes ninguna tarea pendiente",
    no_task_lists = "No hay listas de tareas",
    no_tasks = "No has añadido ninguna tarea",
    not_pinned_task = "Tarea no fijada",
    ok = "OK",
    open_source_licenses = "Licencias de código abierto",
    optional = "Opcional",
    others = "Otras",
    pinned = "Fijadas",
    pinned_task = "Tarea fijada",
    privacy_policy = "Política de privacidad",
    privacy_policy_device_and_network_abuse = "Abuso de dispositivos y redes",
    privacy_policy_device_and_network_abuse_body = "El sistema no bloquea ni interfiere con otra aplicación en la que se muestren anuncios. No proporciona ni proporciona instrucciones sobre cómo piratear servicios, software o hardware, o evitar las protecciones de seguridad.",
    privacy_policy_permissions = "Permisos",
    privacy_policy_permissions_body = "El sistema no requiere que el usuario conceda ningún permiso de ubicación o llamada y mensajería.",
    privacy_policy_public = "Público",
    privacy_policy_public_body = "El sistema puede ser utilizado por personas mayores de 3 años. No contiene material violento, material sexual o lenguaje ofensivo. No contiene anuncios. No se recoge información sensible de menores de edad.",
    privacy_policy_user_data = "Datos del usuario",
    privacy_policy_user_data_body = "La aplicación Todometer no almacena ningún dato de usuario en ningún servidor ya que es una aplicación que se ejecuta en un entorno local sin necesidad de conexión. No se recoge, transfiere o divulga ningún dato del usuario.",
    save = "Guardar",
    select_time = "Seleccionar hora",
    selected = "Seleccionado",
    selected_tasks = { d -> "$d seleccionadas" },
    settings = "Ajustes",
    task = "Tarea",
    task_list = "Lista de tareas",
    task_list_name_input = "Nombre de la lista de tareas",
    task_lists = "Listas de tareas",
    task_title_input = "Nombre de la tarea",
    tasks = "Tareas",
    theme = "Tema",
    title = "Título",
    uncheck_task = "Desmarcar tarea",
    you_have_completed_all_tasks = "Has completado todas las tareas",
    you_have_not_any_task_list = "No tienes ninguna lista de tareas"
)
