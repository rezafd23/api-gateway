package com.gateway.database.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class UserService {

    private RestTemplate restTemplate;

    public UserService() {
    }

    public String getDataUser(String params){
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(params);
            json.remove("queueName");

            HttpHeaders header = new HttpHeaders();
            header.set("authorization", String.valueOf(json.get("apikey")));

            restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(header);
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://192.168.18.5:3000/app/api/user/getUser", HttpMethod.GET, entity, String.class);

            System.out.println("isiRESULT: "+response.getBody());
            return String.valueOf(response.getBody());

        } catch (Exception e){
            System.out.println("error LOGIN PIN");
            e.printStackTrace();
            return "0";
        }
    }

    public String addPersonalData(String params){
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();


            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(params);
            json.remove("queueName");

            Map map = new HashMap();
            map.put("Content-Type", "application/json");
            map.put("authorization", json.get("apikey"));
            headers.setAll(map);
            json.remove("apikey");
            System.out.println("ISIJSON: "+json);

            HttpEntity<?> request = new HttpEntity<>(json, headers);

//            System.out.println("isi URL: "+url);
            restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.postForEntity("http://192.168.18.5:3000/app/api/user/addPersonalData", request, String.class);
            System.out.println("isiRESULT: "+response.getBody());
            return String.valueOf(response.getBody());

        } catch (Exception e){
            System.out.println("error Register Phone Number");
            e.printStackTrace();
            return "0";
        }

    }
}
