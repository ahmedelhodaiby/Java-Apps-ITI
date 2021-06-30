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
public interface PyramidDao {
    
    public List<Pyramid> readPyramidsFromCSV(String filename);
    public Pyramid createPyramid(String[] metadata);
}