package nl.louis.filecapdemo.repository;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class FileCapRepository {

    private RestTemplate restTemplate;

    public FileCapRepository() {
        this.restTemplate = new RestTemplate();
    }

    public boolean execute(String url, HttpEntity httpEntity) {
        ResponseEntity<String> exchange = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        if (exchange.getStatusCode().is2xxSuccessful()) {
            return true;
        }
        return false;
    }
}
