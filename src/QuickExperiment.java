/**
 * Quick experiment runner - specifically for testing new features
 * Runs multiple replications for statistical reliability
 */
public class QuickExperiment {
    
    private static final int NUM_REPLICATIONS = 30;  // Number of replications for statistical analysis
    
    public static void main(String[] args) {
        System.out.println("=== Quick Experiment: Validate New Features ===");
        System.out.println("Running " + NUM_REPLICATIONS + " replications of " + Params.MAX_SIMULATION_STEPS + " steps each...\n");
        
        // Enable silent mode to suppress detailed output
        World.SILENT_MODE = true;
        
        try {
            // Run two core comparison experiments
            runSoilComparisonExperiment();
            runGrayDaisyExperiment();
        } finally {
            // Restore normal output mode
            World.SILENT_MODE = false;
        }
        
        System.out.println("\n=== Experiment Completed ===");
        System.out.println("Results are averaged over " + NUM_REPLICATIONS + " independent runs for statistical reliability.");
    }
    
    /**
     * Experiment A: Soil heterogeneity comparison (uniform vs heterogeneous)
     */
    public static void runSoilComparisonExperiment() {
        System.out.println("--- Experiment A: Soil Heterogeneity Effect Validation ---");
        
        // Save original settings
        boolean originalHeterogeneity = Params.ENABLE_SPATIAL_HETEROGENEITY;
        int originalSteps = Params.MAX_SIMULATION_STEPS;
        
        try {
            // Control group: uniform soil (multiple runs)
            System.out.print("Running uniform soil experiments... ");
            Params.ENABLE_SPATIAL_HETEROGENEITY = false;
            ExperimentStats uniformStats = runMultipleExperiments("uniform_soil");
            System.out.println("Done.");
            
            // Experimental group: heterogeneous soil (multiple runs)
            System.out.print("Running heterogeneous soil experiments... ");
            Params.ENABLE_SPATIAL_HETEROGENEITY = true;
            Params.SOIL_QUALITY_VARIANCE = 0.5; // Medium heterogeneity
            ExperimentStats heteroStats = runMultipleExperiments("heterogeneous_soil");
            System.out.println("Done.");
            
            // Compare results
            System.out.println("\nSoil Heterogeneity Experiment Results:");
            System.out.printf("Uniform Soil   - Final Temp: %.2f±%.2f°C, Total Daisies: %.1f±%.1f, Temp Stability: %.3f±%.3f\n", 
                uniformStats.meanFinalTemp, uniformStats.stdFinalTemp,
                uniformStats.meanTotalDaisies, uniformStats.stdTotalDaisies,
                uniformStats.meanTempStability, uniformStats.stdTempStability);
            System.out.printf("Heterogeneous  - Final Temp: %.2f±%.2f°C, Total Daisies: %.1f±%.1f, Temp Stability: %.3f±%.3f\n", 
                heteroStats.meanFinalTemp, heteroStats.stdFinalTemp,
                heteroStats.meanTotalDaisies, heteroStats.stdTotalDaisies,
                heteroStats.meanTempStability, heteroStats.stdTempStability);
            
            // Statistical significance tests
            double daisyDifference = heteroStats.meanTotalDaisies - uniformStats.meanTotalDaisies;
            double stabilityDifference = uniformStats.meanTempStability - heteroStats.meanTempStability; // Lower is better
            
            System.out.println("\nStatistical Analysis:");
            if (daisyDifference > 0) {
                System.out.printf("✓ Soil heterogeneity increased total daisy count by %.1f (%.1f%%)\n", 
                    daisyDifference, (daisyDifference/uniformStats.meanTotalDaisies)*100);
            } else {
                System.out.printf("✗ Soil heterogeneity decreased total daisy count by %.1f\n", Math.abs(daisyDifference));
            }
            
            if (stabilityDifference > 0) {
                System.out.printf("✓ Soil heterogeneity improved temperature stability by %.3f\n", stabilityDifference);
            } else {
                System.out.printf("✗ Soil heterogeneity reduced temperature stability by %.3f\n", Math.abs(stabilityDifference));
            }
            
        } finally {
            Params.ENABLE_SPATIAL_HETEROGENEITY = originalHeterogeneity;
            Params.MAX_SIMULATION_STEPS = originalSteps;
        }
    }
    
