package naf.norsys.reservation.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import naf.norsys.reservation.common.CoreConstant;
import naf.norsys.reservation.dto.JwtToken;
import naf.norsys.reservation.exception.BusinessException;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.exception.UnauthorizedException;
import naf.norsys.reservation.model.GenericEnum;
import naf.norsys.reservation.model.User;
import naf.norsys.reservation.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@Slf4j
public class AuthenticationHelper {


    private final UserService userService;

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    public AuthenticationHelper(UserService userService, AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }


    public Long getCurrentUserId() throws BusinessException {
        final Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();

        if (Objects.isNull(authentication.getPrincipal())) {
            log.error(CoreConstant.Exception.AUTHENTICATION_NULL_PRINCIPAL);
            throw new BusinessException(null, new BusinessException(), CoreConstant.Exception.AUTHENTICATION_NULL_PRINCIPAL, null);
        }
        return (Long) authentication.getPrincipal();
    }

    public User getCurrentUser() throws ElementNotFoundException {
        return userService.findById(getCurrentUserId());
    }

    public Authentication authenticateToken(Authentication authToken) throws UnauthorizedException {
        try {
            return authenticationManager.authenticate(authToken);
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new UnauthorizedException(null, e.getCause(), CoreConstant.Exception.AUTHENTICATION_BAD_CREDENTIALS, null);
        }
    }

    public void generateAndSetTokens(User user, GenericEnum.JwtTokenType tokenType, HttpServletResponse response) {
        JwtToken token = jwtProvider.generateToken(user, tokenType);
        String refreshTokenId = jwtProvider.getDecodedJWT(token.getToken(), tokenType).getId();

        user.setRefreshTokenId(refreshTokenId);
        userService.update(user.getId(), user);

        Cookie tokenCookie = jwtProvider.generateTokenCookie(token, tokenType);
        response.addCookie(tokenCookie);
    }

    public void validateRefreshToken(User user, String refreshTokenId) throws BusinessException {
        if (user.getRefreshTokenId() == null || !user.getRefreshTokenId().equals(refreshTokenId)) {
            throw new UnauthorizedException(null, new UnauthorizedException(), CoreConstant.Exception.AUTHORIZATION_INVALID_TOKEN, null);
        }
    }

    public void deleteTokenCookie(HttpServletResponse response, String tokenName) {
        Cookie tokenCookie = new Cookie(tokenName, null);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(0);
        response.addCookie(tokenCookie);
    }

}
