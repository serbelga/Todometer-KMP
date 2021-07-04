<h1 align="center">Todometer Kotlin Multiplatform</h1></br>

<h4 align="center">
  🚧🚧🚧 WIP: https://github.com/serbelga/ToDometer to Kotlin Multiplatform
</h4>

<p align="center">
<img src="https://github.com/serbelga/ToDometer_Compose/workflows/Android%20CI/badge.svg">
</p>

<h5 align="center">
✅ A meter to-do list built with Android Jetpack based on https://cassidoo.github.io/todometer/
</h5>

## Modules

<div align="center">
<img src="./resources/arch/modules.png" width="700" />
</div>

### Backend

▶️ Run:

```
./gradlew :backend:run
```

### Desktop

▶️ Run:

```
./gradlew :desktop:run
```

## App UI Design

| <img width="300" src="./resources/Home.png"></img> | <img width="300" src="./resources/Home_BottomSheet.png"></img> | <img width="300" src="./resources/Task_Detail.png"></img> | <img width="300" src="./resources/Edit_Task.png"></img> |
|---|---|---|---|

## Tech Stack

### Android
* [Android Jetpack](https://developer.android.com/jetpack)
  * [Compose](https://developer.android.com/jetpack/compose)
  * [Android KTX](https://developer.android.com/kotlin/ktx)
  * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel)

### Backend
* [Ktor Server](https://ktor.io/)
* [Jetbrains Exposed](https://github.com/JetBrains/Exposed)

### Common
* Kotlin & Coroutines
  * Flow
* [Koin Multiplatform](https://insert-koin.io/docs/setup/v3/)
* [SQLDelight](https://cashapp.github.io/sqldelight/)
* [ktlint](https://ktlint.github.io/)
* [Ktor Client](https://ktor.io/docs/client.html)

### Desktop
* [Compose for Desktop](https://github.com/JetBrains/compose-jb)

### iOS
* Swift UI

