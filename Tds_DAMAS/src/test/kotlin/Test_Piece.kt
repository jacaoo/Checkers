import isel.leic.tds.damas.model.initialBoard
import isel.leic.tds.damas.model.toSquare
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class TestPiece {
    @Test fun `Check if canMove detects if its an Queen or an Pawn`() {
        val board = initialBoard()
        val ex = assertFailsWith<IllegalArgumentException> {
            val to = "4a".toSquare()
            val from = "3a".toSquare()
            if(to != null && from != null) {
                board.moves.values.toList()["3a".toSquare()!!.index]?.canMove(to,from,board)
            }
        }
        assertEquals("To isn't Black", ex.message)// texto que metemos na Queen "Can´t use non black squares" texto que metemos na Pawn "To isn't Black"
    }
}