# Delivery Options API

## Overview

The Delivery Options API is a Java library that provides functionality for generating a list of 
delivery options based on a given postal code and list of products. The API includes a DeliveryService class that contains the core functionality for generating delivery options, as well as a DeliveryUtils class that provides utility functions for working with dates and products.

### Architecture

The Delivery Options API is built using Java and Spring Boot. It follows a layered architecture, with the following layers:

- **Controller**: Exposes the DeliveryService functionality through a REST API endpoint.
- **Service**: Contains the DeliveryService class, which provides the core functionality for 
  generating delivery options.
- **Repository**: Contains the ProductLoader class, which loads product data from a configuration file.
- **Utils**: Contains the DeliveryUtils class, which provides utility functions for working with 
  dates and products.

### Decisions

The following design decisions were made when developing the Delivery Options API:

- **REST API**: The API is designed as a REST API, with a single endpoint for generating delivery 
options. It is not the best to be using a POST request to get information, but GET requests 
  don't allow us to pass the product information. For fixing this, one suggestion would be a 
  gRPC API. See [improvement ideas](#improvement-ideas).
- **Spring Boot**: The API is built using Spring Boot, which provides a lightweight and easy-to-use 
  framework for building web applications.
- **Layered Architecture**: The API follows a layered architecture, which separates concerns and 
  allows for easier maintenance and testing.
- **LocalDate for delivery options**: The time of day is not used, so I chose to use LocalDate 
  instead of Date. It changes the way the output looks compared to the instructions, but since 
  the time meant nothing, it doesn't actually change the amount of information we are getting 
  from the endpoint.


## How to Run

To run the Delivery Options API, you can follow these steps:

- Clone the repository to your local machine.
- Open the project in your IDE of choice.
- Build the project using Maven: mvn clean install
- Start the application using Maven: mvn spring-boot:run

- The API will be available at http://localhost:8080/api/deliveryOptions

### Request Example

To generate delivery options, you can send a POST request to 
http://localhost:8080/api/deliveryOptions?postalCode="11130" with the following request 
body:

```json
[
    {
        "productId": "1",
        "name": "Product A",
        "deliveryDays": ["MONDAY", "WEDNESDAY", "FRIDAY"],
        "productType": "NORMAL",
        "daysInAdvance": 2
    },
    {
        "productId": "2",
        "name": "Product B",
        "deliveryDays": ["TUESDAY", "THURSDAY", "SATURDAY"],
        "productType": "EXTERNAL",
        "daysInAdvance": 5
    }
]
```

This request body contains a list of two products. The API will generate a list of delivery options based on the given postal code and products. The response will be a list of DeliveryOption objects, which includes the following properties:

postalCode: The given postal code.
deliveryDate: The date of the delivery option, in ISO 8601 format.
isGreenDelivery: A boolean value indicating whether the delivery option is a "green" delivery option.
Here's an example response:

```json
[
    {
        "postalCode": "12345",
        "deliveryDate": "2023-03-16",
        "isGreenDelivery": true
    },
    {
        "postalCode": "12345",
        "deliveryDate": "2023-03-18",
        "isGreenDelivery": true
    }
]
```

## Improvement Ideas

- **Authentication and Authorization**: Currently, the API does not include any authentication or 
authorization mechanisms. In the future, it could be improved by implementing security features such as authentication and authorization to protect sensitive data and prevent unauthorized access.

- **Error Handling**: The API currently returns basic error messages for invalid requests. In the 
future, it could be improved by adding more detailed error messages and error handling mechanisms to help clients understand and resolve issues.

- **Integration Testing**: The API currently includes unit tests, but it could be improved by adding 
integration tests to ensure that the API works correctly in a production-like environment.

- **Monitoring and Logging**: The API could be improved by adding monitoring and logging features to 
help identify and diagnose issues in real-time. This could include logging application events and errors, as well as monitoring system health and performance metrics.

- **Scalability**: If the API is expected to handle a high volume of requests or a large number of 
products, it could be improved by implementing scalability features such as load balancing, horizontal scaling, or clustering.

- **gRPC API**: By implementing gRPC, the Delivery Options API could potentially improve its 
  performance and scalability, especially if it needs to handle a large volume of requests or a high number of products. gRPC can also provide more robust error handling, automatic code generation, and a simpler API design compared to traditional REST APIs.

- **Storage**: By storing the product data in a database, the API could potentially improve its 
  scalability, performance, and flexibility. Instead of passing the full product information, the 
  API could only receive product ids and query the database to retrieve the necessary information 
  for generating delivery options. This would also allow the API to support more complex queries and filtering options, such as retrieving delivery options for specific products or postal codes.