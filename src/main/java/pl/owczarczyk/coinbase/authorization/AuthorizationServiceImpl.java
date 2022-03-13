package pl.owczarczyk.coinbase.authorization;

import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import pl.owczarczyk.coinbase.config.ConfigLoaderServiceImpl;
import pl.owczarczyk.coinbase.timestamp.CoinbaseTimeStampServiceImpl;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.management.RuntimeErrorException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class AuthorizationServiceImpl implements AuthorizationService {

    private static final String API_SECRET = "client.coinbase.secret";
    private static final String API_KEY = "client.coinbase.key";
    private static final String API_PASSPHRASE = "client.coinbase.pass-phrase";
    private static final String HMAC_SHA_256 = "HmacSHA256";
    private final ConfigLoaderServiceImpl configLoaderService;
    private final CoinbaseTimeStampServiceImpl timeStampService;

    public AuthorizationServiceImpl(ConfigLoaderServiceImpl configLoaderService, CoinbaseTimeStampServiceImpl timeStampService) {
        this.configLoaderService = configLoaderService;
        this.timeStampService = timeStampService;
    }

    @Override
    public MultiValueMap<String, String> getHeader(String requestPath, String method, String body, String timestamp) {
        String preHash = timestamp + method.toUpperCase() + requestPath + body;
        String apiSecret = this.configLoaderService.getPropertyByName(API_SECRET);
        String apiKey = this.configLoaderService.getPropertyByName(API_KEY);
        var signature1 = new HmacUtils(HMAC_SHA_256, apiSecret.getBytes()).hmacHex(preHash);
        MultiValueMap<String, String> outPut = new HttpHeaders();
        outPut.add("CB-ACCESS-SIGN", signature1);
        outPut.add("CB-ACCESS-TIMESTAMP", String.valueOf(timestamp));
        outPut.add("CB-ACCESS-KEY", apiKey);
        outPut.add("Content-Type","application/json");
        return outPut;
    }

    @Override
    public MultiValueMap<String, String> getHeaderPro(String endpoint, String method, String body) {
        try {
            String timeStamp = timeStampService.getTimeStamp();
            String apiSecret = this.configLoaderService.getPropertyByName(API_SECRET);
            String apiKey = this.configLoaderService.getPropertyByName(API_KEY);
            String passPhrase = this.configLoaderService.getPropertyByName(API_PASSPHRASE);
            String prehash = timeStamp + method.toUpperCase() + endpoint + body;
            byte[] secretDecoded = Base64.getDecoder().decode(apiSecret);
            SecretKeySpec keyspec = new SecretKeySpec(secretDecoded, Mac.getInstance(HMAC_SHA_256).getAlgorithm());
            Mac sha256 = Mac.getInstance(HMAC_SHA_256);
            sha256.init(keyspec);
            String sign = Base64.getEncoder().encodeToString(sha256.doFinal(prehash.getBytes()));
            MultiValueMap<String, String> outPut = new HttpHeaders();
            outPut.add("CB-ACCESS-SIGN", sign);
            outPut.add("CB-ACCESS-TIMESTAMP", String.valueOf(timeStamp));
            outPut.add("CB-ACCESS-KEY", apiKey);
            outPut.add("CB-ACCESS-PASSPHRASE", passPhrase);
            outPut.add("Content-Type","application/json");
            outPut.add("Accept", "application/json");
            return outPut;
        } catch (InvalidKeyException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeErrorException(new Error("Cannot set up authentication headers."));
        }
    }
}