    /**
     * Experiment B: Gray daisy ecological role (two vs three species)
     */
    public static void runGrayDaisyExperiment() {
        System.out.println("\n--- Experiment B: Gray Daisy Ecological Role Validation ---");
        
        // Save original settings
        double originalGrayRatio = Params.START_GRAY_RATIO;
        int originalSteps = Params.MAX_SIMULATION_STEPS;
        
        try {
            // Control group: classic two-species daisies (multiple runs)
            System.out.print("Running two-species experiments... ");
            Params.START_GRAY_RATIO = 0.0;
            ExperimentStats twoSpeciesStats = runMultipleExperiments("two_species");
            System.out.println("Done.");
            
            // Experimental group: three species daisies (multiple runs)
            System.out.print("Running three-species experiments... ");
            Params.START_GRAY_RATIO = 0.1;
            ExperimentStats threeSpeciesStats = runMultipleExperiments("three_species");
            System.out.println("Done.");
            
            // Compare results
            System.out.println("\nDaisy Species Experiment Results:");
            System.out.printf("Two Species    - Final Temp: %.2f±%.2f°C, Total Daisies: %.1f±%.1f, Temp Stability: %.3f±%.3f\n", 
                twoSpeciesStats.meanFinalTemp, twoSpeciesStats.stdFinalTemp,
                twoSpeciesStats.meanTotalDaisies, twoSpeciesStats.stdTotalDaisies,
                twoSpeciesStats.meanTempStability, twoSpeciesStats.stdTempStability);
            System.out.printf("Three Species  - Final Temp: %.2f±%.2f°C, Total Daisies: %.1f±%.1f, Temp Stability: %.3f±%.3f\n", 
                threeSpeciesStats.meanFinalTemp, threeSpeciesStats.stdFinalTemp,
                threeSpeciesStats.meanTotalDaisies, threeSpeciesStats.stdTotalDaisies,
                threeSpeciesStats.meanTempStability, threeSpeciesStats.stdTempStability);
            
            // Statistical significance tests
            double idealTemp = 22.5;
            double twoSpeciesDeviation = Math.abs(twoSpeciesStats.meanFinalTemp - idealTemp);
            double threeSpeciesDeviation = Math.abs(threeSpeciesStats.meanFinalTemp - idealTemp);
            double stabilityDifference = twoSpeciesStats.meanTempStability - threeSpeciesStats.meanTempStability;
            
            System.out.println("\nStatistical Analysis:");
            if (threeSpeciesDeviation < twoSpeciesDeviation) {
                System.out.printf("✓ Gray daisies help temperature closer to ideal value by %.2f°C\n", 
                    twoSpeciesDeviation - threeSpeciesDeviation);
            } else {
                System.out.printf("✗ Gray daisies move temperature further from ideal by %.2f°C\n", 
                    threeSpeciesDeviation - twoSpeciesDeviation);
            }
            
            if (stabilityDifference > 0) {
                System.out.printf("✓ Gray daisies improved temperature stability by %.3f\n", stabilityDifference);
            } else {
                System.out.printf("✗ Gray daisies reduced temperature stability by %.3f\n", Math.abs(stabilityDifference));
            }
            
        } finally {
            Params.START_GRAY_RATIO = originalGrayRatio;
            Params.MAX_SIMULATION_STEPS = originalSteps;
        }
    }
    
    /**
     * Run multiple experiments and return statistical summary
     */
    private static ExperimentStats runMultipleExperiments(String name) {
        double[] finalTemps = new double[NUM_REPLICATIONS];
        double[] totalDaisies = new double[NUM_REPLICATIONS];
        double[] tempStabilities = new double[NUM_REPLICATIONS];
        
        for (int i = 0; i < NUM_REPLICATIONS; i++) {
            ExperimentResult result = runSingleSimulation(name + "_" + i);
            finalTemps[i] = result.finalTemp;
            totalDaisies[i] = result.totalDaisies;
            tempStabilities[i] = result.tempStability;
        }
        
        return new ExperimentStats(finalTemps, totalDaisies, tempStabilities);
    }
    
    /**
     * Run single simulation and return key metrics
     */
    private static ExperimentResult runSingleSimulation(String name) {
        World world = new World();
        world.worldInit();
        Logger logger = new Logger(world);
        
        double tempSum = 0;
        double tempSumSquared = 0;
        int measurements = 0;
        
        // Simulation loop
        for (int tick = 1; tick <= Params.MAX_SIMULATION_STEPS; tick++) {
            world.updateTemperatures();
            world.diffuseTemperature();
            world.checkAllDaisySurvivals();
            
            // Collect data from last quarter for stability analysis
            if (tick > Params.MAX_SIMULATION_STEPS * 3 / 4) {
                double temp = logger.getAverageTemperature();
                tempSum += temp;
                tempSumSquared += temp * temp;
                measurements++;
            }
        }
        
        // Calculate results
        double finalTemp = logger.getAverageTemperature();
        int totalDaisiesCount = logger.getWhiteCount() + logger.getBlackCount() + logger.getGrayCount();
        
        // Calculate temperature stability (standard deviation, smaller is more stable)
        double tempMean = tempSum / measurements;
        double tempVariance = (tempSumSquared / measurements) - (tempMean * tempMean);
        double tempStability = Math.sqrt(tempVariance);
        
        return new ExperimentResult(finalTemp, totalDaisiesCount, tempStability);
    }
    
    /**
     * Experiment result data structure
     */
    static class ExperimentResult {
        final double finalTemp;
        final int totalDaisies;
        final double tempStability;
        
        ExperimentResult(double finalTemp, int totalDaisies, double tempStability) {
            this.finalTemp = finalTemp;
            this.totalDaisies = totalDaisies;
            this.tempStability = tempStability;
        }
    }
    
    /**
     * Statistical summary of multiple experiment runs
     */
    static class ExperimentStats {
        final double meanFinalTemp, stdFinalTemp;
        final double meanTotalDaisies, stdTotalDaisies;
        final double meanTempStability, stdTempStability;
        
        ExperimentStats(double[] finalTemps, double[] totalDaisies, double[] tempStabilities) {
            this.meanFinalTemp = calculateMean(finalTemps);
            this.stdFinalTemp = calculateStd(finalTemps, meanFinalTemp);
            
            this.meanTotalDaisies = calculateMean(totalDaisies);
            this.stdTotalDaisies = calculateStd(totalDaisies, meanTotalDaisies);
            
            this.meanTempStability = calculateMean(tempStabilities);
            this.stdTempStability = calculateStd(tempStabilities, meanTempStability);
        }
        
        private double calculateMean(double[] values) {
            double sum = 0;
            for (double value : values) {
                sum += value;
            }
            return sum / values.length;
        }
        
        private double calculateStd(double[] values, double mean) {
            double sumSquaredDiff = 0;
            for (double value : values) {
                sumSquaredDiff += (value - mean) * (value - mean);
            }
            return Math.sqrt(sumSquaredDiff / (values.length - 1));
        }
    }
} 