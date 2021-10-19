package com.example.task.utils;

import com.example.task.repository.Data;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

@Component
public class MessageDecoder {
    private final ByteBuffer bb = ByteBuffer.allocate(65535);

    public byte[] encode(final Data rs) {
        bb.clear();
        bb.putInt(rs.getInt1());
        bb.putFloat(rs.getFloat2());
        putString(bb, rs.getString3());
        putString(bb, rs.getDate4().toString());
        bb.flip();

        byte[] msg = new byte[bb.limit()];
        bb.get(msg);
        bb.clear();
        return msg;
    }

    public Data decode(final byte[] msg) {
        bb.clear();
        bb.put(msg);
        bb.flip();
        return new Data(bb.getInt(),
                bb.getFloat(),
                getString(bb),
                Date.valueOf(getString(bb)));
    }

    private String getString(ByteBuffer bb) {
        byte[] strBytes = new byte[bb.getInt()];
        bb.get(strBytes);
        return new String(strBytes);
    }

    private void putString(final ByteBuffer bb, final String str) {
        byte[] strBytes = str.getBytes(StandardCharsets.UTF_8);
        bb.putInt(strBytes.length);
        bb.put(strBytes);
    }
}
