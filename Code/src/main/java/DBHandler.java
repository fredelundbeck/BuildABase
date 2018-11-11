import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
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
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dbFiles.get(databaseID)), "UTF-8"));

            String newLine;

            while ((newLine = br.readLine()) != null) 
            {

            }

            br.close();


            String joinedData = "hello";

            FileWriter writer = new FileWriter(dbFiles.get(databaseID));
            writer.append(joinedData, 0, (joinedData).length());
            
            writer.close();
        } 
        catch (IOException e) 
        {
           
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
                        //TODO: find a better (softcoded) solution to detecting ID matches, instead of being dependent on two letters in ID String fx. (tt0000001) 
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
        //TODO: Finish method
        try 
        {
            File inputFile = dbFiles.get(databaseID);
            File tempFile = new File(UserInformation.GetDatabasePath() + "\\temp.tsv");

            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF-8"));
            
            String newLine = reader.readLine();
            writer.write(newLine + System.getProperty("line.separator"));

            while ((newLine = reader.readLine()) != null) 
            {
                if (Integer.parseInt(newLine.substring(0, newLine.indexOf("\t")).substring(2)) == dataID) 
                {
                    continue;
                }
                writer.write(newLine + System.getProperty("line.separator"));
            }

            writer.flush();
            writer.close();
            
            Files.move(tempFile.toPath(), inputFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);         
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

    private byte[] randomAccessFileRead(File file, long pos, int size)
    {
        try 
        {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            randomAccessFile.seek(pos);
            byte[] bytes = new byte[size];
            randomAccessFile.read(bytes);
            randomAccessFile.close();
            return bytes;
        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
            return null;
        }
    }
    
    public String[] getColumnTitles(int databaseID)
    {
        return read(databaseID, 0);
    }
}