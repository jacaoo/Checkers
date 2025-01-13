package tds

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.*
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
import jdk.jshell.Snippet.Status
import tds.model.*
import tds.storage.*
import tds.view.*
import tds.viewmodel.AppViewModel

@Composable
@Preview
private fun FrameWindowScope.GridApp(driver: MongoDriver, onExit: () -> Unit) {

    val scope = rememberCoroutineScope()
    var vm: AppViewModel = remember { AppViewModel(driver, scope) }
    var fromSquare by remember { mutableStateOf<Square?>(null) }



    MaterialTheme {
        MenuBar {
            Menu("Game") {
                Item("Start", onClick = vm::openStartDialog)
                Item("Refresh", enabled = vm.hasClash, onClick = vm::refresh)
                Item("Exit", onClick = onExit)
            }
            Menu("Options") {
               // Item("Show targets"



            }



        }
        }
        Column() {
            GridView(
                onClickCell = { sqr: Square ->
                    if (vm.clash is ClashRun) {
                        if ((vm.clash as ClashRun).sideplayer == Player.w) {
                            val piece = vm.board!!.moves[transformSquare(sqr, true)]
                            if (piece != null && piece.player == (vm.board as BoardRun).turn) {
                                fromSquare = if (fromSquare == sqr) null else sqr
                            }
                            if (fromSquare != null) {
                                if (sqr in vm.board!!.possibleMoves2(fromSquare!!)) {
                                    vm.play(fromSquare!!, sqr)
                                    //Fazer aqui a condição de autorefresh
                                    fromSquare = null
                                }
                            }
                        } else {
                            val black = vm.board!!.moves[transformSquare(sqr, false)]
                            if (black != null && black.player == (vm.board as BoardRun).turn) {
                                fromSquare = if (fromSquare == sqr) null else sqr
                            }
                            if (fromSquare != null) {
                                if (transformSquare(sqr,false) in vm.board!!.possibleMoves2(transformSquare(fromSquare!!,false) )) {
                                    vm.play(transformSquare(fromSquare!!,false),  transformSquare(sqr,false))
                                    //Fazer aqui a condição de autorefresh
                                    fromSquare = null
                                }
                            }
                        }

                    }


                },
                selectedSQR = fromSquare,
                clash = vm.clash
                //Vai ter que receber um true ou false baseado no show target
            )
            StatusBar(vm.clash)
        }
        vm.inputName?.let {
            StartDialog(
                type = it,
                onCancel = vm::closeStartOrJoinDialog,
                onAction = vm::start
            )
        }
        vm.errorMessage?.let { ErrorDialog(it, onClose = vm::hideError) }
}

fun main() = MongoDriver("checkers").use { driver ->
    application {
        Window(
            onCloseRequest = ::exitApplication,
            state = WindowState(size = DpSize.Unspecified),
            title = "Damas"
        ) {
            GridApp(driver, ::exitApplication)
        }
    }
}