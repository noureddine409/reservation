package naf.norsys.reservation.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import naf.norsys.reservation.common.CoreConstant;
import naf.norsys.reservation.dto.JwtToken;
import naf.norsys.reservation.exception.BusinessException;
import naf.norsys.reservation.exception.UnauthorizedException;
import naf.norsys.reservation.model.GenericEnum;
import naf.norsys.reservation.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtProvider {

    @Value("${jwt.access-token.secret}")
    private String accessTokenSecret;

    @Value("${jwt.access-token.expiration-in-mins}")
    private int accessTokenExpirationInMins;

    @Value("${jwt.refresh-token.secret}")
    private String refreshTokenSecret;

    @Value("${jwt.refresh-token.expiration-in-weeks}")
    private int refreshTokenExpirationInWeeks;

    @Value("${jwt.reset-token.secret}")
    private String resetTokenSecret;

    @Value("${jwt.reset-token.expiration-in-weeks}")
    private int resetTokenExpirationInWeeks;

    public JwtToken generateToken(User user, GenericEnum.JwtTokenType tokenType) throws BusinessException {
        String secret;
        Instant creationDate = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant();
        Instant expiryDate;
        String token;

        JWTCreator.Builder builder = JWT.create()
                .withJWTId(UUID.randomUUID().toString())
                .withSubject(user.getId().toString())
                .withIssuedAt(creationDate);

        try {
            switch (tokenType) {
                case ACCESS -> {
                    secret = accessTokenSecret;
                    expiryDate = LocalDateTime.now().plusMinutes(accessTokenExpirationInMins)
                            .atZone(ZoneId.systemDefault()).toInstant();

                    List<String> claims = user.getAuthorities().stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.toList());

                    builder
                            .withExpiresAt(expiryDate)
                            .withClaim("roles", claims);
                }
                case REFRESH -> {
                    secret = refreshTokenSecret;
                    expiryDate = LocalDateTime.now().plusWeeks(refreshTokenExpirationInWeeks)
                            .atZone(ZoneId.systemDefault()).toInstant();

                    builder
                            .withExpiresAt(expiryDate);
                }
                case RESET -> {
                    secret = resetTokenSecret;
                    expiryDate = LocalDateTime.now().plusWeeks(resetTokenExpirationInWeeks)
                            .atZone(ZoneId.systemDefault()).toInstant();

                    builder
                            .withExpiresAt(expiryDate);
                }
                default -> throw new BusinessException("Invalid token type", new BusinessException(), null, null);
            }

            token = builder.sign(Algorithm.HMAC256(secret));

        } catch (IllegalArgumentException | JWTCreationException e) {
            throw new BusinessException(e.getMessage(), e.getCause(), null, null);
        }

        return JwtToken.builder()
                .token(token)
                .createdAt(creationDate)
                .expiresIn(expiryDate)
                .build();
    }

    public DecodedJWT getDecodedJWT(String token, GenericEnum.JwtTokenType tokenType) throws UnauthorizedException {
        String secret = (Objects.equals(GenericEnum.JwtTokenType.ACCESS, tokenType)) ? accessTokenSecret : refreshTokenSecret;

        try {
            return JWT
                    .require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token);
        } catch (JWTVerificationException e) {
            throw new UnauthorizedException(null, e.getCause(), CoreConstant.Exception.AUTHORIZATION_INVALID_TOKEN, null);
        }
    }

    public Cookie generateTokenCookie(JwtToken token, GenericEnum.JwtTokenType tokenType) throws BusinessException {

        Cookie cookie = new Cookie(tokenType.name().toLowerCase() + "_token", token.getToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge((int) Duration.between(token.getCreatedAt(), token.getExpiresIn()).getSeconds()); // Calculate remaining seconds
        return cookie;
    }

    public String extractTokenFromRequest(HttpServletRequest request, GenericEnum.JwtTokenType tokenType) throws UnauthorizedException {
        Cookie[] cookies = request.getCookies();

        if (Objects.isNull(cookies) || cookies.length==0) {
            throw new UnauthorizedException(null, new UnauthorizedException(), CoreConstant.Exception.AUTHORIZATION_MISSING_COOKIE, null);
        }

        String tokenName = (tokenType == GenericEnum.JwtTokenType.ACCESS) ? "access_token" :
                (tokenType == GenericEnum.JwtTokenType.REFRESH) ? "refresh_token" : null;

        if (tokenName != null) {
            Optional<Cookie> tokenCookie = Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equalsIgnoreCase(tokenName))
                    .findFirst();

            if (tokenCookie.isPresent()) {
                return tokenCookie.get().getValue();
            } else {
                throw new UnauthorizedException(null, new UnauthorizedException(), CoreConstant.Exception.AUTHORIZATION_MISSING_TOKEN, null);
            }


        } else {
            throw new BusinessException("Invalid token type", new BusinessException(), null, null);
        }

    }

}



