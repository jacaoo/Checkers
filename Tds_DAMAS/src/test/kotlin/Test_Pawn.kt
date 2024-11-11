import isel.leic.tds.damas.model.initialBoard
import isel.leic.tds.damas.model.toSquare
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TestPawn {
    @Test fun `Check if GenericCanMove works( it should see if there is at least a possible move associated with a piece on a specific square`() {
        val board = initialBoard()

        val From = "3a".toSquare()
        val secondFrom = "5c".toSquare()

        val bool = board.moves.values.toList()["3a".toSquare()!!.index]?.GenericCanMove(From!!, board)

        val Secondboard = playSequence("3a 4b w", "6h 5g b", "4b 5c w", board = initialBoard())

        val Secondbool = board.moves.values.toList()["3a".toSquare()!!.index]?.GenericCanMove(secondFrom!!, Secondboard)

        assertEquals(true, bool)
        assertEquals(false, Secondbool)

    }


    @Test fun `Check if GenericCanKill works it should see if there is at least a possible kill`() {
        val board = initialBoard()

        val From = "3a".toSquare()
        val secondFrom = "6d".toSquare()

        val bool = board.moves.values.toList()["3a".toSquare()!!.index]?.GenericCanKill(board.moves.keys.toList()[From!!.index], board)

        val Secondboard = playSequence("3a 4b w", "6h 5g b", "4b 5c w", board = initialBoard())

        val Secondbool = board.moves.values.toList()["6d".toSquare()!!.index]?.GenericCanKill(Secondboard.moves.keys.toList()[secondFrom!!.index], Secondboard)

        assertEquals(false, bool)
        assertEquals(true, Secondbool)

    }
}