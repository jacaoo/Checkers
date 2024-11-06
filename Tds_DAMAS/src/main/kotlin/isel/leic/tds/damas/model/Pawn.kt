package isel.leic.tds.damas.model

class Pawn (player: Player) : Piece(player) {
    override fun canMove(to: Square, from: Square, board: Board): Boolean {
        require(board.moves.keys.indexOf(to) == board.moves.keys.indexOf(from) - (BOARD_DIM - 1) ||
                    board.moves.keys.indexOf(to) == board.moves.keys.indexOf(from) - (BOARD_DIM + 1) && Square(
                board.moves.keys.indexOf(to)).black
        ) { "Invalid Position" }
        require(board.moves.values.toList()[to.index] == null) { "Position already occupied" }
        return true
    }

    override fun canKill(to: Square, from: Square, board: Board): Boolean {
        if (board.player == Player.w) {
            require(
                board.moves.keys.indexOf(to) == board.moves.keys.indexOf(from) - (BOARD_DIM * 2 - 2) ||
                        board.moves.keys.indexOf(to) == board.moves.keys.indexOf(from) - (BOARD_DIM * 2 + 2)
            ) { "Invalid PLAY" }
            require(board.moves.values.toList()[(board.moves.keys.indexOf(from) - (BOARD_DIM - 1))] != null &&
                    board.moves.values.toList()[(board.moves.keys.indexOf(from) - (BOARD_DIM - 1))]?.player == board.player.other
                    || board.moves.values.toList()[(board.moves.keys.indexOf(from) - (BOARD_DIM + 1))] != null &&
                    board.moves.values.toList()[(board.moves.keys.indexOf(from) - (BOARD_DIM + 1))]?.player == board.player.other
            ) { "Impossible Play" }
        }
        else {
            require(
                board.moves.keys.indexOf(to) == board.moves.keys.indexOf(from) + (BOARD_DIM * 2 - 2) ||
                        board.moves.keys.indexOf(to) == board.moves.keys.indexOf(from) + (BOARD_DIM * 2 + 2)
            ) { "Invalid PLAY" }
            require(board.moves.values.toList()[(board.moves.keys.indexOf(from) + (BOARD_DIM - 1))] != null &&
                    board.moves.values.toList()[(board.moves.keys.indexOf(from) + (BOARD_DIM - 1))]?.player == board.player.other
                    || board.moves.values.toList()[(board.moves.keys.indexOf(from) + (BOARD_DIM + 1))] != null &&
                    board.moves.values.toList()[(board.moves.keys.indexOf(from) + (BOARD_DIM + 1))]?.player == board.player.other

            ) { "Impossible Play" }
        }
        return true
    }
}

