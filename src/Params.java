/**
 * This class centralizes all configuration parameters for the Daisyworld simulation.
 * Parameters from all other classes are consolidated here for easy modification.
 */
public class Params {
    public enum LUMINOSITY_SCENARIO {
        RAMP_UP_RAMP_DOWN,
        OUR_SOLOAR_LUMINOSITY,
        MAINTAIN_CURRENT_LUMINOSITY,
        LOW_SOLAR_LUMINOSITY,
        HIGH_SOLAR_LUMINOSITY

    }
    // World parameters
    public static int WORLD_SIZE = 29;
    public static double SOLAR_LUMINOSITY = 0.8;

    public static double START_WHITE_RATIO=0.2;
    public static double START_BLACK_RATIO=0.2;

    // Daisy parameters
    public static final int DAISY_MAX_AGE = 25;
    // 使用界面上显示的实际参数值
    public static final double WHITE_DAISY_ALBEDO = 0.75; // 从截图看到的值
    public static final double BLACK_DAISY_ALBEDO = 0.25; // 从截图看到的值

    // Cell parameters
    public static double DEFAULT_CELL_TEMPERATURE = 0.0;
    public static double SURFACE_ALBEDO = 0.40;

    public static LUMINOSITY_SCENARIO SCENARIO =LUMINOSITY_SCENARIO.RAMP_UP_RAMP_DOWN;


    // Simulation parameters
    // Add more simulation parameters as needed
    public static int MAX_SIMULATION_STEPS = 1000;
    public static double DIFFUSION_RATIO = 0.5;

    // Temperature calculation parameters
    // These can be added based on the temperature calculation logic
    public static double STEFAN_BOLTZMANN = 5.67E-8; // Stefan-Boltzmann constant if needed

    // Daisy growth parameters
    // These can be customized for the simulation
    public static double OPTIMAL_TEMPERATURE = 22.5; // Optimal temperature for daisy growth
    public static final double TEMPERATURE_RANGE = 17.5;
    public static double GROWTH_RATE_COEFFICIENT = 0.1; // Coefficient for growth rate calculation
} 