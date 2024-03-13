This project was made on 16th of April 2023

Client-Server Time Processing Application

This Java application demonstrates a non-blocking client-server architecture where the server handles time-related requests from multiple clients. Configuration is managed through YAML files using the SnakeYAML library.

Features

Non-blocking client-server communication using Java NIO.
YAML-based configuration for server and client settings.
Support for concurrent and sequential client processing modes.
Time calculation and processing functionalities.
Getting Started

Dependencies
Java JDK 8 or higher.
SnakeYAML 2.0 library.
Setting Up
Download and Add SnakeYAML:
Download snakeyaml-2.0.jar from the official repository.
Place the JAR in a lib directory within your project.
Add the JAR to your project's classpath.
Clone the Repository:
Clone or download this repository to your local machine.
Configure Your YAML File:
Modify PassTimeServerOptions.yaml with your server and client configurations.
Running the Application
Execute Main.java to start the server and initiate client connections based on the YAML configuration.
Usage

The application reads the configuration from a YAML file, starts the server, and processes client requests.
Clients can send time-related requests to the server, which calculates and returns the results.
