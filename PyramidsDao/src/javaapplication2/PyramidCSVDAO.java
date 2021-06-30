/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author El-Hodaiby
 */
public class PyramidCSVDAO implements PyramidDao
{
    List<Pyramid> pyramids = new ArrayList<Pyramid>();
    public PyramidCSVDAO()
    {
        
    }
    
    @Override
    public List<Pyramid> readPyramidsFromCSV(String filename)
    {
        BufferedReader buffread = null;
        try {
            buffread = new BufferedReader(new FileReader(filename));
            String line = buffread.readLine();
            while((line = buffread.readLine()) != null)
            {
                String[] attributes = line.split(",");
                Pyramid newp =  createPyramid(attributes);
                pyramids.add(newp);
                line = buffread.readLine();
            }
            return pyramids;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PyramidCSVDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PyramidCSVDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                buffread.close();
            } catch (IOException ex) {
                Logger.getLogger(PyramidCSVDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }
    
    @Override
    public Pyramid createPyramid(String[] metadata)
    {
        Pyramid myp;
        if((metadata[7].length() > 0))
            {
                myp = new Pyramid(metadata[0], metadata[2], metadata[4], Double.valueOf(metadata[7]));
            }else{
                myp = new Pyramid(metadata[0], metadata[2], metadata[4], 0.0);
            }
        return myp;
    }
    
}
