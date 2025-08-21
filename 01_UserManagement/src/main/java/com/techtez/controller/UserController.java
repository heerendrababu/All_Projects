package com.techtez.controller;

import com.techtez.entity.User;
import com.techtez.service.UserService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;

import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController
{

    private final UserService userService;

   // @Autowired
    public UserController(UserService userService) 
    {
        this.userService = userService;
    }

    @PostMapping("/insert")
    public ResponseEntity<String> createUser(@Valid @RequestBody User user) {
        User savedUser = userService.createOrUpdateUser(user);

        // Construct the response using JSONObject
        JSONObject response = new JSONObject();
        response.put("Status code", HttpStatus.CREATED.value());
        response.put("message", "User created successfully");
    //    response.put("description", "User has been created successfully.");

        // Return the response as a JSON string
        return ResponseEntity.status(HttpStatus.CREATED).body(response.toString(4)); // print JSON with 4 spaces
    }




 // GET endpoint to fetch all users
//    @GetMapping("/getAll")
//    public ResponseEntity<?> getAll() {
//        List<User> users = userService.getAllUsers();
//        
//        // Check if the list is empty
//        if (users.isEmpty()) {
//            // Return 404 Not Found with a meaningful message
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body("No users found in the database.");
//        }
//        
////        return ResponseEntity.status(HttpStatus.OK).body(users);
//    }
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll()
    {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) 
        {
            // Return a message when no data is found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Data not found in the database.");
        }
        // Return the list of users if found
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


	

    @GetMapping("get/{id}")
    public ResponseEntity<User> getUserDataById(@PathVariable int id) {
        if (id <= 0) {
            // Invalid ID, throw IllegalArgumentException to be handled by GlobalExceptionHandler
            throw new IllegalArgumentException("ID must be greater than 0.");
        }

        // Get user from service layer
        User user = userService.getUserById(id);

        if (user == null) {
            // If user is not found, throw NoSuchElementException
            throw new NoSuchElementException("User not found in the database");
        }

        // Return success with user data in the response body
        return ResponseEntity.status(HttpStatus.OK).body(user);  // Returning user object as JSON
    }

    @PutMapping("update/{id}")
    public ResponseEntity<?> updateUserById(@PathVariable int id, @Valid @RequestBody User updatedUser) {
        // Fetch the user and update their details
        User existingUser = userService.getUserById(id);

        // If user not found, this will be handled by the global exception handler
        if (existingUser == null) {
            throw new NoSuchElementException("User with the given ID does not exist.");
        }

        // Update the user's details
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setSurName(updatedUser.getSurName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setMobileNo(updatedUser.getMobileNo());
        existingUser.setPassword(updatedUser.getPassword());

        // Save the updated user
        userService.createOrUpdateUser(existingUser);

        // Return success response without data
        return ResponseEntity.status(HttpStatus.OK)
                .body("User with ID " + id + " updated successfully.");
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        // Call the service to delete the user
        userService.deleteUser(id);

        // Return a success message with status code
        return ResponseEntity.status(HttpStatus.OK)
                .body("User with ID " + id + " has been deleted successfully.");
    }



    // DELETE endpoint to remove all users
    @DeleteMapping("/deleteAll")
    public ResponseEntity<?> deleteAllUsers() 
     {
        userService.deleteAllUsers();
        String responseMessage = "All users have been deleted successfully. Status: " + HttpStatus.OK.value();
        return ResponseEntity.status(HttpStatus.OK).body(responseMessage);
    }
    
 }







// ---------------------------------------------------------------------------------------------------------------------
// Why didn't i use (throw new NoSuchElementException) in getAll method ?
/* Ans:-
  * This method retrieves all users from the database.
  * 
  * - If no users are found, it returns a 404 (Not Found) with a message: "No users found in the database."
  * - If users are found, it returns the list with a 200 (OK) status.
  * 
  * No exception is thrown because "no users found" is not an error—it's a normal scenario.
  * We only use exceptions when an unexpected issue occurs, not for situations where data simply doesn't exist.
  */
 
 

// ------------------------------------------------------------------------------------------------------
/*putmapping : 2nd form:- handled exceptions by springFramework without explicitly declaring and we can send structured json response as like we want.
 * //    @PutMapping("update/{id}")
//    public ResponseEntity<String> updateUserById(@PathVariable int id, @Valid @RequestBody User updatedUser) {
//        if (id <= 0) {
//            throw new IllegalArgumentException("ID must be greater than 0.");
//        }
//
//        try {
//            User existingUser = userService.getUserById(id);
//            
//            // Update user data
//            existingUser.setFirstName(updatedUser.getFirstName());
//            existingUser.setSurName(updatedUser.getSurName());
//            existingUser.setEmail(updatedUser.getEmail());
//            existingUser.setAge(updatedUser.getAge());
//            existingUser.setMobileNo(updatedUser.getMobileNo());
//            existingUser.setPassword(updatedUser.getPassword());
//
//            User savedUser = userService.createOrUpdateUser(existingUser);
//
//            // Return success message as JSON
//            JSONObject response = new JSONObject();
//            response.put("message", "User with ID " + id + " updated successfully.");
//            return ResponseEntity.ok(response.toString(4)); // Pretty print with 4 spaces
//
//        } 
//        catch (Exception e) 
//        {
//            // Global exception handler will manage this
//            throw e;
//        }
//    }
 * 
 */





//------------------------------------------------------------------------------------------------------------------

/* putmapping-1st form:- which handled exceptions manually from here and give json structure response
 * // PUT endpoint to update an existing user
    @PutMapping("update/{id}")
	public ResponseEntity<?> updateUserById(@PathVariable int id, @Valid @RequestBody User updatedUser) {
	    JSONObject response = new JSONObject();

	    if (id <= 0) 
	    {
	        response.put("code", HttpStatus.NOT_FOUND.value());
	        response.put("message", "Not Found");
	        response.put("description", "Invalid ID: ID must be greater than 0.");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(response.toString(4));
	    }

	    try {
	        User existingUser = userService.getUserById(id);


	   
	        // Update the user's details
	        existingUser.setFirstName(updatedUser.getFirstName());
	        existingUser.setSurName(updatedUser.getSurName());
	        existingUser.setEmail(updatedUser.getEmail());
	        existingUser.setAge(updatedUser.getAge());
	        existingUser.setMobileNo(updatedUser.getMobileNo());
	        existingUser.setPassword(updatedUser.getPassword());

	        User savedUser = userService.createOrUpdateUser(existingUser);

	        response.put("code", HttpStatus.OK.value());
	        response.put("message", "User updated successfully.");
	        response.put("description", "The user details were updated successfully.");
	        response.put("data", new JSONObject(savedUser));
	        return ResponseEntity.ok(response.toString(4));

	    } catch (org.springframework.dao.DataIntegrityViolationException ex) {
	        if (ex.getMessage().contains("UKob8kqyqqgmefl0aco34akdtpe")) {
	            response.put("code", HttpStatus.BAD_REQUEST.value());
	            response.put("message", "Email already exists.");
	            response.put("description", "The provided email address is already registered.");
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString(4));
	        }
	        response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
	        response.put("message", "An unexpected error occurred.");
	        response.put("description", ex.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response.toString(4));
	    } catch (NoSuchElementException e) {
	        response.put("code", HttpStatus.NOT_FOUND.value());
	        response.put("message", "User Not Found");
	        response.put("description", "User with the given ID does not exist.");
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString(4));
	    } catch (Exception e) {
	        response.put("code", HttpStatus.BAD_REQUEST.value());
	        response.put("message", "An unexpected error occurred.");
	        response.put("description", e.getMessage());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString(4));
	    }
	}
 */

//-----------------------------------------------------------------------------------------------------------------------
/*
 * Another form of GetMapping By Id Code check when you are free:--
 * 
    @GetMapping("get/{id}")
    public ResponseEntity<String> getCustomerById(@PathVariable int id) {
        JSONObject response = new JSONObject();

        if (id <= 0) {
            return createErrorResponse(HttpStatus.BAD_REQUEST, "Invalid ID", "ID must be greater than 0.");
        }

        try 
        {
            User user = userService.getUserById(id);
            return createSuccessResponse(HttpStatus.OK, "User found", user);
        } catch (NoSuchElementException e) {
            return createErrorResponse(HttpStatus.NOT_FOUND, "User Not Found", "The ID you provided does not exist.");
        } catch (Exception e) {
            return createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An error occurred", e.getMessage());
        }
    }

    // Helper Methods for Responses
    private ResponseEntity<String> createErrorResponse(HttpStatus status, String message, String description) {
        JSONObject response = new JSONObject();
        response.put("code", status.value());
        response.put("message", message);
        response.put("description", description);
        return ResponseEntity.status(status).body(response.toString(4));
    }

    private ResponseEntity<String> createSuccessResponse(HttpStatus status, String message, User user) {
        JSONObject response = new JSONObject();
        response.put("data", new JSONObject(user));
        return ResponseEntity.status(status).body(response.toString(4));
    }
 */
//--------------------------------------------------------------------------------------------------------------------------
/*
 * 1)In the context of Spring Framework, FieldError is a class used to represent validation errors specific to a particular field in an object. 
 * It is commonly used with the BindingResult object, which holds the results of a data-binding and validation process.
 */
/*
 * 2)No, the getUpdatedFields() method is not provided by the FieldError class. 
 * The FieldError class is part of the Spring Validation framework-
 and is used to represent a validation error on a specific field (e.g., missing or invalid data in a form).
 */


//------------------------------------------------------------------------------------------------------------------------

/*
 *  // Return the deleted user data along with status code
    return new ResponseEntity<>(existingUser, HttpStatus.OK); 
 */

/* if you dont want to update mail and keep it as same, then write after checking existing user in putmapping
 * // Ensure email stays the same
if (!user.getEmail().equals(existingUser.getEmail())) {
    return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", "Email cannot be updated"), HttpStatus.BAD_REQUEST);
}

 */
//------------------------------------------------------------------
/*// Handling exceptions entirely not particularly
 *  @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", "Validation failed"), HttpStatus.BAD_REQUEST);
        }

        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found", "User not found in the database"), HttpStatus.NOT_FOUND);
        }

        user.setId(id);
        try {
            User updatedUser = userService.createOrUpdateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", "Email already exists"), HttpStatus.BAD_REQUEST);
        }
    }
 */
//------------------------------------------------------
/*// for updating only particular columns
@PutMapping("/{id}")
public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody Map<String, Object> updates) {
    // Fetch the existing user by ID
    User existingUser = userService.getUserById(id);
    if (existingUser == null) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.NOT_FOUND.value(), "Not Found", "User not found in the database"),
                HttpStatus.NOT_FOUND);
    }

    // Apply updates to specific fields
    updates.forEach((key, value) -> {
        switch (key) {
            case "firstName":
                existingUser.setFirstName((String) value);
                break;
            case "surName":
                existingUser.setSurName((String) value);
                break;
            case "email":
                existingUser.setEmail((String) value);
                break;
            case "mobileNo":
                existingUser.setMobileNo((String) value);
                break;
            case "password":
                existingUser.setPassword((String) value);
                break;
            // Add other fields as needed
            default:
                throw new IllegalArgumentException("Invalid field: " + key);
        }
    });

    try {
        // Save the updated user
        User updatedUser = userService.createOrUpdateUser(existingUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    } catch (IllegalArgumentException ex) {
        return new ResponseEntity<>(
                new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Bad Request", "Email already exists or invalid data"),
                HttpStatus.BAD_REQUEST);
    }
}

*/
