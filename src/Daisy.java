public class Daisy {
    private static final int MAX_AGE = 25; // set the max age of daisy
    public static final double WHITE_ALBEDO = 0.75;
    public static final double BLACK_ALBEDO = 0.25;

    public enum Color {
        WHITE, BLACK
    }

    private Color color;
    private int age;

    public Daisy(Color color) {
        this.color = color;
        this.age = 0;
    }

    public Color getColor() {
        return color;
    }

    public int getAge() {
        return age;
    }

    public void incrementAge() {
        this.age++;
    }

    public boolean isDead() {
        return this.age > MAX_AGE;
    }

} 