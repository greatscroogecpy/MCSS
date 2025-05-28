# Daisyworld Java Implementation

## Introduction
This project implements the classic Daisyworld ecological model (originally from NetLogo) in Java. The model simulates a simplified ecosystem where white and black daisies regulate planetary temperature through their different albedo properties.

### Key Features
- **Classic Daisyworld Mechanics**: Temperature regulation through daisy albedo feedback
- **Spatial Heterogeneity**: Soil quality variation across the world grid
- **Multiple Scenarios**: Different solar luminosity conditions
- **Data Export**: CSV output for analysis and visualization

## Model Description

### Core Mechanics
The Daisyworld model demonstrates how life can regulate planetary temperature:
- **White daisies** have high albedo (0.75) - reflect sunlight, cooling their environment
- **Black daisies** have low albedo (0.25) - absorb sunlight, warming their environment
- **Temperature feedback** affects daisy survival and reproduction
- **Diffusion** spreads temperature across neighboring patches

### Extended Feature: Soil Quality Heterogeneity
Our implementation adds spatial heterogeneity through soil quality variation:
- Each patch has a randomly assigned soil quality factor (0.5 - 1.5)
- Soil quality directly affects daisy reproduction probability
- Higher quality soil increases breeding success
- This simulates real-world environmental variation

## Directory Structure
- `src/Simulation.java`: Main simulation entry point and control
- `src/World.java`: World grid management and initialization
- `src/Patch.java`: Individual grid cell with temperature and daisy logic
- `src/Daisy.java`: Daisy entity with age and color properties
- `src/Logger.java`: Data collection and statistics
- `src/Params.java`: Centralized parameter configuration

## Parameters

### Core Parameters
- `WORLD_SIZE`: Grid dimensions (default: 29x29)
- `SOLAR_LUMINOSITY`: Solar energy input (default: 0.8)
- `START_WHITE_RATIO`: Initial white daisy coverage (default: 0.2)
- `START_BLACK_RATIO`: Initial black daisy coverage (default: 0.2)
- `DAISY_MAX_AGE`: Maximum daisy lifespan (default: 25)

### Soil Quality Parameters
- `ENABLE_SPATIAL_HETEROGENEITY`: Enable/disable soil quality variation
- `SOIL_QUALITY_VARIANCE`: Controls soil quality range (default: 0.5)

### Simulation Parameters
- `MAX_SIMULATION_STEPS`: Number of simulation steps
- `DIFFUSION_RATIO`: Temperature diffusion rate (default: 0.5)

## Compile and Run

```bash
# Compile the project
javac -d out src/*.java

# Run simulation
java -cp out Simulation
```

## Output
The simulation generates:
- **Console output**: Real-time statistics and progress
- **CSV file**: Detailed data for each time step including:
  - Average temperature
  - White and black daisy populations
  - Total daisy count
  - Solar luminosity

## Scientific Background
The Daisyworld model, created by James Lovelock, demonstrates the Gaia hypothesis - how life can regulate planetary conditions. Our soil quality extension explores how environmental heterogeneity affects ecosystem stability and species distribution.

## Implementation Notes
- Faithful reproduction of NetLogo Daisyworld mechanics
- Object-oriented design for modularity and extensibility
- Efficient grid-based spatial simulation
- Configurable parameters for experimental flexibility