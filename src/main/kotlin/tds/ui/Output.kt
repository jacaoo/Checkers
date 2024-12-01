package tds.ui
import tds.model.*

fun Clash.show() {
    if (this is ClashRun) {
        if (this.game.board is BoardRun) {
            val turn = this.game.board.turn
            val toplimit = "  +${"-".repeat(BOARD_DIM * 2 - 1)}+  Turn = $turn"
            println(toplimit)
            val list =
                game.board.moves.keys.mapIndexed { index, square -> // Este val serve para criar a lista com as peças nos sítios certos e caso nao tiver peça num board black mete-se -
                    val value = square.black
                    if (value && game.board[Square(index)] == null) "-"
                    else {
                        if (game.board.moves.values.toList()[index] is Queen) {

                            game.board[Square(index)].toString().uppercase()
                        } else {
                            "${game.board[Square(index)]}"
                        }
                    }
                }
            for (i in BOARD_DIM downTo 1) {
                if (i == BOARD_DIM) println(
                    "$i |${
                        list.subList(0, i).joinToString(" ", postfix = "|") { if (it == "null") " " else it }
                    }  Player = ${this.sideplayer}"
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
            for (i in 0..<BOARD_DIM) {
                print("${'a' + i} ")
            }
            println() // Para dar um enter extra
        }
        else if (this.game.board is BoardWin) println("You win ${this.game.board.winner}")
    }
}
/*
fun Clash.show() {
    if (this is ClashRun) {
        if (this.game.board is BoardRun) {
            val turn = this.game.board.turn
            val toplimit = "  +${"-".repeat(BOARD_DIM * 2 - 1)}+ Turn = $turn"
            println(toplimit)

            // Gera uma lista com as peças nos sítios corretos, ou "-" para casas pretas vazias
            val list = List(BOARD_DIM * BOARD_DIM) { index ->
                val square = Square(index)
                val value = square.black
                if (value && this.game.board[square] == null) "-"
                else "${this.game.board[square] ?: " "}"
            }

            // Imprime cada linha do tabuleiro
            for (i in BOARD_DIM downTo 1) {
                val line = if (i == BOARD_DIM) {
                    "$i |${
                        list.subList(0, BOARD_DIM).joinToString(" ", postfix = "|") { if (it == "null") " " else it }
                    } Player = ${this.sideplayer}"
                } else {
                    "$i |${
                        list.subList((BOARD_DIM - i) * BOARD_DIM, (BOARD_DIM - i) * BOARD_DIM + BOARD_DIM)
                            .joinToString(" ", postfix = "|") { if (it == "null") " " else it }
                    }"
                }
                println(line)
            }
            val bottomlimit = "  +${"-".repeat(BOARD_DIM * 2 - 1)}+"
            println(bottomlimit)

            // Exibe a linha inferior com as letras das colunas
            print("   ")
            for (i in 0 until BOARD_DIM) {
                print("${'a' + i} ")
            }
            println() // Linha em branco para separar
        }
        else if (this.game.board is BoardWin) println("You win ${this.game.board.winner}")
    }

}

fun Game.showScore() {
    print("Score:")
    score.forEach{ (player, score) ->
        print(" $player = $score")
    }
    println()
}
/*
fun Clash.show() {
    if (this is ClashRun) {
        game.show()
    }
    else println("Clash not started")
}

 */