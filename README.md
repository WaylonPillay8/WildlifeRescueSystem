# Wildlife Rescue Operations System

Console application for WildLife SA to record, search, update and report on wildlife rescue cases.
Built in Java (Apache NetBeans, Maven) with JUnit 4 tests.

## Features
- Create, search, update and display rescue cases (stored in an `ArrayList`)
- Three rescue types: Injured Animal, Orphaned Animal, Endangered Species
- Each type calculates its own total cost and priority
- Start / complete rescue operations and generate a rescue summary
- Wildlife Rescue Report with total cases and total cost
- Input validation (blank fields, unique IDs, positive numbers, valid menu options)

## OOP design
| Principle | Where |
|---|---|
| Interface | `RescueOperations` (startRescue, completeRescue, generateRescueSummary) |
| Abstraction | `abstract class RescueCase` with abstract `calculateTotalCost()`, `determinePriority()`, `getRescueType()`, `getSpecificDetails()` |
| Inheritance | `InjuredAnimalRescue`, `OrphanedAnimalRescue`, `EndangeredSpeciesRescue` extend `RescueCase` |
| Encapsulation | Private fields with getters/setters |
| Method overriding | Each subclass overrides the cost, priority and details methods |
| Polymorphism | `RescueManager` stores `ArrayList<RescueCase>` and calls overridden methods |

## Cost rules
- Base cost (all types) = Number of Rescue Days x Daily Care Cost
- Injured: base + vet treatment cost (+ R5 000 if surgery required)
- Orphaned: base + feeding cost (+ R2 500 if foster care required)
- Endangered: base + security cost (+ R8 000 if specialist team required)

## Priority rules
- Injured: surgery required = Critical; vet cost over R10 000 = High; otherwise Medium
- Orphaned: under 3 months = Critical; under 12 months or needs foster care = High; otherwise Medium
- Endangered: Critically Endangered or specialist team = Critical; otherwise High

## Status flow
Reported -> Rescue in Progress (start) -> Under Observation -> Rescue Completed (complete)

## Running
- Run: open in NetBeans and press F6 (main class `WildlifeRescueSystem`)
- Test: Alt+F6 (or right-click project > Test)
