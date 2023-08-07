package naf.norsys.reservation.controller;


import naf.norsys.reservation.dto.JwtToken;
import naf.norsys.reservation.dto.JwtTokenResponseDto;
import naf.norsys.reservation.dto.UserLoginDto;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.exception.UnauthorizedException;
import naf.norsys.reservation.model.GenericEnum;
import naf.norsys.reservation.model.User;
import naf.norsys.reservation.service.UserService;
import naf.norsys.reservation.utils.JwtProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<JwtTokenResponseDto> login( @RequestBody UserLoginDto userLoginDto)
            throws UnauthorizedException, ElementNotFoundException {

        Authentication authToken =
                new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword());

        Authentication authResult;

        try {
            authResult = authenticationManager.authenticate(authToken);
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new UnauthorizedException(null, e.getCause(), "", null);
        }

        User authenticatedUser = (User) authResult.getPrincipal();

        JwtToken accessToken = jwtProvider.generateToken(authenticatedUser, GenericEnum.JwtTokenType.ACCESS);
        JwtToken refreshToken = jwtProvider.generateToken(authenticatedUser, GenericEnum.JwtTokenType.REFRESH);
        String refreshTokenId = jwtProvider.getDecodedJWT(refreshToken.getToken(), GenericEnum.JwtTokenType.REFRESH).getId();

        User connectedUser = userService.findById(authenticatedUser.getId());
        connectedUser.setRefreshTokenId(refreshTokenId);
        userService.update(connectedUser.getId(), connectedUser);
        return ResponseEntity
                .ok()
                .body(
                        JwtTokenResponseDto.builder()
                                .accessToken(accessToken)
                                .refreshToken(refreshToken)
                                .build()
                );
    }
}
