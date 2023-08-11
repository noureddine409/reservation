package naf.norsys.reservation.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import naf.norsys.reservation.common.CoreConstant;
import naf.norsys.reservation.dto.JwtToken;
import naf.norsys.reservation.dto.UserDto;
import naf.norsys.reservation.dto.UserLoginDto;
import naf.norsys.reservation.dto.UserRegisterDto;
import naf.norsys.reservation.exception.BusinessException;
import naf.norsys.reservation.exception.ElementAlreadyExistsException;
import naf.norsys.reservation.exception.ElementNotFoundException;
import naf.norsys.reservation.exception.UnauthorizedException;
import naf.norsys.reservation.model.GenericEnum;
import naf.norsys.reservation.model.User;
import naf.norsys.reservation.service.UserService;
import naf.norsys.reservation.utils.JwtProvider;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
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

    private final ModelMapper modelMapper;

    public AuthController(AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserService userService, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid UserLoginDto userLoginDto, HttpServletResponse response) throws UnauthorizedException, ElementNotFoundException {
        Authentication authToken = new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword());
        Authentication authResult = authenticateToken(authToken);

        User authenticatedUser = (User) authResult.getPrincipal();
        generateAndSetTokens(authenticatedUser, GenericEnum.JwtTokenType.ACCESS, response);
        generateAndSetTokens(authenticatedUser, GenericEnum.JwtTokenType.REFRESH, response);
        UserDto userDto = modelMapper.map(authenticatedUser, UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<Void> refreshToken(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        String refreshToken = jwtProvider.extractTokenFromRequest(request, GenericEnum.JwtTokenType.REFRESH);
        DecodedJWT decodedRefreshToken = jwtProvider.getDecodedJWT(refreshToken, GenericEnum.JwtTokenType.REFRESH);

        Long userId = Long.valueOf(decodedRefreshToken.getSubject());
        User user = userService.findById(userId);

        validateRefreshToken(user, decodedRefreshToken.getId());

        generateAndSetTokens(user, GenericEnum.JwtTokenType.ACCESS, response);
        generateAndSetTokens(user, GenericEnum.JwtTokenType.REFRESH, response);

        return ResponseEntity.ok().build();
    }

    private Authentication authenticateToken(Authentication authToken) throws UnauthorizedException {
        try {
            return authenticationManager.authenticate(authToken);
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new UnauthorizedException(null, e.getCause(), CoreConstant.Exception.AUTHENTICATION_BAD_CREDENTIALS, null);
        }
    }

    private void generateAndSetTokens(User user, GenericEnum.JwtTokenType tokenType, HttpServletResponse response) {
        JwtToken token = jwtProvider.generateToken(user, tokenType);
        String refreshTokenId = jwtProvider.getDecodedJWT(token.getToken(), tokenType).getId();

        user.setRefreshTokenId(refreshTokenId);
        userService.update(user.getId(), user);

        Cookie tokenCookie = jwtProvider.generateTokenCookie(token, tokenType);
        response.addCookie(tokenCookie);

    }

    private void validateRefreshToken(User user, String refreshTokenId) throws BusinessException {
        if (user.getRefreshTokenId() == null || !user.getRefreshTokenId().equals(refreshTokenId)) {
            throw new UnauthorizedException(null, new UnauthorizedException(), CoreConstant.Exception.AUTHORIZATION_INVALID_TOKEN, null);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) throws BusinessException {
        // Clear the cookies
        deleteTokenCookie(response, "access_token");
        deleteTokenCookie(response, "refresh_token");

        return ResponseEntity.ok().build();
    }
    private void deleteTokenCookie(HttpServletResponse response, String tokenName) {
        Cookie tokenCookie = new Cookie(tokenName, null);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(0);
        response.addCookie(tokenCookie);
    }




    @PostMapping("/register")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserRegisterDto userDto) throws ElementAlreadyExistsException {
        User convertedUser = modelMapper.map(userDto, User.class);
        User savedUser = userService.save(convertedUser);
        return new ResponseEntity<>(modelMapper.map(savedUser, UserDto.class), HttpStatus.CREATED);

    }

}
