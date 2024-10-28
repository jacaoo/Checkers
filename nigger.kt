
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

sealed class Board(val moves: Moves, val player: Player)
class BoardRun(mvs: Moves, val turn: Player) : Board(mvs, turn)
class BoardWin(mvs: Moves, val winner: Player) : Board(mvs, winner)

fun initialBoard(): Board {
    var init = emptyMap<Square, Piece?>()
    Square.values.forEach { init = init.renew(it, null) }
    var pieces = 0
    var reverse = BOARD_CELLS - 1
    for ( i in 0..<BOARD_CELLS-1) {
        if (Square(i).black){
            init = init.renew(Square.values[i], Piece(Player.b))
            init = init.renew(Square.values[reverse], Piece(Player.w))
            pieces++
            reverse--
            if (pieces == playerPieces) break
        }
        else reverse--
    }
    return BoardRun(init, Player.w)
}

operator fun Board.get(position: Square): Player? = moves[position]?.player // Verifica qual é o player dentro do Quadrado Indicado

class Piece(val player: Player) {
    var queen = false
    fun toQueen() {
        queen = true
    }
    override fun toString(): String {
        return if (queen) player.name.uppercase() else player.name
    }
    override fun equals(other: Any?): Boolean {
        return if ( other is Piece ) player == other.player else error("Can't compare different types")
    }
}



class Square (val index: Int) {
    val row get() = Row(index / BOARD_DIM)
    val column get() = Column(index % BOARD_DIM)
    val black: Boolean get() = (row.index + column.index) % 2 != 0
    override fun toString(): String {
        return "${row.digit}${column.symbol}"
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is Square && this.index == other.index) return true
        else return false
    }
    override fun hashCode(): Int {
        return this.index
    }
    companion object {
        val values : List<Square> = CreateSquares()
    }
}
fun String.toSquareOrNull(): Square? {
    if(this.length != 2) return null
    val row = this[0]
    val column = this[1]
    val rindex = BOARD_DIM - (row - '0')
    val cindex = column - 'a'
    if (rindex !in 0..<BOARD_DIM || cindex !in 0..<BOARD_DIM) return null
    else return Square(Row(rindex),Column(cindex))
}

fun String.toSquare(): Square? {
    if(this.length != 2) throw IllegalArgumentException()
    if (this.toSquareOrNull() == null) throw IllegalArgumentException()
    else return this.toSquareOrNull()
}

fun CreateSquares(): List<Square> {
    val list = mutableListOf<Square>()
    var index = 0
    while (index != (BOARD_DIM*BOARD_DIM)){
        list.add(Square(index))
        index++
    }
    return list
}

fun Square(row: Row, col: Column): Square {
    return Square.values[row.index * BOARD_DIM + col.index]
}




@JvmInline
value class Row (val index: Int) {
    val digit: Char get() = '0' + (BOARD_DIM - index)
    init {
        if (index !in 0..<BOARD_DIM) throw IllegalArgumentException("Invalid row index: $index")
    }
    companion object {
        val values = List(BOARD_DIM) { Row(it) }
    }

}

fun Char.toRowOrNull(): Row? {
    val rindex =  BOARD_DIM - (this - '0')
    return if (this !in '1'..BOARD_DIM.digitToChar()) null
    else Row(rindex)
}


@JvmInline
value class Column(val index: Int){
    val symbol: Char get() = 'a' + index
    init {
        if (index !in 0..<BOARD_DIM) throw IllegalArgumentException("Invalid column index: $index")
    }
    companion object {
        val values = List(BOARD_DIM){Column(it)}
    }
}
fun Char.toColumnOrNull(): Column? {
    return if (this !in 'a'..'a' + (BOARD_DIM - 1)) null
    else Column(this - 'a')
}




data class Game (
    val id: String, //nome do jogo
    val player: Player, //para saber se é w ou b
    var board: Board
)

fun createGame(id: String): Game {
    val board = initialBoard()
    return Game(id, Player.w, board )
}
/*
fun Game.play(from: Square, to: Square, board: Board): Game {
    val newBoard = board.play(from,to)
    return copy(board = newBoard)
}

fun Game.show() = this.board.print(playerID)
*/
/*
fun Game.show() {
    println("+${"-".repeat(BOARD_DIM*2-1)}+     Turn = b") //adicionar depois e perguntar como fazer o player na linha de baixo
    for (line in BOARD_DIM-1..0){
        println(this.board.moves.toList().subList(line*BOARD_DIM,line*BOARD_DIM+BOARD_DIM).joinToString("","$line |","|"))
    }
    println("+${"-".repeat(BOARD_DIM*2-1)}+")
    println("a b c d e f g h")
}
*/


