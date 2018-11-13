package UserSettings;

public class UserInformation 
{
    private static String DATABASE_PATH;
    private static OSType OPERATING_SYSTEM;

    public static String GetDatabasePath()
    {
        return DATABASE_PATH;
    }

    public static void SetDatabasePath(String path)
    {
        if (DATABASE_PATH == null) 
        {
            DATABASE_PATH = path;
        }
    }

    public static OSType GetOSType()
    {
        return OPERATING_SYSTEM;
    }

    public static void findAndSetOSTYPE()
    {
        if (OPERATING_SYSTEM == null) 
        {
            if (System.getProperty("os.name").contains("Windows")) 
            {
                OPERATING_SYSTEM = OSType.WINDOWS;
            }
        }
    }
}

