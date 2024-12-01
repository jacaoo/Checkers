package tds.model

typealias Score = Map<Player,Int>

data class Game (
     //para saber se é w ou b
    val board: Board = initialBoard() ,
    val firstplayer: Player = Player.w,
    val score: Score = mapOf(Player.w to 0, Player.b to 0)
)

fun Game.createGame(): Game = Game(
    board = initialBoard(),
    firstplayer = firstplayer
)

fun Game.play(from: Square, to: Square): Game {
    val newBoard = this.board.play(from,to)
    return copy(
        board = newBoard,
        score = when (newBoard) {
            is BoardWin -> score.advance(newBoard.winner)
            else -> score
        }
    )
}

fun Score.advance(player: Player) =
    this + (player to (this[player]!! + 1))


