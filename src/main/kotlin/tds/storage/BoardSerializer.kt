package tds.storage
import tds.model.*

object BoardSerializer : Serializer<Board> {
    override fun serialize(data: Board): String {
        val serializeddata = when(data) { // Este when serve para detetar o estado do jogo
            is BoardRun -> "run:${data.turn}" + ":" + if(data.FromSequenceMandatory == null) "null" else "${data.FromSequenceMandatory}"
            is BoardWin -> "win:${data.winner}"
            // Esta linha a abaixo ser para indicar o square a posição e o player a ela associado
        } + "\n" + data.moves.map {it.key.toString() + ":" + if (it.value == null) "null" else {
            if (it.value is Queen) it.value?.player.toString().uppercase()
            else it.value?.player.toString() }
        }.joinToString()
        return serializeddata
    }

    override fun deserialize(text: String): Board {
        // Divide o texto em linhas
        val separation = text.lines()
        val lines = separation[1].trim().split(",")
        // A primeira linha indica o estado do jogo (run ou win) e o turn atual ou vencedor

        val boardstuff = separation[0].split(" ")

        if (boardstuff.size == 2) {
            val (type, playerStr) = separation[0].split(":")
            val player = Player.valueOf(playerStr)
        }

        val (type, playerStr, lastplay) = separation[0].split(" ")
        val player = Player.valueOf(playerStr)


        // As linhas seguintes representam as posições e as peças no tabuleiro


        val moves = lines.associate { state ->
            val (Sqr,Piece) = state.trim().split(":")
            val square = Sqr.toSquarenotNull()
            val piece = when(Piece){
                "null" -> null
                "b" -> Pawn(Player.b)
                "w" -> Pawn(Player.w)
                "W" -> Queen(Player.w)
                "B" -> Queen(Player.b)
                else -> error("Invalid piece format: $Piece")
            }
            square to piece
        }

        // Reconstrói o objeto `Board` com base no tipo identificado
        return when (type) {
            "run" -> BoardRun(moves, player, lastplay.toSquareOrNull())
            "win" -> BoardWin(moves, player)
            else -> error("Tipo de board inválido: $type")
        }

    }
}

