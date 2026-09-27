package com.mycompany.wildliferescuesystem;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests rescue status updates, searching for an existing case,
 * preventing duplicate Rescue Case IDs, the report totals and validation.
 */
public class RescueManagerTest {

    private static final double DELTA = 0.001;
    private RescueManager manager;

    @Before
    public void setUp() {
        manager = new RescueManager();
        manager.addRescueCase(new InjuredAnimalRescue("WR101", "Tembo", "African Elephant",
                "Kruger National Park", "J. Mokoena", 10, 500, "Snare wound", 10500, true));
        manager.addRescueCase(new OrphanedAnimalRescue("WR102", "Kiko", "White Rhino",
                "Hluhluwe", "S. Naidoo", 10, 400, 8, 5000, true));
    }

    // ---------------- Status updates ----------------

    @Test
    public void testNewCaseStartsAsReported() {
        assertEquals(RescueCase.STATUS_REPORTED, manager.searchRescueCase("WR101").getStatus());
    }

    @Test
    public void testStartRescueUpdatesStatus() {
        assertTrue(manager.startRescue("WR101"));
        assertEquals(RescueCase.STATUS_IN_PROGRESS, manager.searchRescueCase("WR101").getStatus());
    }

    @Test
    public void testCompleteRescueUpdatesStatus() {
        manager.startRescue("WR102");
        assertTrue(manager.completeRescue("WR102"));
        assertEquals(RescueCase.STATUS_COMPLETED, manager.searchRescueCase("WR102").getStatus());
    }

    @Test
    public void testUpdateStatusToUnderObservation() {
        assertTrue(manager.updateStatus("WR102", RescueCase.STATUS_UNDER_OBSERVATION));
        assertEquals(RescueCase.STATUS_UNDER_OBSERVATION, manager.searchRescueCase("WR102").getStatus());
    }

    @Test
    public void testUpdateStatusOfMissingCaseFails() {
        assertFalse(manager.startRescue("WR999"));
        assertFalse(manager.completeRescue("WR999"));
    }

    // ---------------- Searching ----------------

    @Test
    public void testSearchExistingCase() {
        RescueCase found = manager.searchRescueCase("WR101");
        assertNotNull(found);
        assertEquals("African Elephant", found.getSpecies());
        assertEquals("Injured Animal Rescue", found.getRescueType());
    }

    @Test
    public void testSearchIsNotCaseSensitive() {
        assertNotNull(manager.searchRescueCase("wr102"));
    }

    @Test
    public void testSearchMissingCaseReturnsNull() {
        assertNull(manager.searchRescueCase("WR999"));
    }

    // ---------------- Duplicate IDs ----------------

    @Test
    public void testDuplicateCaseIdIsRejected() {
        boolean added = manager.addRescueCase(new EndangeredSpeciesRescue("WR101", "Dup", "Pangolin",
                "Limpopo", "L. Dlamini", 2, 100, "Vulnerable", 500, false));
        assertFalse(added);
        assertEquals(2, manager.getTotalCases());
    }

    @Test
    public void testIsCaseIdUnique() {
        assertFalse(manager.isCaseIdUnique("WR101"));
        assertTrue(manager.isCaseIdUnique("WR300"));
    }

    @Test
    public void testBlankCaseIdIsRejected() {
        boolean added = manager.addRescueCase(new InjuredAnimalRescue("  ", "A", "Lion",
                "Loc", "Ranger", 1, 100, "Cut", 100, false));
        assertFalse(added);
    }

    // ---------------- Report totals ----------------

    @Test
    public void testReportTotals() {
        assertEquals(2, manager.getTotalCases());
        assertEquals(32000.00, manager.getTotalRescueCost(), DELTA);
        String report = manager.generateReport();
        assertTrue(report.contains("WILDLIFE RESCUE REPORT"));
        assertTrue(report.contains("R32 000.00"));
    }

    // ---------------- Validation ----------------

    @Test
    public void testValidatorBlankChecks() {
        assertTrue(InputValidator.isBlank(""));
        assertTrue(InputValidator.isBlank("   "));
        assertTrue(InputValidator.isBlank(null));
        assertFalse(InputValidator.isBlank("Lion"));
    }

    @Test
    public void testValidatorPositiveAndMenuChecks() {
        assertTrue(InputValidator.isPositive(5));
        assertFalse(InputValidator.isPositive(0));
        assertFalse(InputValidator.isPositive(-3));
        assertTrue(InputValidator.isValidMenuChoice(6, 1, 6));
        assertFalse(InputValidator.isValidMenuChoice(7, 1, 6));
    }
}
