# Application of Mutual Assistance

## Description
This **Maven** project implements a *chat system* designed to serve as the foundation for a **Vulnerable Person Help application**. The goal is to connect individuals in need of assistance with volunteers willing to help, facilitated by a validation system.  
**Key features include**:
- Users can register as individuals in need, volunteers, or validators.
- Needs can be entered by individuals, and volunteers can respond to these requests or propose spontaneous help.
- Validators oversee and approve missions, providing reasons for rejected validations.
- Missions progress through statuses: *WAITING*, *VALIDATED*, *ACCEPTED*, *REJECTED*, or *DONE*.
- Feedback can be left by users after a mission.

## Prerequisites
Ensure the following tools are installed before proceeding:
- **JDK 17**
- **Maven**

## Installation
1. Clone this repository to your local machine:
   ```bash
   git clone https://github.com/Elsahindi/conduite_projet.git
   ```
2. Navigate to the project directory:
   ```bash
   cd conduite_projet
   ```
3. Compile the project and manage dependencies with Maven:
   ```bash
   mvn compile
   ```

## Usage
To start the application, run the main class using Maven:
```bash
mvn exec:java -Dexec.mainClass="Main"
```

## Tests
Run unit tests to verify the implementation:
```bash
mvn test
```

## Automation
The project supports automation through tools like **Maven**, **Git**, and **CI pipelines** to streamline development and deployment processes.