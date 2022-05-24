package pl.owczarczyk.coinbase.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;
import pl.owczarczyk.coinbase.config.ConfigLoaderServiceImpl;
import pl.owczarczyk.coinbase.generic.WebClientFilter;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;


@Configuration
@ComponentScan("pl.owczarczyk.coinbase")
@EnableJpaRepositories("pl.owczarczyk.coinbase")
@PropertySource("classpath:application.properties")
@EnableWebFlux
public class AppConfig {

    private static final String SERVER_COINBASE_URL = "server.coinbase.url";

    @Bean
    public WebClient webClient() {
        HttpClient client = HttpClient.create()
                .responseTimeout(Duration.ofSeconds(5));
        String baseUri = configLoaderService().getPropertyByName(SERVER_COINBASE_URL);
        return WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl(baseUri)
                .filter(WebClientFilter.logRequest())
                .filter(WebClientFilter.logResponse())
                .build();
    }

    @Bean
    public ConfigLoaderServiceImpl configLoaderService() {
        return new ConfigLoaderServiceImpl();
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


}
