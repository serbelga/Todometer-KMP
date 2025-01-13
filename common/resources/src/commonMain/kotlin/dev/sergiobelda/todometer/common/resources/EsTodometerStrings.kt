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

@Suppress("MaxLineLength")
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
    cannotDeleteThisTaskList = "No se puede eliminar la lista predeterminada",
    cannotEditThisTaskList = "No se puede editar la lista predeterminada",
    checkTask = "Marcar tarea",
    checklist = "Checklist",
    chooseTag = "Elige una etiqueta",
    chooseTheme = "Elegir un tema",
    clear = "Limpiar",
    completedTasks = { d -> "Completadas ($d)" },
    congratulations = "Enhorabuena!",
    darkTheme = "Oscuro",
    dateTime = "Fecha y hora",
    defaultTaskListName = "Mis tareas",
    deleteTask = "Eliminar tarea",
    deleteTaskList = "Eliminar lista de tareas",
    deleteTaskListQuestion = "Se eliminarán todas las tareas de esta lista de tareas. ¿Desea continuar?",
    deleteTaskQuestion = "¿Desea eliminar esta tarea?",
    deleteTasks = "Eliminar tareas",
    deleteTasksQuestion = "¿Desea eliminar estas tareas?",
    description = "Descripción",
    discardTaskAlertDialogBody = "Estás seguro? Si sales del proceso actual sin guardar, perderás la información introducida.",
    discardTaskAlertDialogTitle = "Descartar tarea",
    editTask = "Editar tarea",
    editTaskList = "Editar lista de tareas",
    enterDateTime = "Añadir fecha y hora",
    enterDescription = "Introduce una descripción",
    enterTaskListName = "Introduce un nombre para la lista de tareas",
    enterTaskName = "Introduce el nombre de la tarea",
    fieldNotEmpty = "El campo no puede quedar vacío",
    followSystem = "Seguir sistema",
    lightTheme = "Claro",
    menu = "Menú",
    more = "Més",
    name = "Nombre",
    noDescription = "No hay descripción",
    noPendingTasks = "No tienes ninguna tarea pendiente",
    noTaskLists = "No hay listas de tareas",
    noTasks = "No has añadido ninguna tarea",
    notPinnedTask = "Tarea no fijada",
    ok = "OK",
    openSourceLicenses = "Licencias de código abierto",
    optional = "Opcional",
    others = "Otras",
    pinned = "Fijadas",
    pinnedTask = "Tarea fijada",
    privacyPolicy = "Política de privacidad",
    privacyPolicyDeviceAndNetworkAbuse = "Abuso de dispositivos y redes",
    privacyPolicyDeviceAndNetworkAbuseBody = "El sistema no bloquea ni interfiere con otra aplicación en la que se muestren anuncios. No proporciona ni proporciona instrucciones sobre cómo piratear servicios, software o hardware, o evitar las protecciones de seguridad.",
    privacyPolicyPermissions = "Permisos",
    privacyPolicyPermissionsBody = "El sistema no requiere que el usuario conceda ningún permiso de ubicación o llamada y mensajería.",
    privacyPolicyPublic = "Público",
    privacyPolicyPublicBody = "El sistema puede ser utilizado por personas mayores de 3 años. No contiene material violento, material sexual o lenguaje ofensivo. No contiene anuncios. No se recoge información sensible de menores de edad.",
    privacyPolicyUserData = "Datos del usuario",
    privacyPolicyUserDataBody = "La aplicación Todometer no almacena ningún dato de usuario en ningún servidor ya que es una aplicación que se ejecuta en un entorno local sin necesidad de conexión. No se recoge, transfiere o divulga ningún dato del usuario.",
    save = "Guardar",
    selectTime = "Seleccionar hora",
    selected = "Seleccionado",
    selectedTasks = { d -> "$d seleccionadas" },
    settings = "Ajustes",
    task = "Tarea",
    taskList = "Lista de tareas",
    taskListNameInput = "Nombre de la lista de tareas",
    taskLists = "Listas de tareas",
    taskTitleInput = "Nombre de la tarea",
    tasks = "Tareas",
    theme = "Tema",
    title = "Título",
    uncheckTask = "Desmarcar tarea",
    youHaveCompletedAllTasks = "Has completado todas las tareas",
    youHaveNotAnyTaskList = "No tienes ninguna lista de tareas",
)
