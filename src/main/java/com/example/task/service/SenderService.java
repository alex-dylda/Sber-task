package com.example.task.service;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("sender")
public class SenderService {

    private int counter;
    private final RabbitTemplate template;
    private final Queue queue;

    @Autowired
    public SenderService(RabbitTemplate template, Queue queue) {
        this.template = template;
        this.queue = queue;
    }

    public void send(final byte[] msg) {
        final String queueName = queue.getName();
        template.convertAndSend(queueName, msg);
        counter++;
        System.out.println("Message " + counter + " sent to queue");
    }
}
