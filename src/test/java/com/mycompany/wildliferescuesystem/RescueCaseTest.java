package com.mycompany.wildliferescuesystem;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests rescue cost calculations and rescue priority calculations
 * for all three rescue types.
 */
public class RescueCaseTest {

    private static final double DELTA = 0.001;

    // ---------------- Cost calculations ----------------

    @Test
    public void testInjuredRescueCostWithSurgery() {
        // 10 days x R500 = R5 000 + R10 500 vet + R5 000 surgery = R20 500
        RescueCase rescue = new InjuredAnimalRescue("WR101", "Tembo", "African Elephant",
                "Kruger National Park", "J. Mokoena", 10, 500, "Snare wound", 10500, true);
        assertEquals(20500.00, rescue.calculateTotalCost(), DELTA);
    }

    @Test
    public void testInjuredRescueCostWithoutSurgery() {
        // 5 x R200 = R1 000 + R3 000 vet = R4 000
        RescueCase rescue = new InjuredAnimalRescue("WR103", "Spots", "Leopard",
                "Cederberg", "A. Smith", 5, 200, "Leg fracture", 3000, false);
        assertEquals(4000.00, rescue.calculateTotalCost(), DELTA);
    }

    @Test
    public void testOrphanedRescueCostWithFosterCare() {
        // 10 x R400 = R4 000 + R5 000 feeding + R2 500 foster = R11 500
        RescueCase rescue = new OrphanedAnimalRescue("WR102", "Kiko", "White Rhino",
                "Hluhluwe", "S. Naidoo", 10, 400, 8, 5000, true);
        assertEquals(11500.00, rescue.calculateTotalCost(), DELTA);
    }

    @Test
    public void testOrphanedRescueCostWithoutFosterCare() {
        // 4 x R250 = R1 000 + R1 500 feeding = R2 500
        RescueCase rescue = new OrphanedAnimalRescue("WR104", "Bokkie", "Springbok",
                "Karoo", "P. van Wyk", 4, 250, 14, 1500, false);
        assertEquals(2500.00, rescue.calculateTotalCost(), DELTA);
    }

    @Test
    public void testEndangeredRescueCostWithSpecialistTeam() {
        // 6 x R1 000 = R6 000 + R4 000 security + R8 000 specialist = R18 000
        RescueCase rescue = new EndangeredSpeciesRescue("WR105", "Shadow", "Pangolin",
                "Limpopo", "L. Dlamini", 6, 1000, "Vulnerable", 4000, true);
        assertEquals(18000.00, rescue.calculateTotalCost(), DELTA);
    }

    @Test
    public void testEndangeredRescueCostWithoutSpecialistTeam() {
        // 3 x R600 = R1 800 + R2 200 security = R4 000
        RescueCase rescue = new EndangeredSpeciesRescue("WR106", "Flash", "Cheetah",
                "Kalahari", "M. Botha", 3, 600, "Vulnerable", 2200, false);
        assertEquals(4000.00, rescue.calculateTotalCost(), DELTA);
    }

    // ---------------- Priority calculations ----------------

    @Test
    public void testInjuredPriorityCriticalWhenSurgeryRequired() {
        RescueCase rescue = new InjuredAnimalRescue("WR201", "A", "Lion", "Loc", "Ranger",
                2, 100, "Deep wound", 2000, true);
        assertEquals(RescueCase.PRIORITY_CRITICAL, rescue.determinePriority());
    }

    @Test
    public void testInjuredPriorityHighWhenVetCostOver10000() {
        RescueCase rescue = new InjuredAnimalRescue("WR202", "A", "Lion", "Loc", "Ranger",
                2, 100, "Infection", 12000, false);
        assertEquals(RescueCase.PRIORITY_HIGH, rescue.determinePriority());
    }

    @Test
    public void testInjuredPriorityMedium() {
        RescueCase rescue = new InjuredAnimalRescue("WR203", "A", "Lion", "Loc", "Ranger",
                2, 100, "Scratch", 800, false);
        assertEquals(RescueCase.PRIORITY_MEDIUM, rescue.determinePriority());
    }

    @Test
    public void testOrphanedPriorityCriticalWhenVeryYoung() {
        RescueCase rescue = new OrphanedAnimalRescue("WR204", "A", "Baboon", "Loc", "Ranger",
                2, 100, 2, 500, false);
        assertEquals(RescueCase.PRIORITY_CRITICAL, rescue.determinePriority());
    }

    @Test
    public void testOrphanedPriorityHigh() {
        RescueCase rescue = new OrphanedAnimalRescue("WR205", "A", "White Rhino", "Loc", "Ranger",
                2, 100, 8, 500, true);
        assertEquals(RescueCase.PRIORITY_HIGH, rescue.determinePriority());
    }

    @Test
    public void testOrphanedPriorityMedium() {
        RescueCase rescue = new OrphanedAnimalRescue("WR206", "A", "Kudu", "Loc", "Ranger",
                2, 100, 18, 500, false);
        assertEquals(RescueCase.PRIORITY_MEDIUM, rescue.determinePriority());
    }

    @Test
    public void testEndangeredPriorityCriticalWhenCriticallyEndangered() {
        RescueCase rescue = new EndangeredSpeciesRescue("WR207", "A", "Riverine Rabbit", "Loc", "Ranger",
                2, 100, "Critically Endangered", 1000, false);
        assertEquals(RescueCase.PRIORITY_CRITICAL, rescue.determinePriority());
    }

    @Test
    public void testEndangeredPriorityHigh() {
        RescueCase rescue = new EndangeredSpeciesRescue("WR208", "A", "Wild Dog", "Loc", "Ranger",
                2, 100, "Endangered", 1000, false);
        assertEquals(RescueCase.PRIORITY_HIGH, rescue.determinePriority());
    }

    // ---------------- Formatting ----------------

    @Test
    public void testFormatRand() {
        assertEquals("R20 500.00", RescueCase.formatRand(20500));
    }
}
