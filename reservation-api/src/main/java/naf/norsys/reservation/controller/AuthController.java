package naf.norsys.reservation.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import naf.norsys.reservation.dto.UserLoginDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Api(tags = "Authentication", description = "web services for authentication management")
public class AuthController {
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDto dto) {
        return null;
    }
}
