package com.example.task.configuration;

import com.example.task.repository.ReceiverRepository;
import com.example.task.utils.Parser;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Profile("receiver")
@Component
public class ReceiverCommandLineRunner implements CommandLineRunner {

    @Autowired
    private ReceiverRepository repo;

    @Autowired
    private RabbitListenerEndpointRegistry listenerRegistry;

    @Override
    public void run(String... args) {
        System.out.println("Enter the command:");
        Scanner scanner = new Scanner(System.in);
        final String command = scanner.nextLine();
        final String destination = Parser.getDestinationTableNameFromCommand(command);
        final String columns = Parser.getColumnsFromCommand(command);
        repo.setTableName(destination);
        repo.setColumns(columns);
        listenerRegistry.getListenerContainer("receiver").start();
        System.out.println("Waiting for messages...");
    }
}
