import java.util.*;
import java.util.stream.Collectors;

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

    // 随机初始化 Daisy 分布
    public void worldInit(){
        int totalPatchs = size*size;
        int whiteCount=(int) (totalPatchs* Params.START_WHITE_RATIO);
        int blackCount=(int) (totalPatchs* Params.START_BLACK_RATIO);
        int surfaceCount= totalPatchs-whiteCount-blackCount;

        List<String> daisies = new ArrayList<>();

        for (int i = 0; i < whiteCount; i++) daisies.add("W");
        for (int i = 0; i < blackCount; i++) daisies.add("B");
        for (int i = 0; i < surfaceCount; i++) daisies.add("E");

        Collections.shuffle(daisies);

        int idx = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                String state = daisies.get(idx++);
                switch (state) {
                    case "W":
                        grid[j][i] = new Patch(new Daisy(Daisy.Color.WHITE,rand.nextInt(Params.DAISY_MAX_AGE)));
                        break;
                    case "B":
                        grid[j][i] = new Patch(new Daisy(Daisy.Color.BLACK,rand.nextInt(Params.DAISY_MAX_AGE)));
                        break;
                    case "E":
                        grid[j][i] = new Patch(); // 空地
                        break;
                }
            }
        }

        // 初始化完毕后更新每个 Patch 的温度
        updateTemperatures();

    }

    // 重新计算每个 Patch 的温度
    public void updateTemperatures() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j].updateTemperature();  // 每个 patch 自己更新温度
            }
        }
    }

    public void diffuseTemperature() {
        double[][] newTemps = new double[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                double oldTemp = grid[i][j].getTemperature();
                double retained = oldTemp * (1 - diffusionRatio);
                
                // 自己保留部分
                newTemps[i][j] += retained;
                
                // 获取邻居并计算分享温度
                List<Patch> neighbors = getNeighbors(i, j);
                double shared = oldTemp * diffusionRatio / neighbors.size();
                
                // 将共享部分扩散到邻居
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

        // 统一更新所有单元格的温度
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