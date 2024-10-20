import isel.leic.tds.damas.model.*

const val BOARD_DIM = 8
const val BOARD_CELLS = BOARD_DIM*BOARD_DIM
const val playerPieces = 12

enum class Player{
    w, b;
    val other get() = if(this == w) b else w
}
typealias Move = Pair<Square, Piece?> // É para associar um quadrado com uma peça ( w, b )

typealias Moves = Map<Square, Piece?> // É uma espécie de lista para armazenar todos os possíveis espaços do tabuleiro

fun Moves.renew (old: Square, new: Piece?) = this.plus(Move(old, new)) // Serve para adicionar a peça no board

class Board(val moves: Moves, val player: Player)

fun initialBoard(): Board {
    val init = emptyMap<Square, Piece?>()
    Square.values.forEach { init.renew(it, null) }
    var pieces = 0
    var reverse = BOARD_CELLS - 1
    for ( i in 0..<BOARD_CELLS-1) {
        if (Square(i).black){
            init.renew(Square.values[i], Piece(Player.b))
            init.renew(Square.values[reverse], Piece(Player.w))
            pieces++
            reverse--
            if (pieces == playerPieces) break
        }
        else reverse--
    }
    return Board(init, Player.w)
}


