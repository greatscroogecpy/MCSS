import java.util.Collections;
import java.io.FileWriter;
import java.io.IOException;

// Since all files are in the same package (src), no import is needed
public class Simulation {
    public static void main(String[] args) {
        System.out.println("===== 模拟开始 =====");
        System.out.println("参数设置:");
        System.out.println("  白雏菊反照率: " + Params.WHITE_DAISY_ALBEDO);
        System.out.println("  黑雏菊反照率: " + Params.BLACK_DAISY_ALBEDO);
        System.out.println("  地表反照率: " + Params.SURFACE_ALBEDO);
        System.out.println("  太阳光度: " + Params.SOLAR_LUMINOSITY);
        System.out.println("  白雏菊初始比例: " + Params.START_WHITE_RATIO);
        System.out.println("  黑雏菊初始比例: " + Params.START_BLACK_RATIO);

        World world = new World();
        world.worldInit();

        Logger logger = new Logger(world);

        // 记录第0步的初始状态
        System.out.println("初始状态 (第0步):");
        System.out.println("  平均温度: " + logger.getAverageTemperature());
        System.out.println("  白雏菊数量: " + logger.getWhiteCount());
        System.out.println("  黑雏菊数量: " + logger.getBlackCount());
        System.out.println("  总雏菊数量: " + (logger.getWhiteCount() + logger.getBlackCount()));

        // Create CSV filename based on parameters
        String csvFilename = String.format("%.2f_%.2f_%s.csv",
                Params.START_WHITE_RATIO,
                Params.START_BLACK_RATIO,
                Params.SCENARIO.name());

        // Create CSV file and write header
        try (FileWriter csvWriter = new FileWriter(csvFilename)) {
            // Write header
            csvWriter.append("Tick,AverageTemperature,WhiteDaisyCount,BlackDaisyCount,TotalDaisyCount,SolarLuminosity\n");

            // 输出第0步
            int whiteCount = logger.getWhiteCount();
            int blackCount = logger.getBlackCount();
            int totalCount = whiteCount + blackCount;
            double avgTemp = logger.getAverageTemperature();

            csvWriter.append(String.format("%d,%.2f,%d,%d,%d,%.4f\n",
                    0, avgTemp, whiteCount, blackCount, totalCount, Params.SOLAR_LUMINOSITY));

            for (int t = 1; t < Params.MAX_SIMULATION_STEPS; t++) {
                // 先更新温度和雏菊状态
                world.updateTemperatures();
                world.diffuseTemperature();
                world.checkAllDaisySurvivals();

                // 然后记录数据
                whiteCount = logger.getWhiteCount();
                blackCount = logger.getBlackCount();
                totalCount = whiteCount + blackCount;
                avgTemp = logger.getAverageTemperature();

                // 只记录前10步的详细日志
                if (t <= 10) {
                    System.out.println("第" + t + "步:");
                    System.out.println("  平均温度: " + avgTemp);
                    System.out.println("  白雏菊数量: " + whiteCount);
                    System.out.println("  黑雏菊数量: " + blackCount);
                }

                // 输出到控制台
                logger.printStats(t);

                // 写入CSV
                csvWriter.append(String.format("%d,%.2f,%d,%d,%d,%.4f\n",
                        t, avgTemp, whiteCount, blackCount, totalCount, Params.SOLAR_LUMINOSITY));

                // === SCENARIO 控制 ===
                if (Params.SCENARIO== Params.LUMINOSITY_SCENARIO.RAMP_UP_RAMP_DOWN) {
                    if (t > 200 && t <= 400) {
                        Params.SOLAR_LUMINOSITY = roundTo4(Params.SOLAR_LUMINOSITY + 0.005);
                    } else if (t > 600 && t <= 850) {
                        Params.SOLAR_LUMINOSITY = roundTo4(Params.SOLAR_LUMINOSITY - 0.0025);
                    }
                } else if (Params.SCENARIO== Params.LUMINOSITY_SCENARIO.LOW_SOLAR_LUMINOSITY) {
                    Params.SOLAR_LUMINOSITY = 0.6;
                } else if (Params.SCENARIO== Params.LUMINOSITY_SCENARIO.OUR_SOLOAR_LUMINOSITY) {
                    Params.SOLAR_LUMINOSITY = 1.0;
                } else if (Params.SCENARIO== Params.LUMINOSITY_SCENARIO.HIGH_SOLAR_LUMINOSITY) {
                    Params.SOLAR_LUMINOSITY = 1.4;
                }
            }

            System.out.println("模拟结束，结果已保存到 " + csvFilename);

        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    private static double roundTo4(double val) {
        return Math.round(val * 10000.0) / 10000.0;
    }
}