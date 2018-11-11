import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

import UserSettings.UserInformation;

public class DBPathFinder 
{
    Scanner scanner;

    public DBPathFinder() 
    {
        scanner = new Scanner(System.in);
    }

    public ArrayList<File> getPaths()
    {
        ArrayList<File> files = new ArrayList<File>();

        Path storeDBFolderPathTxt = Paths.get("C:/BuildABase_Resources/dbPath");
        String path = null;

        //If resources folder doesn't exist, create it!
        if (!Files.exists(storeDBFolderPathTxt))
        {
            System.out.println("Could not find resources folder! Trying to create folder...");
          
            try 
            {
                Files.createDirectories(storeDBFolderPathTxt);
                Files.createFile(Paths.get(storeDBFolderPathTxt + "\\dbsFolderPath.txt"));
                System.out.println("Folder successfully created!");
            } 
            catch (IOException e) 
            {
                System.out.println("Could not create folder in " + storeDBFolderPathTxt.toString());
            }
        } 
        //If it exists
        else
        {
            System.out.println("Resources folder was found! Checking if dbsFolderPath.txt exists...");

            //Check if dbsFolderPath.txt exists in that folder. If not, create it
            if (!Files.exists(Paths.get(storeDBFolderPathTxt + "\\dbsFolderPath.txt")))
            {
                System.out.println("dbsFolderPath.txt was not found inside resources folder! Trying to create file...");
                try 
                {
                    Files.createFile(Paths.get(storeDBFolderPathTxt + "\\dbsFolderPath.txt")); 
                    System.out.println("File was successfully created!");
                } 
                catch (IOException e) 
                {
                    System.out.println(e.getMessage());
                }
            }
            //If it already exists, check if it's empty or contains path
            else
            {
                System.out.println("dbsFolderPath.txt was found! Checking if file contains path...");
                File dbPathTxtFile = new File(storeDBFolderPathTxt + "\\dbsFolderPath.txt");

                try 
                {
                    Scanner reader = new Scanner(dbPathTxtFile, "UTF-8");
                    
                    if (reader.hasNextLine()) 
                    {
                        path = reader.nextLine(); 
                        System.out.println("dbsFolderPath.txt contained a path! Adding database path(s)...");  
                    } 
                    else
                    {
                        System.out.println("dbsFolderPath.txt didn't contain a path!");
                    }
                    reader.close();
                } 
                catch (IOException e) 
                {
                    System.out.println(e.getMessage());
                }
            }
        }

        System.out.println("");

        File folder;
        File[] dbFolderFiles;

        //If path was not found
        if (path == null) 
        {
            System.out.println("Please navigate to your database folder manually (.../databases)");
            System.out.print("Path: ");
            path = InputHandler.getInput();
        } 

        while (true) 
        {
            folder = new File(path);
            
            if (folder.exists() && folder.isDirectory()) 
            {
                dbFolderFiles = folder.listFiles();

                if (dbFolderFiles.length > 0) 
                {
                    for (int i = 0; i < dbFolderFiles.length; i++) 
                    {
                        if (dbFolderFiles[i].getName().endsWith(".tsv")) 
                        {
                            files.add(dbFolderFiles[i]);
                            System.out.println("[ADDED] " + dbFolderFiles[i].getName());
                        }
                    }

                    try 
                    {    
                        Files.write(Paths.get(storeDBFolderPathTxt + "\\dbsFolderPath.txt"), path.getBytes("UTF-8"));
                    } 
                    catch (IOException e) 
                    {
                        System.out.println(e.getMessage());
                    }
                    
                    UserInformation.SetDatabasePath(path);
                    System.out.println();
                    return files;
                } 
                else
                {
                    System.out.println("Path folder was found, but does not contain any .tsv files!\n" +
                    "Closing program..");
                    System.exit(0);
                }
            } 
            else
            {
                System.out.println("\nThe path doesn't exist, would you like to try again? (y/n)");

                if (InputHandler.getInput().toUpperCase().equals("N")) 
                {
                    System.exit(0);       
                } 
                else 
                {
                    System.out.print("Enter path: ");
                    path = InputHandler.getInput();
                }
            }
        }
    }
}