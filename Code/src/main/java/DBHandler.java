import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class DBHandler 
{
    private ArrayList<File> dbFiles;

    public DBHandler()
    {

    }

    public DBHandler(ArrayList<File> dbFiles) 
    {
        this.dbFiles = dbFiles;
    }

    public int getDBCount()
    {
        return dbFiles.size();
    }

    public ArrayList<String> getDBNames()
    {
        ArrayList<String> names = new ArrayList<String>();

        for (File dbFile : dbFiles) 
        {
            names.add(dbFile.getName());
        }   
        return names;
    }

    public void setDBPaths(ArrayList<File> dbFiles)
    {
        this.dbFiles = dbFiles;
    }
    
    public void create(int databaseID, String[] data)
    {
        
    }

    public String[] read(int databaseID, int dataID)
    {
        String[] data = null; 

        try 
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dbFiles.get(databaseID)), "UTF-8"));
           
            for (int i = 0; i < dataID; i++) 
            {
                br.readLine();
            }

            data = br.readLine().split("\t");
        
            br.close();
        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        
        return data;
    }

    
    public void update(int databaseID, int dataID)
    {
        
    }
    
    public void delete(int databaseID, int dataID)
    {
        
    }
    
    public String[] getColumnTitles(int databaseID)
    {
        return read(databaseID, 0);
    }
    
    public String[] getAvailableDatabases()
    {
        return null;
    }
}