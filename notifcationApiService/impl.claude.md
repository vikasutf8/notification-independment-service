# Implementation Plan for NotificationApiService

## Step 1: Implement Abstract Generic Global Exceptions

This step involves creating a comprehensive exception handling system for the Spring Boot application that follows modern best practices.

### Sub-step 1.1: Create Base Abstract Exception Class
- [x] Create `GlobalException.java` as the base abstract exception class
- [x] Include common exception attributes and methods
- [x] Define standard exception constructors
- [x] Set package structure under `com.project.notifcationApiService.exception`

### Sub-step 1.2: Create Specific Exception Classes
- [x] Create service-specific exception classes:
  - [x] `ResourceNotFoundException.java` - For missing resources
  - [x] `InvalidRequestException.java` - For validation errors
  - [x] `ServiceUnavailableException.java` - For service-related issues
  - [x] `DatabaseException.java` - For database operation failures
  - [x] `ValidationException.java` - For input validation failures
- [x] Each extends `GlobalException`
- [x] Include appropriate HTTP status codes
- [x] Add custom error response structure

### Sub-step 1.3: Create Exception Handler
- [x] Create `GlobalExceptionHandler.java` in `com.project.notifcationApiService.exception.handler`
- [x] Use modern Spring WebMVC exception handling patterns
- [x] Extend `ResponseEntityExceptionHandler` for centralized handling
- [x] Implement exception handlers for:
  - [x] `ResourceNotFoundException` -> 404 Not Found
  - [x] `InvalidRequestException` -> 400 Bad Request
  - [x] `ServiceUnavailableException` -> 503 Service Unavailable
  - [x] `DatabaseException` -> 500 Internal Server Error
  - [x] `ValidationException` -> 422 Unprocessable Entity
  - [x] Generic `Exception` -> 500 Internal Server Error
- [x] Standardize error response format with:
  - [x] Timestamp
  - [x] Error code
  - [x] Error message
  - [x] Request details

### Sub-step 1.4: Exception Documentation
- [x] Add comprehensive Javadoc comments
- [x] Document exception usage across services
- [x] Add error code constants
- [x] Create exception hierarchy diagram below

### Sub-step 1.5: Integration Testing
- [ ] Create test cases for exception handling
- [ ] Test error response formats
- [ ] Test exception propagation across layers
- [ ] Verify HTTP status codes

## Class Diagram

```
┌─────────────────────────────────────────┐
│         <<abstract>>                     │
│          GlobalException                 │
├─────────────────────────────────────────┤
│ - errorCode : String                     │
│ - httpStatus : HttpStatus                │
│ - message : String                       │
│ - details : Object                       │
├─────────────────────────────────────────┤
│ + getErrorCode() : String                │
│ + getHttpStatus() : HttpStatus           │
│ + getMessage() : String                  │
│ + getDetails() : Object                  │
└─────────────────────▲────────────────────┘
                      │
        ┌─────────────┼──────────────┐
        │             │              │
        │             │              │
┌───────┴───────┐ ┌───┴────────────┐ ┌┴──────────────────┐
│ ResourceNotFound │ │ InvalidRequest   │ │ ServiceUnavailable  │
│ Exception        │ │ Exception        │ │ Exception           │
├─────────────────┤ ├────────────────┤ ├───────────────────┤
│ 404 Not Found   │ │ 400 Bad Request │ │ 503 Service Unavail.│
└─────────────────┘ └────────────────┘ └───────────────────┘

┌─────────────────────────────────┐
│        DatabaseException         │
├─────────────────────────────────┤
│ 500 Internal Server Error       │
└───────────────▲─────────────────┘
                │
┌───────────────┴─────────────────┐
│      ValidationException         │
├─────────────────────────────────┤
│ 422 Unprocessable Entity        │
└─────────────────────────────────┘


┌─────────────────────────────────────────┐
│      GlobalExceptionHandler              │
│      <<ControllerAdvice>>                 │
├─────────────────────────────────────────┤
│ + handleResourceNotFoundException()     │
│ + handleInvalidRequestException()       │
│ + handleServiceUnavailableException()   │
│ + handleDatabaseException()             │
│ + handleValidationException()           │
│ + handleGenericException()              │
│ + buildErrorResponseEntity()            │
│ + handleExceptionInternal()             │
└──────────────────▲──────────────────────┘
                   │
                   │ uses
                   ↓
┌─────────────────────────────────────────┐
│      ErrorResponse  <<record>>            │
│      (in utils package)                   │
├─────────────────────────────────────────┤
│ - errorCode : String                      │
│ - message : String                        │
│ - timestamp : String                      │
│ - details : Object                        │
├─────────────────────────────────────────┤
│ + of(errorCode, message, details)        │
│ + of(errorCode, message)                  │
└─────────────────────────────────────────┘


┌─────────────────────────────────────────┐
│    NotifcationApiServiceApplication     │
│    <<SpringBootApplication>>             │
├─────────────────────────────────────────┤
│ + main(args: String[]) : void            │
└─────────────────────────────────────────┘
```

## File Structure After Implementation

```
com/project/notifcationApiService/exception/
├── GlobalException.java (Abstract base)
├── ResourceNotFoundException.java
├── InvalidRequestException.java
├── ServiceUnavailableException.java
├── DatabaseException.java
├── ValidationException.java
└── handler/
    └── GlobalExceptionHandler.java

com/project/notifcationApiService/utils/
├── ErrorResponse.java (Record DTO for error responses)
└── ...
```

## Error Response Format

```json
{
  "errorCode": "RESOURCE_NOT_FOUND",
  "message": "Resource User with id '123' not found.",
  "timestamp": "2026-09-20T10:30:00.000Z",
  "details": null
}
```

## Design Principles Applied

1. **Single Responsibility**: Each exception class has a specific purpose
2. **Open/Closed**: Easy to add new exception types without modifying existing code
3. **Liskov Substitution**: All exceptions can be used interchangeably through base class
4. **Dependency Inversion**: Handler depends on abstractions, not concrete exceptions
5. **Standardization**: Consistent error response format across all exceptions
6. **Testability**: Each component can be tested independently

## Step 2: Implement Additional Features

The user mentioned these steps (to be implemented next):
- [ ] configuration notification to propagate common data using thread local
- [ ] auth filter to filter and process context data
- [ ] design entity, DTO req/res
- [ ] implements service and dao layer
- [ ] implement create notification API

## Testing Strategy

1. Unit tests for individual exception classes
2. Integration tests for exception handling
3. Controller tests for error scenarios
4. Cross-service exception propagation tests