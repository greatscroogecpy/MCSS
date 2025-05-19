// Since all files are in the same package (src), no import is needed
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

    // Default constructor using parameters from Params class
    public World() {
        this(Params.WORLD_WIDTH, Params.WORLD_HEIGHT, Params.SOLAR_LUMINOSITY);
    }

    // 其他 Daisyworld 逻辑方法后续补充
} 