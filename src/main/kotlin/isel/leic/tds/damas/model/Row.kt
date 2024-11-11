package isel.leic.tds.damas.model


@JvmInline
value class Row (val index: Int) {
    val digit: Char get() = '0' + (BOARD_DIM - index)
    init {
        if (index !in 0..<BOARD_DIM) throw IllegalArgumentException("Invalid row index: $index")
    }
    companion object {
        val values = List(BOARD_DIM) { Row(it) }
    }

}

fun Char.toRowOrNull(): Row? {
    val rindex =  BOARD_DIM - (this - '0')
    return if (this !in '1'..BOARD_DIM.digitToChar()) null
    else Row(rindex)
}