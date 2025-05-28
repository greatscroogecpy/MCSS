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

    // 随机初始化 Daisy 分布 - 采用NetLogo的方式
    public void worldInit(){
        // 首先初始化所有地块为空地
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new Patch(); // 创建空地块
                // grid[i][j].setTemperature(0.0);
            }
        }

        int totalPatches = size * size;
        int whiteCount = (int) (totalPatches * Params.START_WHITE_RATIO);
        int blackCount = (int) (totalPatches * Params.START_BLACK_RATIO);

        // 创建所有地块位置的列表
        List<int[]> availablePositions = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                availablePositions.add(new int[]{i, j});
            }
        }

        // 随机打乱位置列表
        Collections.shuffle(availablePositions);

        // 放置黑雏菊 - 类似NetLogo的 ask n-of ... patches with [not any? daisies-here]
        for (int i = 0; i < blackCount && i < availablePositions.size(); i++) {
            int[] pos = availablePositions.get(i);
            int x = pos[0];
            int y = pos[1];
            // 设置随机年龄，如NetLogo中的 ask daisies [set age random max-age]
            grid[x][y].setDaisy(new Daisy(Daisy.Color.BLACK, rand.nextInt(Params.DAISY_MAX_AGE)));
        }

        // 放置白雏菊 - 在剩余的空位置中选择
        for (int i = blackCount; i < blackCount + whiteCount && i < availablePositions.size(); i++) {
            int[] pos = availablePositions.get(i);
            int x = pos[0];
            int y = pos[1];
            // 设置随机年龄，如NetLogo中的 ask daisies [set age random max-age]
            grid[x][y].setDaisy(new Daisy(Daisy.Color.WHITE, rand.nextInt(Params.DAISY_MAX_AGE)));
        }

        // 打印诊断信息
        System.out.println("初始化完成前:");
        System.out.println("白雏菊反照率: " + Params.WHITE_DAISY_ALBEDO);
        System.out.println("黑雏菊反照率: " + Params.BLACK_DAISY_ALBEDO);
        System.out.println("地表反照率: " + Params.SURFACE_ALBEDO);
        System.out.println("太阳光度: " + Params.SOLAR_LUMINOSITY);
        System.out.println("随机取样的初始温度: " + grid[0][0].getTemperature());

        // 初始化完毕后更新每个 Patch 的温度
        updateTemperatures();

        // 打印更新后的温度
        double totalTemp = 0;
        int count = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                totalTemp += grid[i][j].getTemperature();
                count++;
            }
        }
        System.out.println("初始化后平均温度: " + (totalTemp / count));
        System.out.println("随机取样的更新后温度: " + grid[0][0].getTemperature());
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