package de.senacor.bankathon.pocloy.authentication.framework;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import de.senacor.bankathon.pocloy.authentication.dto.Credentials;

public class GsonRestTemplate extends RestTemplate {
    private static Credentials credentials;
    
    public GsonRestTemplate() {
        this.getMessageConverters().add(new GsonHttpMessageConverter());
    }

    public static Credentials getCredentials() {
        return credentials;
    }

    public static void setCredentials(Credentials credentials) {
        GsonRestTemplate.credentials = credentials;
    }
}
