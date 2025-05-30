import java.util.*;


public class World {
    private final int size;
    private final Patch[][] grid;
    private final double diffusionRatio = 0.5;
    private static final Random rand = new Random();

    public World() {
        this.size = Params.WORLD_SIZE;
        this.grid = new Patch[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                grid[i][j] = new Patch();
    }

    // Random initialization of Daisy distribution - using NetLogo approach
    public void worldInit(){
        // First initialize all patches as empty land and set soil quality
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Patch(); // Create empty patch
                grid[i][j].setSoilQuality(); // Set soil quality
            }
        }

        int totalPatches = size * size;
        int whiteCount = (int) (totalPatches * Params.START_WHITE_RATIO);
        int blackCount = (int) (totalPatches * Params.START_BLACK_RATIO);
        int grayCount = (int) (totalPatches * Params.START_GRAY_RATIO);

        // Create list of all patch positions
        List<int[]> availablePositions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                availablePositions.add(new int[]{i, j});
            }
        }

        // Randomly shuffle position list
        Collections.shuffle(availablePositions);

        int positionIndex = 0;

        // Place black daisies
        for (int i = 0; i < blackCount && positionIndex < availablePositions.size(); i++, positionIndex++) {
            int[] pos = availablePositions.get(positionIndex);
            int x = pos[0];
            int y = pos[1];
            grid[x][y].setDaisy(new Daisy(Daisy.Color.BLACK, rand.nextInt(Params.DAISY_MAX_AGE)));
        }

        // Place white daisies
        for (int i = 0; i < whiteCount && positionIndex < availablePositions.size(); i++, positionIndex++) {
            int[] pos = availablePositions.get(positionIndex);
            int x = pos[0];
            int y = pos[1];
            grid[x][y].setDaisy(new Daisy(Daisy.Color.WHITE, rand.nextInt(Params.DAISY_MAX_AGE)));
        }

        // Place gray daisies
        for (int i = 0; i < grayCount && positionIndex < availablePositions.size(); i++, positionIndex++) {
            int[] pos = availablePositions.get(positionIndex);
            int x = pos[0];
            int y = pos[1];
            grid[x][y].setDaisy(new Daisy(Daisy.Color.GRAY, rand.nextInt(Params.DAISY_MAX_AGE)));
        }

        // Calculate initial temperatures based on albedo and solar luminosity
        // This matches NetLogo's approach where temperature is calculated, not set to 0
        updateTemperatures();

        // Print diagnostic information
        System.out.println("Initialization completed:");
        System.out.println("White daisy albedo: " + Params.WHITE_DAISY_ALBEDO);
        System.out.println("Black daisy albedo: " + Params.BLACK_DAISY_ALBEDO);
        System.out.println("Gray daisy albedo: " + Params.GRAY_DAISY_ALBEDO);
        System.out.println("Surface albedo: " + Params.SURFACE_ALBEDO);
        System.out.println("Solar luminosity: " + Params.SOLAR_LUMINOSITY);

        // Print updated temperature
        double totalTemp = 0;
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                totalTemp += grid[i][j].getTemperature();
                count++;
            }
        }
        System.out.println("Average temperature after initialization: " + (totalTemp / count));
        System.out.println("Random sample temperature: " + grid[0][0].getTemperature());
    }

    // Recalculate temperature for each Patch
    public void updateTemperatures() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j].updateTemperature();  // Each patch updates its own temperature
            }
        }
    }

    public void diffuseTemperature() {
        double[][] newTemps = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double oldTemp = grid[i][j].getTemperature();
                double retained = oldTemp * (1 - diffusionRatio);

                // Retain portion for self
                newTemps[i][j] += retained;

                // Get neighbors and calculate shared temperature
                List<Patch> neighbors = getNeighbors(i, j);
                double shared = oldTemp * diffusionRatio / neighbors.size();

                // Diffuse shared portion to neighbors
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (dx == 0 && dy == 0) continue;
                        int ni = i + dx, nj = j + dy;
                        if (ni >= 0 && nj >= 0 && ni < size && nj < size) {
                            newTemps[ni][nj] += shared;
                        }
                    }
                }
            }
        }

        // Update all cell temperatures uniformly
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j].setTemperature(newTemps[i][j]);
            }
        }
    }


    public void checkAllDaisySurvivals() {
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                Patch patch = this.grid[i][j];
                patch.checkSurvivability(getNeighbors(i,j));
            }
        }
    }

    private List<Patch> getNeighbors(int x, int y) {
        List<Patch> neighbors = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int nx = x + dx, ny = y + dy;
                if (nx >= 0 && nx < size && ny >= 0 && ny < size) {
                    neighbors.add(grid[nx][ny]);
                }
            }
        }
        return neighbors;
    }

    public int getSize() {
        return size;
    }

    public Patch[][] getGrid() {
        return grid;
    }
}