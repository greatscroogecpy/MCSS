import java.util.Collections;
import java.io.FileWriter;
import java.io.IOException;

// Since all files are in the same package (src), no import is needed
public class Simulation {
    public static void main(String[] args) {
        World world = new World();
        world.worldInit();

        Logger logger = new Logger(world);
        
        // Create CSV filename based on parameters
        String csvFilename = String.format("%.2f_%.2f_%.2f.csv", 
                              Params.SOLAR_LUMINOSITY, 
                              Params.START_WHITE_RATIO, 
                              Params.START_BLACK_RATIO);
        
        // Create CSV file and write header
        try (FileWriter csvWriter = new FileWriter(csvFilename)) {
            // Write header
            csvWriter.append("Tick,AverageTemperature,WhiteDaisyCount,BlackDaisyCount,TotalDaisyCount,SolarLuminosity\n");
            
            for (int t = 0; t < Params.MAX_SIMULATION_STEPS; t++) {
                logger.printStats(t);
                
                // Write data to CSV
                int whiteCount = logger.getWhiteCount();
                int blackCount = logger.getBlackCount();
                int totalCount = whiteCount + blackCount;
                double avgTemp = logger.getAverageTemperature();
                
                csvWriter.append(String.format("%d,%.2f,%d,%d,%d,%.4f\n", 
                                t, avgTemp, whiteCount, blackCount, totalCount, Params.SOLAR_LUMINOSITY));
                
                world.updateTemperatures();
                world.diffuseTemperature();
                world.checkAllDaisySurvivals();

                // === SCENARIO 控制 ===
                if (Params.SCENARIO== Params.LUMINOSITY_SCENARIO.RAMP_UP_RAMP_DOWN) {
                    if (t > 200 && t <= 400) {
                        Params.SOLAR_LUMINOSITY = roundTo4(Params.SOLAR_LUMINOSITY + 0.005);
                    } else if (t > 600 && t <= 850) {
                        Params.SOLAR_LUMINOSITY = roundTo4(Params.SOLAR_LUMINOSITY - 0.0025);
                    }
                } else if (Params.SCENARIO== Params.LUMINOSITY_SCENARIO.LOW_SOLAR_LUMINOSITY) {
                    Params.SOLAR_LUMINOSITY = 0.6;
                } else if (Params.SCENARIO== Params.LUMINOSITY_SCENARIO.OUR_SOLOAR_LUMINOSITY) {
                    Params.SOLAR_LUMINOSITY = 1.0;
                } else if (Params.SCENARIO== Params.LUMINOSITY_SCENARIO.HIGH_SOLAR_LUMINOSITY) {
                    Params.SOLAR_LUMINOSITY = 1.4;
                }
            }
            
            System.out.println("Simulation results saved to " + csvFilename);
            
        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    private static double roundTo4(double val) {
        return Math.round(val * 10000.0) / 10000.0;
    }
}