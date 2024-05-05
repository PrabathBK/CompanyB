package com.example.CompanyB.ManufacturingModule.Testing;


import com.example.CompanyB.ManufacturingModule.Service.UserControllingService;
import com.example.CompanyB.ManufacturingModule.Controller.UserController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {
    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;


    @Mock
    private UserControllingService userControllingService;


    @Test
    void adminLogin_Successful() {
        when(userControllingService.loginAdmin(anyString(), anyString())).thenReturn(0);
        ResponseEntity<?> responseEntity = userController.adminLogin("Cat", "King");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Login Successful", responseEntity.getBody());
    }

    @Test
    void adminLogin_NoAccess() {
        when(userControllingService.loginAdmin(anyString(), anyString())).thenReturn(-1);
        ResponseEntity<?> responseEntity = userController.adminLogin("King", "King");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("You Dont Have access", responseEntity.getBody());
    }

    @Test
    void adminLogin_IncorrectPassword() {
        when(userControllingService.loginAdmin(anyString(), anyString())).thenReturn(-2);
        ResponseEntity<?> responseEntity = userController.adminLogin("Cat", "King");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Incorrect Password", responseEntity.getBody());
    }

    @Test
    void adminLogin_IncorrectUsername() {
        when(userControllingService.loginAdmin(anyString(), anyString())).thenReturn(-3);
        ResponseEntity<?> responseEntity = userController.adminLogin("admin", "password");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("User name is incorrect", responseEntity.getBody());
    }
    @Test
    void workstationLogin_Successful() {
        ResponseEntity<?> responseEntity = userController.workstationLogin(1, "King", "King");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Login Successful", responseEntity.getBody());
    }

    @Test
    void workstationLogin_CannotLogWorkstation() {
        // Adjusting mock behavior for this scenario
        when(userControllingService.loginWorkstation(anyInt(), anyString(), anyString())).thenReturn(-1);

        ResponseEntity<?> responseEntity = userController.workstationLogin(1, "King", "King");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Can't log this workstation now", responseEntity.getBody());
    }

    @Test
    void workstationLogin_IncorrectPassword() {
        // Adjusting mock behavior for this scenario
        when(userControllingService.loginWorkstation(anyInt(), anyString(), anyString())).thenReturn(-2);

        ResponseEntity<?> responseEntity = userController.workstationLogin(1, "King", "password");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Password is incorrect", responseEntity.getBody());
    }

    @Test
    void workstationLogin_IncorrectUsername() {
        // Adjusting mock behavior for this scenario
        when(userControllingService.loginWorkstation(anyInt(), anyString(), anyString())).thenReturn(-3);

        ResponseEntity<?> responseEntity = userController.workstationLogin(1, "username", "password");
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Username is wrong", responseEntity.getBody());
    }

}




