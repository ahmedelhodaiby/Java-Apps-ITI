import java.io.*;


/**
 *
 * @author El-Hodaiby
 */
public class ConsoleToFile {

    public static void main(String[] args) throws IOException
    {
        InputStreamReader consoleIn = new InputStreamReader(System.in);
        BufferedReader buffread = new BufferedReader(consoleIn);
        FileWriter writer = new FileWriter("out.txt");
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
    
