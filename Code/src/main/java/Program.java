import UserSettings.UserInformation;

public class Program 
{
    public static void main(String[] args) 
    {
        UserInformation.findAndSetOSTYPE();

        Menu menu = new Menu();
        menu.start();
    }
}