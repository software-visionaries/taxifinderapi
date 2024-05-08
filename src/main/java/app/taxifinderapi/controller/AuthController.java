package app.taxifinderapi.controller;

import java.util.List;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import app.taxifinderapi.dto.UserRegistrationDto;
import app.taxifinderapi.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @PostMapping("/sign-in")
    public ResponseEntity<?> authicateUser(Authentication auth, HttpServletResponse httpServletResponse){
        return ResponseEntity.ok(authService.getJwtTokensAfterAuthentication(auth, httpServletResponse));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDto userRegistrationDto, BindingResult bindingResult, HttpServletResponse httpServletResponse){

        System.out.println("[AuthController:registerUser]Signup Process Started for user:{}"+userRegistrationDto.email());
        if(bindingResult.hasErrors()){
            List<String> errorMessage = bindingResult.getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
            .toList();
            System.out.println("[AuthController:registerUser]Errors in user: "+errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
        }
        return ResponseEntity.ok(authService.registerUser(userRegistrationDto, httpServletResponse));
    }

    @PreAuthorize("hasAuthority('SCOPE_REFRESH_TOKEN')")
    @PostMapping("/refresh-token")
    public ResponseEntity<?> getAccessToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) {
        return ResponseEntity.ok(authService.getAccessTokenUsingRefreshToken(authHeader));
    }
    

}
