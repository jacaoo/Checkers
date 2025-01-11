package tds

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.window.FrameWindowScope
import androidx.compose.ui.window.MenuBar
import tds.model.*
import tds.storage.*
import tds.viewmodel.AppViewModel

@Composable
@Preview
private fun FrameWindowScope.GridApp(driver: MongoDriver, onExit: () -> Unit) {

    val scope = rememberCoroutineScope()
    var vm: AppViewModel = remember { AppViewModel(driver, scope) }

    MaterialTheme {
        MenuBar {
            Menu("Game") {
               // Item("Start", onClick = vm::start)
            }
            Menu("Options") {

            }
        }
    }

}