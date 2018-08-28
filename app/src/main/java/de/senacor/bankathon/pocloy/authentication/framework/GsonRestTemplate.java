package de.senacor.bankathon.pocloy.authentication.framework;

import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class GsonRestTemplate extends RestTemplate {
    public GsonRestTemplate() {
        this.getMessageConverters().add(new GsonHttpMessageConverter());
    }
}
