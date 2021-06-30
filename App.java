package assignment1;

import java.io.*;


/**
 *
 * @author El-Hodaiby
 */
public class App {

    public static void main(String[] args) throws IOException
    {
        InputStreamReader consoleIn = new InputStreamReader(System.in);
        BufferedReader buffread = new BufferedReader(consoleIn);
        FileWriter writer = new FileWriter("D:\\ITI AI\\Technical\\01 Foundation Period\\10-Java and UML Programming\\Day 2\\out1.txt");
        BufferedWriter buffwriter = new BufferedWriter(writer);
        String data = "";
        while(!data.equalsIgnoreCase("stop")){
            System.out.print("enter data: ");
            data = buffread.readLine();
            buffwriter.write(data+"\n");
        }
        buffwriter.close();
    }
    
}
    
