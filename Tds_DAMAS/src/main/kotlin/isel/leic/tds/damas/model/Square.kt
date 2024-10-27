package isel.leic.tds.damas.model

class Square (val index: Int) {
    val row get() = Row(index / BOARD_DIM)
    val column get() = Column(index % BOARD_DIM)
    val black: Boolean get() = (row.index + column.index) % 2 != 0
    override fun toString(): String {
        return "${row.digit}${column.symbol}"
    }
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other is Square && this.index == other.index) return true
        else return false
    }
    override fun hashCode(): Int {
        return this.index
    }
    companion object {
        val values : List<Square> = CreateSquares()
    }
}
fun String.toSquareOrNull(): Square? {
    if(this.length != 2) return null
    val row = this[0]
    val column = this[1]
    val rindex = BOARD_DIM - (row - '0')
    val cindex = column - 'a'
    if (rindex !in 0..<BOARD_DIM || cindex !in 0..<BOARD_DIM) return null
    else return Square(Row(rindex),Column(cindex))
}

fun String.toSquare(): Square? {
    if(this.length != 2) throw IllegalArgumentException()
    if (this.toSquareOrNull() == null) throw IllegalArgumentException()
    else return this.toSquareOrNull()
}

fun CreateSquares(): List<Square> {
    val list = mutableListOf<Square>()
    var index = 0
    while (index != (BOARD_DIM*BOARD_DIM)){
        list.add(Square(index))
        index++
    }
    return list
}

fun Square(row: Row, col: Column): Square {
    return Square.values[row.index * BOARD_DIM + col.index]
}

