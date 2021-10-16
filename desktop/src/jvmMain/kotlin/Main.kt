import androidx.compose.animation.Crossfade
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.sergiobelda.todometer.common.di.initKoin
import com.sergiobelda.todometer.compose.ui.icons.iconToDometer
import ui.home.HomeScreen
import ui.task.AddTaskScreen
import ui.theme.ToDometerTheme

val koin = initKoin().koin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ToDometer",
        state = WindowState(
            position = WindowPosition.Aligned(Alignment.Center)
        ),
        icon = iconToDometer()
    ) {
        var currentPage by remember { mutableStateOf(Screen.Home) }
        val navigateToHome: () -> Unit = {
            currentPage = Screen.Home
        }
        val navigateToAddTask: () -> Unit = {
            currentPage = Screen.AddTask
        }
        ToDometerTheme(darkTheme = isSystemInDarkTheme()) {
            Crossfade(currentPage) { screen ->
                when (screen) {
                    Screen.Home -> HomeScreen(navigateToAddTask)
                    Screen.AddTask -> AddTaskScreen(navigateToHome)
                }
            }
        }
    }
}
