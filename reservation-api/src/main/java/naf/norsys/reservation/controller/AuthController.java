package naf.norsys.reservation.controller;


import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
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
import naf.norsys.reservation.utils.AuthenticationHelper;
import naf.norsys.reservation.utils.JwtProvider;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final JwtProvider jwtProvider;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final AuthenticationHelper authenticationHelper;

    public AuthController(JwtProvider jwtProvider, UserService userService, ModelMapper modelMapper, AuthenticationHelper authenticationHelper) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.authenticationHelper = authenticationHelper;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody @Valid UserLoginDto userLoginDto, HttpServletResponse response) throws UnauthorizedException, ElementNotFoundException {
        Authentication authToken = new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(), userLoginDto.getPassword());
        Authentication authResult = authenticationHelper.authenticateToken(authToken);

        User authenticatedUser = (User) authResult.getPrincipal();
        authenticationHelper.generateAndSetTokens(authenticatedUser, GenericEnum.JwtTokenType.ACCESS, response);
        authenticationHelper.generateAndSetTokens(authenticatedUser, GenericEnum.JwtTokenType.REFRESH, response);
        UserDto userDto = modelMapper.map(authenticatedUser, UserDto.class);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/token")
    public ResponseEntity<Void> refreshToken(HttpServletRequest request, HttpServletResponse response) throws BusinessException {
        String refreshToken = jwtProvider.extractTokenFromRequest(request, GenericEnum.JwtTokenType.REFRESH);
        DecodedJWT decodedRefreshToken = jwtProvider.getDecodedJWT(refreshToken, GenericEnum.JwtTokenType.REFRESH);

        Long userId = Long.valueOf(decodedRefreshToken.getSubject());
        User user = userService.findById(userId);

        authenticationHelper.validateRefreshToken(user, decodedRefreshToken.getId());

        authenticationHelper.generateAndSetTokens(user, GenericEnum.JwtTokenType.ACCESS, response);
        authenticationHelper.generateAndSetTokens(user, GenericEnum.JwtTokenType.REFRESH, response);

        return ResponseEntity.ok().build();
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletResponse response) throws BusinessException {
        authenticationHelper.deleteTokenCookie(response, "access_token");
        authenticationHelper.deleteTokenCookie(response, "refresh_token");
        return ResponseEntity.ok().build();
    }





    @PostMapping("/register")
    public ResponseEntity<UserDto> saveUser(@RequestBody UserRegisterDto userDto) throws ElementAlreadyExistsException {
        User convertedUser = modelMapper.map(userDto, User.class);
        User savedUser = userService.save(convertedUser);
        return new ResponseEntity<>(modelMapper.map(savedUser, UserDto.class), HttpStatus.CREATED);

    }

}
