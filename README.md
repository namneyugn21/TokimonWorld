# **Tokimon World**
**Tokimon World** is a Spring Boot application that allows users to interact with RESTful APIs through a user-friendly interface implemented with JavaFX. The application supports various operations, including retrieving Tokimon data (GET), creating new Tokimon entries (POST), updating existing Tokimon details (PUT), and deleting Tokimon records (DELETE).

## How to Run the Application
To run the application, follow these steps:

1. **Start the Backend**:
   - Navigate to the `springboot-tokimons` directory.
   - Run the Spring Boot application to start the backend server.

2. **Launch the User Interface**:
   - Navigate to the `javafx-tokimons` directory.
   - Run the launcher to open the JavaFX-based user interface, allowing you to interact with the backend.

**Important:**  
Since this is a small application, data is stored in a JSON file located at `springboot-tokimons/src/main/resources/static/data/tokimoncards.json`. You will need to update the file path in `TokimonCollection.java` to match the location on your computer.

## Preview
### Home Page
![Home Page](<javafx-tokimons/src/main/resources/ca/cmpt213/asn5/tokimonworld/tokimons/javafxtokimons/documentation/homepage.png>)

### View a Tokimon
![View](<javafx-tokimons/src/main/resources/ca/cmpt213/asn5/tokimonworld/tokimons/javafxtokimons/documentation/view.png>)

### Add a Tokimon
![Add](<javafx-tokimons/src/main/resources/ca/cmpt213/asn5/tokimonworld/tokimons/javafxtokimons/documentation/add.png>)

### Edit a Tokimon
![Edit](<javafx-tokimons/src/main/resources/ca/cmpt213/asn5/tokimonworld/tokimons/javafxtokimons/documentation/edit.png>)
