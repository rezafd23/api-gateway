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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class UserService {

    private RestTemplate restTemplate;

    public UserService() {
    }

    public String getDataUser(String params) {
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(params);
            json.remove("queueName");

            restTemplate = new RestTemplate();

            HttpHeaders header = new HttpHeaders();
            header.set("authorization", String.valueOf(json.get("apikey")));
            HttpEntity entity = new HttpEntity(header);
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://192.168.18.5:3000/app/api/user/getUser", HttpMethod.GET, entity, String.class);

            System.out.println("isiRESULT: " + response.getBody());
            if (response.getBody().contains("401")){
                return response.getBody();
            }
            JSONObject responseObject = (JSONObject) parser.parse(response.getBody());
            JSONObject payload = (JSONObject) responseObject.get("payload");
            System.out.println("APP_STATUS: " + payload.get("application_status"));

            if (payload.get("application_status").equals("APPLICATION_SUCCESS")) {
                JSONObject personalData = (JSONObject) payload.get("personal");

                HttpHeaders header2 = new HttpHeaders();
                header.set("apikey", "1001");
                HttpEntity entity2 = new HttpEntity(header);
                ResponseEntity<String> response2 = restTemplate.exchange(
                        "http://192.168.18.5:8081/api/nasabah/info/" + personalData.get("no_ktp"), HttpMethod.GET, entity2, String.class);
                JSONObject jsonResponse2 = (JSONObject)parser.parse(response2.getBody());
                JSONObject jsonResponse3 = (JSONObject) jsonResponse2.get("payload");
                payload.put("banking",jsonResponse3);
                responseObject.remove("payload");
                responseObject.put("payload",payload);
                return responseObject.toJSONString();
//                return String.valueOf(response2.getBody());
            } else {
                return String.valueOf(response.getBody());
            }

        } catch (Exception e) {
            System.out.println("error LOGIN PIN");
            e.printStackTrace();
            return "0";
        }
    }

    public String addPersonalData(String params) {
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
            System.out.println("ISIJSON: " + json);
            if (json.get("income") != null) {
                json.remove("income");
            }

            HttpEntity<?> request = new HttpEntity<>(json, headers);

            restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.postForEntity("http://192.168.18.5:3000/app/api/user/addPersonalData", request, String.class);
            System.out.println("isiRESULT: " + response.getBody());
            return String.valueOf(response.getBody());

        } catch (Exception e) {
            System.out.println("error Add Personal Data");
            e.printStackTrace();
            return "0";
        }

    }

    public String addRelative(String params) {
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
            if (json.get("income") != null) {
                json.remove("income");
            }

            System.out.println("ISIJSON: " + json);

            HttpEntity<?> request = new HttpEntity<>(json, headers);

//            System.out.println("isi URL: "+url);
            restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.postForEntity("http://192.168.18.5:3000/app/api/user/addRelativeData", request, String.class);
            System.out.println("isiRESULT: " + response.getBody());
            return String.valueOf(response.getBody());

        } catch (Exception e) {
            System.out.println("error Add Relative Data");
            e.printStackTrace();
            return "0";
        }

    }

    public String addWorkData(String params) {
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
            System.out.println("ISIJSON: " + json);

            HttpEntity<?> request = new HttpEntity<>(json, headers);

//            System.out.println("isi URL: "+url);
            restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.postForEntity("http://192.168.18.5:3000/app/api/user/addWorkData", request, String.class);
            System.out.println("isiRESULT: " + response.getBody());
            return String.valueOf(response.getBody());

        } catch (Exception e) {
            System.out.println("error Add Work");
            e.printStackTrace();
            return "0";
        }

    }

    public String uploadEKTP(String params) {
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
            System.out.println("ISIJSON: " + json);

            HttpEntity<?> request = new HttpEntity<>(json, headers);

//            System.out.println("isi URL: "+url);
            restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.postForEntity("http://192.168.18.5:3000/app/api/user/uploadEktp", request, String.class);
            System.out.println("isiRESULT: " + response.getBody());
            return String.valueOf(response.getBody());

        } catch (Exception e) {
            System.out.println("error Upload EKTP");
            e.printStackTrace();
            return "0";
        }

    }

    public String finishRegister(String params) {
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(params);
            json.remove("queueName");

            Map map = new HashMap();
            map.put("Content-Type", "application/json");
            map.put("apikey", "1001");
            headers.setAll(map);
            System.out.println("ISIJSON: " + json);

            restTemplate = new RestTemplate();

            HttpHeaders header = new HttpHeaders();
            header.set("authorization", String.valueOf(json.get("apikey")));
            json.remove("apikey");
            System.out.println("isi json skrg: " + json);

            HttpEntity<?> request = new HttpEntity<>(json, headers);

            HttpEntity entity = new HttpEntity(header);
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://192.168.18.5:3000/app/api/user/finishRegis", HttpMethod.PUT, entity, String.class);

            if (response.getBody().contains("Success")) {
                ResponseEntity<?> response2 = restTemplate.
                        postForEntity("http://192.168.18.5:8081/api/nasabah/add/", request, String.class);
                if (response2.getBody().toString().contains("200")) {

                    return String.valueOf(response.getBody());
                }
                return "0";
            } else {
                return "0";
            }
//            return String.valueOf(response.getBody());

        } catch (Exception e) {
            System.out.println("error Finish Registration");
            e.printStackTrace();
            return "0";
        }

    }

