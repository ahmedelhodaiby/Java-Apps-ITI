import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ReadFromCSV implements CoutryDao,CityDao{
    List<Country> countries = new ArrayList<Country>();
    List<City> cities = new ArrayList<City>();



    @Override
    public List<City> readCityFromCSV(String filepath) {

        BufferedReader buffread = null;
        try {
            buffread = new BufferedReader(new FileReader(filepath));
            String line;
            line = buffread.readLine();
            while (line  != null) {
                String[] attributes = line.split(",");
                City newc = createCity(attributes);
                cities.add(newc);
                line = buffread.readLine();
            }
            return cities;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public City createCity(String[] data) {
        City myc= null;
        myc = new City(Integer.parseInt(data[0]), data[1], Integer.parseInt(data[2]), data[3].trim());
        return myc;
    }

    @Override
    public List<Country> readCountryFromCSV(String filepath) {

        BufferedReader buffread = null;
        try {
            buffread = new BufferedReader(new FileReader(filepath));
            String line;
            line = buffread.readLine();
            while (line  != null) {
                String[] attributes = line.split(",");
                Country newc;
                newc = createCountry(attributes);
                countries.add(newc);
                line = buffread.readLine();
            }
            return countries;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Country createCountry(String[] data) {
        Country myc;
        myc = new Country(data[0], data[1], data[2] , Double.parseDouble(data[3]), Double.parseDouble(data[4]), Integer.parseInt(data[6]));
        return myc;
    }


    public List<City> sortCitiesByPopulation(String countryCode){
/**
 *      Creating a map of Country codes as keys and a list of the cities(objects) in that country as values
 */
        Map<String, List<City>> cityMap = cities.stream()
                .collect(Collectors.groupingBy(City::getCountryCode));
/**
 *      Sorting cities in a given Country by Population
 */
        return cityMap.get(countryCode).stream()
                .sorted(Comparator.comparingInt(City::getPopulation)).collect(Collectors.toList());
    }

    public Map<String, Double> getCountryPopulation(){
/**
 *      Creating a Map of the country code and the population of that country
 */
        return countries.stream()
                .collect(Collectors.toMap(Country::getCode, Country::getPopulation));
    }

    public OptionalDouble getAvgCountriesPopulation(){
/**
 *      Returning the average population of all countries
 */
        return countries.stream()
                .mapToDouble(Country::getPopulation).average();
    }

    public OptionalDouble getMaxCountriesPopulation() {
/**
 *      Returning the max population of all countries
 */
        return countries.stream()
                .mapToDouble(Country::getPopulation).max();
    }

    public OptionalDouble getHighestPopulationInCountry(String countrycode){
/**
 *      Returning the highest population city in a given country
 */
        return sortCitiesByPopulation(countrycode).stream()
                .mapToDouble(City::getPopulation).max();
    }

    public Optional<City> getHighestPopulationCapital(){
/**
 *      Returning the capital city with the highest population
 */
        //capitalmap is a map of the country codes and the capital index
        Map<String, Integer> capitalMap = countries.stream().collect(Collectors.toMap(Country::getCode, Country::getCapital));
        //capitals is a list of all the cities indexed as capitals in the countries file
        List<City> capitals=null;
        for (String s:capitalMap.keySet()) {
            capitals = cities.stream().filter(city -> capitalMap.containsValue(city.getId())).collect(Collectors.toList());
        }

        return capitals.stream().max(Comparator.comparingInt(City::getPopulation));
    }

}
