package com.mycompany.wildliferescuesystem;

/**
 * Endangered Species Rescue.
 * Total cost = base cost + security cost (+ R8 000 if a specialist team is required).
 */
public class EndangeredSpeciesRescue extends RescueCase {

    public static final double SPECIALIST_TEAM_FEE = 8000.00;

    private String conservationClassification;
    private double securityCost;
    private boolean specialistTeamRequired;

    public EndangeredSpeciesRescue(String caseId, String animalName, String species, String location,
                                   String assignedRanger, int rescueDays, double dailyCareCost,
                                   String conservationClassification, double securityCost,
                                   boolean specialistTeamRequired) {
        super(caseId, animalName, species, location, assignedRanger, rescueDays, dailyCareCost);
        this.conservationClassification = conservationClassification;
        this.securityCost = securityCost;
        this.specialistTeamRequired = specialistTeamRequired;
    }

    @Override
    public String getRescueType() {
        return "Endangered Species Rescue";
    }

    @Override
    public double calculateTotalCost() {
        double total = calculateBaseCost() + securityCost;
        if (specialistTeamRequired) {
            total += SPECIALIST_TEAM_FEE;
        }
        return total;
    }

    /**
     * Critically Endangered OR specialist team required -> Critical
     * Otherwise (every endangered species is important) -> High
     */
    @Override
    public String determinePriority() {
        if (specialistTeamRequired
                || conservationClassification.equalsIgnoreCase("Critically Endangered")) {
            return PRIORITY_CRITICAL;
        }
        return PRIORITY_HIGH;
    }

    @Override
    public String getSpecificDetails() {
        return String.format("%-22s: %s%n", "Classification", conservationClassification)
                + String.format("%-22s: %s%n", "Security Cost", formatRand(securityCost))
                + String.format("%-22s: %s%n", "Specialist Team", yesNo(specialistTeamRequired));
    }

    public String getConservationClassification() {
        return conservationClassification;
    }

    public double getSecurityCost() {
        return securityCost;
    }

    public boolean isSpecialistTeamRequired() {
        return specialistTeamRequired;
    }
}
