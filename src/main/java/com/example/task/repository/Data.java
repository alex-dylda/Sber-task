package com.example.task.repository;

import lombok.Getter;
import java.sql.Date;

@Getter
public class Data {
    private final Integer int1;
    private final Float float2;
    private final String string3;
    private final Date date4;

    public Data(Integer int1, Float float2, String string3, Date date4) {
        this.int1 = int1;
        this.float2 = float2;
        this.string3 = string3;
        this.date4 = date4;
    }

    public String convertToRow() {
        return int1 +
                ", " +
                float2 +
                ", '" +
                string3 +
                "', '" +
                date4 +
                "'";
    }
}
