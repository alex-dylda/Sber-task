package com.example.task.service;

import com.example.task.repository.Data;
import com.example.task.repository.ReceiverRepository;
import com.example.task.utils.MessageDecoder;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

import static com.example.task.configuration.AppConfiguration.QUEUE_NAME;

@Profile("receiver")
@Component
@RabbitListener(id = "receiver", queues = QUEUE_NAME, autoStartup = "false")
public class ReceiverService {

    private int counter;
    private final MessageDecoder decoder;
    private final ReceiverRepository repo;

    @Autowired
    public ReceiverService(MessageDecoder decoder, ReceiverRepository repo) {
        this.decoder = decoder;
        this.repo = repo;
    }

    @RabbitHandler
    public void handle(byte[] message) throws SQLException {
        if (message == null) {
            return;
        }
        counter++;
        final Data row = decoder.decode(message);
        System.out.println("Message arrived. Trying to insert.");
        repo.insert(row);
        System.out.println("[x] Row " + counter + " inserted. Waiting for new row.");
    }
}
