import kotlin.test.*
import isel.leic.tds.*
import isel.leic.tds.damas.model.*


fun playSequence(vararg moves: String, board: Board): Board =
    moves.fold(board) { b, idx ->
        val (pos, suffix, pl) = idx.split(" ")
        val from = pos.toSquare()
        val to = suffix.toSquare()
        val player = toPlayer(pl)
        if(from != null && to != null && player != null){
            b.play(from, to)
        }else{
            b
        }
    }
fun toPlayer(pl : String) : Player?{
    if (pl == "b") return Player.b
    else if(pl == "w") return Player.w
    else return null

}
fun CreateSpecificBoard(): Board{
    val init = Square.values.map {  Pair(it, getPawnSpecific(it))}.toMap()
    return BoardRun(init,Player.w)
}

fun getPawnSpecific(sq: Square) : Pawn?{
    if(!sq.black ) return null
    if(sq.index  ==  33 ||  sq.index == 44) return Pawn(Player.w)
    else if (sq.index == 26 || sq.index == 12 || sq.index == 37 ) return Pawn(Player.b)
    else return null
}

class TestPlay {
    @Test fun `Try Move Back`() {
        val board = playSequence("3a 4b w" ,"6h 5g b",board = initialBoard())
        val ex = assertFailsWith<IllegalArgumentException>{
            playSequence("4b 3a w",board = board)
        }
        assertEquals("Invalid Position", ex.message)
    }


    @Test fun `Test if when have an double kill and a normal kill at the same time and start the double kill i am obligated to finish the sequence with that piece and after that even knowing that i have in another place an possible kill change the turn( like it happens in Checkers)`() {
        val board = CreateSpecificBoard()
        val BoardTest = playSequence("4b 6d w", board = board)
        val ex = assertFailsWith<IllegalArgumentException> {
            playSequence("3e 5g w", board = BoardTest)
        }
        val KillBool = "there is a mandatory sequence" == ex.message
        val LastBoard = playSequence("6d 8f w", board = BoardTest)

        val TurnBool = LastBoard.player == Player.b

        assertEquals(true, KillBool && TurnBool)
    }


    @Test fun `Try Move to White Square`() {
        val ex = assertFailsWith<IllegalArgumentException>{
            playSequence("3a 4a w",board = initialBoard())
        }
        assertEquals("Invalid Play", ex.message)
    }


    @Test fun `Test move when mandatory Kill`() {
        val board = playSequence("3a 4b w" ,"6d 5c b",board = initialBoard())
        val ex = assertFailsWith<IllegalArgumentException>{
            playSequence("3c 4d w",board = board)
        }
        assertEquals("Invalid PLAY", ex.message)
    }


    @Test fun `Try move on other Turn`() {
        val ex = assertFailsWith<IllegalArgumentException>{
            playSequence("6d 5c w",board = initialBoard())
        }
        assertEquals("You only can move your pieces", ex.message)
    }


    @Test fun `Test kill with Queen when there is a piece on the way`() {
        val board = playSequence("3a 4b w" ,"6b 5a b","4b 5c w","6d 4b b","3c 4d w","4b 3a b", "2b 3c w", "7a 6b b","1c 2b w","3a 1c b","1a 2b w",board = initialBoard())
        val ex = assertFailsWith<IllegalArgumentException>{
            playSequence("1c 4f b",board = board)
        }
        println(ex.message)
        assertEquals(" Invalid Move, there is a piece on the way ", ex.message)
    }


    @Test fun `Test changing to Queen`() {
        val board = playSequence("3a 4b w" ,"6b 5a b","4b 5c w","6d 4b b","3c 4d w","4b 3a b", "2b 3c w", "7a 6b b","1c 2b w","3a 1c b",board = initialBoard())
        val pos = "1c".toSquare()
        if(pos != null){
            val sqr = board.moves.values.toList()[pos.index]
            val bool = sqr is Queen
            assertEquals(true, bool)
        }
        else error("test an valid square")
    }


    @Test fun `Kill with White`() {
        val board = playSequence("3a 4b w" ,"6d 5c b","4b 6d w",board = initialBoard())
        val pos0 = "4b".toSquare()
        val pos1 = "5c".toSquare()
        val pos2 = "6d".toSquare()
        if (pos0 != null && pos1 != null && pos2 != null) {
            val piece = board.moves.values.toList()[pos2.index]
            val BoardTest = playSequence("3a 4b w", board = initialBoard())
            val pieceCheck = BoardTest.moves.values.toList()[pos0.index]
            val bool = piece is Pawn && piece.player == pieceCheck?.player
            assertEquals(true, bool)
        }
        else error("erro")

    }
    @Test fun `Kill with Queen testing also double kill`() {
        val board = playSequence("3a 4b w" ,"6h 5g b","2b 3a w", "7g 6h b", "1c 2b w", "8f 7g b","3g 4h w","6d 5c b",
            "4b 6d w","6d 8f w", "6b 5c b", "8f 4b w" ,board = initialBoard())
        val pos0 = "8f".toSquare()
        val pos1 = "4b".toSquare()
        if (pos0 != null && pos1 != null) {
            val piece = board.moves.values.toList()[pos1.index]
            val BoardTest = playSequence("3a 4b w" ,"6h 5g b","2b 3a w", "7g 6h b", "1c 2b w", "8f 7g b","3g 4h w","6d 5c b",
                "4b 6d w","6d 8f w", "6b 5c b", board = initialBoard())
            val pieceCheck = BoardTest.moves.values.toList()[pos0.index]
            val bool = piece is Queen && pieceCheck is Queen && piece.player == pieceCheck.player
            assertEquals(true, bool)
        }
        else error("erro")
    }

}
