package isel.leic.tds.damas.model

import com.sun.org.apache.xalan.internal.xsltc.runtime.BasisLibrary.copy
import kotlin.math.absoluteValue

const val BOARD_DIM = 8
const val BOARD_CELLS = BOARD_DIM * BOARD_DIM
const val playerPieces = 12



enum class Player{
    w, b;
    val other get() = if(this == w) b else w

}
typealias Move = Pair<Square, Piece?> // É para associar um quadrado com uma peça ( w, b )

typealias Moves = Map<Square, Piece?> // É uma espécie de lista para armazenar todos os possíveis espaços do tabuleiro

fun Moves.renew (old: Square, new: Piece?) = this.plus(Move(old, new)) // Serve para adicionar a peça no board

sealed class Board(val moves: Moves, val player: Player)
class BoardRun(mvs: Moves, val turn: Player) : Board(mvs, turn)
class BoardWin(mvs: Moves, val winner: Player) : Board(mvs, winner)

operator fun Board.get(position: Square): Player? = moves[position]?.player // Verifica qual é o player dentro do Quadrado Indicado

fun initialBoard(): Board {
    var init = emptyMap<Square,Piece?>()
    Square.values.forEach { init = init.renew(it,null)}
    var pieces = 0
    var reverse = Square.values.size - 1
    for (i in 0..<Square.values.size - 1) {
        if ( Square(i).black ) {
            init = init.renew(Square.values[i],Piece(Player.b))
            init = init.renew(Square.values[reverse],Piece(Player.w))
            reverse--
            pieces++
            if (pieces == playerPieces) break
        }
        else reverse--
    }
    return BoardRun(init,Player.w)
}

fun Board.play(from: Square, to: Square, player: Player): Board =
    when (this) {
        is BoardWin -> error("Game is over")
        else -> {
            val list = this.moves.values.toList()
            require(this.moves.keys.toList()[to.index].black && this.moves.keys.toList()[from.index].black)
            {"Invalid Play"}
            if (canKill(player)) {
                var newBoard = this
                newBoard = newBoard.Kill(from, to, player)
                if (newBoard.canKill(player)) {
                    newBoard = BoardRun(newBoard.moves, newBoard.player)
                }
                else newBoard = BoardRun(newBoard.moves, newBoard.player.other)
                newBoard
            } else {

                if (player == Player.w){
                    require(
                        this.moves.keys.indexOf(to) == this.moves.keys.indexOf(from) - (BOARD_DIM - 1) ||
                                this.moves.keys.indexOf(to) == this.moves.keys.indexOf(from) - (BOARD_DIM + 1) && Square(
                            this.moves.keys.indexOf(to)
                        ).black
                    ) { "Invalid Position" }
                    require(list[this.moves.keys.indexOf(to)] == null) { "Position already occupied" }
                    val newb = this.exchange(from, to)
                    newb
                }
                else {
                    require(
                        this.moves.keys.indexOf(to) == this.moves.keys.indexOf(from) + (BOARD_DIM - 1) ||
                                this.moves.keys.indexOf(to) == this.moves.keys.indexOf(from) + (BOARD_DIM + 1) && Square(
                            this.moves.keys.indexOf(to)
                        ).black
                    ) { "Invalid Position" }
                    require(list[this.moves.keys.indexOf(to)] == null) { "Position already occupied" }
                    this.exchange(from, to)
                }
            }
        }
    }

// Função exchangekill corrigida
fun Board.exchangekill(from: Square, to: Square, player: Player): Board {
    val newMoves = this.moves.toMutableMap()
    if (player == Player.w) {
        if (this.moves.keys.indexOf(from) % BOARD_DIM > this.moves.keys.indexOf(to) % BOARD_DIM)
            newMoves[Square(this.moves.keys.indexOf(from) - (BOARD_DIM + 1))] = null
        else newMoves[Square(this.moves.keys.indexOf(from) - (BOARD_DIM - 1))] = null
    }
    else {
        if (this.moves.keys.indexOf(from) % BOARD_DIM > this.moves.keys.indexOf(to) % BOARD_DIM)
            newMoves[Square(this.moves.keys.indexOf(from) + BOARD_DIM - 1)] = null
        else newMoves[Square(this.moves.keys.indexOf(from) + (BOARD_DIM + 1))] = null
    }
    newMoves[to] = Piece(this.player)
    newMoves[from] = null
    return BoardRun(newMoves,player)
}

