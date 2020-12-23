package com.gateway.database.rabbitmq;

import com.gateway.database.service.AuthService;
import com.gateway.database.service.TokenService;
import com.gateway.database.service.UserService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class DatabaseReceiver {
    DatabaseSender sender = new DatabaseSender();
    private ConnectionFactory factory;
    private Connection connection;
    private Channel channel;
    private AuthService authService = new AuthService();
    private UserService userService = new UserService();
    private TokenService tokenService = new TokenService();

    public void connectRabbitMQ() throws IOException, TimeoutException {
        factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
    }

    public void register() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("registerQueue", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String nasabahData = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + nasabahData + "'");

                try {
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(nasabahData);
                    String queueName = String.valueOf(json.get("queueName"));
                    String response = "";
                    String queueNameReceive = "";
                    switch (queueName) {
                        case "registerPhone":
                            response = authService.registerPhoneNumber(json.toJSONString());
                            queueNameReceive = "registerQueueMessage";
                            sender.sendToRestApi(response, queueNameReceive);
                            break;
                        case "submitOtp":
                            queueNameReceive = "submitOtpQueueMessage";
                            response = authService.submitOtpRegister(json.toJSONString());
                            sender.sendToRestApi(response, queueNameReceive);
                            // code block
                            break;
                        case "createPin":
                            queueNameReceive = "createPinQueueMessage";
                            response = authService.createPin(json.toJSONString());
                            sender.sendToRestApi(response, queueNameReceive);
                            break;
                        case "loginPin":
                            queueNameReceive = "loginPinQueueMessage";
                            response = authService.loginPin(json.toJSONString());
                            sender.sendToRestApi(response, queueNameReceive);
                            break;
                        default:
                            // code block
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            };
            channel.basicConsume("registerQueue", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error Add Nasabah = " + e);
        }
    }

    public void user() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("userQueue", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String nasabahData = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + nasabahData + "'");

                try {
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(nasabahData);
                    String queueName = String.valueOf(json.get("queueName"));
                    String response = "";
                    String queueNameReceive = "";
                    switch (queueName) {
                        case "getDataUser":
                            response = userService.getDataUser(json.toJSONString());
                            queueNameReceive = "getDataUserQueueMessage";
                            sender.sendToRestApi(response, queueNameReceive);
                            break;
                        case "addPersonalData":
                            response = userService.addPersonalData(json.toJSONString());
                            queueNameReceive = "addPersonalDataQueueMessage";
                            sender.sendToRestApi(response, queueNameReceive);
                            break;
                        case "addRelativeData":
                            response = userService.addRelative(json.toJSONString());
                            queueNameReceive = "addRelativeDataQueueMessage";
                            sender.sendToRestApi(response, queueNameReceive);
                            break;
                        case "addWorkData":
                            response = userService.addWorkData(json.toJSONString());
                            queueNameReceive = "addWorkDataQueueMessage";
                            sender.sendToRestApi(response, queueNameReceive);
                            break;
                        case "uploadEKTP":
                            queueNameReceive = "uploadEKTPQueueMessage";
                            response = userService.uploadEKTP(json.toJSONString());
                            sender.sendToRestApi(response, queueNameReceive);
                            // code block
                            break;
                        case "finishRegister":
                            queueNameReceive = "finishRegisterQueueMessage";
                            response = userService.finishRegister(json.toJSONString());
                            sender.sendToRestApi(response, queueNameReceive);
                            break;
//                        case "loginPin":
//                            queueNameReceive = "loginPinQueueMessage";
//                            response = authService.loginPin(json.toJSONString());
//                            sender.sendToRestApi(response, queueNameReceive);
//                            break;
                        default:
                            // code block
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            };
            channel.basicConsume("userQueue", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error Add Nasabah = " + e);
        }
    }

    public void token() {
        try {
            connectRabbitMQ();
            channel = connection.createChannel();
            channel.queueDeclare("tokenQueue", false, false, false, null);
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String nasabahData = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" [x] Received '" + nasabahData + "'");

                try {
                    JSONParser parser = new JSONParser();
                    JSONObject json = (JSONObject) parser.parse(nasabahData);
                    String queueName = String.valueOf(json.get("queueName"));
                    String response = "";
                    String queueNameReceive = "";
                    switch (queueName) {
                        case "getVoucer":
                            response=tokenService.getListVoucer();
                            queueNameReceive = "getVoucerQueueMessage";
                            sender.sendToRestApi(response, queueNameReceive);
                            break;
                        case "checkUser":
                            response = tokenService.checkuser(json.get("no_pelanggan").toString());
//                                    userService.addPersonalData(json.toJSONString());
                            queueNameReceive = "checkUserQueueMessage";
                            sender.sendToRestApi(response, queueNameReceive);
                            break;
//                        case "addRelativeData":
//                            response = userService.addRelative(json.toJSONString());
//                            queueNameReceive = "addRelativeDataQueueMessage";
//                            sender.sendToRestApi(response, queueNameReceive);
//                            break;
//                        case "addWorkData":
//                            response = userService.addWorkData(json.toJSONString());
//                            queueNameReceive = "addWorkDataQueueMessage";
//                            sender.sendToRestApi(response, queueNameReceive);
//                            break;
//                        case "uploadEKTP":
//                            queueNameReceive = "uploadEKTPQueueMessage";
//                            response = userService.uploadEKTP(json.toJSONString());
//                            sender.sendToRestApi(response, queueNameReceive);
//                            // code block
//                            break;
//                        case "finishRegister":
//                            queueNameReceive = "finishRegisterQueueMessage";
//                            response = userService.finishRegister(json.toJSONString());
//                            sender.sendToRestApi(response, queueNameReceive);
//                            break;
//                        case "loginPin":
//                            queueNameReceive = "loginPinQueueMessage";
//                            response = authService.loginPin(json.toJSONString());
//                            sender.sendToRestApi(response, queueNameReceive);
//                            break;
                        default:
                            // code block
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            };
            channel.basicConsume("tokenQueue", true, deliverCallback, consumerTag -> {
            });
        } catch (Exception e) {
            System.out.println("Error Token Queue= " + e);
        }
    }
}
