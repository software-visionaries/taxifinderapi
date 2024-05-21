package app.taxifinderapi.service;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken.TokenType;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import app.taxifinderapi.JwtAuth.JwtTokenGenerator;
import app.taxifinderapi.dto.AuthResponseDto;
import app.taxifinderapi.dto.UserRegistrationDto;
import app.taxifinderapi.mapper.UserInfoMapper;
import app.taxifinderapi.model.PushToken;
import app.taxifinderapi.model.RefreshToken;
import app.taxifinderapi.model.User;
import app.taxifinderapi.repository.PushTokenRepository;
import app.taxifinderapi.repository.RefreshTokenRepository;
import app.taxifinderapi.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {

    private final UserRepository userRepository;

    private final JwtTokenGenerator jwtTokenGenerator;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserInfoMapper userInfoMapper;

    @Autowired
    PushTokenRepository pushTokenRepository;

    public AuthService(UserRepository userRepository, JwtTokenGenerator jwtTokenGenerator,
            RefreshTokenRepository refreshTokenRepository, UserInfoMapper userInfoMapper) {
        this.userRepository = userRepository;
        this.jwtTokenGenerator = jwtTokenGenerator;
        this.refreshTokenRepository = refreshTokenRepository;
        this.userInfoMapper = userInfoMapper;
    }

    public AuthResponseDto getJwtTokensAfterAuthentication(Authentication authentication,
            HttpServletResponse httpServletResponse) {

        try {

            User user = userRepository.findByEmail(authentication.getName())
                    .orElseThrow(() -> new Exception("User :" + authentication.getName() + " not found"));

            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

            saveUserRefreshToken(user, refreshToken);

            createRefreshTokenCookie(httpServletResponse, refreshToken);

            return new AuthResponseDto.AuthResponseDtoBuilder()
                    .accessToken(accessToken)
                    .accessTokenExpiry(15 * 60)
                    .userName(user.getEmail())
                    .tokenType(OAuth2AccessToken.TokenType.BEARER)
                    .userId(user.getUser_id())
                    .build();

        } catch (Exception e) {
            throw new RuntimeException(HttpStatus.INTERNAL_SERVER_ERROR + "Please Try Again");
        }
    }

    private Cookie createRefreshTokenCookie(HttpServletResponse httpServletResponse, String refreshToken) {
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        // refreshTokenCookie.setSecure(true);
        refreshTokenCookie.setMaxAge(15 * 24 * 60 * 60);
        httpServletResponse.addCookie(refreshTokenCookie);
        return refreshTokenCookie;
    }

    private void saveUserRefreshToken(User user, String refreshToken) {
        RefreshToken refreshTokenEntity = RefreshToken.RefreshTokenBuilder()
                .user(user)
                .refreshToken(refreshToken)
                .revoked(false)
                .build();

        refreshTokenRepository.save(refreshTokenEntity);
    }

    public Object getAccessTokenUsingRefreshToken(String authHeader){
        if(!authHeader.startsWith(TokenType.BEARER.getValue())){
            return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Please verify your token type");
        }

        final String refreshToken = authHeader.substring(7);

        RefreshToken refreshTokenEntity = refreshTokenRepository.findByRefreshToken(refreshToken)
        .filter(tokens-> tokens.isRevoked())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Refresh token revoked"));

        User user = refreshTokenEntity.getUser();

        Authentication authentication = createAuthenticationObject(user);

        String accessToken = jwtTokenGenerator.generateAccessToken(authentication);

        return new AuthResponseDto.AuthResponseDtoBuilder()
        .accessToken(accessToken)
        .accessTokenExpiry(5*60)
        .userName(user.getEmail())
        .tokenType(TokenType.BEARER)
        .build();
    }

    private static Authentication createAuthenticationObject(User user){
        String username = user.getEmail();
        String password = user.getPassword();
        String roles = user.getRoles();

        String[] roleArray = roles.split(",");
        GrantedAuthority[] authorities = Arrays.stream(roleArray)
        .map(role -> (GrantedAuthority) role::trim)
        .toArray(GrantedAuthority[]::new);

        return new UsernamePasswordAuthenticationToken(username, password, Arrays.asList(authorities));
    }

    public AuthResponseDto registerUser(UserRegistrationDto userRegistrationDto, HttpServletResponse httpServletResponse){
        try {
            System.out.println("[AuthService:registerUser]User Registration Started with :::{}"+userRegistrationDto);

            Optional<User> user = userRepository.findByEmail(userRegistrationDto.email());
            if(user.isPresent()){
                throw new Exception("User Already Exists");
            }

            User userDetails = userInfoMapper.convertToEntity(userRegistrationDto);
            Authentication authentication = createAuthenticationObject(userDetails);

            String accessToken = jwtTokenGenerator.generateAccessToken(authentication);
            String refreshToken = jwtTokenGenerator.generateRefreshToken(authentication);

            User savedUserDetails = userRepository.save(userDetails);
            saveUserRefreshToken(userDetails, refreshToken);

            PushToken pushToken = new PushToken();
            pushToken.setPush_token_value(userRegistrationDto.pushToken());
            pushToken.setUser(savedUserDetails);
            pushTokenRepository.save(pushToken);

            createRefreshTokenCookie(httpServletResponse, refreshToken);

            return new AuthResponseDto.AuthResponseDtoBuilder()
            .accessToken(accessToken)
            .accessTokenExpiry(5 * 60)
            .userName(savedUserDetails.getEmail())
            .tokenType(TokenType.BEARER)
            .userId(savedUserDetails.getUser_id())
            .build();
        } catch (Exception e){
            System.out.println("[AuthService:registerUser]Exception while registering the user due to :"+e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
