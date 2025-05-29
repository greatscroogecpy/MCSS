import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Simulation {
    public static void main(String[] args) {
        final int RUNS = 20;
        final int MAX_TICKS = Params.MAX_SIMULATION_STEPS;

        List<Double> sumTemperatures = new ArrayList<>(Collections.nCopies(MAX_TICKS, 0.0));
        List<Integer> sumWhiteCounts = new ArrayList<>(Collections.nCopies(MAX_TICKS, 0));
        List<Integer> sumBlackCounts = new ArrayList<>(Collections.nCopies(MAX_TICKS, 0));

        for (int run = 0; run < RUNS; run++) {
            World world = new World();
            world.worldInit();
            Logger logger = new Logger(world);

            for (int t = 0; t < MAX_TICKS; t++) {
                if (t > 0) {
                    world.updateTemperatures();
                    world.diffuseTemperature();
                    world.checkAllDaisySurvivals();
                }
                logger.record(t);

                if (Params.SCENARIO == Params.LUMINOSITY_SCENARIO.RAMP_UP_RAMP_DOWN) {
                    if (t > 200 && t <= 400) {
                        Params.SOLAR_LUMINOSITY = roundTo4(Params.SOLAR_LUMINOSITY + 0.005);
                    } else if (t > 600 && t <= 850) {
                        Params.SOLAR_LUMINOSITY = roundTo4(Params.SOLAR_LUMINOSITY - 0.0025);
                    }
                } else if (Params.SCENARIO == Params.LUMINOSITY_SCENARIO.LOW_SOLAR_LUMINOSITY) {
                    Params.SOLAR_LUMINOSITY = 0.6;
                } else if (Params.SCENARIO == Params.LUMINOSITY_SCENARIO.OUR_SOLOAR_LUMINOSITY) {
                    Params.SOLAR_LUMINOSITY = 1.0;
                } else if (Params.SCENARIO == Params.LUMINOSITY_SCENARIO.HIGH_SOLAR_LUMINOSITY) {
                    Params.SOLAR_LUMINOSITY = 1.4;
                }
            }

            for (int t = 0; t < MAX_TICKS; t++) {
                sumTemperatures.set(t, sumTemperatures.get(t) + logger.getTemperatures().get(t));
                sumWhiteCounts.set(t, sumWhiteCounts.get(t) + logger.getWhiteCounts().get(t));
                sumBlackCounts.set(t, sumBlackCounts.get(t) + logger.getBlackCounts().get(t));
            }
        }

        List<Double> avgTemperatures = new ArrayList<>();
        List<Integer> avgWhiteCounts = new ArrayList<>();
        List<Integer> avgBlackCounts = new ArrayList<>();
        List<Integer> ticks = new ArrayList<>();

        for (int t = 0; t < MAX_TICKS; t++) {
            ticks.add(t);
            avgTemperatures.add(sumTemperatures.get(t) / RUNS);
            avgWhiteCounts.add(sumWhiteCounts.get(t) / RUNS);
            avgBlackCounts.add(sumBlackCounts.get(t) / RUNS);
        }

        String folderName = String.format("Result/avg_%.2f_%.2f_%.2f_%s",
                Params.SOLAR_LUMINOSITY,
                Params.START_WHITE_RATIO,
                Params.START_BLACK_RATIO,
                Params.SCENARIO.name());
        new File(folderName).mkdirs();

        String csvFilename = folderName + "/result.csv";

        try (FileWriter csvWriter = new FileWriter(csvFilename)) {
            csvWriter.append("Tick,AverageTemperature,WhiteDaisyCount,BlackDaisyCount\n");
            for (int t = 0; t < MAX_TICKS; t++) {
                csvWriter.append(String.format("%d,%.2f,%d,%d\n",
                        ticks.get(t), avgTemperatures.get(t), avgWhiteCounts.get(t), avgBlackCounts.get(t)));
            }
        } catch (IOException e) {
            System.err.println("Error writing CSV: " + e.getMessage());
        }

        Logger.displayCharts(ticks, avgTemperatures, avgWhiteCounts, avgBlackCounts,folderName);
    }

    private static double roundTo4(double val) {
        return Math.round(val * 10000.0) / 10000.0;
    }
}
