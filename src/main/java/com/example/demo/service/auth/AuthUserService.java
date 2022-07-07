package com.example.demo.service.auth;

import com.example.demo.dto.auth.AuthUserDto;
import com.example.demo.dto.auth.RegisterUserDto;
import com.example.demo.dto.auth.SessionDto;
import com.example.demo.dto.response.AppErrorDto;
import com.example.demo.dto.response.DataDto;
import com.example.demo.entity.auth.AuthUser;
import com.example.demo.enums.AuthRole;
import com.example.demo.mappers.auth.AuthMapper;
import com.example.demo.properties.ServerProperties;
import com.example.demo.repository.auth.AuthUserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthUserService implements AbstractService, UserDetailsService {

    private final AuthUserRepository authUserRepository;
    private final ServerProperties serverProperties;
    private final AuthMapper authMapper;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<DataDto<Boolean>> registerUser(RegisterUserDto registerUserDto) {
        if (!registerUserDto.getPassword().equals(registerUserDto.getPrePassword()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto
                    .builder()
                    .message("Pre password not equals to password")
                    .status(HttpStatus.CONFLICT)
                    .build()),
                    HttpStatus.CONFLICT);

        boolean exists = authUserRepository.existsByPhoneNumber(registerUserDto.getPhoneNumber());
        if (exists)
            return new ResponseEntity<>(new DataDto<>(AppErrorDto
                    .builder()
                    .message("This user already exist")
                    .status(HttpStatus.CONFLICT)
                    .build()
            ), HttpStatus.CONFLICT);

        AuthUser authUser = new AuthUser();

        authUser.setFirstName(registerUserDto.getFirstName());
        authUser.setLastName(registerUserDto.getLastName());
        authUser.setDate(registerUserDto.getDate());
        authUser.setPhoneNumber(registerUserDto.getPhoneNumber());
        authUser.setEmail(registerUserDto.getEmail());
        authUser.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        authUser.setPrePassword(passwordEncoder.encode(registerUserDto.getPrePassword()));
        authUser.setRoles(AuthRole.USER);
        authUser.setEnabled(registerUserDto.isEnabled());

        authUserRepository.save(authUser);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.CREATED);
    }

    public ResponseEntity<DataDto<Boolean>> changeUserInfo(Long userId, RegisterUserDto registerUserDto) {

        if (!registerUserDto.getPassword().equals(registerUserDto.getPrePassword()))
            return new ResponseEntity<>(new DataDto<>(AppErrorDto
                    .builder()
                    .message("Pre password not equals to password")
                    .status(HttpStatus.CONFLICT)
                    .build()),
                    HttpStatus.CONFLICT);

        boolean exists = authUserRepository.existsByPhoneNumber(registerUserDto.getPhoneNumber());
        if (exists)
            return new ResponseEntity<>(new DataDto<>(AppErrorDto
                    .builder()
                    .message("This user already exist")
                    .status(HttpStatus.CONFLICT)
                    .build()
            ), HttpStatus.CONFLICT);


        Optional<AuthUser> authUserOptional = authUserRepository.findById(userId);
        if (authUserOptional.isEmpty())
            return new ResponseEntity<>(new DataDto<>(AppErrorDto
                    .builder()
                    .message("This user already exist")
                    .status(HttpStatus.CONFLICT)
                    .build()
            ), HttpStatus.CONFLICT);


        AuthUser authUser = authUserOptional.get();

        authUser.setFirstName(registerUserDto.getFirstName());
        authUser.setLastName(registerUserDto.getLastName());
        authUser.setDate(registerUserDto.getDate());
        authUser.setPhoneNumber(registerUserDto.getPhoneNumber());
        authUser.setEmail(registerUserDto.getEmail());
        authUser.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        authUser.setPrePassword(passwordEncoder.encode(registerUserDto.getPrePassword()));
        authUser.setRoles(AuthRole.USER);
        authUser.setEnabled(registerUserDto.isEnabled());

        authUserRepository.save(authUser);
        return new ResponseEntity<>(new DataDto<>(true), HttpStatus.CREATED);
    }

    public ResponseEntity<DataDto<List<RegisterUserDto>>> getAllUser() {
        List<AuthUser> authUserList = authUserRepository.findAll();

        return new ResponseEntity<>(new DataDto<>(authMapper.toDto(authUserList), (long) authUserList.size()), HttpStatus.OK);
    }

    public ResponseEntity<DataDto<SessionDto>> getToken(AuthUserDto dto) {
        try {
            HttpClient httpclient = HttpClientBuilder.create().build();
            HttpPost httppost = new HttpPost(serverProperties.getServerUrl() + "/api/login");
            byte[] bytes = objectMapper.writeValueAsBytes(dto);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            httppost.addHeader("Content-Type", "application/x-www-form-urlencoded");
            httppost.setEntity(new InputStreamEntity(byteArrayInputStream));

            HttpResponse response = httpclient.execute(httppost);

            JsonNode json_auth = objectMapper.readTree(EntityUtils.toString(response.getEntity()));

            if (json_auth.has("success") && json_auth.get("success").asBoolean()) {
                JsonNode node = json_auth.get("data");
                SessionDto sessionDto = objectMapper.readValue(node.toString(), SessionDto.class);

                return new ResponseEntity<>(new DataDto<>(sessionDto), HttpStatus.OK);
            }
            return new ResponseEntity<>(new DataDto<>(objectMapper.readValue(json_auth.get("error").toString(),
                    AppErrorDto.class)), HttpStatus.OK);

        } catch (IOException e) {
            return new ResponseEntity<>(new DataDto<>(AppErrorDto.builder()
                    .message(e.getLocalizedMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build()), HttpStatus.OK);
        }
    }

    public ResponseEntity<DataDto<SessionDto>> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = authUserRepository.findAuthUserByPhoneNumber(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found");
        });

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String authority = String.valueOf(user.getAuthorities());
        if(authority == null)
            authority = AuthRole.ADMIN.name();

        GrantedAuthority grantedAuthority =  new SimpleGrantedAuthority(authority);
        grantedAuthorities.add(grantedAuthority);

        return user;

/*        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(grantedAuthorities)
                .accountLocked(false)
                .accountExpired(false)
                .disabled(false)
                .credentialsExpired(false)
                .build();*/
    }
}
