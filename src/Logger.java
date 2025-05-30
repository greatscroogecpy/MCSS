import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.io.FileWriter;

public class Logger {
    private final World world;

    public Logger(World world) {
        this.world = world;
    }

    public int getWhiteCount() {
        int count = 0;
        for (int i = 0; i < world.getSize(); i++) {
            for (int j = 0; j < world.getSize(); j++) {
                Daisy daisy = world.getGrid()[i][j].getDaisy();
                if (daisy != null && daisy.getColor() == Daisy.Color.WHITE) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getBlackCount() {
        int count = 0;
        for (int i = 0; i < world.getSize(); i++) {
            for (int j = 0; j < world.getSize(); j++) {
                Daisy daisy = world.getGrid()[i][j].getDaisy();
                if (daisy != null && daisy.getColor() == Daisy.Color.BLACK) {
                    count++;
                }
            }
        }
        return count;
    }

    public int getGrayCount() {
        int count = 0;
        for (int i = 0; i < world.getSize(); i++) {
            for (int j = 0; j < world.getSize(); j++) {
                Daisy daisy = world.getGrid()[i][j].getDaisy();
                if (daisy != null && daisy.getColor() == Daisy.Color.GRAY) {
                    count++;
                }
            }
        }
        return count;
    }

    public double getAverageTemperature() {
        double sum = 0.0;
        int size = world.getSize();
        Patch[][] grid = world.getGrid();
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                sum += grid[i][j].getTemperature();
        return sum / (size * size);
    }

    public void printStats(int tick) {
        int white = getWhiteCount();
        int black = getBlackCount();
        int gray = getGrayCount();
        int total = white + black + gray;
        double temp = getAverageTemperature();
        System.out.printf("Tick %d | Temp: %.2f°C | White: %d | Black: %d | Gray: %d | Total: %d\n",
                tick, temp, white, black, gray, total);
    }

    public void printGrid() {
        Patch[][] grid = world.getGrid();
        int size = world.getSize();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Daisy d = grid[i][j].getDaisy();
                if (d == null) {
                    System.out.print(". ");
                } else if (d.getColor() == Daisy.Color.WHITE) {
                    System.out.print("W ");
                } else if (d.getColor() == Daisy.Color.BLACK) {
                    System.out.print("B ");
                } else {
                    System.out.print("G ");
                }
            }
            System.out.println();
        }
    }
}