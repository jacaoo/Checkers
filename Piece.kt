package isel.leic.tds.damas.model
import Player


class Piece(val player: Player) {
    var queen = false
    fun toQueen() {
        queen = true
    }
    override fun toString(): String {
        return if (queen) player.name.uppercase() else player.name
    }
    override fun equals(other: Any?): Boolean {
        return if ( other is Piece ) player == other.player else error("Can't compare different types")
    }
}