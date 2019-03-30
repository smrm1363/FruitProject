package com.cybercom.fruitstore.common;


import com.cybercom.fruitstore.common.MessageObserver;
import com.cybercom.fruitstore.config.AfterBusinessLogicAspect;
import com.cybercom.fruitstore.domain.Fruit.FruitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GeneralMessagesCallback implements MqttCallback {


    private final Logger logger = LoggerFactory.getLogger(AfterBusinessLogicAspect.class);
    private Map<String, MessageObserver> messageObserverMap = new HashMap<>();
    @Autowired
    public GeneralMessagesCallback(FruitService fruitService, Environment env) throws MqttException{
        MqttClient mqttClient= new MqttClient(env.getProperty("mqtt.connectionUrl"), MqttClient.generateClientId());
        mqttClient.setCallback(this);
        mqttClient.connect();
        List<String> topicList = new ArrayList<>();
        /**
         * Adding topics in both observer map and topic list for subscription
         */
        messageObserverMap.put(env.getProperty("mqtt.topic.new.fruit"),fruitService);
        topicList.add(env.getProperty("mqtt.topic.new.fruit"));
        /**
         * We are able to add more subscriber
         */
        mqttClient.subscribe(topicList.toArray(new String[0]));
    }

	@Override
	public void connectionLost(Throwable cause) {

        logger.info("MQ Connection lost");
        cause.printStackTrace();
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
        //would be nice to save the fruits..
        String strMessage = new String(message.getPayload());
        messageObserverMap.get(topic).update(strMessage);
        logger.info("Read from MQ : "+new String(message.getPayload()));
	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
		
	}


}