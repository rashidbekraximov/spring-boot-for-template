package com.example.demo.controller.auth;

import com.example.demo.controller.AbstractController;
import com.example.demo.dto.auth.AuthUserDto;
import com.example.demo.dto.auth.SessionDto;
import com.example.demo.dto.response.DataDto;
import com.example.demo.service.auth.AuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@CrossOrigin
public class AuthController extends AbstractController<AuthUserService> {

    public AuthController(AuthUserService service) {
        super(service);
    }

    @RequestMapping(value = PATH + "/auth/token", method = RequestMethod.POST)
    public ResponseEntity<DataDto<SessionDto>> getToken(@RequestBody AuthUserDto dto) {
        return service.getToken(dto);
    }

    @RequestMapping(value = PATH + "/auth/refresh-token", method = RequestMethod.GET)
    public ResponseEntity<DataDto<SessionDto>> getToken(HttpServletRequest request, HttpServletResponse response) {
        return service.refreshToken(request, response);
    }
}
