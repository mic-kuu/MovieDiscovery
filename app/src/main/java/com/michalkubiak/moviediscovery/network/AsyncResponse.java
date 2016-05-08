package com.michalkubiak.moviediscovery.network;

/**
 * Interface to make a callback after the AsyncResponse task finishes
 */
public interface AsyncResponse {

    void processOutput(String output);
}
