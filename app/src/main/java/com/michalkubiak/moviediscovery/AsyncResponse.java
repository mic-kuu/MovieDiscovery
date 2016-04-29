package com.michalkubiak.moviediscovery;

/**
 * Interface to make a callback after the async task finishes
 */
public interface AsyncResponse {

    void processOutput(String output);
}
