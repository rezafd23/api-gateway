package com.gateway.restapi.controller;

import com.gateway.restapi.rabbitmq.ApiReceiver;
import com.gateway.restapi.rabbitmq.ApiSender;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/token")
public class TokenController {
    public final ApiReceiver receiver = new ApiReceiver();

    @RequestMapping(value = "/getVoucer/", method = RequestMethod.GET)
    public ResponseEntity<?> getVoucerList() {
        String queueNameReceive = "getVoucerQueueMessage";
        String response = "0";
        try {
            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("apikey", authorization);
            jsonObject.put("queueName", "getVoucer");

            ApiSender.sendToDb(jsonObject.toJSONString(), "tokenQueue");
            response = receiver.receiveFromDatabase(queueNameReceive);
            System.out.println("isi Response: " + response);
            if (response.equals("0")) {
                JSONObject object = new JSONObject();
                object.put("response", "400");
                object.put("status", "Error");
                object.put("payload", "Something Went Wrong with service");
                response = object.toJSONString();
            }

        } catch (Exception e) {
            System.out.println("Error Get Voucer");
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @RequestMapping(value = "/checkUser/{no_pelanggan}", method = RequestMethod.GET)
    public ResponseEntity<?> getUserCheck(@PathVariable("no_pelanggan") String no_pelanggan) {
        String queueNameReceive = "checkUserQueueMessage";
        String response = "0";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("no_pelanggan", no_pelanggan);
            jsonObject.put("queueName", "checkUser");

            ApiSender.sendToDb(jsonObject.toJSONString(), "tokenQueue");
            response = receiver.receiveFromDatabase(queueNameReceive);
            System.out.println("isi Response: " + response);
            if (response.equals("0")) {
                JSONObject object = new JSONObject();
                object.put("response", "400");
                object.put("status", "Error");
                object.put("payload", "Something Went Wrong with service");
                response = object.toJSONString();
            }

        } catch (Exception e) {
            System.out.println("Error Check User");
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
