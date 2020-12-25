package com.gateway.database.service;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

public class AuthService {

    private RestTemplate restTemplate;

//    @Value("${file.nodeUrl}")
//    private String url;

    public AuthService() {

    }
    public String registerPhoneNumber(String params){
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            Map map = new HashMap();
            map.put("Content-Type", "application/json");
            headers.setAll(map);

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(params);
            json.remove("queueName");

            HttpEntity<?> request = new HttpEntity<>(json, headers);

            restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.postForEntity("http://192.168.18.5:3000/app/api/user/register", request, String.class);
            System.out.println("isiRESULT: "+response.getBody());
            return String.valueOf(response.getBody());

        } catch (Exception e){
            System.out.println("error Register Phone Number");
            e.printStackTrace();
            return "0";
        }

    }

    public String loginPhoneNumber(String params){
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            Map map = new HashMap();
            map.put("Content-Type", "application/json");
            headers.setAll(map);

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(params);
            json.remove("queueName");

            HttpEntity<?> request = new HttpEntity<>(json, headers);

            restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.postForEntity("http://192.168.18.5:3000/app/api/user/loginPhone", request, String.class);
            System.out.println("isiRESULT: "+response.getBody());
            return String.valueOf(response.getBody());

        } catch (Exception e){
            System.out.println("error Register Phone Number");
            e.printStackTrace();
            return "0";
        }

    }

    public String generateOtp(String params){
        try {

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(params);
            json.remove("queueName");

            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            Map map = new HashMap();
            map.put("Content-Type", "application/json");
            headers.setAll(map);

            HttpEntity<?> request = new HttpEntity<>(json, headers);

            restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.postForEntity("http://192.168.18.5:3000/app/api/otp/generateOtp", request, String.class);
            System.out.println("isiRESULT: "+response.getBody());
            return String.valueOf(response.getBody());

        } catch (Exception e){
            System.out.println("error Generate OTP");
            e.printStackTrace();
            return "0";
        }

    }

    public String submitOtpRegister(String params){
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            Map map = new HashMap();
            map.put("Content-Type", "application/json");
            headers.setAll(map);

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(params);
            json.remove("queueName");

            HttpEntity<?> request = new HttpEntity<>(json, headers);

            restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.postForEntity("http://192.168.18.5:3000/app/api/user/submitOtpRegister", request, String.class);
            System.out.println("isiRESULT: "+response.getBody());
            return String.valueOf(response.getBody());

        } catch (Exception e){
            System.out.println("error Submit OTP");
            e.printStackTrace();
            return "0";
        }
    }

    public String createPin(String params){
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            Map map = new HashMap();
            map.put("Content-Type", "application/json");
            headers.setAll(map);

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(params);
            json.remove("queueName");

            HttpEntity<?> request = new HttpEntity<>(json, headers);

            restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.postForEntity("http://192.168.18.5:3000/app/api/user/createPin", request, String.class);
            System.out.println("isiRESULT: "+response.getBody());
            return String.valueOf(response.getBody());

        } catch (Exception e){
            System.out.println("error Submit PIN");
            e.printStackTrace();
            return "0";
        }
    }

    public String loginPin(String params){
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            Map map = new HashMap();
            map.put("Content-Type", "application/json");
            headers.setAll(map);

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(params);
            json.remove("queueName");

            HttpEntity<?> request = new HttpEntity<>(json, headers);

            restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.postForEntity("http://192.168.18.5:3000/app/api/user/loginPin", request, String.class);
            System.out.println("isiRESULT: "+response.getBody());
            return String.valueOf(response.getBody());

        } catch (Exception e){
            System.out.println("error LOGIN PIN");
            e.printStackTrace();
            return "0";
        }
    }

}
