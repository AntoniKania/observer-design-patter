# Army Management System

This project implements the Publisher/Subscriber programming pattern, specifically using the Reactive Subscriber and Publisher Interfaces from Java's Flow class.

## Overview

The Army Management System simulates a scenario where two generals command their armies. The system provides functionalities such as ordering maneuvers, initiating attacks, purchasing soldiers, and generating reports.

## Implementation Details

- Publisher Classes:
    - Soldier: Notifies subscribers about changes in soldier attributes.
    - General: Notifies subscribers about general actions and army updates.

- Subscriber Class:
    - GeneralsSecretary: Observes actions and generates reports.