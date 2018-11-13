package UserSettings;

public class UserInformation 
{
    private static String DATABASE_PATH;
    private static OSType OPERATING_SYSTEM;

    public static String getDatabasePath()
    {
        return DATABASE_PATH;
    }

    public static void setDatabasePath(String path)
    {
        if (DATABASE_PATH == null) 
        {
            DATABASE_PATH = path;
        }
    }

    public static OSType getOSType()
    {
        return OPERATING_SYSTEM;
    }

    public static void findAndSetOSTYPE()
    {
        if (OPERATING_SYSTEM == null) 
        {
            String osName = System.getProperty("os.name");
            if (osName.contains("Windows")) 
            {
                OPERATING_SYSTEM = OSType.WINDOWS;
            }
            else if (osName.equals("Linux")) 
            {
                OPERATING_SYSTEM = OSType.LINUX;
            }
            else 
            {
                OPERATING_SYSTEM = OSType.MAC;
            }
        }
    }
}

