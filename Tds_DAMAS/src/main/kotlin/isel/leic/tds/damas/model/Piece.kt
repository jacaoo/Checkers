package isel.leic.tds.damas.model


class Piece(val player: Player) {
    val queen = mutableListOf(false)
    fun toQueen() {
        queen[0] = true
    }
    override fun toString(): String {
        return if (queen[0]) player.name.uppercase() else player.name
    }
    override fun equals(other: Any?): Boolean {
        return if ( other is Piece ) player == other.player else error("Can't compare different types")
    }
}

fun checkQueen(to: Square, player: Player) : Boolean {
    return if (player != Player.b) {
        to.row.index == BOARD_DIM - 1
    }
    else {
        to.row.index == 0
    }
}




