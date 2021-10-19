package com.example.task.repository;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Profile("receiver")
@Getter
public class ReceiverRepository {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    @Setter
    private String tableName;

    @Setter
    private String columns;

    public void insert(final Data row) throws SQLException {
        var insertQuery = "INSERT INTO " + tableName + "(" + columns + ") values (" + row.convertToRow() + ")";

        try (var con = DriverManager.getConnection(url, userName, password);
             var st = con.createStatement()) {
            st.executeUpdate(insertQuery);
        }
    }
}