// Função Kill corrigida
fun Board.Kill(from: Square, to: Square, player: Player): Board {
    val list = this.moves.toMutableMap()
    return if (player == Player.w) {
        require(
            this.moves.keys.indexOf(to) == this.moves.keys.indexOf(from) - (BOARD_DIM * 2 - 2) ||
                    this.moves.keys.indexOf(to) == this.moves.keys.indexOf(from) - (BOARD_DIM * 2 + 2)
        ) { "Invalid PLAY" }
        require(
            list[Square(this.moves.keys.indexOf(from) - (BOARD_DIM - 1))] != null ||
                    list[Square(this.moves.keys.indexOf(from) - (BOARD_DIM + 1))] != null
        ) { "Impossible Play" }

        val newBoard = this.exchangekill(from, to, player)
        newBoard
    } else {
        require(
            this.moves.keys.indexOf(to) == this.moves.keys.indexOf(from) + (BOARD_DIM * 2 - 2) ||
                    this.moves.keys.indexOf(to) == this.moves.keys.indexOf(from) + (BOARD_DIM * 2 + 2)
        ) { "Invalid PLAY" }
        require(
            list[Square(this.moves.keys.indexOf(from) + (BOARD_DIM - 1))] != null ||
                    list[Square(this.moves.keys.indexOf(from) + (BOARD_DIM + 1))] != null
        ) { "Impossible Play" }

        val newBoard = this.exchangekill(from, to, player)
        newBoard
    }
}

fun Board.canKill(player: Player): Boolean {
    val list = this.moves.toMutableMap()

    for ((sqr, pl) in list) {
        if (pl != null && pl.player == player) {
            if (player == Player.w) {
                val middler = list.values.toList().getOrNull(sqr.index - (BOARD_DIM - 1))
                val afterr = list.values.toList().getOrNull(sqr.index - (BOARD_DIM * 2 - 2))
                val middlel = list.values.toList().getOrNull(sqr.index - (BOARD_DIM + 1))
                val afterl = list.values.toList().getOrNull(sqr.index - (BOARD_DIM * 2 + 2))

                if ((middlel != null && sqr.index% BOARD_DIM !in 0..1 && middlel.player == player.other && afterl == null) ||
                    (middler != null && sqr.index% BOARD_DIM !in BOARD_DIM-2..BOARD_DIM-1 &&
                            middler.player == player.other && afterr == null)) {
                    return true
                }
            } else {
                val middlel = list.values.toList().getOrNull(sqr.index + (BOARD_DIM - 1))
                val afterl = list.values.toList().getOrNull(sqr.index + (BOARD_DIM * 2 - 2))
                val middler = list.values.toList().getOrNull(sqr.index + (BOARD_DIM + 1))
                val afterr = list.values.toList().getOrNull(sqr.index + (BOARD_DIM * 2 + 2))

                if ((middlel != null && sqr.index% BOARD_DIM !in 0..1 && middlel.player == player.other && afterl == null) ||
                    (middler != null && sqr.index% BOARD_DIM !in BOARD_DIM-2..BOARD_DIM-1
                            && middler.player == player.other && afterr == null)) {
                    return true
                }
            }
        }
    }
    return false
}

fun Board.exchange(from: Square, to: Square): Board {
    val newMoves = this.moves.toMutableMap()
    newMoves[to] = Piece(this.player)
    newMoves[from] = null
    val newBoard = BoardRun(newMoves,player.other)
    return newBoard
}
/*


fun Board.moveQueen(from: Square,to: Square,player: Player): Board {
    require(to.index in 0..<this.moves.keys.size) { " Position not in Board "}
    require(to.black) { " Can´t move on a non black square "}
    require( this.moves.values.toList()[to.index] == null ) {" Position already occupied "}
    require( ((to.index - from.index).absoluteValue) % BOARD_DIM - 1 == 0 || ((to.index - from.index).absoluteValue) % BOARD_DIM + 1 == 0 )
    {" Queen can only move in diagonals "}
    val steper = checkquarter(from, to)
    val dif = (from.row.index - to.row.index).absoluteValue
    for ( i in 1..dif) {
        val num = from.index + i * steper
        require(this.moves.values.toList()[num] == null) {" Invalid Move, there is a piece on the way "}
    }
    val newboard = this.exchange(from,to)
    return newboard
}

fun checkquarter(from: Square,to: Square): Int {
    return if (from.row.index > to.row.index && from.column.index < to.column.index || from.row.index < to.row.index &&
        from.column.index > to.column.index) {
        if (from.row.index < to.row.index &&
            from.column.index > to.column.index) {
            -(BOARD_DIM - 1)
        }
        else BOARD_DIM - 1
    }
    else {
        if (from.row.index > to.row.index &&
            from.column.index > to.column.index) {
            -(BOARD_DIM + 1)
        }
        else BOARD_DIM + 1
    }
}

*/