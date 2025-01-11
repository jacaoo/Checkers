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
fun StatusBar(game: Game){
    Row(
        Modifier
            .width(GRID_WIDTH)
            .background(BACKGROUND_COLOR),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center


    ){
        /*
        if (clash is ClashRun) {
            Text("Game:${clash.id}")
            Spacer(Modifier.width(CELL_SIZE*2 + 15.dp))

            if (clash.sideplayer == Player.w) {
                Text("You:WHITE")
                Spacer(Modifier.width(CELL_SIZE*2 + 15.dp))
            } else {
                Text("You:BLACK")
                Spacer(Modifier.width(CELL_SIZE*2 + 15.dp))
            }
            if (clash.game.board is BoardRun) {
                if (clash.sideplayer == clash.game.board.turn) {
                    Text("Your turn")
                } else Text("Waiting..")
            }
        } else {
            Text("Start a new game")
        }

         */
        Text("Game:Null")
        Spacer(Modifier.width(CELL_SIZE*2 + 15.dp))
        Text("You:WHITE")
        Spacer(Modifier.width(CELL_SIZE*2 + 15.dp))
        Text("Your turn")


    }

}

@Composable
@Preview
fun StatusBarPreview() {
    StatusBar(Game())
}



