import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Patch {
    private double temperature;
    private Daisy daisy;
    private double surfaceAlbedo=0.4;
    private final double diffusionRatio = 0.5;
    Random rand = new Random();



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
        if (daisy == null) return surfaceAlbedo;
        return daisy.getColor() == Daisy.Color.WHITE ? Params.WHITE_DAISY_ALBEDO : Params.BLACK_DAISY_ALBEDO;
    }

    public void removeDaisy(){
        daisy = null;
    }

    public void updateTemperature(){
        double absorbedLuminosity;
        double localHeating;

        absorbedLuminosity = (1 - this.getAlbedo()) * Params.SOLAR_LUMINOSITY;

        if (absorbedLuminosity > 0) {
            localHeating = 72 * Math.log(absorbedLuminosity) + 80;
        } else {
            localHeating = 80;
        }

        this.temperature = (this.temperature + localHeating) / 2;
    }

    public void diffuse(List<Patch> neighbors) {
        double oldTemp = this.temperature;
        this.temperature *= (1 - diffusionRatio); // 保留部分温度

        double share = (oldTemp * diffusionRatio) / neighbors.size();
        for (Patch neighbor : neighbors) {
            neighbor.temperature += share; // 向每个邻居扩散等份温度
        }
    }

//    public void checkSurvivability(List<Patch> neighbours){
//        if (this.daisy != null) {
//            this.daisy.incrementAge();
//            if (this.daisy.isDead()){
//                this.daisy = null;
//            } else {
//                // calculate the possibility of seeding
//                double seedThreshold = 0.1457 * this.temperature - 0.0032 * Math.pow(this.temperature, 2) - 0.6443;
//
//                // check if any neighbour is empty
//                List<Patch> openNeighbours = new ArrayList<>();
//                for(Patch neighbour : neighbours){
//                    if(neighbour.daisy == null){
//                        openNeighbours.add(neighbour);
//                    }
//                }
//
//                // choose one open neighbour randomly and sprout a new daisy
//                double possibility = Math.random();
//                if(!openNeighbours.isEmpty() && possibility < seedThreshold){
//                    int index = rand.nextInt(openNeighbours.size());
//                    openNeighbours.get(index).daisy = new Daisy(this.daisy.getColor());
//                }
//            }
//        }
//    }

} 