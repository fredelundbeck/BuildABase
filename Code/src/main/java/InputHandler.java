import java.util.Scanner;

class InputHandler
{
    private static Scanner input = new Scanner(System.in);

    public static String getInput()
    {
        System.out.print("> ");
        return input.nextLine();
    }

    public static int getNumericalInput()
    {
        int value = 0;

        while (true) 
            {
               String input = getInput();

               if (isNumericalValue(input)) 
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

    public static int getNumericalInputRange(int min, int max)
    {
        int value = getNumericalInput();

        while (true) 
        {
            if (value >= min && value <= max) 
            {
                break;    
            }

            System.out.println("Numerical value must be in the range of (" + min + ", " + max + ")");
            value = getNumericalInput();    
        }
        return value;
    }

    private static boolean isNumericalValue(String input)
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

}