package tds.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import tds.model.*


val CELL_SIZE = 100.dp
val LINE_WIDTH = 5.dp
val GRID_WIDTH = CELL_SIZE * BOARD_DIM + LINE_WIDTH * (BOARD_DIM - 1)

@Composable
fun GridView(moves: Moves, onClickCell: (Square) -> Unit) {
    Column {
        repeat(BOARD_DIM) { lin ->
            Row {
                repeat(BOARD_DIM) { col ->
                    val square = Square(Row(lin), Column(col))
                    val color = if (square.black) Color.Gray else Color.White
                    Box(
                        modifier = Modifier.size(CELL_SIZE).background(color)
                    ) {
                        val piece = moves[square]
                        PieceView(size = CELL_SIZE, piece = piece)
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun GridViewPreview() {
    val board = initialBoard()
    GridView(moves = board.moves) {}
}