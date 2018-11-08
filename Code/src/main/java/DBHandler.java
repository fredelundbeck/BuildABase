import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DBHandler 
{
    private int id;
    private String file;
    private ArrayList<String> dbPaths;

    public DBHandler()
    {

    }

    public DBHandler(ArrayList<String> dbPaths) 
    {
        this.dbPaths = dbPaths;
    }

    public void setDBPaths(ArrayList<String> dbPaths)
    {
        this.dbPaths = dbPaths;
    }
    
    public void create(int databaseID, String[] data)
    {
        
    }

    public String[] read(int databaseID, int dataID) throws FileNotFoundException
    {
        String path = dbPaths.get(databaseID);
        File file = new File(path);
        Scanner scanner = new Scanner(file, "UTF-8");
        
        for (int i = 0; i < dataID; i++) 
        {
            scanner.nextLine();
        }

        String[] data = scanner.nextLine().split("\t");
        
        scanner.close();
        
        return data;
    }

    
    public void update(int databaseID, int dataID)
    {
        
    }
    
    public void delete(int databaseID, int dataID)
    {
        
    }
    
    public String[] getColumnTitles(int databaseID) throws FileNotFoundException
    {
        return read(databaseID, 0);
    }
}