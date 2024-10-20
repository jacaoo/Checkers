import isel.leic.tds.damas.model.*
import isel.leic.tds.damas.UI.*

fun main(){
    var listg : MutableList<Game> = mutableListOf()
    var game: Game? = null
    val cmds = getCommands()
    while(true) {
        val (name, args) = readCommand()
        val cmd: Command? = cmds[name]
        if (cmd == null) println(" Invalid Command $name ")
        else try {
            game = cmd.execute(args, game) ?: break
            listg.add(game)
        }
        catch (e : IllegalArgumentException) {
            println("${e.message}. Use: $name ${cmd.syntax}")
        } catch (e : IllegalStateException) {
            println(e.message)
        }
    }
}