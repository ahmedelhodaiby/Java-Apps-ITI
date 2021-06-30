/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Day3;

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
    
    @Override
    public List<Pyramid> readPyramidsFromCSV(String filename)
    {
        BufferedReader buffread = null;
        try {
//reading lines from the file
            buffread = new BufferedReader(new FileReader(filename));
            String line = buffread.readLine();
            while((line = buffread.readLine()) != null)
            {
                String[] attributes = line.split(",");
//passing split line as an array of strings to create pyramid objects and add them to the list of pyramids
                Pyramid newp =  createPyramid(attributes);
                pyramids.add(newp);
                line = buffread.readLine();
            }
            return pyramids;
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PyramidCSVDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException e) {
            e.printStackTrace();
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
        //method takes each line from the csv as an array of strings, then creates and returns a new pyramid object using constructor
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
