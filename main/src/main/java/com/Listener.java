package com;

import api.ActiveMQ.Producer;
import com.google.gson.Gson;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Map;

@Component
@Log
public class Listener {
    @Autowired
    Producer producer2;

    @JmsListener(destination = "inbound.topic")
    @SendTo("answer")
    public String receiveMessageFromTopic(final Message jsonMessage) throws JMSException
    {
        String messageData = null;
        log.info("[1] Received message " + jsonMessage);
        String response = null;
        if(jsonMessage instanceof TextMessage) {
            TextMessage textMessage = (TextMessage)jsonMessage;
            messageData = textMessage.getText();
            Map map = new Gson().fromJson(messageData, Map.class);
            log.info("[1] Text message " +  map.get("name"));
            response  = "Hello " +map.get("name");
        }
        //producer2.sendMessage("outbound.topic","{\"name\":\"HEllp\"} ");
        return "{\"name\":\"request1\"}";
    }

}