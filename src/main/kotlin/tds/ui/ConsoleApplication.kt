package tds.ui
import tds.model.*
import tds.storage.GameSerializer
import tds.storage.TextFileStorage
import tds.storage.MongoDriver
import tds.storage.MongoStorage


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