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
            JSONObject responseObject = (JSONObject) parser.parse(response.getBody());
            JSONObject payload = (JSONObject) responseObject.get("payload");
            System.out.println("APP_STATUS: "+payload.get("application_status"));

            if (payload.get("application_status").equals("APPLICATION_SUCCESS")){
                JSONObject personalData = (JSONObject) payload.get("personal");

            }


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
            if (json.get("income")!=null){
                json.remove("income");
            }

            HttpEntity<?> request = new HttpEntity<>(json, headers);

//            System.out.println("isi URL: "+url);
            restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.postForEntity("http://192.168.18.5:3000/app/api/user/addPersonalData", request, String.class);
            System.out.println("isiRESULT: "+response.getBody());
            return String.valueOf(response.getBody());

        } catch (Exception e){
            System.out.println("error Add Personal Data");
            e.printStackTrace();
            return "0";
        }

    }

    public String addRelative(String params){
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
            if (json.get("income")!=null){
                json.remove("income");
            }

            System.out.println("ISIJSON: "+json);

            HttpEntity<?> request = new HttpEntity<>(json, headers);

//            System.out.println("isi URL: "+url);
            restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.postForEntity("http://192.168.18.5:3000/app/api/user/addRelativeData", request, String.class);
            System.out.println("isiRESULT: "+response.getBody());
            return String.valueOf(response.getBody());

        } catch (Exception e){
            System.out.println("error Add Relative Data");
            e.printStackTrace();
            return "0";
        }

    }

    public String addWorkData(String params){
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
            ResponseEntity<?> response = restTemplate.postForEntity("http://192.168.18.5:3000/app/api/user/addWorkData", request, String.class);
            System.out.println("isiRESULT: "+response.getBody());
            return String.valueOf(response.getBody());

        } catch (Exception e){
            System.out.println("error Add Work");
            e.printStackTrace();
            return "0";
        }

    }

    public String uploadEKTP(String params){
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();


            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(params);
            json.remove("queueName");

            Map map = new HashMap();
            map.put("Content-Type", "multipart/form-data");
            map.put("authorization", json.get("apikey"));
            headers.setAll(map);
            json.remove("apikey");
            System.out.println("ISIJSON: "+json);

            HttpEntity<?> request = new HttpEntity<>(json, headers);

//            System.out.println("isi URL: "+url);
            restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.postForEntity("http://192.168.18.5:3000/app/api/user/uploadEktp", request, String.class);
            System.out.println("isiRESULT: "+response.getBody());
            return String.valueOf(response.getBody());

        } catch (Exception e){
            System.out.println("error Upload EKTP");
            e.printStackTrace();
            return "0";
        }

    }

    public String finishRegister(String params){
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(params);
            json.remove("queueName");

            Map map = new HashMap();
            map.put("Content-Type", "application/json");
            map.put("apikey", "1001");
            headers.setAll(map);
            System.out.println("ISIJSON: "+json);

            restTemplate = new RestTemplate();

            HttpHeaders header = new HttpHeaders();
            header.set("authorization", String.valueOf(json.get("apikey")));
            json.remove("apikey");
            System.out.println("isi json skrg: "+json);

            HttpEntity<?> request = new HttpEntity<>(json, headers);

            HttpEntity entity = new HttpEntity(header);
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://192.168.18.5:3000/app/api/user/finishRegis", HttpMethod.PUT, entity, String.class);

            if (response.getBody().contains("Success")){
                ResponseEntity<?> response2 = restTemplate.
                        postForEntity("http://192.168.18.5:8081/api/nasabah/add/", request, String.class);
                if (response2.getBody().toString().contains("200")){

                return String.valueOf(response.getBody());
                }
                return "0";
            }else {
                return "0";
            }
//            return String.valueOf(response.getBody());

        } catch (Exception e){
            System.out.println("error Finish Registration");
            e.printStackTrace();
            return "0";
        }

    }
}
