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

public class TokenService {

    private RestTemplate restTemplate;

    public TokenService() {
    }

    public String getListVoucer() {
        try {
            HttpHeaders header = new HttpHeaders();
            header.set("apikey", "1001");

            restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(header);
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://192.168.18.5:8082/api/voucer/", HttpMethod.GET, entity, String.class);

            System.out.println("isiRESULT: " + response.getBody());

            return String.valueOf(response.getBody());

        } catch (Exception e) {
            System.out.println("error Fetch VOucer");
            e.printStackTrace();
            return "0";
        }
    }

    public String checkuser(String no_pelanggan) {
        try {
            HttpHeaders header = new HttpHeaders();
            header.set("apikey", "1001");

            restTemplate = new RestTemplate();

            HttpEntity entity = new HttpEntity(header);
            ResponseEntity<String> response = restTemplate.exchange(
                    "http://192.168.18.5:8082/api/customer/" + no_pelanggan, HttpMethod.GET, entity, String.class);

            System.out.println("isiRESULT: " + response.getBody());

            return String.valueOf(response.getBody());

        } catch (Exception e) {
            System.out.println("error Fetch VOucer");
            e.printStackTrace();
            return "0";
        }
    }

    public String ValidToken(String auth){
        String res="1";
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            Map map = new HashMap();
            map.put("authorization", auth);
            headers.setAll(map);

            HttpEntity<?> request = new HttpEntity<>(headers);

            restTemplate = new RestTemplate();
            ResponseEntity<?> response = restTemplate.exchange(
                    "http://192.168.18.5:3000/app/api/otp/validateToken", HttpMethod.GET, request, String.class);
            System.out.println("isiResponse: "+response.getBody());
            JSONParser parser = new JSONParser();

            JSONObject jsonObject = (JSONObject) parser.parse(String.valueOf(response.getBody()));
            String resp=String.valueOf(jsonObject.get("response").toString());
            if (resp.equals("401")){
                res= jsonObject.toJSONString();
            }
        } catch (Exception e){
            System.out.println("error token validation");
            e.printStackTrace();
        }

        return res;
    }

    public String buyToken(String params) {
        try {
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();

            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(params);
            json.remove("queueName");

            String tokenValid = ValidToken(json.get("authorization").toString());

            if (tokenValid.equals("1")){
                json.remove("authorization");
                JSONObject paramTrx = (JSONObject) parser.parse(params);

                paramTrx.remove("no_pelanggan");
                paramTrx.remove("id_voucer");
//            paramTrx.remove("id_card_nasabah");
                paramTrx.remove("queueName");

                Map map = new HashMap();
                map.put("Content-Type", "application/json");
                map.put("apikey", "1001");
                headers.setAll(map);

                System.out.println("ISIJSON: " + paramTrx);

                HttpEntity<?> request = new HttpEntity<>(paramTrx, headers);

                restTemplate = new RestTemplate();
                ResponseEntity<?> response = restTemplate.postForEntity("http://192.168.18.5:8081/api/transaksi/add/", request, String.class);
                JSONObject responseTransaksi = (JSONObject) parser.parse(String.valueOf(response.getBody()));
                if (responseTransaksi.get("response").toString().equals("200")) {
                    MultiValueMap<String, String> headers2 = new LinkedMultiValueMap<>();
//                Map map2 = new HashMap();
//                map.put("Content-Type", "application/json");
//                map.put("apikey", 1001);
//                headers2.setAll(map);

                    JSONObject objectPln = new JSONObject();
                    objectPln.put("id_voucer", Integer.parseInt(json.get("id_voucer").toString()));
                    objectPln.put("no_pelanggan", json.get("no_pelanggan").toString());
                    objectPln.put("status_bayar", responseTransaksi.get("status").toString());
                    objectPln.put("portal_bayar", "mySyaria");
                    objectPln.put("kode_bayar", responseTransaksi.get("trx_code").toString());

                    HttpEntity<?> request2 = new HttpEntity<>(objectPln, headers);
                    System.out.println("isiJSONPLN: " + objectPln);

                    ResponseEntity<?> responsePln = restTemplate.postForEntity("http://192.168.18.5:8082/api/token/buy/", request2, String.class);

                    JSONObject resPLN = (JSONObject) parser.parse(String.valueOf(responsePln.getBody()));
                    System.out.println("isiJSONPLN: " + resPLN);
                    JSONObject payloadToken = (JSONObject) resPLN.get("payload");
                    String token = payloadToken.get("token").toString();

                    map.remove("Content-Type");
                    headers2.setAll(map);

                    System.out.println("ISIJSON: " + paramTrx);

                    HttpEntity<?> reqUpdate = new HttpEntity<>(headers);

                    restTemplate.exchange(
                            "http://192.168.18.5:8081/api/transaksi/update/" + responseTransaksi.get("trx_code").toString()
                                    + "/" + token
                            , HttpMethod.PUT, reqUpdate, String.class);

                    return String.valueOf(responsePln.getBody());

                } else {
                    System.out.println("isiRESULT: " + response.getBody());
                    return String.valueOf(response.getBody());

                }
            } else {
                return tokenValid;
            }
        } catch (Exception e) {
            System.out.println("error Fetch VOucer");
            e.printStackTrace();
            return "0";
        }
    }
}