fun Game.show(player: Player) {
    val toplimit = "  +${"-".repeat(BOARD_DIM * 2 - 1)}+  Turn = $player"
    println(toplimit)
    val list = board.moves.keys.mapIndexed { index, square -> // Este val serve para criar a lista com as peças nos sítios certos e caso nao tiver peça num board black mete-se -
        val value = square.black
        if (value && board[Square(index)] == null) "-"
        else "${board[Square(index)]}"
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

fun Board.play(from : Square, to: Square, player: Player): Board =
    when (this) {
        is BoardWin -> error("Game is over")
        else -> {
            val list =
                this.moves.values.toList() // Lista das Peças do Board atual, criamos isto para usar as propriedades da lista
            if (player == Player.w) {
                if (this.moves.keys.indexOf(to) == this.moves.keys.indexOf(from) - (BOARD_DIM * 2 - 2) ||
                    this.moves.keys.indexOf(to) == this.moves.keys.indexOf(from) - (BOARD_DIM * 2 + 2)
                ) {
                    require(
                        list[this.moves.keys.indexOf(from) - (BOARD_DIM - 1)] != null ||
                                list[this.moves.keys.indexOf(from) - (BOARD_DIM + 1)] != null
                    ) { " Impossible Play " }
                    val newboard = this.exchange(from, to)
                    newboard.Kill(from, to, player)
                }
                else {
                    require(
                        this.moves.keys.indexOf(to) == this.moves.keys.indexOf(from) - (BOARD_DIM - 1) ||
                                this.moves.keys.indexOf(to) == this.moves.keys.indexOf(from) - (BOARD_DIM + 1) && Square(
                            this.moves.keys.indexOf(to)
                        ).black
                    ) { "Invalid Position" }
                    require(list[this.moves.keys.indexOf(to)] == null) { "Position already occupied" }
                    this.exchange(from, to)
                }
            } else {
                    if (this.moves.keys.indexOf(to) == this.moves.keys.indexOf(from) + (BOARD_DIM * 2 - 2) ||
                        this.moves.keys.indexOf(to) == this.moves.keys.indexOf(from) + (BOARD_DIM * 2 + 2)
                    ) {
                        require(
                            list[this.moves.keys.indexOf(from) + (BOARD_DIM - 1)] != null ||
                                    list[this.moves.keys.indexOf(from) + (BOARD_DIM + 1)] != null
                        ) { " Impossible Play " }
                        val newboard = this.exchange(from, to)
                        newboard.Kill(from, to, player)
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


fun Board.exchange(from: Square, to: Square): Board {
    val newmoves1 = this.moves.toMutableMap()
    newmoves1[to] = Piece(this.player)
    newmoves1[from] = null
    return BoardRun(newmoves1, this.player.other)
}
/*
fun Board.kill(square: Square): Board {
    val newboardkill = this.moves.toMutableMap()
    newboardkill[square] = null
    return BoardRun(newboardkill, this.player.other)
}
*/

fun Board.Kill(from: Square, to: Square, player : Player): Board {
    val newmoves = this.moves.toMutableMap()
    if (player == Player.w) {
        if (this.moves.keys.indexOf(from) % BOARD_DIM > this.moves.keys.indexOf(to) % BOARD_DIM)
            newmoves[Square(this.moves.keys.indexOf(from) - BOARD_DIM - 1)] = null
        else newmoves[Square(this.moves.keys.indexOf(from) - (BOARD_DIM + 1))] = null
    }
    else {
        if (this.moves.keys.indexOf(from) % BOARD_DIM > this.moves.keys.indexOf(to) % BOARD_DIM)
            newmoves[Square(this.moves.keys.indexOf(from) + BOARD_DIM - 1)] = null
        else newmoves[Square(this.moves.keys.indexOf(from) + (BOARD_DIM + 1))] = null
    }
    return BoardRun(newmoves, this.player.other)
}

fun main() {
    var game = createGame("nigger")
    game.show(game.player)
    val newboard = game.board.play(Square(40), Square(33), game.player)
    game.board = newboard
    game.show(game.player)
    val newboard1 = game.board.play(Square(17), Square(24), game.player.other)
    game.board = newboard1
    game.show(game.player)
    val newboard2 = game.board.play(Square(42), Square(35), game.player)
    game.board = newboard2
    game.show(game.player)
    val newboard3 = game.board.play(Square(24), Square(42), game.player.other)
    game.board = newboard3
    game.show(game.player)
}





