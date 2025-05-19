// Since all files are in the same package (src), no import is needed
public class Simulation {
    public static void main(String[] args) {
        World world = new World(Params.WORLD_WIDTH, Params.WORLD_HEIGHT, Params.SOLAR_LUMINOSITY);
        // 后续补充初始化和模拟主循环
        System.out.println("Daisyworld simulation initialized.");
    }
} 