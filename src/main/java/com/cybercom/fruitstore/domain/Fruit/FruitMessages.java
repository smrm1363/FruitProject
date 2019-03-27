package com.cybercom.fruitstore.domain.Fruit;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
@Component
public class FruitMessages implements MqttCallback {



    private final FruitService fruitService;
    private final Environment env;

    MqttClient mqtt;
    @Autowired
    public FruitMessages(FruitService fruitService, Environment env) throws MqttException{
        this.fruitService = fruitService;
        this.env = env;
        this.mqtt= new MqttClient(env.getProperty("mqtt.connectionUrl"), MqttClient.generateClientId());
        mqtt.setCallback(this);
        mqtt.connect();
        mqtt.subscribe("new/fruit");
//        mqtt.subscribe("foo/bar");
    }

	@Override
	public void connectionLost(Throwable cause) {
		System.out.println("Connection lost");
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
        //would be nice to save the fruits..
        String strMessage = new String(message.getPayload());
        ObjectMapper mapper = new ObjectMapper();
        FruitEntity fruitEntity = mapper.readValue(strMessage, FruitEntity.class);
        fruitService.saveOrUpdate(fruitEntity);
        
    System.out.println(new String(message.getPayload()));
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		
	}


}