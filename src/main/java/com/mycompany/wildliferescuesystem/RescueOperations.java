package com.mycompany.wildliferescuesystem;

/**
 * Interface defining the operations every rescue case type must support.
 * (Design requirement: interfaces)
 */
public interface RescueOperations {

    /** Starts the rescue operation and updates the status. */
    void startRescue();

    /** Completes the rescue operation and updates the status. */
    void completeRescue();

    /** Returns a summary of the rescue case. */
    String generateRescueSummary();
}
