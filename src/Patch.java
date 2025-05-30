import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Patch {
    private double temperature;
    private Daisy daisy;
    private static final Random rand = new Random();
    
    // Soil quality (0.5-1.5, 1.0 is standard)
    private double soilQuality;

    public Patch() {
        this.temperature = Params.DEFAULT_CELL_TEMPERATURE;
        this.daisy = null;
        this.soilQuality = 1.0;
    }

    public Patch(Daisy daisy){
        this.temperature = Params.DEFAULT_CELL_TEMPERATURE;
        this.daisy = daisy;
        this.soilQuality = 1.0;
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
        switch (daisy.getColor()) {
            case WHITE:
                return Params.WHITE_DAISY_ALBEDO;
            case BLACK:
                return Params.BLACK_DAISY_ALBEDO;
            case GRAY:
                return Params.GRAY_DAISY_ALBEDO;
            default:
                return Params.SURFACE_ALBEDO;
        }
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

    public void checkSurvivability(List<Patch> neighbours){
        if (this.daisy != null) {
            // First increment age
            this.daisy.incrementAge();

            // Check for death
            if (this.daisy.isDead()) {
                this.daisy = null;
            }
            else {
                double seedThreshold = 0.1457 * this.temperature - 0.0032 * Math.pow(this.temperature, 2) - 0.6443;
                
                // Soil quality affects reproduction probability
                if (Params.ENABLE_SPATIAL_HETEROGENEITY) {
                    seedThreshold *= this.soilQuality;
                }

                List<Patch> freeNeighbours = new ArrayList<>();
                for(Patch neighbour : neighbours){
                    if(neighbour.daisy == null){
                        freeNeighbours.add(neighbour);
                    }
                }

                if(!freeNeighbours.isEmpty() && Math.random() < seedThreshold){
                    Patch chosen = freeNeighbours.get(rand.nextInt(freeNeighbours.size()));
                    chosen.setDaisy(new Daisy(this.daisy.getColor(), 0));
                }
            }
        }
    }


    // Set soil quality with random variation
    public void setSoilQuality() {
        if (Params.ENABLE_SPATIAL_HETEROGENEITY) {
            // Generate soil quality between 0.5 and 1.5
            this.soilQuality = 1.0 + (rand.nextGaussian() * Params.SOIL_QUALITY_VARIANCE);
            // Clamp to reasonable range
            this.soilQuality = Math.max(0.5, Math.min(1.5, this.soilQuality));
        } else {
            this.soilQuality = 1.0; // Standard soil quality
        }
    }
}