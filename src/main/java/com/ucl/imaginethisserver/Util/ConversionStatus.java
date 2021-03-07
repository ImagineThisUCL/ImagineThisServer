package com.ucl.imaginethisserver.Util;

// It's easier to use Class rather than Enum because we need String values
// as we'll be adding them to database
public class ConversionStatus {
    public static final String RUNNING = "RUNNING";
    public static final String SUCCEEDED = "SUCCEEDED";
    public static final String FAILED = "FAILED";
    public static final String NOT_STARTED = "NOT_STARTED";
    public static final String NOT_TRIGGERED = "NOT_TRIGGERED";
}
