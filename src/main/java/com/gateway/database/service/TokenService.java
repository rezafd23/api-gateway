package com.gateway.database.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class TokenService {

    private RestTemplate restTemplate;

    public TokenService() {
    }

    public String getListVoucer(){
        try {
            HttpHeaders header = new HttpHeaders();
            header.set("apikey", "1001");

            restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(header);
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://192.168.18.5:8082/api/voucer/", HttpMethod.GET, entity, String.class);

            System.out.println("isiRESULT: "+response.getBody());

            return String.valueOf(response.getBody());

        } catch (Exception e){
            System.out.println("error Fetch VOucer");
            e.printStackTrace();
            return "0";
        }
    }

    public String checkuser(String no_pelanggan){
        try {
            HttpHeaders header = new HttpHeaders();
            header.set("apikey", "1001");

            restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(header);
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://192.168.18.5:8082/api/customer/"+no_pelanggan, HttpMethod.GET, entity, String.class);

            System.out.println("isiRESULT: "+response.getBody());

            return String.valueOf(response.getBody());

        } catch (Exception e){
            System.out.println("error Fetch VOucer");
            e.printStackTrace();
            return "0";
        }
    }
}
