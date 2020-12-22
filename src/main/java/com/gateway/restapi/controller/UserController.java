package com.gateway.restapi.controller;

import com.gateway.restapi.model.User;
import com.gateway.restapi.rabbitmq.ApiReceiver;
import com.gateway.restapi.rabbitmq.ApiSender;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/nasabah")
public class UserController {
    public final ApiReceiver receiver = new ApiReceiver();

    // -------------------Register Phone Number Nasabah-------------------------------------------
    @RequestMapping(value = "/getData/", method = RequestMethod.GET)
    public ResponseEntity<?> getNasbah(@RequestHeader String authorization) {
        String queueNameReceive="getDataUserQueueMessage";
        String response="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("apikey", authorization);
            jsonObject.put("queueName", "getDataUser");

            ApiSender.sendToDb(jsonObject.toJSONString(),"userQueue");
            response = receiver.receiveFromDatabase(queueNameReceive);
            System.out.println("isi Response: "+response);

        }  catch (Exception e) {
            System.out.println("Error Register Phone Number");
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // -------------------Add Personal Data Nasabah-------------------------------------------
    @RequestMapping(value = "/addPersonalData/", method = RequestMethod.POST)
    public ResponseEntity<?> addPersonalData(@RequestBody User user, @RequestHeader String authorization) {
        String queueNameReceive="addPersonalDataQueueMessage";
        String response="";
        try {

            String jsonString = new Gson().toJson(user);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);

            jsonObject.put("apikey", authorization);
            jsonObject.put("queueName", "addPersonalData");

            ApiSender.sendToDb(jsonObject.toJSONString(),"userQueue");
            response = receiver.receiveFromDatabase(queueNameReceive);
            System.out.println("isi Response: "+response);

        }  catch (Exception e) {
            System.out.println("Error Add Personal Data");
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // -------------------Add Relative Data Nasabah-------------------------------------------
    @RequestMapping(value = "/addRelativeData/", method = RequestMethod.POST)
    public ResponseEntity<?> addRelativeData(@RequestBody User user, @RequestHeader String authorization) {
        String queueNameReceive="addRelativeDataQueueMessage";
        String response="";
        try {

            String jsonString = new Gson().toJson(user);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
            System.out.println("Isi JSON PARAM");

            jsonObject.put("apikey", authorization);
            jsonObject.put("queueName", "addRelativeData");


            ApiSender.sendToDb(jsonObject.toJSONString(),"userQueue");
            response = receiver.receiveFromDatabase(queueNameReceive);
            System.out.println("isi Response: "+response);

        }  catch (Exception e) {
            System.out.println("Error Add Relative Data");
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // -------------------Add Work Data Nasabah-------------------------------------------
    @RequestMapping(value = "/addWorkData/", method = RequestMethod.POST)
    public ResponseEntity<?> addWorkData(@RequestBody User user, @RequestHeader String authorization) {
        String queueNameReceive="addWorkDataQueueMessage";
        String response="";
        try {

            String jsonString = new Gson().toJson(user);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);

            jsonObject.put("apikey", authorization);
            jsonObject.put("queueName", "addWorkData");

            ApiSender.sendToDb(jsonObject.toJSONString(),"userQueue");
            response = receiver.receiveFromDatabase(queueNameReceive);
            System.out.println("isi Response: "+response);

        }  catch (Exception e) {
            System.out.println("Error Add Work Data");
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    public ResponseEntity<?> handleFileUpload(@RequestParam("name") String name,
//                                              @RequestParam(name="file", required=false) MultipartFile file) {

        // -------------------Add Work Data Nasabah-------------------------------------------
    @RequestMapping(value = "/uploadEKTP/", method = RequestMethod.POST)
    public ResponseEntity<?> uploadEKTP(@RequestParam(name = "ektp")MultipartFile file, @RequestHeader String authorization) {
        String queueNameReceive="uploadEKTPQueueMessage";
        String response="";
        try {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("ektp", file.toString());
            jsonObject.put("apikey", authorization);
            jsonObject.put("queueName", "uploadEKTP");

            ApiSender.sendToDb(jsonObject.toJSONString(),"userQueue");
            response = receiver.receiveFromDatabase(queueNameReceive);
            System.out.println("isi Response: "+response);

        }  catch (Exception e) {
            System.out.println("Error Upload EKTP Data");
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/finishRegister/", method = RequestMethod.POST)
    public ResponseEntity<?> finishRegister(@RequestParam(name = "ektp")String ektp, @RequestHeader String authorization) {
        String queueNameReceive="finishRegisterQueueMessage";
        String response="";
        try {

            JSONObject jsonObject = new JSONObject();

            jsonObject.put("no_ktp", ektp);
            jsonObject.put("request_card", 3);
            jsonObject.put("apikey", authorization);
            jsonObject.put("queueName", "finishRegister");

            ApiSender.sendToDb(jsonObject.toJSONString(),"userQueue");
            response = receiver.receiveFromDatabase(queueNameReceive);
            System.out.println("isi Response: "+response);

        }  catch (Exception e) {
            System.out.println("Error Finish Register Data");
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
