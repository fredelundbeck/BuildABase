package Utilities;

import java.io.IOException;

import UserSettings.OSType;
import UserSettings.UserInformation;

public class ConsoleUtilityMethods
{
    public static void newLine()
    {
        System.out.println();
    }

    public static void clearScreen()
    {
        try 
        {
            if (UserInformation.GetOSType() == OSType.WINDOWS)
            {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else 
            {
                Runtime.getRuntime().exec("clear");
            }
        } 
        catch (IOException | InterruptedException ex) {}
    }
}