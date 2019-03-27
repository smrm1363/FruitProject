package com.cybercom.fruitstore;

import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
public class FruitStore {

	public static void main(String[] args) throws MqttException {
		SpringApplication.run(FruitStore.class, args);
		System.out.println(".......");
		MqttClient client = new MqttClient("tcp://localhost:1883", "Mohammadreza");
		client.connect();
		MqttMessage message = new MqttMessage();
		String s = "{\n" +
				"  \n" +
				"  \"type\": {\n" +
				"    \"name\": \"khiyar\"\n" +
				"  },\n" +
				"  \"name\": \"Kedoo\",\n" +
				"  \"price\": 100\n" +
				"}";
		message.setPayload(s.getBytes());
		client.publish("new/fruit", message);
//		IMqttToken iMqttToken =client.subscribeWithResponse("Mohammadreza");

		client.disconnect();
	}


}
