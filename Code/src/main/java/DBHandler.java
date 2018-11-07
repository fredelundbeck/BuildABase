import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DBHandler 
{
    private int id;
    private String file;

    public DBHandler() 
    {
        
    }
    
    public void create()
    {

    }

    public String[] read(int i2, int dataID) throws FileNotFoundException
    {
        String path = "C:/Users/Bruger/Documents/Programming/BuildABase/TheProject/databases/name.basics.tsv";
        File file = new File(path);
        Scanner scanner = new Scanner(file, "UTF-8");
        
        for (int i = 0; i < dataID; i++) 
        {
            scanner.nextLine();
        }

        return scanner.nextLine().split("\t");
    }

    public String[] getColumnTitles(int i) throws FileNotFoundException
    {
        return read(i, 0);
    }

    public void update()
    {

    }

    public void delete()
    {

    }

}