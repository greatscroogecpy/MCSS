/**
 * This class centralizes all configuration parameters for the Daisyworld simulation.
 * Parameters from all other classes are consolidated here for easy modification.
 */
public class Params {
    // World parameters
    public static final int WORLD_SIZE = 29;

    // Initial distribution ratios
    public static double START_WHITE_RATIO=0.2;
    public static double START_BLACK_RATIO=0.2;
    public static double START_GRAY_RATIO=0.1;

    // Daisy parameters
    public static final int DAISY_MAX_AGE = 25;
    // Using actual parameter values shown in NetLogo interface
    public static final double WHITE_DAISY_ALBEDO = 0.75; // NetLogo default value
    public static final double BLACK_DAISY_ALBEDO = 0.25; // NetLogo default value
    public static final double GRAY_DAISY_ALBEDO = 0.5;  // Medium albedo for gray daisies

    // Cell parameters
    public static double DEFAULT_CELL_TEMPERATURE = 0.0;
    public static double SURFACE_ALBEDO = 0.4; // NetLogo default albedo-of-surface value

    // Solar parameters
    public static double SOLAR_LUMINOSITY = 0.8;

    // Scenario control
    public enum LUMINOSITY_SCENARIO {
        RAMP_UP_RAMP_DOWN,
        LOW_SOLAR_LUMINOSITY,
        OUR_SOLOAR_LUMINOSITY,
        HIGH_SOLAR_LUMINOSITY,
        MAINTAIN_CURRENT_LUMINOSITY
    }

    public static LUMINOSITY_SCENARIO SCENARIO =LUMINOSITY_SCENARIO.MAINTAIN_CURRENT_LUMINOSITY;

    // Spatial Heterogeneity parameters
    public static boolean ENABLE_SPATIAL_HETEROGENEITY = true;
    public static double SOIL_QUALITY_VARIANCE = 0.5; // Soil quality variance, controls soil quality variation range

    // Simulation parameters
    // Add more simulation parameters as needed
    public static int MAX_SIMULATION_STEPS = 1000; // Quick test
    public static double DIFFUSION_RATIO = 0.5;
} 