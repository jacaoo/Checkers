package isel.leic.tds.damas.model

import BOARD_DIM

@JvmInline
value class Column(val index: Int){
    val symbol: Char get() = 'a' + index
    init {
        if (index !in 0..<BOARD_DIM) throw IllegalArgumentException("Invalid column index: $index")
    }
    companion object {
        val values = List(BOARD_DIM){Column(it)}
    }
}
fun Char.toColumnOrNull(): Column? {
    return if (this !in 'a'..'a' + (BOARD_DIM - 1)) null
    else Column(this - 'a')
}

