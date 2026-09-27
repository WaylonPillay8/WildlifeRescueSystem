package com.mycompany.wildliferescuesystem;

/**
 * Injured Animal Rescue.
 * Total cost = base cost + veterinary treatment cost (+ R5 000 if surgery is required).
 * Design requirements shown here: inheritance, method overriding.
 */
public class InjuredAnimalRescue extends RescueCase {

    public static final double SURGERY_FEE = 5000.00;

    private String injuryDescription;
    private double vetTreatmentCost;
    private boolean surgeryRequired;

    public InjuredAnimalRescue(String caseId, String animalName, String species, String location,
                               String assignedRanger, int rescueDays, double dailyCareCost,
                               String injuryDescription, double vetTreatmentCost, boolean surgeryRequired) {
        super(caseId, animalName, species, location, assignedRanger, rescueDays, dailyCareCost);
        this.injuryDescription = injuryDescription;
        this.vetTreatmentCost = vetTreatmentCost;
        this.surgeryRequired = surgeryRequired;
    }

    @Override
    public String getRescueType() {
        return "Injured Animal Rescue";
    }

    @Override
    public double calculateTotalCost() {
        double total = calculateBaseCost() + vetTreatmentCost;
        if (surgeryRequired) {
            total += SURGERY_FEE;
        }
        return total;
    }

    /**
     * Surgery required            -> Critical
     * Vet treatment above R10 000 -> High
     * Otherwise                   -> Medium
     */
    @Override
    public String determinePriority() {
        if (surgeryRequired) {
            return PRIORITY_CRITICAL;
        } else if (vetTreatmentCost > 10000) {
            return PRIORITY_HIGH;
        }
        return PRIORITY_MEDIUM;
    }

    @Override
    public String getSpecificDetails() {
        return String.format("%-22s: %s%n", "Injury Description", injuryDescription)
                + String.format("%-22s: %s%n", "Vet Treatment Cost", formatRand(vetTreatmentCost))
                + String.format("%-22s: %s%n", "Surgery Required", yesNo(surgeryRequired));
    }

    public String getInjuryDescription() {
        return injuryDescription;
    }

    public double getVetTreatmentCost() {
        return vetTreatmentCost;
    }

    public boolean isSurgeryRequired() {
        return surgeryRequired;
    }
}
