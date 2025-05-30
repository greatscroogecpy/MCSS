import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

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
//        logger.printStats(0);

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

            if (Params.SCENARIO == Params.LUMINOSITY_SCENARIO.RAMP_UP_RAMP_DOWN) {
                if (tick > 200 && tick <= 400) {
                    Params.SOLAR_LUMINOSITY = roundTo4(Params.SOLAR_LUMINOSITY + 0.005);
                } else if (tick > 600 && tick <= 850) {
                    Params.SOLAR_LUMINOSITY = roundTo4(Params.SOLAR_LUMINOSITY - 0.0025);
                }
            } else if (Params.SCENARIO == Params.LUMINOSITY_SCENARIO.LOW_SOLAR_LUMINOSITY) {
                Params.SOLAR_LUMINOSITY = 0.6;
            } else if (Params.SCENARIO == Params.LUMINOSITY_SCENARIO.OUR_SOLOAR_LUMINOSITY) {
                Params.SOLAR_LUMINOSITY = 1.0;
            } else if (Params.SCENARIO == Params.LUMINOSITY_SCENARIO.HIGH_SOLAR_LUMINOSITY) {
                Params.SOLAR_LUMINOSITY = 1.4;
            }
            
            // Print statistics every 10 steps
//            if (tick % 10 == 0) {
//                logger.printStats(tick);
//            }
            logger.printStats(tick);

        }

        System.out.println("Simulation completed!");
        System.out.println("Results can be analyzed from the console output above");
    }

    private static double roundTo4(double val) {
        return Math.round(val * 10000.0) / 10000.0;
    }
}