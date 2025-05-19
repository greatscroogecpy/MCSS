# Daisyworld Java Implementation

## Introduction
This project implements the classic Daisyworld ecological model (originally from NetLogo) in Java. It features a grid world, daisy growth, and temperature feedback mechanisms.

## Directory Structure
- `src/World.java`: Main world class
- `src/Cell.java`: Grid cell class
- `src/Daisy.java`: Daisy class
- `src/Simulation.java`: Simulation entry point

## Compile and Run

```bash
javac -d out src/*.java
java -cp out Simulation
```