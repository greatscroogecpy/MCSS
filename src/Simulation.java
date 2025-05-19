public class Simulation {
    public static void main(String[] args) {
        int width = 29;
        int height = 29;
        double solarLuminosity = 1.0;
        World world = new World(width, height, solarLuminosity);
        // 后续补充初始化和模拟主循环
        System.out.println("Daisyworld simulation initialized.");
    }
} 