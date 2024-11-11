import isel.leic.tds.damas.model.*
import kotlin.test.Test
import kotlin.test.assertEquals


class TestQueen {
    @Test fun `Check if GenericCanMove on Queen works, it should see if there is at least a possible move associated with a piece on a specific square`() {

        val board = playSequence("3a 4b w", "6h 5g b", "2b 3a w", "7g 6h b","1c 2b w","8f 7g b","3g 4h w","6d 5c b","4b 6d w","6d 8f w", board = initialBoard())

        val From = "8f".toSquare()


        val bool = board.moves.values.toList()[From!!.index]?.GenericCanMove(From,board)

        val secondboard = playSequence("3a 4b w", "6h 5g b", "2b 3a w", "7g 6h b","1c 2b w","8f 7g b","3g 4h w","6d 5c b","4b 6d w","6d 8f w","8d 7e b", board = initialBoard())

        val secondbool = board.moves.values.toList()[From.index]?.GenericCanMove(From, secondboard)

        assertEquals(true, bool)
        assertEquals(false, secondbool)

    }


    @Test fun `Check if GenericCanKill on Queen works, it should see if there is at least a possible kill`() {

        val board = playSequence("3a 4b w", "6h 5g b", "2b 3a w", "7g 6h b","1c 2b w","8f 7g b","3g 4h w","6d 5c b","4b 6d w","6d 8f w", board = initialBoard())

        val From = "8f".toSquare()

        val bool = board.moves.values.toList()[From!!.index]?.GenericCanKill(From,board)

        val secondboard = playSequence("3a 4b w", "6h 5g b", "2b 3a w", "7g 6h b","1c 2b w","8f 7g b","3g 4h w","6d 5c b","4b 6d w","6d 8f w","8d 7e b", board = initialBoard())

        val secondbool = board.moves.values.toList()[From.index]?.GenericCanKill(From, secondboard)

        assertEquals(false, bool)
        assertEquals(true, secondbool)

    }


    @Test fun `Check if CheckLimits calculates correctly the amount of squares on a certain quadrant-diagonal, from the pos of the from Square until the end of the Board`() {

        val board = initialBoard()

        val FromTest1 = "3a".toSquare() // 0
        val StepperTest1 = BOARD_DIM - 1

        val FromTest2 = "3g".toSquare() // 1
        val StepperTest2= - BOARD_DIM + 1

        val FromTest3 = "5c".toSquare() // 4
        val StepperTest3=  BOARD_DIM + 1

        val FromTest4 = "1g".toSquare() //6
        val StepperTest4= - BOARD_DIM - 1

        assertEquals(0, CheckLimits(FromTest1!!,StepperTest1, board))
        assertEquals(1, CheckLimits(FromTest2!!,StepperTest2, board))
        assertEquals(4, CheckLimits(FromTest3!!,StepperTest3, board))
        assertEquals(6, CheckLimits(FromTest4!!,StepperTest4, board))


    }

    @Test fun `Check if checkquadrant calculates correctly the quadrant, an the stepper to travel on that diagonal on a certain direction `() {

        val board = initialBoard()

        val FromTest = "4d".toSquare()
        val ToTest1 = "5c".toSquare() // -BOARD_DIM -1
        val ToTest2 = "5e".toSquare() // -BOARD_DIM +1
        val ToTest3 = "3c".toSquare() //  BOARD_DIM -1
        val ToTest4 = "3e".toSquare() //  BOARD_DIM +1


        assertEquals(-BOARD_DIM -1, checkquadrant(FromTest!!, ToTest1!!))
        assertEquals(-BOARD_DIM +1, checkquadrant(FromTest, ToTest2!!))
        assertEquals(BOARD_DIM -1,  checkquadrant(FromTest, ToTest3!!))
        assertEquals(BOARD_DIM +1,  checkquadrant(FromTest, ToTest4!!))


    }

}