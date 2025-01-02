package tds

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import tds.model.*
import tds.view.GridView


@Composable
@Preview
fun GridApp() {
    var board: Board by remember { mutableStateOf(BoardRun(initialBoard().moves, turn= Player.w)) }
    var selectedSquare by remember { mutableStateOf<Square?>(null) }
    if(board is BoardRun){
        GridView(
            board.moves,
            onClickCell = { sqr: Square ->
                val piece = board.moves[sqr]
                if (piece != null && piece.player == (board as BoardRun).turn) {
                    selectedSquare = if (selectedSquare == sqr) null else sqr

                }
            },
            toOnClickCell = { // Isto vai chamar o pla

            } ,
            selectedSQR = selectedSquare
        )

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