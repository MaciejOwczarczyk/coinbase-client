package pl.owczarczyk.coinbase.configuration;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class ConfigLoaderServiceImpl implements ConfigLoaderService {

    private final Properties properties;

    public ConfigLoaderServiceImpl() {
        this.properties = new Properties();
        this.init();
    }

    private void init() {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("client.properties"))  {
            this.properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getPropertyByName(String name) {
        String property = properties.getProperty(name);
        return Objects.nonNull(property) ? property : "";
    }
}
