import androidx.compose.animation.Crossfade
import androidx.compose.desktop.Window
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.sergiobelda.todometer.common.di.initKoin
import ui.home.HomeScreen
import ui.task.AddTaskScreen
import ui.theme.ToDometerTheme

val koin = initKoin().koin

fun main() = Window(title = "ToDo_meter") {
    var currentPage by remember { mutableStateOf(Screen.Home) }
    val navigateToHome: () -> Unit = {
        currentPage = Screen.Home
    }
    val navigateToAddTask: () -> Unit = {
        currentPage = Screen.AddTask
    }
    ToDometerTheme(darkTheme = true) {
        Crossfade(currentPage) { screen ->
            when (screen) {
                Screen.Home -> HomeScreen(navigateToAddTask)
                Screen.AddTask -> AddTaskScreen(navigateToHome)
            }
        }
    }
}
