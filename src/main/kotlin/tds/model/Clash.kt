package tds.model
import tds.storage.*

typealias GameStorage = Storage<Name,Game>


open class Clash( val gs: GameStorage)

class ClashRun(
    gs: GameStorage,
    val id: Name,
    val sideplayer: Player,
    val game: Game
) : Clash(gs)



fun Clash.start(id: Name): Clash {
    return try {
        gs.read(id)
        this.joinClash(id)
    } catch (e : Exception) {
        ClashRun(gs,id,Player.w,Game().createGame()).also { gs.create(id,it.game) }
    }
}


fun Clash.joinClash(name: Name): Clash = ClashRun(
    gs, name,
    sideplayer = Player.b,
    game = checkNotNull(gs.read(name)) { "Clash $name not found" }
)

private fun Clash.runOper(action: ClashRun.() -> Game): Clash {
    check(this is ClashRun) { "Clash not started" }
    return ClashRun(gs, id, sideplayer, action())
}

fun Clash.play(from: Square, to: Square) = runOper{
    game.play(from, to)
        .also {
            check((game.board as? BoardRun)?.turn == sideplayer) {" Not your turn "}
            gs.update(id, it)
        }
}

fun Clash.refresh() = runOper {
    val gameAfter = gs.read(id) as Game
    check(this.game.board != gameAfter.board) { "No changes" }
    gameAfter
}

class NoChangesException : IllegalStateException("No changes")
class GameDeletedException : IllegalStateException("Game deleted")

fun Clash.canNewBoard() = this is ClashRun && game.board is BoardWin // Serve para ver se o jogo já terminou porque não existe nenhuma class ClashWinner



