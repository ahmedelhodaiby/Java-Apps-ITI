/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day3;

import java.util.*;
import java.util.stream.Collectors;

/**
 *
 * @author El-Hodaiby
 */
public class Application {

    public static void main(String[] args) {
        //reading from CSV file using DAO and POJO
        String csvpath = "src/Day3/pyramids.csv";
        PyramidCSVDAO pDAO = new PyramidCSVDAO();
        List<Pyramid> pyramidslist = pDAO.readPyramidsFromCSV(csvpath);

        //Using the List sort function and Comparator interface to sort the list of pyramids by pyramids' height
        List<Pyramid> sortedP = pyramidslist.stream().sorted(Comparator.comparingDouble(Pyramid::getHeight)).collect(Collectors.toList());
        //printing the sorted pyramid objects in ascending order
        for (Pyramid i: sortedP){
            System.out.println(i);
        }

        //calculating aggregations of heights of pyramid list
        System.out.println("\nMax height is "+ pyramidslist.stream().max(Comparator.comparingDouble(Pyramid::getHeight)));
        System.out.println("Min height is "+ pyramidslist.stream().min(Comparator.comparingDouble(Pyramid::getHeight)));
        System.out.println("Mean pyramids' heights is "+pyramidslist.stream().mapToDouble(Pyramid::getHeight).average());

        //Building a map from the list of pyramids that shows all site-location with the number of pyramids in this site
        Map<String, Integer> pyramidsMap = new HashMap<String, Integer>();
        for (Pyramid p: pyramidslist) {
            String key = p.getSite();
            pyramidsMap.putIfAbsent(key, 1);
            if(pyramidsMap.containsKey(key)){
                pyramidsMap.put(key, pyramidsMap.get(key)+1);
            }
        }

        System.out.println("\n"+pyramidsMap);
    }
}
