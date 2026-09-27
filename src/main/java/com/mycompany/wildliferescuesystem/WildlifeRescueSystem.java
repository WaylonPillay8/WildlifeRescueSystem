package com.mycompany.wildliferescuesystem;

import java.util.Scanner;

/**
 * WildLife SA - Wildlife Rescue Operations System (console application).
 * Main class: shows the menu and handles user input.
 */
public class WildlifeRescueSystem {

    private static final Scanner input = new Scanner(System.in);
    private static final RescueManager manager = new RescueManager();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            showMenu();
            int choice = readMenuChoice("Select an option: ", 1, 6);

            switch (choice) {
                case 1:
                    createRescueCase();
                    break;
                case 2:
                    searchRescueCase();
                    break;
                case 3:
                    updateRescueStatus();
                    break;
                case 4:
                    displayAllRescueCases();
                    break;
                case 5:
                    System.out.println();
                    System.out.println(manager.generateReport());
                    break;
                case 6:
                    System.out.println("Thank you for using the Wildlife Rescue Operations System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void showMenu() {
        System.out.println();
        System.out.println("=======================================");
        System.out.println("WILDLIFE RESCUE OPERATIONS SYSTEM");
        System.out.println("=======================================");
        System.out.println();
        System.out.println("1. Create Rescue Case");
        System.out.println("2. Search Rescue Case");
        System.out.println("3. Update Rescue Status");
        System.out.println("4. Display All Rescue Cases");
        System.out.println("5. Rescue Report");
        System.out.println("6. Exit");
        System.out.println();
    }

    // ================= Menu option 1 =================
    private static void createRescueCase() {
        System.out.println();
        System.out.println("--- CREATE RESCUE CASE ---");
        System.out.println("1. Injured Animal Rescue");
        System.out.println("2. Orphaned Animal Rescue");
        System.out.println("3. Endangered Species Rescue");
        int type = readMenuChoice("Select rescue type: ", 1, 3);

        // Rescue Case ID: not blank and unique
        String caseId;
        while (true) {
            caseId = readNonBlank("Enter Rescue Case ID: ").toUpperCase();
            if (manager.isCaseIdUnique(caseId)) {
                break;
            }
            System.out.println("Error: Rescue Case ID '" + caseId + "' already exists. Enter a unique ID.");
        }

        String animalName = readNonBlank("Enter Animal Name: ");
        String species = readNonBlank("Enter Species: ");
        String location = readNonBlank("Enter Rescue Location: ");
        String ranger = readNonBlank("Enter Assigned Ranger: ");
        int days = readPositiveInt("Enter Number of Rescue Days: ");
        double dailyCost = readPositiveDouble("Enter Daily Care Cost (R): ");

        RescueCase newCase;
        switch (type) {
            case 1: {
                String injury = readNonBlank("Enter Injury Description: ");
                double vetCost = readPositiveDouble("Enter Veterinary Treatment Cost (R): ");
                boolean surgery = readYesNo("Is surgery required? (Y/N): ");
                newCase = new InjuredAnimalRescue(caseId, animalName, species, location, ranger,
                        days, dailyCost, injury, vetCost, surgery);
                break;
            }
            case 2: {
                int age = readPositiveInt("Enter Estimated Age (Months): ");
                double feedingCost = readPositiveDouble("Enter Feeding Cost (R): ");
                boolean foster = readYesNo("Is foster care required? (Y/N): ");
                newCase = new OrphanedAnimalRescue(caseId, animalName, species, location, ranger,
                        days, dailyCost, age, feedingCost, foster);
                break;
            }
            default: {
                String classification = readClassification();
                double securityCost = readPositiveDouble("Enter Security Cost (R): ");
                boolean specialist = readYesNo("Is a specialist team required? (Y/N): ");
                newCase = new EndangeredSpeciesRescue(caseId, animalName, species, location, ranger,
                        days, dailyCost, classification, securityCost, specialist);
                break;
            }
        }

        if (manager.addRescueCase(newCase)) {
            System.out.println();
            System.out.println("Rescue case " + caseId + " created successfully.");
            System.out.println("Priority   : " + newCase.determinePriority());
            System.out.println("Total Cost : " + RescueCase.formatRand(newCase.calculateTotalCost()));
        } else {
            System.out.println("Error: rescue case could not be created.");
        }
    }

    // ================= Menu option 2 =================
    private static void searchRescueCase() {
        System.out.println();
        String caseId = readNonBlank("Enter the Rescue Case ID to search for: ");
        RescueCase found = manager.searchRescueCase(caseId);

        if (found == null) {
            System.out.println("No rescue case found with ID: " + caseId);
        } else {
            System.out.println();
            System.out.println("--- RESCUE CASE FOUND ---");
            System.out.print(found.displayDetails());
        }
    }

    // ================= Menu option 3 =================
    private static void updateRescueStatus() {
        System.out.println();
        String caseId = readNonBlank("Enter the Rescue Case ID to update: ");
        RescueCase found = manager.searchRescueCase(caseId);

        if (found == null) {
            System.out.println("No rescue case found with ID: " + caseId);
            return;
        }

        System.out.println("Current status: " + found.getStatus());
        System.out.println("1. Start Rescue Operation   (" + RescueCase.STATUS_IN_PROGRESS + ")");
        System.out.println("2. Place Under Observation  (" + RescueCase.STATUS_UNDER_OBSERVATION + ")");
        System.out.println("3. Complete Rescue Operation (" + RescueCase.STATUS_COMPLETED + ")");
        int choice = readMenuChoice("Select new status: ", 1, 3);

        switch (choice) {
            case 1:
                manager.startRescue(caseId);
                break;
            case 2:
                manager.updateStatus(caseId, RescueCase.STATUS_UNDER_OBSERVATION);
                break;
            default:
                manager.completeRescue(caseId);
                break;
        }

        System.out.println();
        System.out.println("Status updated. Rescue summary:");
        System.out.print(found.generateRescueSummary());
    }

    // ================= Menu option 4 =================
    private static void displayAllRescueCases() {
        System.out.println();
        if (manager.getTotalCases() == 0) {
            System.out.println("No rescue cases have been captured yet.");
            return;
        }
        System.out.println("--- ALL RESCUE CASES ---");
        for (RescueCase rescueCase : manager.getRescueCases()) {
            System.out.print(rescueCase.displayDetails()); // polymorphism
            System.out.println("-".repeat(50));
        }
    }

    // ================= Input helpers (validation) =================

    private static String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = input.nextLine();
            if (!InputValidator.isBlank(value)) {
                return value.trim();
            }
            System.out.println("Error: this field cannot be blank.");
        }
    }

    private static int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(input.nextLine().trim());
                if (InputValidator.isPositive(value)) {
                    return value;
                }
                System.out.println("Error: value must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a whole number.");
            }
        }
    }

    private static double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(input.nextLine().trim().replace(",", "."));
                if (InputValidator.isPositive(value)) {
                    return value;
                }
                System.out.println("Error: value must be greater than zero.");
            } catch (NumberFormatException e) {
                System.out.println("Error: please enter a valid number.");
            }
        }
    }

    private static int readMenuChoice(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int choice = Integer.parseInt(input.nextLine().trim());
                if (InputValidator.isValidMenuChoice(choice, min, max)) {
                    return choice;
                }
            } catch (NumberFormatException e) {
                // handled below
            }
            System.out.println("Invalid selection. Please enter a number from " + min + " to " + max + ".");
        }
    }

    private static boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String answer = input.nextLine().trim().toUpperCase();
            if (answer.equals("Y") || answer.equals("YES")) {
                return true;
            }
            if (answer.equals("N") || answer.equals("NO")) {
                return false;
            }
            System.out.println("Error: please enter Y or N.");
        }
    }

    private static String readClassification() {
        System.out.println("Conservation Classification:");
        System.out.println("1. Vulnerable");
        System.out.println("2. Endangered");
        System.out.println("3. Critically Endangered");
        int choice = readMenuChoice("Select classification: ", 1, 3);
        switch (choice) {
            case 1:
                return "Vulnerable";
            case 2:
                return "Endangered";
            default:
                return "Critically Endangered";
        }
    }
}
