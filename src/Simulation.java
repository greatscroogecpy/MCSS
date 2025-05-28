import java.io.FileWriter;
import java.io.IOException;

// Since all files are in the same package (src), no import is needed
public class Simulation {
    public static void main(String[] args) {
        System.out.println("=== Daisyworld Simulation ===");
        System.out.println("Parameter Settings:");
        System.out.println("  White Daisy Albedo: " + Params.WHITE_DAISY_ALBEDO);
        System.out.println("  Black Daisy Albedo: " + Params.BLACK_DAISY_ALBEDO);
        System.out.println("  Gray Daisy Albedo: " + Params.GRAY_DAISY_ALBEDO);
        System.out.println("  Surface Albedo: " + Params.SURFACE_ALBEDO);
        System.out.println("  Solar Luminosity: " + Params.SOLAR_LUMINOSITY);
        System.out.println("  Initial White Ratio: " + Params.START_WHITE_RATIO);
        System.out.println("  Initial Black Ratio: " + Params.START_BLACK_RATIO);
        System.out.println("  Initial Gray Ratio: " + Params.START_GRAY_RATIO);
        System.out.println("  Soil Quality Heterogeneity: " + (Params.ENABLE_SPATIAL_HETEROGENEITY ? "Enabled" : "Disabled"));
        if (Params.ENABLE_SPATIAL_HETEROGENEITY) {
            System.out.println("  Soil Quality Variance: " + Params.SOIL_QUALITY_VARIANCE);
        }

        World world = new World();
        world.worldInit();

        Logger logger = new Logger(world);

        // Create CSV output
        try (FileWriter csvWriter = new FileWriter("daisyworld_results.csv")) {
            csvWriter.append("Step,Temperature,WhiteDaisies,BlackDaisies,GrayDaisies,TotalDaisies,SolarLuminosity\n");
            
            // Record initial state (step 0)
            csvWriter.append(String.format("%d,%.4f,%d,%d,%d,%d,%.4f\n", 
                0, logger.getAverageTemperature(), logger.getWhiteCount(), 
                logger.getBlackCount(), logger.getGrayCount(),
                logger.getWhiteCount() + logger.getBlackCount() + logger.getGrayCount(),
                Params.SOLAR_LUMINOSITY));

            // Record initial state (step 0)
            System.out.println("Initial State (Step 0):");
            System.out.println("  Average Temperature: " + logger.getAverageTemperature());
            System.out.println("  White Daisies: " + logger.getWhiteCount());
            System.out.println("  Black Daisies: " + logger.getBlackCount());
            System.out.println("  Gray Daisies: " + logger.getGrayCount());
            System.out.println("  Total Daisies: " + (logger.getWhiteCount() + logger.getBlackCount() + logger.getGrayCount()));

            // Main simulation loop
            for (int tick = 1; tick <= Params.MAX_SIMULATION_STEPS; tick++) {
                world.updateTemperatures();
                world.diffuseTemperature();
                world.checkAllDaisySurvivals();
                
                // Record data to CSV
                csvWriter.append(String.format("%d,%.4f,%d,%d,%d,%d,%.4f\n", 
                    tick, logger.getAverageTemperature(), logger.getWhiteCount(), 
                    logger.getBlackCount(), logger.getGrayCount(),
                    logger.getWhiteCount() + logger.getBlackCount() + logger.getGrayCount(),
                    Params.SOLAR_LUMINOSITY));
                
                // Print statistics every 10 steps
                if (tick % 10 == 0) {
                    logger.printStats(tick);
                }
            }

            System.out.println("Simulation completed!");
            System.out.println("Results saved to daisyworld_results.csv");
            
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
            System.out.println("Simulation completed (without CSV output)!");
        }
    }

    private static double roundTo4(double val) {
        return Math.round(val * 10000.0) / 10000.0;
    }
}