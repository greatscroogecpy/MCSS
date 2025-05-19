public class World {
    private int width;
    private int height;
    private Cell[][] grid;
    private double solarLuminosity;

    public World(int width, int height, double solarLuminosity) {
        this.width = width;
        this.height = height;
        this.solarLuminosity = solarLuminosity;
        this.grid = new Cell[width][height];
        // 初始化网格
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    // 其他 Daisyworld 逻辑方法后续补充
} 