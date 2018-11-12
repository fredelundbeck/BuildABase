import java.io.File;
import java.io.RandomAccessFile;


public class Program 
{
    private static final String FILE_PATH = "C:\\Users\\Frede\\Documents\\Programming\\BuildABase\\databases\\empty.tsv";

    public static void main(String[] args) 
    {
        Menu menu = new Menu();
        menu.start();

        //TESTING
        //doSomething();
    }

    public static void doSomething()
    {
        try {

            File file = new File(FILE_PATH);
            
            RandomAccessFile raf = new RandomAccessFile(file, "rw");

            String newLine = null;
            int targetLineLength = 0;
            long startIndex = 0;

            while ((newLine = raf.readLine()) != null) 
            {
                if (newLine.contains("tt00001"))
                {
                    targetLineLength = newLine.length();
                    break;
                } 
                else
                {
                    startIndex += newLine.length();
                }
            }

            raf.seek(startIndex);

            raf.writeBytes("#########");

            System.out.println(startIndex);
            
         } 
         catch (Exception ex) 
         {
            ex.printStackTrace();
         }
        
    }

    
}