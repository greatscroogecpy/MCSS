import java.util.Random;

// All files are in the default package
public class Daisy {
    public void setAge(int age) {
        this.age = age;
    }

    public enum Color {
        WHITE, BLACK
    }

    private Color color;
    private int age;


    public Daisy(Color color) {
        this.color = color;
        this.age = 0;
    }

    public Daisy(Color color, int age) {
        this.color = color;
        this.age = age;
    }

    public Color getColor() {
        return color;
    }


    public void incrementAge() {
        this.age++;
    }

    public boolean isDead() {
        return this.age >= Params.DAISY_MAX_AGE;
    }
} 