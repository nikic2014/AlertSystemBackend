package com.AlertSystem.backendSiteDiplom.controllers;

import com.AlertSystem.backendSiteDiplom.dto.AuthDTO;
import com.AlertSystem.backendSiteDiplom.dto.PeopleDTO;
import com.AlertSystem.backendSiteDiplom.models.People;
import com.AlertSystem.backendSiteDiplom.services.RegistrationService;
import com.AlertSystem.backendSiteDiplom.util.JWTUtil;
import com.AlertSystem.backendSiteDiplom.util.MyLogger;
import com.AlertSystem.backendSiteDiplom.util.PeopleValidator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final PeopleValidator peopleValidator;
    private final RegistrationService registrationService;
    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final MyLogger myLogger;

    @Autowired
    public AuthController(PeopleValidator peopleValidator,
                          RegistrationService registrationService,
                          ModelMapper modelMapper,
                          AuthenticationManager authenticationManager,
                          JWTUtil jwtUtil, MyLogger myLogger) {
        this.peopleValidator = peopleValidator;
        this.registrationService = registrationService;
        this.modelMapper = modelMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.myLogger = myLogger;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthDTO authDTO) {
        myLogger.sendInfo("Пользователь " + authDTO.getLogin() +
                " пытается авторизоваться");

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(authDTO.getLogin(),
                        authDTO.getPassword());
        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        }
        catch (Exception exception) {
            myLogger.sendWarn("У пользователя " + authDTO.getLogin() +
                    " возникли проблемы с авторизацией");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "Неправильный логин или пароль");
        }

        myLogger.sendInfo("Пользователь " + authDTO.getLogin() +
                " успешно авторизовался");

        String token = jwtUtil.generateToken(authDTO.getLogin(),
                                             authDTO.getPassword());

        Cookie jwtCookie = new Cookie("jwt-token", token);
        jwtCookie.setMaxAge(86400); // 24 часа
        jwtCookie.setHttpOnly(true);
        jwtCookie.setDomain("127.0.0.1");
        jwtCookie.setPath("/");
        jwtCookie.setSecure(false);
        jwtCookie.setHttpOnly(false);

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        response.addCookie(jwtCookie);
        System.out.println(response.getHeaderNames());
        System.out.println(response.getHeader("Set-Cookie"));

        if (response instanceof HttpServletResponse) {
            ((HttpServletResponse) response).setHeader("Set-Cookie", "jwt" +
                    "-token=" + token +
                    "; HttpOnly; Secure; Path=/; SameSite=None");
        } else {
//            jwtCookie.setSameSite("None");
        }

        return ResponseEntity.ok("Пользователь успешно залогинился");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid PeopleDTO peopleDTO,
                                           BindingResult bindingResult) {
        People people = convertToPeople(peopleDTO);

        myLogger.sendInfo("Пользователь:\n" +
                people.getLogin() + "\n" +
                people.getEmail() + "\n" +
                people.getTelephone() + "\n" +
                people.getTgLink() + "\n" +
                "пытается зарегистрироваться");

        peopleValidator.validate(people, bindingResult);

        if (bindingResult.hasErrors()) {
            myLogger.sendWarn("Пользователь " + people.getLogin() +
                    " уже существует");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    "Пользователь с таким именем уже существует");
        }

        registrationService.register(people);
        myLogger.sendInfo("Пользователь: " + people.getLogin() + " успешно " +
                "зарегестрировался");

        return ResponseEntity.ok("Пользователь успешно зарегестрирован");
    }
    private People convertToPeople(PeopleDTO peopleDTO) {
        return this.modelMapper.map(peopleDTO, People.class);
    }

}
