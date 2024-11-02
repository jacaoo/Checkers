package isel.leic.tds.damas.UI
import isel.leic.tds.damas.model.*
import isel.leic.tds.damas.storage.*
import isel.leic.tds.damas.UI.*

abstract class Command(val syntax: String = ""){
    open fun execute(args: List<String>, g: Game?): Game? = g
    open val isTerminate: Boolean get() = false
}
object PlayCommand :Command("<fromSquare> <toSquare>"){
    override fun execute(args: List<String>, g: Game?): Game {
        checkNotNull(g) { "Game not created." }
        require(args.isNotEmpty()) { "Missing position" }
        val from = args.firstOrNull()?.toSquareOrNull()
        requireNotNull(from) { "Invalid position ${args.firstOrNull()}" }
        val to = args.lastOrNull()?.toSquareOrNull()
        requireNotNull(to) { "Invalid position ${args.lastOrNull()}" }
        val newgame1 = g.play(from, to,g.board)
        newgame1.show(newgame1.player.other)
        return newgame1
    }
}
fun getCommands() = mapOf(
    "EXIT" to object : Command() {
        override val isTerminate: Boolean get() = true
    },
    "START" to object : Command() {
        override fun execute(args: List<String>, g: Game?): Game {
            require (args.isNotEmpty()) {" MISSING GAME ID"}
            val newgame =createGame(
                args.first(),
            )
            newgame.show(newgame.player)
            return newgame
        }
    },
    /*
    "REFRESH" to object : Command() {
        override fun execute(args: List<String>, g: Game?): Game {
            checkNotNull(g){ " Game not created. "} //se o jogo n for criado e null e da illegal state
            val s = storage.read(id = g.id) //basicamente esta função read da interface sotrage lê o id que passas como parametro e aqui, basicamente colocamos no s o id de g porque precisamos da informaçao de como se encontra o tabuleiro g
            require(s != null) { "Board not created." } //n percebi mt bem mas sem isto nunca se tinha a certeza se o board s era null ou nao
            return g.copy(board = s) //returna o mesmo game so que com o board atualizado com a jogada feita no outro terminal de comandos
        }
    },

    "GRID" to object : Command(){

    },
    */
    "PLAY" to PlayCommand,
)
//temos um problema, pelo q estou a perceber vamos ter de fazer essa merda do mongo ou o crlh para guardar a informaçao, se virem agr o getCOmmands tem 1 parametro e o cassiano passa na mais uma merda do mongoDatabase, n sei se existe uma forma mais simples de fazer aquilo

