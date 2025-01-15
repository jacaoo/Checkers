package tds

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.*
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
    var autorefresh by remember { mutableStateOf(true) }
    var showTarget by remember { mutableStateOf(true) }
    val checkrefresh = vm.hasClash && !autorefresh

    LaunchedEffect(Unit) {
        vm.autorefresh(true) //Para o programar começar a rotina de autorefresh true independentemente de se ter clicado no botão ou não
    }

    MaterialTheme {
        MenuBar {
            Menu("Game") {
                Item("Start", onClick = vm::openStartDialog)
                Item("Refresh", enabled = checkrefresh , onClick = vm::refresh)
                Item("Exit", onClick = onExit)
            }
            Menu("Options") {
                CheckboxItem("Auto Refresh", autorefresh, onCheckedChange = {
                    autorefresh = it
                    vm.autorefresh(it) } )
                CheckboxItem("Show Target", showTarget, onCheckedChange = { showTarget = it })
            }
        }
        }

            Column() {
                GridView(
                    onClickCell = { sqr: Square ->
                        if (vm.clash is ClashRun) {
                            if ((vm.clash as ClashRun).sideplayer == Player.w) {
                                val whitepiece = vm.board!!.moves[sqr]
                                if (whitepiece != null && whitepiece.player == (vm.board as BoardRun).turn) {
                                    fromSquare = if (fromSquare == sqr) null else sqr
                                }
                                if (fromSquare != null) {
                                    if (sqr in vm.board!!.possibleMoves2(fromSquare!!)) {
                                        vm.play(fromSquare!!, sqr)
                                        fromSquare = null
                                    }
                                }
                            } else {
                                val blackpiece = vm.board!!.moves[transformSquare(sqr)]
                                if (blackpiece != null && blackpiece.player == (vm.board as BoardRun).turn) {
                                    fromSquare = if (fromSquare == sqr) null else sqr
                                }
                                if (fromSquare != null) {
                                    if (transformSquare(sqr) in vm.board!!.possibleMoves2(transformSquare(fromSquare!!) )) {
                                        vm.play(transformSquare(fromSquare!!),  transformSquare(sqr))
                                        fromSquare = null
                                    }
                                }
                            }

                        }


                    },
                    selectedSQR = fromSquare,
                    clash = vm.clash,
                    target = showTarget
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