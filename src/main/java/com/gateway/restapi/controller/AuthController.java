package com.gateway.restapi.controller;

import com.gateway.restapi.model.Register;
import com.gateway.restapi.rabbitmq.ApiReceiver;
import com.gateway.restapi.rabbitmq.ApiSender;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

//    @Autowired
    private RestTemplate restTemplate;

    public final ApiReceiver receiver = new ApiReceiver();
//    private Properties properties = new Properties();
//    private String propName = "config.properties";

    // -------------------Register Phone Number Nasabah-------------------------------------------
    @RequestMapping(value = "/register/", method = RequestMethod.POST)
    public ResponseEntity<?> addNasabah(@RequestBody Register register) {
        String queueNameReceive="registerQueueMessage";
        String response="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", register.getUsername());
            jsonObject.put("queueName", "registerPhone");

            ApiSender.sendToDb(jsonObject.toJSONString(),"registerQueue");
            response = receiver.receiveFromDatabase(queueNameReceive);
            System.out.println("isi Response: "+response);

        }  catch (Exception e) {
            System.out.println("Error Register Phone Number");
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    // -------------------Login Phone Number Nasabah-------------------------------------------
    @RequestMapping(value = "/login/", method = RequestMethod.POST)
    public ResponseEntity<?> loginPhone(@RequestBody Register register) {
        String queueNameReceive="loginPhoneQueueMessage";
        String response="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", register.getUsername());
            jsonObject.put("queueName", "loginPhone");

            ApiSender.sendToDb(jsonObject.toJSONString(),"registerQueue");
            response = receiver.receiveFromDatabase(queueNameReceive);

        }  catch (Exception e) {
            System.out.println("Error Login Phone Number");
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // -------------------Submit OTP-------------------------------------------
    @RequestMapping(value = "/submitOtp/", method = RequestMethod.POST)
    public ResponseEntity<?> submitOTP(@RequestBody Register register) {

        String queueNameReceive="submitOtpQueueMessage";
        String response="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone_number", register.getUsername());
            jsonObject.put("otp", register.getOtp());
            jsonObject.put("otpId", register.getOtpId());
            jsonObject.put("queueName", "submitOtp");

            ApiSender.sendToDb(jsonObject.toJSONString(),"registerQueue");
            response = receiver.receiveFromDatabase(queueNameReceive);
            System.out.println("isi Response: "+response);

        }  catch (Exception e) {
            System.out.println("Error Submit OTP");
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // -------------------Create PIN-------------------------------------------
    @RequestMapping(value = "/createPin/", method = RequestMethod.POST)
    public ResponseEntity<?> createPin(@RequestBody Register register) {
        String queueNameReceive="createPinQueueMessage";
        String response="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", register.getUsername());
            jsonObject.put("pin", register.getPin());
            jsonObject.put("queueName", "createPin");

            ApiSender.sendToDb(jsonObject.toJSONString(),"registerQueue");
            response = receiver.receiveFromDatabase(queueNameReceive);
            System.out.println("isi Response: "+response);

        }  catch (Exception e) {
            System.out.println("Error Create PIN");
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // -------------------Create PIN-------------------------------------------
    @RequestMapping(value = "/loginPin/", method = RequestMethod.POST)
    public ResponseEntity<?> loginPin(@RequestBody Register register) {
        String queueNameReceive="loginPinQueueMessage";
        String response="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("username", register.getUsername());
            jsonObject.put("pin", register.getPin());
            jsonObject.put("queueName", "loginPin");

            ApiSender.sendToDb(jsonObject.toJSONString(),"registerQueue");
            response = receiver.receiveFromDatabase(queueNameReceive);
            System.out.println("isi Response: "+response);

        }  catch (Exception e) {
            System.out.println("Error Create PIN");
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/generateOtp/", method = RequestMethod.POST)
    public ResponseEntity<?> generateOtp(@RequestParam("phone_number") String phone_number) {
        String queueNameReceive="generateOtpQueueMessage";
        String response="";
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("phone_number", phone_number);
            jsonObject.put("queueName", "generateOtp");

            ApiSender.sendToDb(jsonObject.toJSONString(),"registerQueue");
            response = receiver.receiveFromDatabase(queueNameReceive);
            System.out.println("isi Response: "+response);

        }  catch (Exception e) {
            System.out.println("Error Create PIN");
            e.printStackTrace();
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
