import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Menu 
{
    private Scanner scanner;
    private DBHandler handler;

    private boolean running = false;

    public Menu() 
    {
        scanner = new Scanner(System.in);
        handler = new DBHandler(getDatabasePaths());
    }

    public void start() throws FileNotFoundException
    {
        running = true;
        loop();
    }

    private void loop() throws FileNotFoundException
    {
        while (running) 
        {
            promptMenu();
            
            byte opCode = (byte)getNumericalValueFromInput();
            
            runOPCode(opCode);

            pressEnterToContinue();

        }

        System.out.println("goodbye!");
    }

    private ArrayList<String> getDatabasePaths()
    {
        System.out.println("Please navigate to your database folder (.../databases)");
        
        ArrayList<String> paths = new ArrayList<String>();
        String path;

        File folder;
        File[] files;
        
        while (true) 
        {
            path = scanner.nextLine();
            folder = new File(path);
            
            if (folder.exists() && folder.isDirectory()) 
            {
                files = folder.listFiles();

                if (files.length > 0) 
                {
                    for (int i = 0; i < files.length; i++) 
                    {
                        if (files[i].getName().endsWith(".tsv")) 
                        {
                             paths.add(files[i].getAbsolutePath());
                        }
                    }

                    return paths;
                }
            } 
            else
            {
                System.out.println("The directory doesn't contain any .tsv files! \n" + 
                "Want to try again? (y/n)");

                if (scanner.nextLine().equals("n")) 
                {
                    System.exit(0);       
                }
            }
        }
    }

    

    private void promptMenu()
    {
        System.out.println("What would you like to do? \n" +
        "1. Create new data \n" + 
        "2. Read from data \n" + 
        "3. Update existing data \n" +
        "4. Delete data \n" +
        "5. Exit program \n\n" +
        "Please input which operation you would like to run: ");

    }

    private void runOPCode(byte opCode) throws FileNotFoundException
    {
        switch (opCode) {
            case 1:

                break;

            case 2:

                System.out.println("Which database would you like to read from?");
                
                byte databaseID = (byte)getNumericalValueFromInput();
                
                System.out.println("Which data id would you like to read?");

                int dataID = getNumericalValueFromInput();


                String[] arr = handler.getColumnTitles(0);
                String[] data = handler.read(0, dataID);

                for (int i = 0; i < arr.length; i++) 
                {
                    System.out.println(arr[i] + ": " + data[i]);
                }

                
                break;

            case 5:
                running = false;
                break;
        
            default:
                break;
        }
    }

    private int getNumericalValueFromInput()
    {
        int value = 0;
        
        while (true) 
            {
               String input = scanner.next();

               if (isNumberVal(input)) 
               {
                    value = Integer.parseInt(input);
                    break;
               }
               else
               {
                    System.out.println("You need to input a numerical value!\n");
               }
                
            }

            return value;
    }
    
    private boolean isNumberVal(String input)
    {
        for (int i = 0; i < input.length(); i++) 
        {
            if (!Character.isDigit(input.charAt(i))) 
            {
                return false;
            }
        }
        return true;
    }

    private void pressEnterToContinue()
    {
        System.out.println("\nPress enter to continue..");
        try 
        {
            System.in.read();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    
}