package tds.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import tds.model.*

@Composable
fun PieceView(
    size : Dp = 100.dp,
    piece: Piece?,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier.size(size)
) {
    if(piece == null) {
        Box(
            modifier
                .clickable(onClick = onClick)
        )
    } else {
        val filename = when (piece) {
            is Pawn -> if (piece.player == Player.w) "piece_w.png" else "piece_b.png"
            is Queen -> if (piece.player == Player.w) "piece_wk.png" else "piece_bk.png"
            else -> throw IllegalArgumentException("Invalid piece $piece")
        }
        Image( painter = painterResource(filename),
            contentDescription = "Piece $piece $filename",
            modifier = modifier)
    }
}

@Composable
@Preview
fun PlayerPreview(){
    Column( Modifier.background(Color.Black)) {
        PieceView(100.dp, null)
        PieceView(100.dp, Pawn(Player.w))
        PieceView(100.dp, Pawn(Player.b))
        PieceView(100.dp, Queen(Player.w))
        PieceView(100.dp, Queen(Player.b))
    }
}