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
        if (UserInformation.getOSType() == OSType.WINDOWS)
        {
          try
          {
              new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
          }
          catch (IOException | InterruptedException ex) {}
        }
        else
        {
            String ansiClear = "\033[H\033[2J";
            System.out.print(ansiClear);
            System.out.flush();
            //Runtime.getRuntime().exec("clear");
        }
    }
}
