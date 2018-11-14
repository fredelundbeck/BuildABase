import java.util.ArrayList;
import java.util.Scanner;
import static Utilities.ConsoleUtility.*;

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
            byte opCode = (byte)InputHandler.getNumericalInputRange(1,6);
            runOPCode(opCode);

            if (running) 
            {
                pressEnterToContinue();
                clearScreen();
            }
        }
        scanner.close();
    }

    private void promptMenu()
    {
        System.out.println(
        "\nCRUD Operations\n" +
        "-------------------\n" +
        "1. Create new data \n" + 
        "2. Read from data \n" + 
        "3. Update data \n" +
        "4. Delete data \n" +
        "-------------------\n" +
        "5. Search for data\n" +
        "-------------------\n" +
        "6. Exit program \n\n" +
        "Operation to run: ");
    }

    private void runOPCode(byte opCode)
    {
        int databaseID; 
        int dataID;
        String[] columns;
        ArrayList<String[]> data;

        switch (opCode) {

            //CREATE
            case 1:

                databaseID = promptAndGetDatabaseID();

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

                databaseID = promptAndGetDatabaseID();

                columns = handler.getColumnTitles(databaseID);

                System.out.println("\nEnter the data ID you would like to read: ");

                dataID = InputHandler.getNumericalInput();

                data = new ArrayList<String[]>();

                data.add(handler.read(databaseID, dataID));

                displayData(data, columns);

                break;

            //UPDATE
            case 3:

                databaseID = promptAndGetDatabaseID();

                System.out.println("\nEnter the data ID you would like to update: ");

                dataID = InputHandler.getNumericalInput();

                columns = handler.getColumnTitles(databaseID);

                String[] updatedData = new String[columns.length];

                updatedData[0] = handler.getIDPrefix(databaseID) + dataID;

                for (int i = 1; i < columns.length; i++) 
                {
                    System.out.println("Enter new " + columns[i] + " value: ");
                    updatedData[i] = InputHandler.getInput();
                }

                handler.update(databaseID, dataID, updatedData);

                break;

            //DELETE
            case 4:

                databaseID = promptAndGetDatabaseID();

                System.out.println("Enter data id to delete: ");

                dataID = InputHandler.getNumericalInput();

                handler.delete(databaseID, dataID);    

                break;

            //SEARCH
            case 5:

                databaseID = promptAndGetDatabaseID();

                columns = handler.getColumnTitles(databaseID);

                System.out.println("\nEnter search string: ");

                String search = InputHandler.getInput();

                data = new ArrayList<String[]>(handler.search((int)databaseID, search));

                displayData(data, columns);

                break;

            //EXIT
            case 6:

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
            newLine();

            for (String column : columns) 
            {
                System.out.print(column + "\t");    
            }
    
            newLine();
    
            for (int i = 0; i < String.join("", columns).length()+25; i++) 
            {
                System.out.print("-");    
            }
    
            newLine();
    
            for (String[] arr : data) 
            {
                for (int i = 0; i < arr.length; i++) 
                {
                    System.out.print(arr[i] + "\t");    
                } 
                newLine();   
            }

            String matchMsg = data.size() > 1 ? " matches" : " match";
            System.out.println("\n" + data.size() + matchMsg + " was found!");
        } 
        else
        {
            System.out.println("\nNo matches found!");
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

    private int promptAndGetDatabaseID()
    {
        System.out.println("Enter database id: ");

        displayAvailableDatabases();
        
        return (InputHandler.getNumericalInputRange(1, handler.getDBCount())-1);    
    }
}