# Postfix Expression Evaluator

A production-ready Spring Boot RESTful application for parsing, storing, and evaluating algebraic equations using postfix expression trees.

## Overview

This application provides a RESTful API for managing and evaluating algebraic equations. It parses infix notation equations into postfix notation, builds expression trees, stores them in memory, and evaluates them with provided variable values.

**Key Capability**: The application correctly handles operator precedence, associativity, parentheses, and variable substitution during evaluation.

## Key Features

- **Robust Equation Parsing**: Converts infix notation to postfix (Reverse Polish Notation) using the Shunting Yard algorithm
- **Expression Tree Construction**: Builds binary trees with operators as parent nodes and operands as children
- **Variable Evaluation**: Supports multiple variables in equations with dynamic value substitution
- **Error Handling**: Comprehensive validation with detailed error messages for malformed equations
- **In-Memory Storage**: Stores equations with auto-generated unique IDs
- **RESTful API**: Three simple endpoints for storing, retrieving, and evaluating equations
- **Comprehensive Testing**: Over 60 unit tests with high code coverage using JUnit 5

## Prerequisites

- **Java 21** or higher
- **Maven 3.6.0** or higher
- **Postman** (optional, for manual API testing)

## Project Setup

### 1. Clone or Extract the Repository

```bash
cd postfix-evaluator
```

```

## Building the Application

### Using Maven

```bash
# Build the project
./mvnw clean package

# Or on Windows
mvnw.cmd clean package

# Skip tests during build (if needed)
./mvnw clean package -DskipTests
```

The built JAR file will be located at: `target/postfix-evaluator-0.0.1-SNAPSHOT.jar`

## Running the Application

### Option 1: Using Maven

```bash
./mvnw spring-boot:run
```

### Option 2: Using Java JAR

```bash
java -jar target/postfix-evaluator-0.0.1-SNAPSHOT.jar
```

### Verify the Application

The application will start on `http://localhost:8080`

Check the console output for:
```
Started PostfixEvaluatorApplication in X.XXX seconds (JVM running for X.XXX)
```

## API Endpoints

### 1. Store Equation

**Endpoint**: `POST /api/equations/store`

**Description**: Parses an equation and stores it in memory.

**Request Body**:
```json
{
    "equation": "3*x + 2*y - z"
}
```

**Success Response** (HTTP 201):
```json
{
    "message": "Equation stored successfully",
    "equationId": "1"
}
```

**Error Response** (HTTP 400):
```json
{
    "error": "Invalid Equation",
    "message": "Invalid character at position 2: '&'",
    "timestamp": 1676200000000
}
```

---

### 2. Get All Equations

**Endpoint**: `GET /api/equations`

**Description**: Retrieves all stored equations in infix notation.

**Request**: No body required

**Success Response** (HTTP 200):
```json
{
    "equations": [
        {
            "equationId": "1",
            "equation": "3*x + 2*y - z"
        },
        {
            "equationId": "2",
            "equation": "a + b * c"
        }
    ]
}
```

**Empty Response**:
```json
{
    "equations": []
}
```

---

### 3. Evaluate Equation

**Endpoint**: `POST /api/equations/{equationId}/evaluate`

**Description**: Evaluates an equation with provided variable values.

**Path Parameter**:
- `equationId` (string): The ID of the equation to evaluate

**Request Body**:
```json
{
    "variables": {
        "x": 2,
        "y": 3,
        "z": 1
    }
}
```

**Success Response** (HTTP 200):
```json
{
    "equationId": "1",
    "equation": "3*x + 2*y - z",
    "variables": {
        "x": 2,
        "y": 3,
        "z": 1
    },
    "result": 11
}
```

**Error Response - Missing Variable** (HTTP 400):
```json
{
    "error": "Evaluation Error",
    "message": "Variable 'x' not provided in variables map",
    "timestamp": 1676200000000
}
```

**Error Response - Not Found** (HTTP 404):
```json
{
    "error": "Not Found",
    "message": "Equation with ID 'nonexistent' not found",
    "timestamp": 1676200000000
}
```

## Testing with Postman

### Setting Up Postman

1. **Set Base URL**: Create a collection variable `base_url` = `http://localhost:8080`

### Test Case 1: Store an Equation

1. **Create a POST request**
   - URL: `{{base_url}}/api/equations/store`
   - Method: POST
   - Header: `Content-Type: application/json`

2. **Request Body**:
   ```json
   {
       "equation": "3*x + 2*y - z"
   }
   ```

3. **Click Send** and verify the response contains an `equationId`

### Test Case 2: Retrieve All Equations

1. **Create a GET request**
   - URL: `{{base_url}}/api/equations`
   - Method: GET

2. **Click Send** and verify the response includes all stored equations

### Test Case 3: Evaluate an Equation

1. **Create a POST request**
   - URL: `{{base_url}}/api/equations/1/evaluate` (use the ID from Test Case 1)
   - Method: POST
   - Header: `Content-Type: application/json`

2. **Request Body**:
   ```json
   {
       "variables": {
           "x": 2,
           "y": 3,
           "z": 1
       }
   }
   ```

3. **Click Send** and verify the result equals 11 (3*2 + 2*3 - 1 = 6 + 6 - 1 = 11)


## Running Tests

### Run All Tests

```bash
./mvnw test
```

### Run Tests for Specific Class

```bash
./mvnw test -Dtest=TokenizerTest
./mvnw test -Dtest=EquationParserTest
./mvnw test -Dtest=EvaluatorServiceTest
```

### Run Tests with Coverage Report

```bash
./mvnw clean test
```

### Test Statistics

- **Total Tests**: 60+
- **Test Classes**: 8
- **Coverage Areas**:
  - Tokenization and validation
  - Infix to postfix conversion with precedence/associativity
  - Expression tree construction
  - Tree evaluation with variables
  - Error handling and edge cases
  - Repository CRUD operations
  - Service orchestration

### Key Test Scenarios

1. **Operator Precedence**: `3 + 2 * x` correctly evaluates as `3 + (2*x)`
2. **Associativity**: `10 - 5 - 2` correctly evaluates as `(10-5) - 2 = 3`
3. **Power Operator**: `2^3^2` correctly evaluates as `2^(3^2) = 512` (right associative)
4. **Parentheses**: `(a + b) * c` overrides default precedence
5. **Variable Substitution**: Variables are correctly replaced with their values
6. **Division by Zero**: Throws appropriate error
7. **Missing Variables**: Throws error with variable name
8. **Malformed Equations**: Detailed error for syntax violations

## Example Usage Flow

```
1. POST /api/equations/store {"equation": "3*x + 2*y - z"}
   → Returns equationId = "1"

2. GET /api/equations
   → Returns list including {"equationId": "1", "equation": "3*x + 2*y - z"}

3. POST /api/equations/1/evaluate {"variables": {"x": 2, "y": 3, "z": 1}}
   → Evaluates: 3*2 + 2*3 - 1 = 6 + 6 - 1 = 11
   → Returns {"equationId": "1", "equation": "3*x + 2*y - z", "variables": {...}, "result": 11}
```

## Troubleshooting

### Port Already in Use
If port 8080 is already in use, change it in `application.properties`:
```properties
server.port=8081
```

### Build Failures
```bash
# Clear Maven cache
./mvnw clean

# Rebuild
./mvnw package
```

### Tests Failing
Ensure you're using Java 21 or higher:
```bash
java -version  # Should show Java 21+
```
**Author**: Pushkar  
**Version**: 1.0.0  
**Java Version**: 21  
**Spring Boot Version**: 4.0.2