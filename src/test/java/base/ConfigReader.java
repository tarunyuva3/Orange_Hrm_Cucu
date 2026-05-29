package base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;
    private static final String FILE_PATH = "src/test/resources/config.properties";

    public static void loadConfig()
    {
        try (FileInputStream fis = new FileInputStream(FILE_PATH))
        {
            properties = new Properties();
            properties.load(fis);
        } catch (IOException e)
        {
            throw new RuntimeException("Could not find or load config.properties at: " + FILE_PATH, e);
        }
    }

    public static String getProperty(String key) {
        if (properties == null)
        {
            loadConfig();
        }
        return properties.getProperty(key);
    }
}