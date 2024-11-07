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
            init = init.renew(Square.values[i],Pawn(Player.b))
            init = init.renew(Square.values[reverse],Pawn(Player.w))
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
            if (ThereIsAKill(player) || this.moves.values.toList()[from.index] is Queen ){
                var newBoard = this
                newBoard = newBoard.exchangekill(from, to, player)
                if (newBoard.ThereIsAKill(player)) {
                    newBoard = BoardRun(newBoard.moves, newBoard.player)
                }
                else newBoard = BoardRun(newBoard.moves, newBoard.player.other)
                newBoard

            } else {
                val piece =list[from.index]
                require (piece != null && piece.canMove(to, from, this) ){"Invalid Position"}
                val newb = this.exchange(from, to)
                newb
            }
            }
        }


// Função exchangekill corrigida
fun Board.exchangekill(from: Square, to: Square, player: Player): Board {
    val newMoves = this.moves.toMutableMap()
    val piece = this.moves.values.toList()[from.index]
    if (piece != null && piece.canKill(to, from, this)) {
        if (piece is Pawn) {

            if (player == Player.w) {
                if (this.moves.keys.indexOf(from) % BOARD_DIM > this.moves.keys.indexOf(to) % BOARD_DIM)
                    newMoves[Square(this.moves.keys.indexOf(from) - (BOARD_DIM + 1))] = null
                else newMoves[Square(this.moves.keys.indexOf(from) - (BOARD_DIM - 1))] = null
            } else {
                if (this.moves.keys.indexOf(from) % BOARD_DIM > this.moves.keys.indexOf(to) % BOARD_DIM)
                    newMoves[Square(this.moves.keys.indexOf(from) + BOARD_DIM - 1)] = null
                else newMoves[Square(this.moves.keys.indexOf(from) + (BOARD_DIM + 1))] = null
            }

            if(checkQueen(to, player)) {
                newMoves[to] = Queen(this.player)
            }else{
                newMoves[to] = newMoves[from]
            }
            newMoves[from] = null
            return BoardRun(newMoves, player)

        } else {

            val stepper = checkquarter(from,to)
            newMoves[Square(this.moves.keys.indexOf(to) - stepper)] = null
            newMoves[to] = Queen(this.player)
            newMoves[from] = null
            return BoardRun(newMoves, player)


        }
    }else return this
}

fun Board.ThereIsAKill(player: Player): Boolean {
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
    if(checkQueen(to,player)){
        newMoves[to] = Queen(player)
    }
    else newMoves[to] = newMoves[from]

    newMoves[from] = null
    val newBoard = BoardRun(newMoves,player.other)
    return newBoard
}

fun checkQueen(to: Square, player: Player) : Boolean {
    return if (player == Player.b) {
        to.row.index == BOARD_DIM - 1
    }
    else if(player == Player.w){
        to.row.index == 0
    }
    else false
}



