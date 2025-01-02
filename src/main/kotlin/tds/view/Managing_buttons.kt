package tds.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import tds.GridApp
import java.awt.Font
import java.awt.Shape
import javax.swing.border.Border


@Composable
fun GameButton() {
    var showGameButtons by remember { mutableStateOf(false) }
    var showOptionsButtons by remember { mutableStateOf(false) }
    GridApp()
    //GridView()
    Box(
        Modifier
            .height(470.dp)
            .width(435.dp),
        contentAlignment = Alignment.TopStart

    ) {
        Row(
            //Isto vai ter os 2 botões de controlo de jogo o Game e o Options
            Modifier
                .width(435.dp)
                .height(37.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White,
                            Color.LightGray
                        )
                    )
                )
                .padding(start = 0.dp, top = 0.dp),

            ) {
            Button(
                onClick = {
                    if (!showOptionsButtons) showGameButtons = !showGameButtons
                }, // Este if serve para não ser possível usar os botões game e options ao mesmo tempo
                elevation = null,
                colors = if (!showGameButtons) ButtonDefaults.buttonColors(backgroundColor = Color.Transparent) else ButtonDefaults.buttonColors(
                    backgroundColor = Color(144, 238, 144)
                ),
            ) {
                Text(
                    "Game",
                    fontSize = 15.sp,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Start
                )
            }
            Button(
                onClick = {
                    if (!showGameButtons) showOptionsButtons = !showOptionsButtons
                }, // Este if serve para não ser possível usar os botões game e options ao mesmo tempo
                elevation = null,
                colors = if (!showOptionsButtons) ButtonDefaults.buttonColors(backgroundColor = Color.Transparent) else ButtonDefaults.buttonColors(
                    backgroundColor = Color(144, 238, 144)
                ),

                ) {
                Text(
                    "Options",
                    fontSize = 15.sp,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Start
                )
            }
        }
        if (showGameButtons) {
            Box(
                Modifier
                    .size(100.dp, 120.dp)
                    .offset(0.dp, 36.dp)
                    .border(2.dp, Color(144, 238, 144))
                    .background(Color.White)

            ) {
                Button(
                    onClick = {
                    }, //AINDA NÃO VOU METER AS FUNCIONALIDADES
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = null,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                ) {
                    Text(
                        "Start",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Start
                    )
                }
                Button(
                    onClick = { },
                    elevation = null,
                    modifier = Modifier
                        .offset(0.dp, 36.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)

                ) {
                    Text(
                        "Refresh",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Start
                    )
                }
                Button(
                    onClick = { },
                    elevation = null,
                    modifier = Modifier
                        .offset(0.dp, 72.dp)
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                ) {
                    Text(
                        "Exit",
                        fontSize = 15.sp,
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
        if (showOptionsButtons) {
            Box(
                Modifier
                    .offset(77.dp, 37.dp)// Para ficar a baixo do botão options
                    .size(155.dp, 90.dp)
                    .border(2.dp, Color(144, 238, 144))
                    .background(Color.White),
                contentAlignment = Alignment.TopStart
            ) {
                Button(
                    onClick = { }, //AINDA NÃO VOU METER AS FUNCIONALIDADES
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = null,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)
                ) {
                    Text(

                        "Show Target",
                        modifier = Modifier
                            .offset(15.dp, 0.dp),
                        fontSize = 15.sp,
                        fontFamily = FontFamily.SansSerif,

                        )
                }
                //IDENIFICADOR VISUAL DO SHOW TARGET SE FOI ATIVADO OU NÃO
                Box(
                    Modifier
                        .offset(12.dp, 18.dp)
                        .size(11.dp, 11.dp) // Define o tamanho do Box
                        .border(2.dp, Color.LightGray) // Adiciona uma borda vermelha com 2.dp
                ) {
                }
                Button(
                    onClick = { },
                    elevation = null,
                    modifier = Modifier
                        .offset(0.dp, 36.dp) // Só para o botão ficar em baixo do outro
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.White)

                ) {
                    Text(
                        "Auto-Refresh",
                        modifier = Modifier
                            .offset(15.dp, 0.dp),
                        fontSize = 15.sp,
                        fontFamily = FontFamily.SansSerif,
                        textAlign = TextAlign.Start
                    )
                }
                Box(
                    Modifier
                        .offset(12.dp, 54.dp)
                        .size(11.dp, 11.dp) // Define o tamanho do Box
                        .border(2.dp, Color.LightGray) // Adiciona uma borda vermelha com 2.dp
                ) {
                }
            }

        }

    }
}

@Composable
@Preview
private fun GameButtonsPreview() {
    GameButton()
}
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        state = WindowState(size = DpSize.Unspecified)
    ) {
        GameButton()
    }
}

