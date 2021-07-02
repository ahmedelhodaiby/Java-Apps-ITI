import java.util.List;

public interface CoutryDao {

    public List<Country> readCountryFromCSV(String filepath);
    public Country createCountry(String[] data);
}
