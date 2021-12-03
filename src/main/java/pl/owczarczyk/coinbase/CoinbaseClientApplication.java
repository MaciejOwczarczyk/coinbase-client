package pl.owczarczyk.coinbase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.owczarczyk.coinbase.configuration.CoinbaseAPI;

@SpringBootApplication
public class CoinbaseClientApplication implements CommandLineRunner {

	CoinbaseAPI coinbaseAPI;

	public CoinbaseClientApplication(CoinbaseAPI coinbaseAPI) {
		this.coinbaseAPI = coinbaseAPI;
	}

	public static void main(String[] args) {
		SpringApplication.run(CoinbaseClientApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		coinbaseAPI.test();
	}
}
