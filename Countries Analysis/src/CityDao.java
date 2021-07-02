import java.util.List;

public interface CityDao {

    public List<City> readCityFromCSV(String filepath);
    public City createCity(String[] data);
}
