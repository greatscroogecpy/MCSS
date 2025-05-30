# Daisyworld Java Implementation

## Introduction
This project implements the classic Daisyworld ecological model (originally from NetLogo) in Java. The model simulates a simplified ecosystem where white, black, and gray daisies regulate planetary temperature through their different albedo properties.

### Key Features
- **Extended Daisyworld Mechanics**: Temperature regulation through three types of daisy albedo feedback
- **Three Daisy Types**: White (high albedo), black (low albedo), and gray (medium albedo) daisies
- **Spatial Heterogeneity**: Soil quality variation across the world grid
- **Multiple Scenarios**: Different solar luminosity conditions
- **Data Export**: CSV output for analysis and visualization

## Model Description

### Core Mechanics
The Daisyworld model demonstrates how life can regulate planetary temperature:
- **White daisies** have high albedo (0.75) - reflect sunlight, cooling their environment
- **Black daisies** have low albedo (0.25) - absorb sunlight, warming their environment
- **Gray daisies** have medium albedo (0.5) - provide neutral temperature regulation
- **Temperature feedback** affects daisy survival and reproduction
- **Diffusion** spreads temperature across neighboring patches

### Extended Feature: Three-Species Model
Our implementation extends the classic two-species model:
- Three daisy colors with different thermal properties
- Independent population dynamics for each species
- Competitive interactions for limited space resources
- More complex ecosystem stability patterns

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
- `src/QuickExperiments.java`: Experiment the extension of the model

## Parameters

### Core Parameters
- `WORLD_SIZE`: Grid dimensions (default: 29x29)
- `SOLAR_LUMINOSITY`: Solar energy input (default: 0.8)
- `START_WHITE_RATIO`: Initial white daisy coverage (default: 0.2)
- `START_BLACK_RATIO`: Initial black daisy coverage (default: 0.2)
- `START_GRAY_RATIO`: Initial gray daisy coverage (default: 0.1)
- `DAISY_MAX_AGE`: Maximum daisy lifespan (default: 25)

### Albedo Parameters
- `WHITE_DAISY_ALBEDO`: White daisy reflectance (default: 0.75)
- `BLACK_DAISY_ALBEDO`: Black daisy reflectance (default: 0.25)
- `GRAY_DAISY_ALBEDO`: Gray daisy reflectance (default: 0.5)
- `SURFACE_ALBEDO`: Bare ground reflectance (default: 0.4)

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
  - White, black, and gray daisy populations
  - Total daisy count
  - Solar luminosity

## Scientific Background
The Daisyworld model, created by James Lovelock, demonstrates the Gaia hypothesis - how life can regulate planetary conditions. Our three-species extension explores how multiple species with different thermal properties can create more complex regulatory dynamics. The soil quality extension examines how environmental heterogeneity affects ecosystem stability and species distribution.

## Implementation Notes
- Faithful reproduction of NetLogo Daisyworld mechanics
- Extended to support three daisy species
- Object-oriented design for modularity and extensibility
- Efficient grid-based spatial simulation
- Configurable parameters for experimental flexibility