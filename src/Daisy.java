// All files are in the default package
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

    public boolean isDead() {
        return this.age > Params.DAISY_MAX_AGE;
    }
    
    public double getAlbedo() {
        return color == Color.WHITE ? Params.WHITE_DAISY_ALBEDO : Params.BLACK_DAISY_ALBEDO;
    }
} 