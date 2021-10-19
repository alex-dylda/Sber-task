package com.example.task.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("sender")
public class SenderRepository {
    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String userName;

    @Value("${spring.datasource.password}")
    private String password;

    public List<Data> findAll(final String tableName, String columns) throws SQLException {
        final List<Data> result = new ArrayList<>();
        var query = "SELECT " + columns + " FROM " + tableName;

        try (var con = DriverManager.getConnection(url, userName, password);
             var st = con.createStatement();
             var rs = st.executeQuery(query)) {
            while (rs.next()) {
                result.add(new Data(rs.getInt(1),
                        rs.getFloat(2),
                        rs.getString(3),
                        rs.getDate(4)));
            }
        }
        return result;
    }
}
