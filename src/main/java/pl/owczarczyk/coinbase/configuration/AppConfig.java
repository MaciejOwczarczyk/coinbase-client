package pl.owczarczyk.coinbase.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@ComponentScan("pl.owczarczyk.coinbase")
@EnableJpaRepositories("pl.owczarczyk.coinbase")
@PropertySource("classpath:application.properties")
public class AppConfig {

    private static final String SERVER_COINBASE_URL = "server.coinbase.url";

    @Bean
    public WebClient webClient() {
        String baseUri = configLoaderService().getPropertyByName(SERVER_COINBASE_URL);
        return WebClient.builder().baseUrl(baseUri).build();
    }

    @Bean
    public ConfigLoaderServiceImpl configLoaderService() {
        return new ConfigLoaderServiceImpl();
    }


}
