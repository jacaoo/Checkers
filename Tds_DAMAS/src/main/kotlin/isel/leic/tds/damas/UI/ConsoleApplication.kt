package isel.leic.tds.damas.UI
import isel.leic.tds.damas.model.*
import isel.leic.tds.damas.storage.GameSerializer
import isel.leic.tds.damas.storage.TextFileStorage

object ConsoleApplication  {
    fun start() {
        var clash = Clash(TextFileStorage("games", GameSerializer))
        val commands = getCommands()
        while (true) {
            val (cmdName, args) = readCommand()
            val cmd = commands[cmdName]
            if (cmd == null) {
                println("Invalid command $cmdName")
            } else try {
                clash = cmd.execute(args, clash)
                clash.show() // Implementar esta função
                if (cmd.isTerminate) break
            } catch (e: IllegalStateException) {
                println(e.message)
            } catch (e: IllegalArgumentException) {
                println("${e.message}\nUse: $cmdName")
            } catch (e: Exception) {
                println("Error: ${e.message}")
            }
        }
    }
}