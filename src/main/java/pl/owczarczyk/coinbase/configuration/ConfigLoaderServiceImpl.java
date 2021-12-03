package pl.owczarczyk.coinbase.configuration;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class ConfigLoaderServiceImpl implements ConfigLoaderService {

    private static final String API_SECRET = "client.coinbase.secret";
    private static final String API_KEY = "client.coinbase.key";
    private static final String API_PASSPHRASE = "client.coinbase.pass-phrase";
    private final Properties properties;
    private Map<String, String> map;

    public ConfigLoaderServiceImpl() {
        this.properties = new Properties();
        this.initProperties();
    }

    private void initProperties() {
        try {
            this.properties.load(new FileInputStream("src/main/resources/client.properties"));
            map = new HashMap<>();
            map.put(API_KEY, properties.getProperty(API_KEY));
            map.put(API_SECRET, properties.getProperty(API_SECRET));
            map.put(API_PASSPHRASE, properties.getProperty(API_PASSPHRASE));
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public String getPropertyByName(String name) {
        return map.get(name) == null ? "" : map.get(name);
    }
}
