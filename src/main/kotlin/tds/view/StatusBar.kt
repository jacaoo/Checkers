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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import tds.model.*
import tds.storage.*


val BACKGROUND_COLOR = Color(180, 100, 25) // cor laranja

@Composable
fun StatusBar(clash: Clash){
    Row(
        Modifier
            .width(GRID_WIDTH)
            .height(40.dp)
            .background(BACKGROUND_COLOR)
            .offset(x= 23.dp),
    ){
        when (clash){
            is ClashRun -> {
                Text(
                    text = "Game:${clash.id}",
                    fontSize = 20.sp
                )
                Spacer(Modifier.width(CELL_SIZE+ CELL_SIZE/2))

                if (clash.sideplayer == Player.w) {
                    Text(
                        text = "You:WHITE",
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.width(CELL_SIZE+ CELL_SIZE/2))
                } else {
                    Text(
                        text = "You:BLACK",
                        fontSize = 20.sp
                    )
                    Spacer(Modifier.width(CELL_SIZE + CELL_SIZE/2))
                }
                if (clash.game.board is BoardRun) {
                    if (clash.sideplayer == clash.game.board.turn) {
                        Text(
                            text = "Your turn",
                            fontSize = 20.sp
                        )
                    } else Text(
                        text = "Waiting..",
                        fontSize = 20.sp
                    )
                } else if (clash.game.board is BoardWin) {
                    if (clash.sideplayer == clash.game.board.winner) {
                        Text(
                            text = "You Win",
                            fontSize = 20.sp
                        )
                    } else Text(
                        text = "You Lose",
                        fontSize = 20.sp
                    )
                }
            }
            else -> Text(
                text = "Start a new game",
                fontSize = 20.sp
            )
        }
    }

}