//    public String getMutasi(String data){
//        try {
//            JSONParser parser = new JSONParser();
//            JSONObject json = (JSONObject) parser.parse(data);
//            json.remove("queueName");
//
//
//            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://192.168.18.5:8081/api/transaksi/mutasi/")
//                    .queryParam("no_rekening", json.get("no_rekening").toString())
//                    .queryParam("start_date", json.get("start_date").toString())
//                    .queryParam("end_date", json.get("end_date").toString());
//
//            URI uri = UriComponentsBuilder
//                    .fromUri(new URI("http://192.168.18.5:8081/api/transaksi/mutasi/"))
//                    .build()
//                    .encode()
//                    .toUri();
//
////
////            HttpEntity<?> entity = new HttpEntity<>(headers);
////
////            HttpEntity<String> response = restTemplate.exchange(
////                    builder.toUriString(),
////                    HttpMethod.GET,
////                    entity,
////                    String.class);
//
//
//            HttpHeaders header = new HttpHeaders();
//            header.set("Content-Type", "application/json");
//            header.set("apikey", "1001");
//
//            HttpEntity<?> entity = new HttpEntity<>(json,header);
//            restTemplate = new RestTemplate();
//
////            HttpEntity entity = new HttpEntity(json,header);
////            HttpEntity<?> entity = new HttpEntity<Object>(header);
//            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
////            ResponseEntity<String> response = restTemplate.exchange(
////                    "http://192.168.18.5:8081/api/transaksi/mutasi/", HttpMethod.GET, entity, String.class);
//
//            System.out.println("isiRESULT: "+response.getBody());
//
//            return String.valueOf(response.getBody());
//
//        } catch (Exception e){
//            System.out.println("error Fetch VOucer");
//            e.printStackTrace();
//            return "0";
//        }
//    }

    public String getMutasi(String data){
        try {
            HttpHeaders header = new HttpHeaders();
            header.set("apikey", "1001");
//            header.set("Content-Type", "application/json");

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(data);
            json.remove("queueName");

            restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(header);
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://192.168.18.5:8081/api/transaksi/mutasi/"+json.get("no_rekening")+"/"+json.get("start_date")
                    +"/"+json.get("end_date")
                    , HttpMethod.GET, entity, String.class);

            System.out.println("isiRESULT: "+response.getBody());

            return String.valueOf(response.getBody());

        } catch (Exception e){
            System.out.println("error Fetch VOucer");
            e.printStackTrace();
            return "0";
        }
    }
}
