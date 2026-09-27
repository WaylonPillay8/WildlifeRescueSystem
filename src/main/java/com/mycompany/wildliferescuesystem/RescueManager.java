package com.mycompany.wildliferescuesystem;

import java.util.ArrayList;

/**
 * Manages all rescue cases. Cases are stored in an ArrayList in memory.
 * Polymorphism: the list holds RescueCase references, but each object runs
 * its own subclass version of calculateTotalCost(), determinePriority(), etc.
 */
public class RescueManager {

    private final ArrayList<RescueCase> rescueCases = new ArrayList<>();

    /** Returns true if no existing case already uses this ID. */
    public boolean isCaseIdUnique(String caseId) {
        return searchRescueCase(caseId) == null;
    }

    /**
     * Adds a rescue case. Returns false (and does not add it) if the case is
     * null, the ID is blank, or the ID already exists.
     */
    public boolean addRescueCase(RescueCase rescueCase) {
        if (rescueCase == null || InputValidator.isBlank(rescueCase.getCaseId())) {
            return false;
        }
        if (!isCaseIdUnique(rescueCase.getCaseId())) {
            return false;
        }
        rescueCases.add(rescueCase);
        return true;
    }

    /** Finds a case by ID (not case-sensitive). Returns null if not found. */
    public RescueCase searchRescueCase(String caseId) {
        if (InputValidator.isBlank(caseId)) {
            return null;
        }
        for (RescueCase rescueCase : rescueCases) {
            if (rescueCase.getCaseId().equalsIgnoreCase(caseId.trim())) {
                return rescueCase;
            }
        }
        return null;
    }

    /** Starts the rescue operation for a case. Returns false if not found. */
    public boolean startRescue(String caseId) {
        RescueCase rescueCase = searchRescueCase(caseId);
        if (rescueCase == null) {
            return false;
        }
        rescueCase.startRescue();
        return true;
    }

    /** Completes the rescue operation for a case. Returns false if not found. */
    public boolean completeRescue(String caseId) {
        RescueCase rescueCase = searchRescueCase(caseId);
        if (rescueCase == null) {
            return false;
        }
        rescueCase.completeRescue();
        return true;
    }

    /** Sets a case's status directly. Returns false if not found or status is blank. */
    public boolean updateStatus(String caseId, String newStatus) {
        RescueCase rescueCase = searchRescueCase(caseId);
        if (rescueCase == null || InputValidator.isBlank(newStatus)) {
            return false;
        }
        rescueCase.setStatus(newStatus);
        return true;
    }

    public ArrayList<RescueCase> getRescueCases() {
        return rescueCases;
    }

    public int getTotalCases() {
        return rescueCases.size();
    }

    public double getTotalRescueCost() {
        double total = 0;
        for (RescueCase rescueCase : rescueCases) {
            total += rescueCase.calculateTotalCost(); // polymorphic call
        }
        return total;
    }

    /** Builds the Wildlife Rescue Report. */
    public String generateReport() {
        String doubleLine = "=".repeat(50) + System.lineSeparator();
        String singleLine = "-".repeat(50) + System.lineSeparator();
        StringBuilder report = new StringBuilder();

        report.append(doubleLine)
              .append("WILDLIFE RESCUE REPORT").append(System.lineSeparator())
              .append(doubleLine);

        if (rescueCases.isEmpty()) {
            report.append("No rescue cases have been captured.").append(System.lineSeparator());
        }

        for (int i = 0; i < rescueCases.size(); i++) {
            RescueCase rc = rescueCases.get(i);
            report.append(String.format("%-15s: %s%n", "Case ID", rc.getCaseId()))
                  .append(String.format("%-15s: %s%n", "Type", rc.getRescueType()))
                  .append(String.format("%-15s: %s%n", "Species", rc.getSpecies()))
                  .append(String.format("%-15s: %s%n", "Location", rc.getLocation()))
                  .append(String.format("%-15s: %s%n", "Ranger", rc.getAssignedRanger()))
                  .append(String.format("%-15s: %s%n", "Priority", rc.determinePriority()))
                  .append(String.format("%-15s: %s%n", "Status", rc.getStatus()))
                  .append(String.format("%-15s: %s%n", "Total Cost", RescueCase.formatRand(rc.calculateTotalCost())));
            if (i < rescueCases.size() - 1) {
                report.append(singleLine);
            }
        }

        report.append(doubleLine)
              .append(String.format("%-19s: %d%n", "Total Rescue Cases", getTotalCases()))
              .append(String.format("%-19s: %s%n", "Total Rescue Cost", RescueCase.formatRand(getTotalRescueCost())));

        return report.toString();
    }
}
