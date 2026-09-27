package com.mycompany.wildliferescuesystem;

/**
 * Orphaned Animal Rescue.
 * Total cost = base cost + feeding cost (+ R2 500 if foster care is required).
 */
public class OrphanedAnimalRescue extends RescueCase {

    public static final double FOSTER_CARE_FEE = 2500.00;

    private int estimatedAgeMonths;
    private double feedingCost;
    private boolean fosterCareRequired;

    public OrphanedAnimalRescue(String caseId, String animalName, String species, String location,
                                String assignedRanger, int rescueDays, double dailyCareCost,
                                int estimatedAgeMonths, double feedingCost, boolean fosterCareRequired) {
        super(caseId, animalName, species, location, assignedRanger, rescueDays, dailyCareCost);
        this.estimatedAgeMonths = estimatedAgeMonths;
        this.feedingCost = feedingCost;
        this.fosterCareRequired = fosterCareRequired;
    }

    @Override
    public String getRescueType() {
        return "Orphaned Animal Rescue";
    }

    @Override
    public double calculateTotalCost() {
        double total = calculateBaseCost() + feedingCost;
        if (fosterCareRequired) {
            total += FOSTER_CARE_FEE;
        }
        return total;
    }

    /**
     * Younger than 3 months                       -> Critical
     * Younger than 12 months OR needs foster care -> High
     * Otherwise                                   -> Medium
     */
    @Override
    public String determinePriority() {
        if (estimatedAgeMonths < 3) {
            return PRIORITY_CRITICAL;
        } else if (estimatedAgeMonths < 12 || fosterCareRequired) {
            return PRIORITY_HIGH;
        }
        return PRIORITY_MEDIUM;
    }

    @Override
    public String getSpecificDetails() {
        return String.format("%-22s: %d%n", "Estimated Age (Months)", estimatedAgeMonths)
                + String.format("%-22s: %s%n", "Feeding Cost", formatRand(feedingCost))
                + String.format("%-22s: %s%n", "Foster Care Required", yesNo(fosterCareRequired));
    }

    public int getEstimatedAgeMonths() {
        return estimatedAgeMonths;
    }

    public double getFeedingCost() {
        return feedingCost;
    }

    public boolean isFosterCareRequired() {
        return fosterCareRequired;
    }
}
