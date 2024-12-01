package tds.ui
import tds.model.*
import tds.storage.*
import tds.ui.*

class Command(
    val syntax: String = "",
    val isTerminate: Boolean = false,
    val execute: (args: List<String>, clash: Clash) -> Clash = { _, c:Clash -> c}
)


private val Play = Command() { args, clash ->
    require(clash is ClashRun) { "Game not started" }
    val arg = requireNotNull(args.isNotEmpty()) {"Missing position"}
    val from = args.firstOrNull()?.toSquareOrNull()
    requireNotNull(from) { "Invalid position ${args.firstOrNull()}" }
    val to = args.lastOrNull()?.toSquareOrNull()
    requireNotNull(to) { "Invalid position ${args.lastOrNull()}" }
    clash.play(from,to)
}

private fun beginCommand(exec: Clash.(Name) -> Clash) = Command("<name>") { args, clash ->
        val word = requireNotNull(args.firstOrNull()) {"Missing name"}
        clash.exec(Name(word))
    }


fun getCommands(): Map<String, Command> =
    mapOf(
        "REFRESH" to Command { _, c:Clash -> c.refresh()},
        "PLAY" to Play,
        "EXIT" to Command(isTerminate = true),
        "START" to beginCommand { name -> this.start(name) },
        "GRID" to Command()
    )




