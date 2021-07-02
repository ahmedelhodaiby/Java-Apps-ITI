public class City {

    private int id;
    private String name;
    private int population;

    public City(int id, String name, int population, String countryCode) {
        this.setId(id);
        this.setName(name);
        this.setPopulation(population);
        this.setCountryCode(countryCode);
    }

    private String countryCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Override
    public String toString() {
        return name;
    }
}
