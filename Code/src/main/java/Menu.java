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
        System.out.println("\nChoose option! \n" +
        "1. Create new data \n" + 
        "2. Read from data \n" + 
        "3. Update existing data \n" +
        "4. Delete data \n" +
        "5. Exit program \n\n" +
        "Operation to run: ");
    }

    private void runOPCode(byte opCode)
    {
        switch (opCode) {

            //CREATE
            case 1:

                break;

            //READ
            case 2:

                System.out.println("Which database would you like to read from?");

                displayAvailableDatabases();
                
                byte databaseID = (byte)(InputHandler.getNumericalInputRange(1, handler.getDBCount())-1);
                
                System.out.println("\nWhich data id would you like to read?");

                //TODO: change to range input 
                int dataID = InputHandler.getNumericalInput();

                String[] columns = handler.getColumnTitles(databaseID);
                String[] data = handler.read(databaseID, dataID);

                for (int i = 0; i < columns.length-1; i++) 
                {
                    System.out.println("[" + columns[i] + "]" + ": " + data[i]);
                }
  
                break;

            //UPDATE
            case 3:
                
                break;

            //DELETE
            case 4:

                break;

            //EXIT
            case 5:

                running = false;
                break;
        
            default:
                break;
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