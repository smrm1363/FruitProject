package com.cybercom.fruitstore.domain.Fruit;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.*;
public class FruitServiceTest {

    @Test
    public void saveOrUpdate() throws MqttException {

        System.out.println(".......");
        MqttClient client = new MqttClient("tcp://localhost:1883", "Mohammadreza");
        client.connect();
        MqttMessage message = new MqttMessage();
        message.setPayload("Hello world from Java".getBytes());
        client.publish("new/fruit", message);
        IMqttToken iMqttToken =client.subscribeWithResponse("Mohammadreza");

        client.disconnect();
    }

    @Test
    public void find() {
    }

    @Test
    public void findAll() {
    }
}