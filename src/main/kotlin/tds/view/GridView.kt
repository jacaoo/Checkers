package tds.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tds.model.*


val CELL_SIZE = 55.dp
val LINE_WIDTH = 7.dp
val GRID_WIDTH = CELL_SIZE * BOARD_DIM + LINE_WIDTH * (BOARD_DIM - 1)


@Composable
fun GridView(board: Board, onClickCell: (Square)->Unit, selectedSQR : Square?) {  //É passado aqui um board para não ter que haver mudança nas funções feitas na parte anterior
    //GRID
    Box(
        Modifier
            .size(GRID_WIDTH)
            .background(BACKGROUND_COLOR),

        //contentAlignment = Alignment.Center
    )
    {
        //LINHA DE LETRAS
        Row(
            Modifier
            .padding(start = CELL_SIZE - LINE_WIDTH), // Ajustar alinhamento inicial
        horizontalArrangement = Arrangement.spacedBy(CELL_SIZE - 10.dp)// Simplesmente para fazer o espaço entre cada letra
        ){
            for(i in 0..<BOARD_DIM) {
                val actualLetter = ('a' + i).toString()
                Text(
                    text = actualLetter,
                    fontSize = 18.sp,
                )
            }
        }
        //COLUNA DE NÚMEROS
        Column(
            Modifier
                .padding(top = CELL_SIZE - 10.dp) // Ajustar o número inicial
                .offset(7.dp,0.dp), // Para centrar os números na coluna
            verticalArrangement = Arrangement.spacedBy(CELL_SIZE - 25.dp)
        ){
            for (i in BOARD_DIM downTo 1) {
                val actualNumb = i.toString()
                Text(
                    text = actualNumb,
                    fontSize = 18.sp,
                )
            }
        }
        //TABULEIRO
        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ){
            repeat(BOARD_DIM) { lin ->

                Row {
                    repeat(BOARD_DIM) { col ->
                        val square = Square(Row(lin), Column(col))
                        val color = if (square.black) Color(65,65,65,255) else Color(200,200,200,255)
                        Box(
                            modifier = Modifier
                                .size(CELL_SIZE)
                                .background(color)
                                .border(
                                    width = 2.dp,
                                    color = if (square == selectedSQR) Color.Red
                                    else Color.Transparent
                                )
                                .clickable { onClickCell(square) },
                            contentAlignment = Alignment.Center,

                        )
                        {
                            if (selectedSQR != null && board.moves.values.toList()[selectedSQR.index] != null) {
                                if (square in board.possibleMoves2(selectedSQR)) {
                                    Box(
                                            Modifier
                                                .clip(CircleShape)
                                                .size(35.dp)
                                                .background(Color(88, 132, 52))
                                        ) {
                                        }
                                    }
                                }

                            val piece = board.moves[square]
                            PieceView(
                                size = CELL_SIZE,
                                piece = piece,
                                onClick = { onClickCell(square) }
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
@Preview
fun GridView(){
    val emptymoves = emptyList<Square>().zip(emptyList<Piece>()).toMap() // Para ver o Grid inicial sem peças
    val board = initialBoard() // Para ver o Grid com as peças
    GridView(board, {}, null)
}




