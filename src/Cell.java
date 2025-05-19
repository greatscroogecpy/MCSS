public class Cell {
    private double temperature;
    private Daisy daisy;

    public Cell() {
        this.temperature = Params.DEFAULT_CELL_TEMPERATURE;
        this.daisy = null;
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
} 