FROM maven:3.8.3-openjdk-17-slim

# Set the working directory to /app
WORKDIR /app

# Copy the project files to the container
COPY pom.xml .
COPY src ./src

# Set the entrypoint to run maven with command-line arguments
ENTRYPOINT ["mvn"]

# Set the default command to run `mvn test` if no other command is provided
CMD ["test"]