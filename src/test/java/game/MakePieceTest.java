package game;

import org.junit.jupiter.api.Test;

import static game.MakePiece.makePiece;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class MakePieceTest {

    @Test
    void testToBoard() {
        assertEquals(6, game.MakePiece.toBoard(420));
        assertEquals(7, game.MakePiece.toBoard(500));
        assertEquals(10, game.MakePiece.toBoard(720));
        assertEquals(0, game.MakePiece.toBoard(10));
        assertNotEquals(7, game.MakePiece.toBoard(420));
        assertNotEquals(10, game.MakePiece.toBoard(683));
        assertNotEquals(0, game.MakePiece.toBoard(37));
        assertNotEquals(4, game.MakePiece.toBoard(251));

    }

    @Test
    void testMakePiece() {
        Piece piece = makePiece(PieceType.RED, 1, 1);

        assertEquals(PieceType.RED, piece.getType());
        assertNotEquals(PieceType.BLUE, piece.getType());
        assertNotEquals(PieceType.GHOSTB, piece.getType());
        assertNotEquals(PieceType.GHOSTR, piece.getType());
    }

}