package de.sadrab.trickster;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@RestController
public class TricksterEndpoint {

    @Value("#{'${cup.ports}'.split(',')}")
    private List<String> cupPorts;

    private RestTemplate restTemplate;

    public TricksterEndpoint(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/play")
    void play(){
        deleteCoinFromCups();

        PutCoinIntoRandomCup();
    }

    @GetMapping("/choose/{number}")
    Boolean choose(@PathVariable String number){
        String cupUrl = getCupUrl(number);

        ResponseEntity<Boolean> response = restTemplate.getForEntity(cupUrl, Boolean.class);

        return response.getBody();
    }


    private String getCupUrl(String number) {
        String port = cupPorts.get(Integer.valueOf(number));
        return "http://localhost:" + port + "/coin";
    }

    private void PutCoinIntoRandomCup() {
        Random randomNumber=new Random(cupPorts.size());
        String randomPort = cupPorts.get(randomNumber.nextInt());
        String randomCupUrl = "http://localhost:" + randomPort + "/coin";
        restTemplate.put(randomCupUrl,null);
    }

    private void deleteCoinFromCups() {
        cupPorts.stream()
                .map(port -> "http://localhost:" + port + "/coin")
                .forEach(url -> restTemplate.delete(url));
    }


}
