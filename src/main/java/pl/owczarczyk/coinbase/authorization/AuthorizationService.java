package pl.owczarczyk.coinbase.authorization;

import org.springframework.util.MultiValueMap;

public interface AuthorizationService {

    MultiValueMap<String, String> getHeader(String requestPath, String method, String body, String timestamp);
    MultiValueMap<String, String> getHeaderPro(String requestPath, String method, String body, String timestamp);
}
