package isel.leic.tds.damas.UI
import isel.leic.tds.damas.model.*
import isel.leic.tds.damas.storage.*
import Board


abstract class Command(val syntax: String = ""){
    open fun execute(args: List<String>, g: Game?): Game? = g
    open val isTerminate: Boolean get() = false
}

fun getCommands(storage: Storage<String, Board>) = mapOf(
    "EXIT" to object : Command() {
        override val isTerminate: Boolean get() = true
    },
    "START" to object : Command() {
        override fun execute(args: List<String>, g: Game?): Game {
            require (args.isNotEmpty()) {" MISSING GAME ID"}
            return createGame(
                args.first(),
            )
        }
    },
    "REFRESH" to object : Command() {
        override fun execute(args: List<String>, g: Game?): Game {
            checkNotNull(g){ " Game not created. "} //se o jogo n for criado e null e da illegal state
            val s = storage.read(id = g.id)
            require(s != null) { "Game not created." } //n percebi mt bem mas sem isto nunca se tinha a certeza se o board s era null ou nao
            return g.copy(board = s) //returna o mesmo game so que com o board ja alterado com a jogada q veio do outro terminal de comandos
        }
    }
)
