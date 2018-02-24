package com.ociweb.gl.test;

import com.ociweb.gl.api.Writable;
import com.ociweb.pronghorn.network.config.HTTPContentTypeDefaults;

import java.util.function.Supplier;

public class ParallelTestPayload {
    public HTTPContentTypeDefaults contentType = null;
    public Supplier<Writable> post = null;
    public int maxPayloadSize = 512;

    public ParallelTestPayload() {
    }

    public ParallelTestPayload(String payload) {
        final byte[] bytes = payload.getBytes();
        maxPayloadSize = bytes.length;
        post = ()->writer->writer.write(bytes);
    }

    public ParallelTestPayload(String[] payload) {
        maxPayloadSize = 0;
        for (int i = 0; i < payload.length; i++) {
            maxPayloadSize = Math.max(maxPayloadSize, payload[i].length());
        }
        post = ()-> new PayloadScript(payload);
    }
}
