package com.example.demo.controller.auth;

import com.example.demo.controller.AbstractController;
import com.example.demo.dto.auth.RegisterUserDto;
import com.example.demo.dto.response.DataDto;
import com.example.demo.service.auth.AuthUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin
public class UserController extends AbstractController<AuthUserService> {

    public UserController(AuthUserService service) {
        super(service);
    }

    @RequestMapping(value = PATH + "/registerUser", method = RequestMethod.POST)
    public ResponseEntity<DataDto<Boolean>> registerUser (@RequestBody RegisterUserDto dto) {
        return service.registerUser(dto);
    }

    @RequestMapping(value = PATH + "/registerUser/{userId}", method = RequestMethod.PUT)
    public ResponseEntity<DataDto<Boolean>> registerUser (@PathVariable("userId") Long userId , @RequestBody RegisterUserDto dto) {
        return service.changeUserInfo(userId, dto);
    }
    @RequestMapping(value = PATH + "/allUsers", method = RequestMethod.GET)
    public ResponseEntity<DataDto<List<RegisterUserDto>>> getAllUsers () {
        return service.getAllUser();
    }
}
