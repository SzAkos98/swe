package game;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static game.MakePiece.makePiece;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MakePieceTest {

    /**
     * Tábla koordináta meghatározó függvény teszt.
     */
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

    /**
     * "Karakter" létrehozó függvény teszt.
     */
    @Test
    void testMakePiece() {
        Piece piece = makePiece(PieceType.RED, 1, 1);

        assertEquals(PieceType.RED, piece.getType());
        assertNotEquals(PieceType.BLUE, piece.getType());
        assertNotEquals(PieceType.GHOSTB, piece.getType());
        assertNotEquals(PieceType.GHOSTR, piece.getType());
    }

    @Test
    void testWinner() {
        String[][] s;
        s = new String[][]{
                {"R", "", "R", "x", "R", "x", "x", "R", "R", "x"},
                {"x", "x", "x", "R", "x", "R", "x", "R", "R", "x"},
                {"x", "x", "B", "R", "x", "R", "B", "x", "x", "x"},
                {"x", "B", "x", "R", "x", "x", "B", "B", "R", "x"},
                {"x", "B", "x", "R", "x", "x", "x", "x", "R", "x"},
                {"x", "B", "x", "R", "x", "x", "x", "x", "x", "x"},
                {"x", "B", "B", "R", "x", "x", "x", "x", "x", "x"},
                {"x", "x", "x", "B", "x", "R", "R", "R", "x", "x"},
                {"x", "x", "B", "R", "x", "x", "x", "x", "x", "x"},
                {"x", "x", "B", "B", "R", "x", "R", "R", "R", "R"}};

        Assert.assertEquals("RED", game.MakePiece.Winner(s));
        Assert.assertNotEquals("BLUE", game.MakePiece.Winner(s));

        s = new String[][]{
                {"R", null, "R", null, null, null, null, "R", "R", null},
                {null, null, null, "R", null, "R", null, "R", "R", null},
                {null, null, "B", "R", null, "R", "B", null, null, null},
                {null, "B", null, "R", null, null, "B", "B", "R", null},
                {null, "B", null, "R", null, null, null, null, "R", null},
                {null, "B", null, "R", null, null, null, null, null, null},
                {null, "B", "B", "R", null, null, null, null, null, null},
                {null, null, null, "B", null, "R", "R", "R", null, null},
                {null, null, "B", "R", null, null, null, null, null, null},
                {null, null, "B", "B", "R", null, "R", "R", "R", "R"}};

        Assert.assertEquals("RED", game.MakePiece.Winner(s));
        Assert.assertNotEquals("BLUE", game.MakePiece.Winner(s));

        s = new String[][]{
                {"R", null, "R", null, null, null, null, "R", "R", null},
                {null, null, null, null, null, "R", null, "R", "R", null},
                {null, "B", "B", null, null, "R", "B", null, "B", "B"},
                {null, "B", null, "R", null, null, "B", "B", "R", null},
                {null, "B", null, "R", null, null, null, null, "R", null},
                {null, "B", null, "R", null, null, null, null, null, null},
                {null, "B", "B", "R", null, null, null, null, null, null},
                {null, null, null, "B", null, "R", "R", "R", null, null},
                {null, null, "B", null, null, null, null, null, null, null},
                {null, null, "B", "B", null, null, "R", "R", "R", "R"}};

        Assert.assertEquals("BLUE", game.MakePiece.Winner(s));
        Assert.assertNotEquals("RED", game.MakePiece.Winner(s));

        s = new String[][]{
                {"R", null, "R", null, null, null, null, "R", null, null},
                {null, null, null, null, null, "R", null, "R", "R", null},
                {null, "B", null, null, null, "R", "B", null, "B", "B"},
                {null, "B", null, "R", null, null, "B", "B", "R", null},
                {null, "B", null, "R", null, null, null, null, "R", null},
                {null, "B", null, "R", null, null, null, null, null, null},
                {null, "B", "B", "R", null, null, null, null, null, null},
                {null, null, null, "B", null, "R", "R", "R", null, null},
                {null, null, "B", null, null, null, null, null, null, null},
                {null, null, "B", "B", null, null, "R", "R", "R", "R"}};

        Assert.assertEquals("BLUE", game.MakePiece.Winner(s));
        Assert.assertNotEquals("RED", game.MakePiece.Winner(s));
    }
}