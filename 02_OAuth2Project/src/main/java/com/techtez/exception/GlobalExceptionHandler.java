package com.techtez.exception;


import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
//import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtException;
//import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.http.MediaType;


@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Handle OAuth2 Authentication errors (Invalid token, etc.)
//    @ExceptionHandler(JwtException.class)
//    public ResponseEntity<String> handleJwtException(JwtException ex) {
//        JSONObject response = new JSONObject();
//        response.put("code", HttpStatus.UNAUTHORIZED.value());
//        response.put("message", "Invalid Token");
//        response.put("description", ex.getMessage());
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.toString(4));
//    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<String> handleOAuth2AuthenticationException(OAuth2AuthenticationException ex) {
        logger.error("OAuth2 Authentication Exception caught: " + ex.getMessage());  // Log to ensure handler is invoked
        JSONObject response = new JSONObject();
        response.put("code", HttpStatus.UNAUTHORIZED.value());
        response.put("message", "Invalid or expired token");
        response.put("description", "The provided token is either invalid or expired. Please authenticate again.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.toString(4));
    }



    // Handle Bad Credentials (for cases where client credentials are invalid)
//    @ExceptionHandler(BadCredentialsException.class)
//    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
//        logger.error("Bad Credentials: {}", ex.getMessage(), ex); // Log bad credentials error
//
//        // Constructing custom JSON response for bad credentials
//        JSONObject response = new JSONObject();
//        response.put("code", HttpStatus.UNAUTHORIZED.value());
//        response.put("message", "Invalid Client Credentials");
//        response.put("description", "The client credentials provided are invalid.");
//        
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.toString(4));
//    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        JSONObject response = new JSONObject();
        response.put("code", HttpStatus.METHOD_NOT_ALLOWED.value());
        response.put("message", "Method Not Allowed");
        response.put("description", "Use the correct HTTP method: " + ex.getSupportedHttpMethods().iterator().next());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response.toString(4));
    }
    // Handle NoHandlerFoundException (Invalid URL)
  	@ExceptionHandler(NoHandlerFoundException.class)
  	public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
  	    // Log the request URL and exception details
  	    logger.error("Resource not found: {}", ex.getRequestURL());
  	   
  	    // Constructing the custom JSON response using JSONObject
  	    JSONObject response = new JSONObject();
  	    response.put("code", HttpStatus.NOT_FOUND.value());
  	    response.put("message", "Resource Not Found");
  	    response.put("description", "The URL you have entered does not exist: " + ex.getRequestURL());
  	   
  	    // Returning the JSON with indented format
  	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString(4)); // Indented JSON
  	}
 // Handle Missing Token (AuthenticationCredentialsNotFoundException)
    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<String> handleMissingToken(AuthenticationCredentialsNotFoundException ex) {
        logger.error("Authentication token is missing: {}", ex.getMessage());
        JSONObject response = new JSONObject();
        response.put("code", HttpStatus.UNAUTHORIZED.value());
        response.put("message", "Authentication token is missing");
        response.put("description", "Please provide a valid token.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.toString(4)); // Indented JSON
    }
 
    // Handle Invalid/Expired JWT Token (JwtException)
    @ExceptionHandler(JwtException.class)
    public ResponseEntity<String> handleInvalidJwtToken(JwtException ex) {
        logger.error("Invalid or expired token: {}", ex.getMessage());
        JSONObject response = new JSONObject();
        response.put("code", HttpStatus.UNAUTHORIZED.value());
        response.put("message", "Invalid or expired token");
        response.put("description", "The provided JWT token is invalid or expired. Please provide a valid token.");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.toString(4)); // Indented JSON
    }
 
}

