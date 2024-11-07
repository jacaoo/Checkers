package isel.leic.tds.damas.UI
import isel.leic.tds.damas.model.*

fun Game.show(player: Player) {
    val toplimit = "  +${"-".repeat(BOARD_DIM * 2 - 1)}+  Turn = $player"
    println(toplimit)
    val list = board.moves.keys.mapIndexed { index, square -> // Este val serve para criar a lista com as peças nos sítios certos e caso nao tiver peça num board black mete-se -
        val value = square.black
        if (value && board[Square(index)] == null) "-"
        else {
            if(board.moves.values.toList()[index] is Queen){

                board[Square(index)].toString().uppercase()
            }
            else{
                "${board[Square(index)]}"
            }
        }
    }
    for (i in BOARD_DIM downTo 1) {
        if (i == BOARD_DIM) println(
            "$i |${
                list.subList(0, i).joinToString(" ", postfix = "|") { if (it == "null") " " else it }
            }  Player = $player"
        )
        else println(
            "$i |${
                list.subList((BOARD_DIM - i) * BOARD_DIM, (BOARD_DIM - i) * BOARD_DIM + BOARD_DIM)
                    .joinToString(" ", postfix = "|") { if (it == "null") " " else it }
            }")
    }
    val bottomlimit = "  +${"-".repeat(BOARD_DIM * 2 - 1)}+"
    println(bottomlimit)
    print("   ")
    for (i in 0..BOARD_DIM - 1) {
        print("${'a' + i} ")
    }
    println() // Para dar um enter extra
}
