package game;

/**
 * A program ál Main osztálya.
 * <p>
 * Ez az osztály indul indításkor, és hívja a {@link GameMain} osztály
 * {@code main(String[])} függvényét.
 * A megoldásról több:
 * https://github.com/javafxports/openjdk-jfx/issues/236
 */
public class Main {
    /**
     * Main függvény.
     *
     * @param args parancssor argumentumok
     */
    public static void main(String[] args) {
        GameMain.main(args);
    }
}

