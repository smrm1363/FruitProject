package com.cybercom.fruitstore.common;

import com.cybercom.fruitstore.domain.Fruit.FruitService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GeneralMessagesCallbackTest {

    @Mock
    FruitService fruitServiceMock;
    @Mock
    Environment envMock;

    private GeneralMessagesCallback generalMessagesCallback;
    @Before
    public void setupMock() throws MqttException {
        doReturn("tcp://localhost:1883").when(envMock).getProperty("mqtt.connectionUrl");
        doReturn("topic").when(envMock).getProperty("mqtt.topic.new.fruit");
        generalMessagesCallback = new GeneralMessagesCallback(fruitServiceMock,envMock);
    }

    @Test
    public void messageArrived() throws Exception {

        doNothing().when(fruitServiceMock).updateObserver(any());
        generalMessagesCallback.messageArrived("topic",new MqttMessage());
    }
}