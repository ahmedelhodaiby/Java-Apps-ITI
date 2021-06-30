import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class App {

    public static void main(String[] args) {

//Creating object lists of Country and City from CSV files
        String countryPath = "src/Countries.csv";
        String cityPath = "src/Cities.csv";
        ReadFromCSV reader = new ReadFromCSV();
        List<City> cityList = reader.readCityFromCSV(cityPath);
        List<Country> countryList = reader.readCountryFromCSV(countryPath);

//Creating a map of the country codes as keys and the list of city names in that country as the value.
        Map<String, List<String>> atlas = cityList.stream()
                .collect(Collectors.groupingBy(City::getCountryCode,TreeMap::new,Collectors.mapping(City::getName, Collectors.toList())));

//For a given country code (from Console) sorting the cities according to the population
        InputStreamReader consoleIn = new InputStreamReader(System.in);
        BufferedReader buffread = new BufferedReader(consoleIn);
        String countryCode = null;
        System.out.print("\nEnter a country code to sort its cities by population: ");
        try {
            countryCode = buffread.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(reader.sortCitiesByPopulation(countryCode));

//Get a List of countries population
        System.out.println("List of countries population\n"+reader.getCountryPopulation()+"\n");

//Get the average countries population
        OptionalDouble avg = reader.getAvgCountriesPopulation();
        System.out.println("Average Country populations= "+ avg+"\n");

//Get the average countries population
        OptionalDouble max = reader.getMaxCountriesPopulation();
        System.out.println("Max Country population= "+ max+"\n");

//Highest population city of each country
        for (Country c: countryList) {
            try {
                System.out.println("Highest population in " + c + " is " + reader.getHighestPopulationInCountry(c.getCode()));
            } catch (Exception e) {
                continue;
            }
        }

//Highest population capital
        System.out.println("\nHighest population capital is "+ reader.getHighestPopulationCapital());

    }
}
