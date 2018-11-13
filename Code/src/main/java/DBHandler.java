import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import UserSettings.UserInformation;

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
        try 
        {
            FileWriter fw = new FileWriter(dbFiles.get(databaseID), true);

            //Set new ID 
            data[0] = getNextAvailableID(databaseID);

            fw.write("\n");
            fw.write(String.join("\t", data));
            fw.close();
        } 
        catch (IOException e) 
        {
           System.out.println(e.getMessage());
        }
    }

    public String[] read(int databaseID, int dataID)
    {
        String[] data = null; 

        try 
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dbFiles.get(databaseID)), "UTF-8"));
            
            String line = null;
            int id = 0;

            if (dataID == 0) 
            {
                if ((line = br.readLine()) != null)
                {
                    br.close();
                    return line.split("\t");   
                }
            }

            while (true) 
            {
                if ((line = br.readLine()) != null && id < dataID) 
                {
                    
                    try 
                    {
                        if ((id = Integer.parseInt(line.substring(0, line.indexOf("\t")).substring(2))) == dataID) 
                        {
                            break;
                        }
                    }
                    
                    catch (Exception e) 
                    {
                        
                    }
                } 
                else
                {
                    br.close();
                    return null;
                }
            }

            br.close();

            return line.split("\t");
        } 
        catch (IOException e) 
        {
            System.out.println(e.getMessage());
        }
        
        return data;
    }

    public void update(int databaseID, int dataID, String[] newData)
    {
        
    }
    
    public void delete(int databaseID, int dataID)
    {
        File inputFile = dbFiles.get(databaseID);
        File tempFile = new File(UserInformation.getDatabasePath() + "/temp.tsv");
        
        try 
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));

            String newLine;

            while ((newLine = br.readLine()) != null) 
            {
                if (getDataID(newLine) == dataID) 
                {
                    continue;
                }
                bw.write(newLine);
            }

        } 
        catch (IOException e) 
        {
           
        }




    }

    public ArrayList<String[]> search(int databaseID, String searchString)
    {
        ArrayList<String[]> data = new ArrayList<String[]>();

        try 
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dbFiles.get(databaseID)), "UTF-8"));
            
            String line = null;

            while (true) 
            {
                if ((line = br.readLine()) != null) 
                {
                    if (line.toLowerCase().contains(searchString.toLowerCase())) 
                    {
                        data.add(line.split("\t"));
                    }
                } 
                else
                {
                    break;
                }
            }

            br.close();

            return data;
        } 
        catch (IOException e) 
        {
            System.out.println(e.getMessage());
        }
        
        return data;
    }
    
    public String[] getColumnTitles(int databaseID)
    {
        return read(databaseID, 0);
    }

    private int getDataID(String data)
    {
        //TODO: Make it not crash!!
        return Integer.parseInt(data.substring(0, data.indexOf("\t")).substring(2));
    }

    private String getNextAvailableID(int databaseID) {
        
        String lastLine = "";

        try 
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dbFiles.get(databaseID)), "UTF-8"));

            String currentLine = br.readLine();

            while ((currentLine = br.readLine()) != null) 
            {
                lastLine = currentLine;
            }

            String idPrefix = lastLine.substring(0, 2);
            int idNumberValue = Integer.parseInt(lastLine.substring(2, lastLine.indexOf("\t"))) + 1;   

            br.close();
            
            return idPrefix + String.valueOf(idNumberValue);
        } 
        catch (IOException e) 
        {
            
        }

        return null;
    }
}
