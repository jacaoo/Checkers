package isel.leic.tds.damas.model
import Board
import Player
import initialBoard

data class Game (
    val id: String, //nome do jogo
    val player: Player, //para saber se é w ou b
    var board: Board
)

fun createGame(id: String) = Game(id, Player.w, initialBoard())
/*
fun Game.play(from: Square, to: Square, board: Board): Game {
    val newBoard = board.play(from,to)
    return copy(board = newBoard)
}

fun Game.show() = this.board.print(playerID)
*/
