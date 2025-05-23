import java.util.Collections;

// Since all files are in the same package (src), no import is needed
public class Simulation {
    public static void main(String[] args) {
        World world = new World();
        world.worldInit();

        Logger logger = new Logger(world);

        for (int t = 0; t < Params.MAX_SIMULATION_STEPS; t++) {
            logger.printStats(t);
            world.updateTemperatures();
            world.diffuseTemperature();
            world.checkAllDaisySurvivals();


            // world.printGrid();
//            System.out.println();

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
    }

    private static double roundTo4(double val) {
        return Math.round(val * 10000.0) / 10000.0;
    }
}