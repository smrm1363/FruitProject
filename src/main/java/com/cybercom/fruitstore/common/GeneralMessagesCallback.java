package com.cybercom.fruitstore.common;

import com.cybercom.fruitstore.config.AfterBusinessLogicAspect;
import com.cybercom.fruitstore.domain.Fruit.FruitService;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a general message callback
 * I is able to call different Services depending on it's Topic on MQ
 * I used Observer pattern to call an appropriate Service
 */
@Component
public class GeneralMessagesCallback implements MqttCallback {


    private final Logger logger = LoggerFactory.getLogger(AfterBusinessLogicAspect.class);
    /**
     * I store the observers in this Map, in each time I only call an observer (The common Observer pattern uses a List)
     */
    private Map<String, MessageObserver> messageObserverMap = new HashMap<>();

    @Autowired
    public GeneralMessagesCallback(FruitService fruitService, Environment env) throws MqttException {
        MqttClient mqttClient = new MqttClient(env.getProperty("mqtt.connectionUrl"), MqttClient.generateClientId());
        mqttClient.setCallback(this);
        mqttClient.connect();
        List<String> topicList = new ArrayList<>();
        /**
         * Adding Topics in both observer map and topic list for subscription
         */
        messageObserverMap.put(env.getProperty("mqtt.topic.new.fruit"), fruitService);
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

    /**
     * This method fires when a message arrives in introduced Topics
     * @param topic
     * @param message
     * @throws Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        //would be nice to save the fruits..
        String strMessage = new String(message.getPayload());
        /**
         * Calling appropriate observer for doing business logic with the message
         */
        messageObserverMap.get(topic).updateObserver(strMessage);
        logger.info("Read from MQ : " + new String(message.getPayload()));
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}