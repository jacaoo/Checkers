package tds.view

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import tds.model.*


val CELL_SIZE = 55.dp
val LINE_WIDTH = 7.dp
val GRID_WIDTH = CELL_SIZE * BOARD_DIM + LINE_WIDTH * (BOARD_DIM - 1)

@Composable
fun GridView(onClickCell: (Square) -> Unit, selectedSQR: Square?, clash: Clash, target: Boolean) {
    Column(
        Modifier
            .width(GRID_WIDTH)
            .height(GRID_WIDTH- 25.dp)
            .background(BACKGROUND_COLOR)
    ){
        Row(
            Modifier
                .padding(start = CELL_SIZE- 10.dp)
                .width(GRID_WIDTH),
            //verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(CELL_SIZE - 10.dp)// Simplesmente para fazer o espaço entre cada letra
        ) {

            for (i in 0..<BOARD_DIM) {
                val actualLetter = ('a' + i).toString()
                Text(
                    text = actualLetter,
                    fontSize = 18.sp,
                    //padding = (start = CELL_SIZE - LINE_WIDTH)
                )
            }

        }
        Column (
            Modifier
                .offset(5.dp, 0.dp)
        ) {
            repeat(BOARD_DIM) { lin ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (clash is ClashRun) {
                        if (clash.sideplayer == Player.w) {
                            Text(
                                text = (BOARD_DIM - lin).toString(),
                                fontSize = 18.sp,
                                modifier = Modifier
                                    //.width(CELL_SIZE - 5.dp)
                                    .padding(end = 10.dp) // Para ajustar o alinhamento
                            )
                            repeat(BOARD_DIM) { col ->

                                val square = Square(Row(lin), Column(col))
                                val color = if (square.black) Color(65, 65, 65, 255) else Color(200, 200, 200, 255)
                                Box(
                                    modifier = Modifier
                                        .size(CELL_SIZE)
                                        .background(color)
                                        .border(
                                            width = 2.dp,
                                            color = if (square == selectedSQR &&
                                                clash.game.board.moves[square]?.player == clash.sideplayer &&
                                                clash.sideplayer == clash.game.board.player) Color.Red
                                            else Color.Transparent)
                                        .clickable { onClickCell(square) },

                                    contentAlignment = Alignment.Center,
                                ) {
                                    if (selectedSQR != null && clash.game.board.moves.values.toList()[selectedSQR.index] != null ) {
                                        if (square in clash.game.board.possibleMoves2(selectedSQR) && clash.sideplayer == clash.game.board.player && target) {
                                            Box(
                                                Modifier
                                                    .clip(CircleShape)
                                                    .size(35.dp)
                                                    .background(Color(88, 132, 52))
                                            ) {
                                            }
                                        }
                                    }
                                    PieceView(
                                        size = CELL_SIZE,
                                        piece = clash.game.board.moves[square],
                                        onClick = { onClickCell(square) }
                                    )
                                }
                            }
                        } else {
                            Text(
                                text = (lin + 1).toString(),
                                fontSize = 18.sp,
                                modifier = Modifier
                                    //.width(CELL_SIZE - 5.dp)
                                    .padding(end = 10.dp) // Para ajustar o alinhamento
                            )
                            repeat(BOARD_DIM) { col ->

                                val square = Square(Row(lin), Column(col))
                                val color = if (square.black) Color(65, 65, 65, 255) else Color(200, 200, 200, 255)
                                Box(
                                    modifier = Modifier
                                        .size(CELL_SIZE)
                                        .background(color)
                                        .border(
                                            width = 2.dp,
                                            color =
                                                if (selectedSQR == square &&
                                                    clash.game.board.moves[square]?.player != clash.sideplayer &&
                                                    clash.sideplayer == clash.game.board.player) Color.Red
                                                else Color.Transparent

                                        ).clickable { onClickCell(square) },
                                    contentAlignment = Alignment.Center,
                                ) {
                                    if (selectedSQR != null && clash.game.board.moves.values.toList()[transformSquare(selectedSQR).index] != null ) {

                                        if (transformSquare(square) in clash.game.board.possibleMoves2(transformSquare(selectedSQR)) && clash.sideplayer == clash.game.board.player && target) {
                                            Box(
                                                Modifier
                                                    .clip(CircleShape)
                                                    .size(35.dp)
                                                    .background(Color(88, 132, 52))
                                            ) {
                                            }
                                        }
                                    }

                                    PieceView(
                                        size = CELL_SIZE,
                                        piece = clash.game.board.moves[transformSquare(square)],
                                        onClick = { onClickCell(square) }
                                    )
                                }
                            }
                        }
                    }
                    else {
                        Text(
                            text = (BOARD_DIM - lin).toString(),
                            fontSize = 18.sp,
                            modifier = Modifier
                            //.width(CELL_SIZE - 5.dp)
                            .padding(end = 10.dp) // Para ajustar o alinhamento
                        )
                        repeat(BOARD_DIM) { col ->
                            val square = Square(Row(lin), Column(col))
                            val color = if (square.black) Color(65, 65, 65, 255) else Color(200, 200, 200, 255)
                            Box(
                                modifier = Modifier
                                    .size(CELL_SIZE)
                                    .background(color)
                                    .clickable { onClickCell(square) },
                                contentAlignment = Alignment.Center,
                            ) {

                            }
                        }
                    }
                }

            }

        }
    }
}





