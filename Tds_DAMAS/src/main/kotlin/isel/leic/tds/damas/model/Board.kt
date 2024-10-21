package isel.leic.tds.damas.model

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

class Board(val moves: Moves, val player: Player)


operator fun Board.get(position: Square): Player? = moves[position]?.player // Verifica qual é o player dentro do Quadrado Indicado





