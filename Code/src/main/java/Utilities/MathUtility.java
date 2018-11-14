package Utilities;

public class MathUtility 
{
    public static boolean isNumericalValue(String input)
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