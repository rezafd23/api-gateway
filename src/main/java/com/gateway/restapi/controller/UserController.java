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

    // -------------------Register Phone Number Nasabah-------------------------------------------
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
            System.out.println("Error Register Phone Number");
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
