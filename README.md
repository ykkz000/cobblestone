# Cobblestone API
[![License: LGPL v3](https://img.shields.io/badge/License-LGPL_v3-blue.svg)](https://www.gnu.org/licenses/lgpl-3.0)  
**Core utility library for Minecraft: Java Edition mod development**

English | [简体中文](README.zh_CN.md)

## Overview
Cobblestone API is a foundational toolkit designed specifically for Minecraft mod developers, providing efficient data persistence and event management solutions to significantly streamline mod development workflows.

## Core Modules
### Data Module
Delivers advanced data persistence capabilities:
- **Entity Data Extension**: Custom data binding through injection into `LivingEntity` class
- **Intelligent Serialization**: Automatic conversion between Java objects ↔ NBT data

### Event Module
Extends the native event system:
- **Enhanced Event Hooks**: Expanded coverage of critical game lifecycle points

## Building
Use Gradle build tool to compile the project.

## License
[View NOTICE file](NOTICE)
### Source Code License
**This project is licensed under LGPL-3.0**  
[View full license text](LICENSE)

### Third-Party Components
| Component        | License        |
|------------------|----------------|
| All dependencies | Apache-2.0/MIT |
| Build toolchain  | MIT            |

[View DEPENDENCIES file](DEPENDENCIES)

### Compliance Notes
1. Distribution of binary files **must include** the [NOTICE](NOTICE) file
2. Modified library code **requires disclosure of source changes**
3. Mods using this library **don't need to open-source their own code**