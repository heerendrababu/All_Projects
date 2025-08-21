package com.techtez.exception;

import java.util.NoSuchElementException;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle Method Argument Validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        JSONObject response = new JSONObject();
        response.put("code", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Bad Request");

        StringBuilder description = new StringBuilder();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            description.append(error.getDefaultMessage()).append(" ");
        }

        response.put("description", description.toString().trim());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString(4)); //  print in postman with 4 spaces
    }

    // Handle IllegalArgumentException (e.g., for duplicate email scenario)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        // Create a JSONObject to store error details
        JSONObject errorResponse = new JSONObject();

        String errorMessage = e.getMessage();
        String description = "Invalid input"; // Default description

        // Check for specific error messages and adjust description
        if (errorMessage != null) {
             if (errorMessage.contains("ID")) {
                description = "ID must be greater than 0.";
            } else if (errorMessage.contains("Email")) {
                description = "The provided email already exists.";
            }
        }

        // Populate the JSON object
        errorResponse.put("code", HttpStatus.BAD_REQUEST.value());
        errorResponse.put("message", "Bad Request");
        errorResponse.put("description", description);

        // Return the error response as a formatted JSON string
        return new ResponseEntity<>(errorResponse.toString(4), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handleNoSuchElementException(NoSuchElementException ex) {
        JSONObject response = new JSONObject();
        response.put("code", HttpStatus.NOT_FOUND.value());
        response.put("message", "User Not Found");
        response.put("description", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString(4));
    }

    // Handle other exceptions 
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        JSONObject response = new JSONObject();
        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.put("message", "An error occurred");
        response.put("description", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString(4));
    }
    
// Handle Typing mismatch exceptions like in place int id, if you enter 'c' or any alphabet the below method will trigger
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        // Create custom error response
        JSONObject response = new JSONObject();
        response.put("code", HttpStatus.BAD_REQUEST.value());
        response.put("message", "Invalid ID Format");
        response.put("description", "The ID provided must be an integer.");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString(4));
    }


    // Handle 404 Not Found errors (triggered when incorrect URL is accessed)
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<String> handleNotFound(NoHandlerFoundException ex) {
        JSONObject response = new JSONObject();
        response.put("code", HttpStatus.NOT_FOUND.value());
        response.put("message", "Resource Not Found");
        response.put("description", "The URL you have entered does not exist.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString(4));
    }

    // Handle 405 Method Not Allowed errors (triggered when incorrect HTTP method is used)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        JSONObject response = new JSONObject();
        response.put("code", HttpStatus.METHOD_NOT_ALLOWED.value());
        response.put("message", "Method Not Allowed");
        response.put("description", "Use the correct HTTP method: " + ex.getSupportedHttpMethods().iterator().next());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response.toString(4));
    }

}









// About getting correct suggestion for http request methods:
/*
 * 1) When your Spring application starts, it scans all your controllers and their @RequestMapping annotations- 
 * (like @GetMapping, @PostMapping, etc.) to determine which HTTP methods are valid for each URL.then,
 *  Spring determines the allowed HTTP methods for the URL from the controller mappings.
   2) ex.getSupportedHttpMethods() provides this list, and iterator().next() picks the first method as a suggestion.
   3) This works because Spring pre-maps all valid methods during application startup.

 * 
 */

   

