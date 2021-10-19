package com.example.task.configuration;

import com.example.task.service.SenderService;
import com.example.task.repository.*;
import com.example.task.utils.MessageDecoder;
import com.example.task.utils.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

@Profile("sender")
@Component
public class SenderCommandLineRunner implements CommandLineRunner {

    @Autowired
    private ConfigurableApplicationContext ctx;

    @Autowired
    private SenderService sender;

    @Autowired
    private MessageDecoder decoder;

    @Autowired
    private SenderRepository repo;

    @Override
    public void run(String... args) throws SQLException {
        System.out.println("Enter the command:");
        Scanner scanner = new Scanner(System.in);
        final String command = scanner.nextLine();
        final String source = Parser.getSourceTableNameFromCommand(command);
        final String columns = Parser.getColumnsFromCommand(command);
        final List<Data> rows = repo.findAll(source, columns);

        rows.forEach(row -> {
            sender.send(decoder.encode(row));
            try {
                Thread.sleep(1000); // To method not finished very quick.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } );
        System.out.println("Finish transfer.");
        ctx.close();
    }
}
