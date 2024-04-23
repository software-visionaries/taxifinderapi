package app.taxifinderapi.JwtAuth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenGenerator {
    
    private final JwtEncoder jwtEncoder;

    public JwtTokenGenerator(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    public String generateAccessToken(Authentication authentication) {
        String roles = getRolesOfUser(authentication);

        String permissions = getPermissionsFromRoles(roles);

        JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("shortLeft")
        .issuedAt(Instant.now())
        .expiresAt(Instant.now().plus(15, ChronoUnit.MINUTES))
        .subject(authentication.getName())
        .claim("scope", permissions)
        .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    }

    public String generateRefreshToken(Authentication authentication){

        System.out.println("[JwtTokenGenerator:generateRefreshToken] Token Creation Started for:{}" + authentication.getName());

        JwtClaimsSet claims = JwtClaimsSet.builder().
        issuer("shortLeft")
        .issuedAt(Instant.now())
        .expiresAt(Instant.now().plus(15, ChronoUnit.DAYS))
        .subject(authentication.getName())
        .claim("scope", "REFRESH_TOKEN")
        .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    private static String getRolesOfUser(Authentication authentication) {
        return authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
    }

    private String getPermissionsFromRoles(String roles){
        Set<String> permissions = new HashSet<>();

        if(roles.contains("ROLE_ADMIN")) {
            permissions.addAll(List.of("READ", "WRITE", "DELETE"));
        }

        if(roles.contains("ROLE_USER")){
            permissions.add("READ");
        }

        return String.join(" ", permissions);
    }
}
