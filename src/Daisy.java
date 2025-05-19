public class Daisy {
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
} 