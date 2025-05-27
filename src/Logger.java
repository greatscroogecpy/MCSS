public class Logger {
    private final World world;

    public Logger(World world) {
        this.world = world;
    }

    public int getWhiteCount() {
        int count = 0;
        for (int i = 0; i < world.getSize(); i++)
            for (int j = 0; j < world.getSize(); j++) {
                Daisy d = world.getGrid()[i][j].getDaisy();
                if (d != null && d.getColor() == Daisy.Color.WHITE)
                    count++;
            }
        return count;
    }

    public int getBlackCount() {
        int count = 0;
        for (int i = 0; i < world.getSize(); i++)
            for (int j = 0; j < world.getSize(); j++) {
                Daisy d = world.getGrid()[i][j].getDaisy();
                if (d != null && d.getColor() == Daisy.Color.BLACK)
                    count++;
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
        int total = white + black;
        double temp = getAverageTemperature();
        System.out.printf("Tick %d | Temp: %.2f°C | White: %d | Black: %d | Total: %d\n",
                tick, temp, white, black, total);
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
                } else {
                    System.out.print("B ");
                }
            }
            System.out.println();
        }
    }
}