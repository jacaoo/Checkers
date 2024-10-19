import isel.leic.tds.damas.model.*

fun main(){
    var game: Game? = null
    val cmds = getCommands()
    while(true) {
        val (name, args) = readCommand()
        val cmd: Command? = cmds[name]
        if (cmd == null) println(" Invalid Command $name ")
        else try {
            game = cmd.execute(args, game) ?= break
            cmd.show(game)
        }
        catch (e : IllegalArgumentException) {
            println("${e.message}. Use: $name ${cmd.syntax}")
        } catch (e : IllegalStateException) {
            println(e.message)
        }
    }
}