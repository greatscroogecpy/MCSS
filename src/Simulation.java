import java.io.FileWriter;
import java.io.IOException;

// Since all files are in the same package (src), no import is needed
public class Simulation {
    public static void main(String[] args) {
        System.out.println("=== Daisyworld Simulation ===");
        System.out.println("Parameter Settings:");
        System.out.println("  White Daisy Albedo: " + Params.WHITE_DAISY_ALBEDO);
        System.out.println("  Black Daisy Albedo: " + Params.BLACK_DAISY_ALBEDO);
        System.out.println("  Surface Albedo: " + Params.SURFACE_ALBEDO);
        System.out.println("  Solar Luminosity: " + Params.SOLAR_LUMINOSITY);
        System.out.println("  Initial White Ratio: " + Params.START_WHITE_RATIO);
        System.out.println("  Initial Black Ratio: " + Params.START_BLACK_RATIO);
        System.out.println("  Soil Quality Heterogeneity: " + (Params.ENABLE_SPATIAL_HETEROGENEITY ? "Enabled" : "Disabled"));
        if (Params.ENABLE_SPATIAL_HETEROGENEITY) {
            System.out.println("  Soil Quality Variance: " + Params.SOIL_QUALITY_VARIANCE);
        }

        World world = new World();
        world.worldInit();

        Logger logger = new Logger(world);

        // Record initial state (step 0)
        System.out.println("Initial State (Step 0):");
        System.out.println("  Average Temperature: " + logger.getAverageTemperature());
        System.out.println("  White Daisies: " + logger.getWhiteCount());
        System.out.println("  Black Daisies: " + logger.getBlackCount());
        System.out.println("  Total Daisies: " + (logger.getWhiteCount() + logger.getBlackCount()));

        // Main simulation loop
        for (int tick = 1; tick <= Params.MAX_SIMULATION_STEPS; tick++) {
            world.updateTemperatures();
            world.diffuseTemperature();
            world.checkAllDaisySurvivals();
            
            // Print statistics every 10 steps
            if (tick % 10 == 0) {
                logger.printStats(tick);
            }
        }

        System.out.println("Simulation completed!");
        System.out.println("Results can be analyzed from the console output above");
    }

    private static double roundTo4(double val) {
        return Math.round(val * 10000.0) / 10000.0;
    }
}