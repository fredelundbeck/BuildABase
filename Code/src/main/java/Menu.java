import java.util.ArrayList;
import java.util.Scanner;


public class Menu 
{
    private Scanner scanner;
    private DBHandler handler;
    private DBPathFinder dbPathFinder;

    private boolean running = false;

    public Menu() 
    {
        dbPathFinder = new DBPathFinder();
        scanner = new Scanner(System.in);
        handler = new DBHandler(dbPathFinder.getPaths());
    }

    public void start()
    {
        running = true;
        loop();
    }

    private void loop()
    {
        while (running) 
        {
            promptMenu();
            
            byte opCode = (byte)InputHandler.getNumericalInputRange(1,5);
            
            runOPCode(opCode);

            if (running) 
            {
                pressEnterToContinue();
            }
        }
        scanner.close();
    }

    private void promptMenu()
    {
        System.out.println("\nDB MENU \n" +
        "1. Create new data \n" + 
        "2. Read from data \n" + 
        "3. Update existing data \n" +
        "4. Delete data \n" +
        "5. Exit program \n\n" +
        "Operation to run: ");
    }

    private void runOPCode(byte opCode)
    {
        int databaseID;
        int dataID;
        String[] columns;

        switch (opCode) {


            //CREATE
            case 1:

                System.out.println("Enter database id: ");

                displayAvailableDatabases();

                databaseID = (InputHandler.getNumericalInputRange(1, handler.getDBCount())-1);

                columns = handler.getColumnTitles(databaseID);
                String[] newData = new String[columns.length];

                for (int i = 1; i < columns.length; i++) 
                {
                    System.out.println("Enter data for " + columns[i] + ": ");
                    newData[i] = InputHandler.getInput();
                }

                handler.create(databaseID, newData);
                break;

            //READ
            case 2:

                System.out.println("\nEnter database id, you want to read from: ");

                displayAvailableDatabases();
                
                databaseID = (InputHandler.getNumericalInputRange(1, handler.getDBCount())-1);

                System.out.println("\nEnter option number: ");
                System.out.println("1. Read from ID input\n" + 
                "2. Read from string search input");

                byte readChoice = (byte)(InputHandler.getNumericalInputRange(1, 2));

                columns = handler.getColumnTitles(databaseID);
                ArrayList<String[]> data = new ArrayList<String[]>();


                if (readChoice == 1) 
                {
                    System.out.println("\nEnter the data ID you would like to read: ");

                    dataID = InputHandler.getNumericalInput();
 
                    data.add(handler.read(databaseID, dataID));
                } 
                else
                {
                    System.out.println("\nEnter the search string you would like to search with: ");

                    String search = InputHandler.getInput();

                    data = handler.search((int)databaseID, search);
                }          
                
                displayData(data, columns);

                break;

            //UPDATE
            case 3:
                
                break;

            //DELETE
            case 4:

                System.out.println("\nEnter database id to delete from: ");
                displayAvailableDatabases();
                databaseID = (InputHandler.getNumericalInputRange(1, handler.getDBCount())-1);

                System.out.println("Enter data id to delete: ");
                dataID = InputHandler.getNumericalInput();

                handler.delete(databaseID, dataID);    

                break;

            //EXIT
            case 5:

                running = false;
                break;
        
            default:
                break;
        }
    }

    private void displayData(ArrayList<String[]> data, String[] columns)
    {
        if (data.size() > 0 && data.get(0) != null) 
        {         
            for (String column : columns) 
            {
                System.out.print(column + "\t");    
            }
    
            System.out.println();
    
            for (int i = 0; i < String.join("", columns).length(); i++) 
            {
                System.out.print("-");    
            }
    
            System.out.println();
    
            for (String[] arr : data) 
            {
                for (int i = 0; i < arr.length; i++) 
                {
                    System.out.print(arr[i] + "\t");    
                } 
                System.out.println();   
            }

            String matchMsg = data.size() > 1 ? " matches" : " match";
            System.out.println(data.size() + matchMsg + " was found!");
        } 
        else
        {
            System.out.println("No matches found!");
        }
    }
    
    private void displayAvailableDatabases()
    {
        ArrayList<String> fileNames = handler.getDBNames();

        for (int i = 0; i < fileNames.size(); i++) 
        {
            System.out.println(i+1 + ". " + fileNames.get(i));
        }
    }
 

    private void pressEnterToContinue()
    {
        System.out.println("\nPress enter to continue..");
        
        InputHandler.getInput();
    }
}