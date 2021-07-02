package org.example.jupai6;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class XChartExamples {
    
    public static void main(String[] args) {
        XChartExamples xChartExamples = new XChartExamples ();
        List<TitanicPassenger> allPassengers = xChartExamples.getPassengersFromJsonFile ();
        
        xChartExamples.graphPassengerAges       (allPassengers);
        
        xChartExamples.graphPassengerClass      (allPassengers);
        
        xChartExamples.graphPassengersurvived   (allPassengers);
        
        xChartExamples.graphPassengersurvivedGender (allPassengers);
    }
    
    
    // Read json file and return a list containing TitanicPassenger Objects
    public List<TitanicPassenger> getPassengersFromJsonFile() {
        List<TitanicPassenger> allPassengers = new ArrayList<> ();
        ObjectMapper objectMapper = new ObjectMapper ();
        objectMapper.configure (DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try (InputStream input = new FileInputStream ("titanic_csv.json")) {
            //Read JSON file
            allPassengers = objectMapper.readValue (input, new TypeReference<List<TitanicPassenger>> () {
            });
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return allPassengers;
    }

    public void graphPassengerAges(List<TitanicPassenger> passengerList) {
        //filter to get an array of passenger ages
        List<Float> pAges = passengerList.stream ().map (TitanicPassenger::getAge).limit (8).collect (Collectors.toList ());
        List<String> pNames = passengerList.stream ().map (TitanicPassenger::getName).limit (8).collect (Collectors.toList ());
//        String[] names = new String[pNames.size ()];
//        Float ages[] = new Float[pAges.size ()];
//        ages = pAges.toArray (ages);
//        names = pNames.toArray (names);

        //Using XCart to graph the Ages
        // Create Chart
        CategoryChart chart = new CategoryChartBuilder ().width (1024).height (768).title ("Age Histogram").xAxisTitle ("Names").yAxisTitle ("Age").build ();
        // Customize Chart
        chart.getStyler ().setLegendPosition (Styler.LegendPosition.InsideNW);
        chart.getStyler ().setHasAnnotations (true);
        chart.getStyler ().setStacked (true);
        // Series
        chart.addSeries ("Passenger's Ages", pNames, pAges);
        // Show it
        new SwingWrapper (chart).displayChart ();
    }

    public void graphPassengerClass(List<TitanicPassenger> passengerList) {
        //filter to get a map of passenger class and total number of passengers in each class
        Map<String, Long> result =
                passengerList.stream ().collect (
                        Collectors.groupingBy (
                                TitanicPassenger::getPclass, Collectors.counting ()
                        )
                );
        // Create Chart
        PieChart chart = new PieChartBuilder ().width (800).height (600).title ("Passengers Classes").build ();
        // Customize Chart
        Color[] sliceColors = new Color[]{new Color (180, 68, 50), new Color (130, 105, 120), new Color (80, 143, 160)};
        chart.getStyler ().setSeriesColors (sliceColors);
        // Series
        chart.addSeries ("First Class", result.get ("1"));
        chart.addSeries ("Second Class", result.get ("2"));
        chart.addSeries ("Third Class", result.get ("3"));
        // Show it
        new SwingWrapper (chart).displayChart ();
    }
    
    public void graphPassengersurvived(List<TitanicPassenger> passengers){
        //filter to get a map of passengers survived and total numbers
        Map<String, Long> survivors = passengers.stream()
                .collect(Collectors.groupingBy(TitanicPassenger::getSurvived, Collectors.counting()));
        
        //create chart
        PieChart chart = new PieChartBuilder().width(800).height(600).title("Passengers survived").build();
        //customize chart colors
        Color[] sliceColors = new Color[]{new Color (180, 68, 50), new Color (80, 143, 160)};
        chart.getStyler ().setSeriesColors (sliceColors);
        
        //series
        chart.addSeries("Survived", survivors.get("1"));
        chart.addSeries("Didn't survive", survivors.get("0"));
        //show chart
        new SwingWrapper(chart).displayChart();
    }
    
    public void graphPassengersurvivedGender(List<TitanicPassenger> passengers){
        
        //filter to get a map of passengers survived and their gender
        List<TitanicPassenger> survivors = passengers.stream()
                .filter(person -> person.getSurvived().equals("1")).collect(Collectors.toList());
        Map<String, Long> survivorsGender = survivors.stream()
                .collect(Collectors.groupingBy(TitanicPassenger::getSex, Collectors.counting()));

        //create chart
        PieChart chart = new PieChartBuilder().width(800).height(600).title("Survival by gender").build();
        
        //customize chart colors
        Color[] sliceColors = new Color[]{new Color (180, 68, 50), new Color (80, 143, 160)};
        chart.getStyler ().setSeriesColors (sliceColors);
        
        //series
        chart.addSeries("Females survived", survivorsGender.get("female"));
        chart.addSeries("Males survived", survivorsGender.get("male"));
        //show chart
        new SwingWrapper(chart).displayChart();
    }
}
