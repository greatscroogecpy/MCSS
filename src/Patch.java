import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Patch {
    private double temperature;
    private Daisy daisy;
    private static final Random rand = new Random();

    public Patch() {
        this.temperature = Params.DEFAULT_CELL_TEMPERATURE;
        this.daisy = null;
    }

    public Patch(Daisy daisy){
        this.temperature = Params.DEFAULT_CELL_TEMPERATURE;
        this.daisy = daisy;
    }

    public double getTemperature() {
        return temperature;
    }
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Daisy getDaisy() {
        return daisy;
    }

    public void setDaisy(Daisy daisy) {
        this.daisy = daisy;
    }

    public double getAlbedo() {
        if (daisy == null) return Params.SURFACE_ALBEDO;
        return daisy.getColor() == Daisy.Color.WHITE ? Params.WHITE_DAISY_ALBEDO : Params.BLACK_DAISY_ALBEDO;
    }

    public void updateTemperature(){
        double absorbedLuminosity;
        double localHeating;

        absorbedLuminosity = (1 - this.getAlbedo()) * Params.SOLAR_LUMINOSITY;

        boolean isFirst = (this.temperature == 0.0);

        if (absorbedLuminosity > 0) {
            localHeating = 72 * Math.log(absorbedLuminosity) + 80;
        } else {
            localHeating =0;
        }

        double oldTemp = this.temperature;
        this.temperature = (this.temperature + localHeating) / 2;

        if (isFirst && Math.random() < 0.01) {
            System.out.println("温度计算示例:");
            System.out.println("  反照率: " + this.getAlbedo());
            System.out.println("  吸收光度: " + absorbedLuminosity);
            System.out.println("  局部加热: " + localHeating);
            System.out.println("  初始温度: " + oldTemp);
            System.out.println("  更新后温度: " + this.temperature);
        }
    }

    public void checkSurvivability(List<Patch> neighbours){
        if (this.daisy != null) {
            // First increment age
            this.daisy.incrementAge();

            // Check for death
            if (this.daisy.isDead()) {
                this.daisy = null;
            }
                        // Only allow reproduction for Daisy older than 1 tick
                        //else if (this.daisy.getAge() >= 1) {
            // NetLogo版本没有年龄限制条件，直接计算繁殖概率
            else {
                double seedThreshold = 0.1457 * this.temperature - 0.0032 * Math.pow(this.temperature, 2) - 0.6443;

                List<Patch> freeNeighbours = new ArrayList<>();
                for(Patch neighbour : neighbours){
                    if(neighbour.daisy == null){
                        freeNeighbours.add(neighbour);
                    }
                }

                if(!freeNeighbours.isEmpty() && Math.random() < seedThreshold){
                    Patch chosen = freeNeighbours.get(rand.nextInt(freeNeighbours.size()));
                    chosen.daisy = new Daisy(this.daisy.getColor()); // default age = 0
                }
            }
        }
    }


    public void diffuse(List<Patch> neighbors){
        double oldTemp = this.temperature;
        this.temperature *= (1 - Params.DIFFUSION_RATIO);
        double share = (oldTemp * Params.DIFFUSION_RATIO) / neighbors.size();
        for (Patch neighbor : neighbors) {
            neighbor.temperature += share;
        }
    }


}