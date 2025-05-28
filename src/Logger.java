import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.None;
import java.util.*;

public class Logger {
    private final World world;

    // Data lists for charting
    private final List<Integer> ticks = new ArrayList<>();
    private final List<Double> temperatures = new ArrayList<>();
    private final List<Integer> whiteCounts = new ArrayList<>();
    private final List<Integer> blackCounts = new ArrayList<>();

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

    public void record(int tick) {
        ticks.add(tick);
        temperatures.add(getAverageTemperature());
        whiteCounts.add(getWhiteCount());
        blackCounts.add(getBlackCount());
    }

    public void printStats(int tick) {
        int white = getWhiteCount();
        int black = getBlackCount();
        int total = white + black;
        double temp = getAverageTemperature();
        System.out.printf("Tick %d | Temp: %.2f°C | White: %d | Black: %d | Total: %d\n",
                tick, temp, white, black, total);
    }

    public void displayCharts() {
        // Temperature Chart
        XYChart tempChart = new XYChartBuilder().width(800).height(400)
                .title("Average Temperature Over Time")
                .xAxisTitle("Tick").yAxisTitle("Temperature (°C)").build();
        tempChart.addSeries("Temperature", ticks, temperatures).setMarker(new None());

        // Daisy Count Chart
        XYChart daisyChart = new XYChartBuilder().width(800).height(400)
                .title("Daisy Count Over Time")
                .xAxisTitle("Tick").yAxisTitle("Count").build();
        daisyChart.addSeries("White Daisies", ticks, whiteCounts).setMarker(new None());
        daisyChart.addSeries("Black Daisies", ticks, blackCounts).setMarker(new None());

        // Show charts
        new SwingWrapper<>(Arrays.asList(tempChart, daisyChart)).displayChartMatrix();
    }
}