/* import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//    // Handle general exceptions
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, Object>> handleException(Exception ex) {
//        logger.error("Internal Server Error: {}", ex.getMessage(), ex); // Log the error
//        Map<String, Object> response = new HashMap<>();
//        response.put("message", "Internal Server Error: " + ex.getMessage());
//        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
//        response.put("error", ex.getClass().getSimpleName());
//
//        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    // Handle OAuth2 Authentication errors (Invalid token, etc.)
//    @ExceptionHandler(OAuth2AuthenticationException.class)
//    public ResponseEntity<Map<String, Object>> handleOAuth2AuthenticationException(OAuth2AuthenticationException ex) {
//        logger.error("OAuth2 Authentication Error: {}", ex.getMessage(), ex); // Log the OAuth2 error
//        Map<String, Object> response = new HashMap<>();
//        response.put("message", "Invalid or expired token: " + ex.getMessage());
//        response.put("status", HttpStatus.UNAUTHORIZED);
//        response.put("error", ex.getClass().getSimpleName());
//
//        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
//    }
//
//    // Handle Access Denied (Unauthorized Access)
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException ex) {
//        logger.error("Access Denied: {}", ex.getMessage(), ex); // Log the access denied error
//        Map<String, Object> response = new HashMap<>();
//        response.put("message", "Unauthorized Access: " + ex.getMessage());
//        response.put("status", HttpStatus.FORBIDDEN);
//        response.put("error", ex.getClass().getSimpleName());
//
//        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
//    }

    // Handle NoHandlerFoundException (Invalid URL)
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<String> handleNoHandlerFoundException(NoHandlerFoundException ex) {
	    // Log the request URL and exception details
	    logger.error("Resource not found: {}", ex.getRequestURL());
	   
	    // Constructing the custom JSON response using JSONObject
	    JSONObject response = new JSONObject();
	    response.put("code", HttpStatus.NOT_FOUND.value());
	    response.put("message", "Resource Not Found");
	    response.put("description", "The URL you have entered does not exist: " + ex.getRequestURL());
	   
	    // Returning the JSON with indented format
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.toString(4)); // Indented JSON
	}
//	@ExceptionHandler(InvalidBearerTokenException.class)
//	public ResponseEntity<String> handleInvalidTokenException(InvalidBearerTokenException ex) {
//	    JSONObject response = new JSONObject();
//	    response.put("code", HttpStatus.UNAUTHORIZED.value());
//	    response.put("message", "Invalid Token");
//	    response.put("description", "The OAuth2 token provided is invalid or expired.");
//
//	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.toString(4));
//	}

//    @ExceptionHandler(OAuth2AuthenticationException.class)
//    public ResponseEntity<String> handleOAuth2AuthenticationException(OAuth2AuthenticationException ex) {
//        JSONObject response = new JSONObject();
//        response.put("code", HttpStatus.UNAUTHORIZED.value());
//        response.put("message", "Invalid Client Credentials");
//        response.put("description", "The client credentials provided are invalid.");
//        
//        // You can log the exception here if needed
//        // logger.error("OAuth2 authentication failed: {}", ex.getMessage());
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.toString(4));
//    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex) {
        JSONObject response = new JSONObject();
        response.put("code", HttpStatus.UNAUTHORIZED.value());
        response.put("message", "Invalid Client Credentials");
        response.put("description", "The client credentials provided are invalid.");
        
        // You can log the exception here if needed
        // logger.error("Bad credentials: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.toString(4));
    }
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<String> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
        JSONObject response = new JSONObject();
        response.put("code", HttpStatus.METHOD_NOT_ALLOWED.value());
        response.put("message", "Method Not Allowed");
        response.put("description", "Use the correct HTTP method: " + ex.getSupportedHttpMethods().iterator().next());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response.toString(4));
    }
//    @ExceptionHandler(OAuth2AuthenticationException.class)
//    public ResponseEntity<String> handleOAuth2AuthenticationException(OAuth2AuthenticationException ex) {
//        JSONObject response = new JSONObject();
//        response.put("code", HttpStatus.UNAUTHORIZED.value());
//        response.put("message", "Authentication Failed");
//        response.put("description", "The token is invalid or expired.");
//
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.toString(4));
//    }
   

        @ExceptionHandler(JwtException.class)
        public ResponseEntity<String> handleJwtException(JwtException ex) {
            JSONObject response = new JSONObject();
            response.put("code", HttpStatus.UNAUTHORIZED.value());
            response.put("message", "Invalid Token");
            response.put("description", ex.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.toString(4));
        }
        @ExceptionHandler(OAuth2AuthenticationException.class)
        public ResponseEntity<String> handleOAuth2AuthenticationException(OAuth2AuthenticationException ex) {
            // You can log the error or process it further here
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired token. Please authenticate again.");
    }

} */



