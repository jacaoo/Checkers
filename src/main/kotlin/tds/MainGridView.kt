package tds

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import tds.model.*
import tds.view.GridView
import tds.view.StatusBar


@Composable
@Preview
fun GridApp() {
    var board: Board by remember { mutableStateOf(BoardRun(initialBoard().moves, turn= Player.w)) }
    var fromSquare by remember { mutableStateOf<Square?>(null) }

    if(board is BoardRun){
        MaterialTheme {
            Column() {

                GridView(
                    board,
                    onClickCell = { sqr: Square ->
                        val piece = board.moves[sqr]
                        if (piece != null && piece.player == (board as BoardRun).turn) {
                            fromSquare = if (fromSquare == sqr) null else sqr
                        }
                        if (fromSquare != null) {
                            if (sqr in board.possibleMoves2(fromSquare!!)) {
                                board = board.play(fromSquare!!, sqr)
                                fromSquare = null
                            }
                        }
                    },
                    selectedSQR = fromSquare
                )
                StatusBar(Game())
            }
        }

    }
}


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(size = DpSize.Unspecified)
    ) {
        GridApp()
    }
}