import com.sergiobelda.todometer.common.App
import androidx.compose.desktop.Window
import ui.theme.ToDometerTheme

fun main() = Window {
    ToDometerTheme {
        App()
    }
}