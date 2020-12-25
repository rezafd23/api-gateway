package com.gateway.restapi.controller;

import com.gateway.restapi.model.BuyTokenParam;
import com.gateway.restapi.rabbitmq.ApiReceiver;
import com.gateway.restapi.rabbitmq.ApiSender;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.Date;

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

    @RequestMapping(value = "/buyToken/", method = RequestMethod.POST)
    public ResponseEntity<?> buyToken(@RequestBody BuyTokenParam buyTokenParam,@RequestHeader String authorization){

        String queueNameReceive = "buyTokenQueueMessage";
        String response = "0";
        try {
            String jsonString = new Gson().toJson(buyTokenParam);
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonString);

            jsonObject.put("queueName", "buyToken");
            jsonObject.put("status_transaksi", "debit");
            jsonObject.put("authorization", authorization);

            Calendar calendar1 = Calendar.getInstance();
            calendar1.set(Calendar.HOUR_OF_DAY, 1);
//                calendar1.set(Calendar.HOUR, 01);
            calendar1.set(Calendar.MINUTE, 0);
            calendar1.set(Calendar.SECOND, 0);
//                Calendar1.get(Calendar.HOUR_OF_DAY);

            Calendar calendar2 = Calendar.getInstance();
            calendar2.set(Calendar.HOUR_OF_DAY, 2);
            calendar2.set(Calendar.MINUTE, 0);
            calendar2.set(Calendar.SECOND, 0);

            System.out.println("isi Date 1: "+calendar1.getTime());
            System.out.println("isi Date 2: "+calendar2.getTime());


            Calendar calendar3 = Calendar.getInstance();
            Date x = calendar3.getTime();
            JSONObject object = new JSONObject();
            if (x.after(calendar1.getTime()) && x.before(calendar2.getTime())) {

                object.put("response", "400");
                object.put("status", "Error");
                object.put("message", "Internal Server Error, Server is Maintanance");
                response = object.toJSONString();
            } else {
                ApiSender.sendToDb(jsonObject.toJSONString(), "tokenQueue");
                response = receiver.receiveFromDatabase(queueNameReceive);
                System.out.println("isi Response: " + response);

                if (response.equals("0")) {
                    object.put("response", "400");
                    object.put("status", "Error");
                    object.put("payload", "Something Went Wrong with service");
                    response = object.toJSONString();
                }
            }

        } catch (Exception e) {
            System.out.println("Error Check User");
            e.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
