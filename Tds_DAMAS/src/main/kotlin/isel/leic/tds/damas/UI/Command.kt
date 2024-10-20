package isel.leic.tds.damas.UI
import isel.leic.tds.damas.model.*


abstract class Command(val syntax: String = ""){
    open fun execute(args: List<String>, g: Game?): Game? = g
    open val isTerminate: Boolean get() = false
}

fun getCommands() = mapOf(
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
    }
)