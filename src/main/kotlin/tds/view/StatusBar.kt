package tds.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.identityHashCode
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import tds.model.*
import tds.storage.*

val BACKGROUND_COLOR = Color(180, 100, 25) // cor laranja

@Composable
fun BoardWithBorder(board: Board) {
    Column(
        modifier = Modifier
            .fillMaxSize() // Preenche toda a tela
            .background(BACKGROUND_COLOR) // Define o fundo laranja
            .padding(30.dp) // Adiciona padding para afastar do limite da tela
    ) {
        Row {
            Spacer(modifier = Modifier.width(CELL_SIZE/2 + 7.dp)) // Espaço para alinhar com os números
            repeat(BOARD_DIM) { i ->
                Text(
                    text = ('a' + i).toString(),
                    fontSize = 25.sp,
                )
                Spacer(modifier = Modifier.width(CELL_SIZE - 10.dp)) // Espaço para alinhar com os números
            }
        }
        Row {
            Column {
                repeat(BOARD_DIM) { i ->
                    Text(
                        text = (BOARD_DIM - i).toString(),
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp)
                    )
                }
            }
            Box {
                GridView(moves = board.moves) {
                }
            }
        }
    }
}

@Composable
@Preview
fun PreviewBoardWithBorder() {
    val board = initialBoard()
    BoardWithBorder(board)
}