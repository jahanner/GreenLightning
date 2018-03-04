package com.ociweb.gl.test;

import com.ociweb.gl.api.TelemetryConfig;
import com.ociweb.json.JSONExtractor;
import com.ociweb.json.JSONExtractorCompleted;
import com.ociweb.json.appendable.StringBuilderWriter;
import com.ociweb.json.encode.JSONRenderer;
import com.ociweb.pronghorn.util.parse.JSONReader;

public class ParallelTestConfig {
    public int parallelTracks = 4;
    public int cyclesPerTrack = 1;
    public String host = "127.0.0.1";
    public int port = 8080;
    public String route = "";
    public long durationNanos = 0;
    public long responseTimeoutNS = 100_000_000;
    public int ignoreInitialPerTrack = 0;
    public Integer telemetryPort = null;
    public String telemetryHost = null;
    public Long rate = null;
	public boolean insecureClient=true;
	public boolean ensureLowLatency=false;
	public int inFlightBits = 0;//only 1 in flight is zero


    public String toString() {
        StringBuilderWriter out = new StringBuilderWriter();
        jsonRenderer.render(out, this);
        return out.toString();
    }

    public JSONReader createReader() {
        return jsonExtractor.reader();
    }

    public ParallelTestConfig() {
    }

    public ParallelTestConfig(
            int cyclesPerTrack,
            int port,
            String route,
            boolean enableTelemetry) {
        this.cyclesPerTrack = cyclesPerTrack;
        this.port = port;
        this.route = route;
        this.telemetryPort = enableTelemetry ? TelemetryConfig.defaultTelemetryPort + 13 : null;
    }

    public ParallelTestConfig(
            int parallelTracks,
            int cyclesPerTrack,
            int port,
            String route,
            boolean enableTelemetry) {
        this.parallelTracks = parallelTracks;
        this.cyclesPerTrack = cyclesPerTrack;
        this.port = port;
        this.route = route;
        this.telemetryPort = enableTelemetry ? TelemetryConfig.defaultTelemetryPort + 13 : null;

    }

    public ParallelTestConfig(String filePath) {
        // TODO: we cannot read JSON from file!
    }

    public static final JSONRenderer<ParallelTestConfig> jsonRenderer = new JSONRenderer<ParallelTestConfig>()
            .beginObject()
                .integer("parallelTracks", o->o.parallelTracks)
                .integer("cyclesPerTrack", o->o.cyclesPerTrack)
                .string("host", o-> (o.host != null ? o.host : "127.0.0.1"))
                .integer("port", o->o.port)
                .string("route", o->o.route)
                .integer("durationNanos", o->o.durationNanos)
                .integer("responseTimeoutNS", o->o.responseTimeoutNS)
                .integer("ignoreInitialPerTrack", o->o.ignoreInitialPerTrack)
                .nullableInteger("telemetryPort", (o, func) -> func.visit(o.telemetryPort != null ? o.telemetryPort : 0, o.telemetryPort == null))
                .string("telemetryHost", o-> (o.telemetryHost != null ? o.telemetryHost : "127.0.0.1"))
                .nullableInteger("rate", (o, func) -> func.visit(o.rate != null ? o.rate : 0, o.rate == null))
                .bool("insecureClient", o->o.insecureClient)
                .bool("ensureLowLatency", o->o.ensureLowLatency)                
                
            .endObject();

    public static final JSONExtractorCompleted jsonExtractor = new JSONExtractor();
}