package isel.leic.tds.damas.UI

data class LineCommand (val name: String, val params: List<String>)

fun readCommand() : LineCommand {
    while (true) {
        print(">")
        val words = readln().trim().split(" ")
        return if (words.isNotEmpty() && words.first().isNotBlank()) {
            LineCommand(words.first().uppercase(),words.drop(1))
        }
        else readCommand()
    }
}