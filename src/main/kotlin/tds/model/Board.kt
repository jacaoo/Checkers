package tds.model

import tds.AppProperties

val BOARD_DIM = AppProperties.p.getProperty("BOARD_DIM").toInt()

val BOARD_CELLS = BOARD_DIM * BOARD_DIM

val playerPieces = AppProperties.p.getProperty("playerPieces").toInt()



enum class Player{
    w, b;
    val other get() = if(this == w) b else w

}



typealias Move = Pair<Square, Piece?> // É para associar um quadrado com uma peça ( w, b )

typealias Moves = Map<Square, Piece?> // É uma espécie de lista para armazenar todos os possíveis espaços do tabuleiro

fun Moves.renew (old: Square, new: Piece?) = this.plus(Move(old, new)) // Serve para adicionar a peça no board

sealed class Board(val moves: Moves, val player: Player) {
    override fun equals(other: Any?) = other is Board && moves == other.moves
    override fun hashCode() = moves.hashCode()
}
class BoardRun(mvs: Moves, val turn: Player, val FromSequenceMandatory: Square? = null) : Board(mvs, turn)
class BoardWin(mvs: Moves, val winner: Player) : Board(mvs, winner)

operator fun Board.get(position: Square): Player? = moves[position]?.player // Verifica qual é o player dentro do Quadrado Indicado

fun initialBoard(): Board {
    val init = Square.values.map {  Pair(it, getPawn(it))}.toMap()
    return BoardRun(init,Player.w)
}


fun String.toPlayer(): Player =
    if (this == "w") Player.w
    else Player.b


fun Board.play(from: Square, to: Square): Board =
    when (this) {
        is BoardWin -> error("Game is over") // nunca vai funcionar pq precisa de uma nova iteração
        is BoardRun -> {
            if(this.FromSequenceMandatory != null) {
                require(from == this.FromSequenceMandatory ){"there is a mandatory sequence in $from"}
            }
            val list = this.moves.values.toList()
            require(this.moves.values.toList()[from.index] != null && this.moves.values.toList()[from.index]?.player == player){"You only can move your pieces"}
            require(this.moves.keys.toList()[to.index].black && this.moves.keys.toList()[from.index].black)
            {"Invalid Play"}
            if (ThereIsAtLeastAKill(this.player)){

                val actualPiece = this.moves.values.toList()[from.index]
                val newBoard = this.exchangekill(from, to)

                if (actualPiece!= null && actualPiece.genericCanKill(to, newBoard)) { //corrigir o problema de quando tens varias kills possiveis e trocas de turn
                    BoardRun(newBoard.moves, newBoard.player, to).CheckWin(player)
                }
                else  BoardRun(newBoard.moves, newBoard.player.other).CheckWin(player)


            } else {
                val piece =list[from.index]
                require (piece != null && piece.canMove(to, from, this) ){"Invalid Position"}
                val newb = this.exchange(from, to)
                val CheckedB= newb.CheckWin(player)
                CheckedB
            }
        }
    }

fun Board.exchangekill(from: Square, to: Square): Board {
    val newMoves = this.moves.toMutableMap()
    val piece1 = this.moves.values.toList()[from.index]
    if (piece1 != null && piece1.canKill(to, from, this)) {
        if (piece1 is Pawn) {

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

            val stepper = checkquadrant(from,to)
            newMoves[Square(this.moves.keys.indexOf(to) - stepper)] = null
            newMoves[to] = newMoves[from]
            newMoves[from] = null
            return BoardRun(newMoves, player)


        }
    }else return this
}

fun Board.ThereIsAtLeastAKill(player: Player): Boolean { // verifica no tabuleiro para um player se há pelo menos uma kill
    val list = this.moves.toMutableMap()
    for ((sqr, piece) in list) {
        if (piece != null && piece.player == player) {
            if(piece.genericCanKill(sqr, this)) return true
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

fun Board.CheckWin(player: Player): Board { //testar quando tivermos o serializer pq so mostra o win quando houver um board.win
    if(this.NoMorePieces(player)) return BoardWin(this.moves, player.other)
    else if(this.NoMorePieces(player.other)) return BoardWin(this.moves, player)
    else{
        if(this.IsLocked(player)) return BoardWin(this.moves, player.other)
        else if(this.IsLocked(player.other)) return BoardWin(this.moves, player)
    }
    return this
}

fun Board.NoMorePieces(player: Player): Boolean {
    val list = this.moves.toMutableMap()
    for ((sqr, piece) in list) {
        if (piece != null && piece.player == player) {
            return false
        }
    }
    return true
}

fun Board.IsLocked(player: Player): Boolean{
    val list = this.moves.toMutableMap()
    for ((sqr, piece) in list) {
        if (piece != null && piece.player == player) {
            if (piece.genericCanKill(sqr, this) || piece.genericCanMove(sqr, this)) return false
        }
    }
    return true

}

//Ter cuidado a usar isto
fun Moves.possibleMoves(from: Square): List<Square> {
    val piece = this.values.toList()[from.index]
    require(piece != null) { "No piece found in the given square" }

    val possibleMoves = mutableListOf<Square>()

    for ((toSquare, toPiece) in this) {
        // Verifique se o destino está vazio e se segue as regras de movimento
        if (toPiece == null && piece.canMove_nd(toSquare, from, this)) {
            possibleMoves.add(toSquare)
        }
    }

    // Retorna a lista de movimentos válidos
    return possibleMoves
}
