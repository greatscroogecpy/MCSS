import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
//import org.knowm.xchart.*;
//import org.knowm.xchart.style.markers.None;
//import org.knowm.xchart.BitmapEncoder;
//import org.knowm.xchart.BitmapEncoder.BitmapFormat;

    public class Logger {

        private final List<Integer> ticks = new ArrayList<>();
        private final List<Double> temperatures = new ArrayList<>();
        private final List<Integer> whiteCounts = new ArrayList<>();
        private final List<Integer> blackCounts = new ArrayList<>();
        private final World world;
        private FileWriter csvWriter;

        public Logger(World world) {
            this.world = world;

            try {
                csvWriter = new FileWriter("daisyworld_output.csv");
                csvWriter.append("Tick,AverageTemperature,WhiteCount,BlackCount,TotalCount,SolarLuminosity\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void closeCSV() {
            try {
                csvWriter.flush();
                csvWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
            double avgTemp = getAverageTemperature();
            int whiteCount = getWhiteCount();
            int blackCount = getBlackCount();
            int totalCount = whiteCount + blackCount;
            double luminosity = Params.SOLAR_LUMINOSITY;

            // 控制台输出
            System.out.printf("Step %d - Temp: %.2f, White: %d, Black: %d, Total: %d, Luminosity: %.4f\n",
                    tick, avgTemp, whiteCount, blackCount, totalCount, luminosity);

            // 写入CSV
            try {
                csvWriter.append(String.format("%d,%.4f,%d,%d,%d,%.4f\n",
                        tick, avgTemp, whiteCount, blackCount, totalCount, luminosity));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public List<Integer> getTicks() {
            return ticks;
        }

        public List<Double> getTemperatures() {
            return temperatures;
        }

        public List<Integer> getWhiteCounts() {
            return whiteCounts;
        }

        public List<Integer> getBlackCounts() {
            return blackCounts;
        }


//        public static void displayCharts(List<Integer> ticks,
//                                         List<Double> avgTemperatures,
//                                         List<Integer> avgWhiteCounts,
//                                         List<Integer> avgBlackCounts, String folderName) {
//
//            XYChart tempChart = new XYChartBuilder().width(800).height(400)
//                    .title("Average Temperature Over Time")
//                    .xAxisTitle("Tick").yAxisTitle("Temperature (°C)").build();
//            tempChart.addSeries("Temperature", ticks, avgTemperatures).setMarker(new None());
//
//            XYChart daisyChart = new XYChartBuilder().width(800).height(400)
//                    .title("Daisy Count Over Time")
//                    .xAxisTitle("Tick").yAxisTitle("Count").build();
//            daisyChart.addSeries("White Daisies", ticks, avgWhiteCounts).setMarker(new None());
//            daisyChart.addSeries("Black Daisies", ticks, avgBlackCounts).setMarker(new None());
//
//            // show charts
//            new SwingWrapper<>(Arrays.asList(tempChart, daisyChart)).displayChartMatrix();
//
//            // Save as png
//            try {
//                BitmapEncoder.saveBitmap(tempChart, folderName + "/TemperatureChart", BitmapFormat.PNG);
//                BitmapEncoder.saveBitmap(daisyChart, folderName + "/DaisyChart", BitmapFormat.PNG);
//                System.out.println("Successfully saved as TemperatureChart.png 和 DaisyChart.png");
//            } catch (IOException e) {
//                System.err.println("Failure Saving: " + e.getMessage());
//
//            }
//        }


}