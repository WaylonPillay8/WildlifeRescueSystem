package com.mycompany.wildliferescuesystem;

import java.util.Locale;

/**
 * Abstract base class holding the information shared by all rescue cases.
 * Design requirements shown here: abstraction, encapsulation, interfaces.
 */
public abstract class RescueCase implements RescueOperations {

    // Rescue status values
    public static final String STATUS_REPORTED = "Reported";
    public static final String STATUS_IN_PROGRESS = "Rescue in Progress";
    public static final String STATUS_UNDER_OBSERVATION = "Under Observation";
    public static final String STATUS_COMPLETED = "Rescue Completed";

    // Rescue priority values
    public static final String PRIORITY_CRITICAL = "Critical";
    public static final String PRIORITY_HIGH = "High";
    public static final String PRIORITY_MEDIUM = "Medium";
    public static final String PRIORITY_LOW = "Low";

    // Encapsulated fields (private, accessed through getters/setters)
    private String caseId;
    private String animalName;
    private String species;
    private String location;
    private String assignedRanger;
    private int rescueDays;
    private double dailyCareCost;
    private String status;

    public RescueCase(String caseId, String animalName, String species, String location,
                      String assignedRanger, int rescueDays, double dailyCareCost) {
        this.caseId = caseId;
        this.animalName = animalName;
        this.species = species;
        this.location = location;
        this.assignedRanger = assignedRanger;
        this.rescueDays = rescueDays;
        this.dailyCareCost = dailyCareCost;
        this.status = STATUS_REPORTED;
    }

    // ---------- Abstract methods each subclass must override ----------

    /** Name of the rescue type, e.g. "Injured Animal Rescue". */
    public abstract String getRescueType();

    /** Each rescue type calculates its own total cost. */
    public abstract double calculateTotalCost();

    /** Each rescue type determines its own priority. */
    public abstract String determinePriority();

    /** Information specific to the rescue type. */
    public abstract String getSpecificDetails();

    // ---------- Shared behaviour ----------

    /** Cost shared by every rescue type: days in care x daily care cost. */
    public double calculateBaseCost() {
        return rescueDays * dailyCareCost;
    }

    @Override
    public void startRescue() {
        status = STATUS_IN_PROGRESS;
    }

    @Override
    public void completeRescue() {
        status = STATUS_COMPLETED;
    }

    @Override
    public String generateRescueSummary() {
        return String.format("%-15s: %s%n", "Case ID", caseId)
                + String.format("%-15s: %s%n", "Type", getRescueType())
                + String.format("%-15s: %s%n", "Species", species)
                + String.format("%-15s: %s%n", "Ranger", assignedRanger)
                + String.format("%-15s: %s%n", "Priority", determinePriority())
                + String.format("%-15s: %s%n", "Status", status)
                + String.format("%-15s: %s%n", "Total Cost", formatRand(calculateTotalCost()));
    }

    /** Full details of the case, including the type-specific information. */
    public String displayDetails() {
        return String.format("%-22s: %s%n", "Case ID", caseId)
                + String.format("%-22s: %s%n", "Rescue Type", getRescueType())
                + String.format("%-22s: %s%n", "Animal Name", animalName)
                + String.format("%-22s: %s%n", "Species", species)
                + String.format("%-22s: %s%n", "Rescue Location", location)
                + String.format("%-22s: %s%n", "Assigned Ranger", assignedRanger)
                + String.format("%-22s: %d%n", "Rescue Days", rescueDays)
                + String.format("%-22s: %s%n", "Daily Care Cost", formatRand(dailyCareCost))
                + getSpecificDetails()
                + String.format("%-22s: %s%n", "Priority", determinePriority())
                + String.format("%-22s: %s%n", "Status", status)
                + String.format("%-22s: %s%n", "Total Rescue Cost", formatRand(calculateTotalCost()));
    }

    /** Formats an amount as South African Rand, e.g. R20 500.00 */
    public static String formatRand(double amount) {
        return "R" + String.format(Locale.US, "%,.2f", amount).replace(',', ' ');
    }

    /** Helper for "Yes"/"No" display. */
    protected static String yesNo(boolean value) {
        return value ? "Yes" : "No";
    }

    // ---------- Getters and setters ----------

    public String getCaseId() {
        return caseId;
    }

    public String getAnimalName() {
        return animalName;
    }

    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAssignedRanger() {
        return assignedRanger;
    }

    public void setAssignedRanger(String assignedRanger) {
        this.assignedRanger = assignedRanger;
    }

    public int getRescueDays() {
        return rescueDays;
    }

    public void setRescueDays(int rescueDays) {
        this.rescueDays = rescueDays;
    }

    public double getDailyCareCost() {
        return dailyCareCost;
    }

    public void setDailyCareCost(double dailyCareCost) {
        this.dailyCareCost = dailyCareCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
