/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.util.List;

/**
 *
 * @author El-Hodaiby
 */
public class Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        String csvpath = "D:\\ITI AI\\Technical\\01 Foundation Period\\10-Java and UML Programming\\Day 2\\pyramids.csv";
        PyramidCSVDAO pDAO = new PyramidCSVDAO();
        List<Pyramid> pyramids = pDAO.readPyramidsFromCSV(csvpath);
        
        int i =0;
        for(Pyramid p:pyramids)
        {
            System.out.println("#"+(i++)+" "+p);
        }
    }
    
}
