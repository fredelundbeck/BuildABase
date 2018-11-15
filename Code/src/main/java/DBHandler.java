import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;


import UserSettings.UserInformation;
import Utilities.MathUtility;

public class DBHandler 
{
    private ArrayList<File> dbFiles;

    public DBHandler() {
      IndexManager idm = new IndexManager();
      idm(dbFiles);
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
            File file = dbFiles.get(databaseID);
            RandomAccessFile handler = new RandomAccessFile(file, "rw");

            long fileSize = file.length();
            handler.seek(fileSize);

            data[0] = getNextAvailableID(databaseID);

            handler.writeBytes(String.join("\t", data) );
            handler.close();
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
            
            String line = br.readLine();
            int idTick = 0;

            //Only get column names
            if (dataID == 0) 
            {
                br.close();
                return line.split("\t");   
            }

            while ((line = br.readLine()) != null && idTick < dataID) 
            {  
                if ((Integer.parseInt(line.substring(0, line.indexOf("\t")).substring(2))) == dataID) 
                {
                    data = line.split("\t");
                    break;
                } 
                else
                {
                    idTick++;
                }
            }
            br.close();
        } 
        catch (IOException e) 
        {
            System.out.println(e.getMessage());
        }
        return data;
    }

    public void update(int databaseID, int dataID, String[] newData)
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
                    bw.append(String.join("\t", newData) + System.getProperty("line.separator"));
                } 
                else 
                {
                    bw.append(newLine + System.getProperty("line.separator"));
                }
            }

            bw.close();
            br.close();

            //inputFile.delete();
            Files.move(Paths.get(tempFile.getAbsolutePath()), Paths.get(inputFile.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
        } 
        catch (IOException e) 
        {
           System.out.println(e.getMessage());
        }
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
                bw.append(newLine + System.getProperty("line.separator"));
            }

            bw.close();
            br.close();

            //inputFile.delete();
            Files.move(Paths.get(tempFile.getAbsolutePath()), Paths.get(inputFile.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
        } 
        catch (IOException e) 
        {
           System.out.println(e.getMessage());
        }
    }

    public ArrayList<String[]> search(int databaseID, String searchString)
    {
        ArrayList<String[]> data = new ArrayList<String[]>();

        try 
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dbFiles.get(databaseID)), "UTF-8"));
            String line = br.readLine();

            while ((line = br.readLine()) != null) 
            {
                if (line.toLowerCase().contains(searchString.toLowerCase())) 
                {
                    data.add(line.split("\t"));
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

    public String getIDPrefix(int databaseID)
    {
        String prefix = null;
        try 
        {
            BufferedReader br = new BufferedReader(new FileReader(dbFiles.get(databaseID)));

            //Sets the pointer to next line (line:1), that will have the id prefix
            br.readLine();

            prefix = br.readLine().substring(0, 2);

            br.close();
        } 
        catch (IOException e) 
        {
        }

        return prefix;
    }

    private int getDataID(String data)
    {
        //Could also wrap Integer.parse in try/catch, but that's too easy! :-)
        if (MathUtility.isNumericalValue(data.substring(2, data.indexOf("\t")))) 
        {
            return Integer.parseInt(data.substring(2, data.indexOf("\t")));
        }
        return -1;
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

            return idPrefix + String.format("%07d", idNumberValue);
        } 
        catch (IOException e) 
        {
            
        }

        return null;
    }
}
