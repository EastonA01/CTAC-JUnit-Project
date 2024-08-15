package org.example;

import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@DisplayName("User Registration")
@ExtendWith(MockitoExtension.class)
public class UserRegistrationTests {

    @Mock
    private static UserService userService;
    @Mock
    private static User existingUser;
    @Mock
    private static User newUser;

    @BeforeEach
    void setUp() {
        newUser = new User("JohnDoe", "password", "johndoe@example.com");
        existingUser = new User("JaneDoe", "password", "janedoe@example.com");
        userService.registerUser(existingUser);
    }


    // Register User Tests
    @Test
    @DisplayName("Positive Test registerUser()")
    public void testRegisterUser(){
        // Specify the behavior of the mock userService
        when(userService.registerUser(newUser)).thenReturn(true);

        Assertions.assertTrue(userService.registerUser(newUser));
    }
    @Test
    @DisplayName("Negative Test registerUser()")
    public void testRegisterUserNegative(){
        // 2nd User to try an re-register
        User newUser2 = new User("John", "password2", "john@example.com");

        // Specify the behavior of the mock userService
        when(userService.registerUser(newUser)).thenReturn(true);
        when(userService.registerUser(newUser2)).thenReturn(false);

        userService.registerUser(newUser);
        Assertions.assertFalse(userService.registerUser(newUser2));
    }
    @Test
    @DisplayName("Edge Case RegisterUser")
    public void testRegisterUserEdgeCase(){
        // Register a different account with the same email and password
        User newUser2 = new User("Mike", "password", "john@example.com");
        when(userService.registerUser(newUser)).thenReturn(true);
        when(userService.registerUser(newUser2)).thenReturn(false);
        userService.registerUser(newUser);
        Assertions.assertFalse(userService.registerUser(newUser2));

    }

    // Login user tests
    @Test
    @DisplayName("LoginUser Positive Case")
    public void testLoginUserPositiveCase(){
        // Mock the behavior of the loginUser method
        when(userService.loginUser("JaneDoe", "password")).thenReturn(existingUser);

        // Login the user
        User loggedUser = userService.loginUser("JaneDoe", "password");

        // Assert that the logged user is the same as the existing user
        Assertions.assertNotNull(loggedUser, "Logged user should not be null");
        Assertions.assertEquals(existingUser.getUsername(), loggedUser.getUsername(), "Usernames should be the same");
        Assertions.assertEquals(existingUser.getPassword(), loggedUser.getPassword(), "Passwords should be the same");
        Assertions.assertEquals(existingUser.getEmail(), loggedUser.getEmail(), "Emails should be the same");
    }
    @Test
    @DisplayName("LoginUser Negative Case")
    public void testLoginUserNegativeCase(){
        // Login the user
        User loggedUser = userService.loginUser("JaneDoe", "password");
        // Assert that the login failed
        Assertions.assertNull(loggedUser, "Logged user should be null");
    }
    @Test
    @DisplayName("LoginUser Edge Case")
    public void testLoginUserEdgeCase(){
        // Try to login with a null username
        User loggedUser = userService.loginUser(null, "password");

        // Assert that the login failed
        Assertions.assertNull(loggedUser, "Logged user should be null");
    }


}