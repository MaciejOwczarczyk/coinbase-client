package pl.owczarczyk.coinbase.authorization;

import org.springframework.util.MultiValueMap;

import java.net.URI;

public interface AuthorizationService {

    MultiValueMap<String, String> getHeader(String requestPath, String method, String body, String timestamp);
    MultiValueMap<String, String> getHeaderPro(URI requestPath, String method, String body, String timestamp);
}
