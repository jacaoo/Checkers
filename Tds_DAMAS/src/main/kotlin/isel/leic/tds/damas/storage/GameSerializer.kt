package isel.leic.tds.damas.storage

import isel.leic.tds.damas.model.*

object GameSerializer: Serializer<Game> {
    override fun serialize(data: Game): String = buildString {
        // Serializa o score de todos os jogadores
        appendLine(data.score.entries.joinToString(" ") { (plyr, pts) ->
            "$plyr=$pts"
        })

        // Serializa o jogador atual (Player)
        appendLine(data.firstplayer.toString()) // Use toString() caso necessário para garantir a conversão correta

        // Serializa o board
        appendLine(BoardSerializer.serialize(data.board))
    }


    override fun deserialize(text: String): Game {
        val lines = text.lines()
        if (lines.size < 4) {
            throw IllegalArgumentException("Formato inválido para deserializar o Game. Esperado 4 partes.")
        }

        // Primeira parte: o score
        val scoreLine = lines[0]

        val scoreMap = scoreLine
            .split(" ")
            .map { it.split("=") }
            .associate { (plyr, pts) -> plyr.toPlayer() to pts.toInt() }

        // Segunda parte: o jogador atual
        val currentPlayer = lines[1].trim()


        val player = try {
            Player.valueOf(currentPlayer)
        } catch (e: IllegalArgumentException) {
            println("Erro ao converter player: $currentPlayer")
            throw e
        }

       val boardstate = lines[2].split(":")

        /*
        No boardstate que é uma lista temos o seguinte
        -boardstate[0] = estado do Board se é run o winner
        -boardstate[1] = o turn atual
        - Caso for run dá existe um boardstate[2] que possui a última jogada que pode ser null
         */

        val boardtext = if (boardstate[0] == "run") {
            "run" + " " + boardstate[1] + " " + boardstate[2] + "\n" + lines.drop(3).joinToString("\n").trim { it in setOf(',', ' ') }
        }
        else "run" + " " + boardstate[1] + "\n" + lines.drop(3).joinToString("\n")


        val board = BoardSerializer.deserialize(boardtext)

        return when (boardstate[0]) {
            "run" -> Game(score = scoreMap, firstplayer = player, board = BoardRun(board.moves,boardstate[1].toPlayer(),boardstate[2].toSquareOrNull()))
            else -> Game(score = scoreMap, firstplayer = player, board = BoardWin(board.moves,boardstate[1].toPlayer()))
        }
    }




}