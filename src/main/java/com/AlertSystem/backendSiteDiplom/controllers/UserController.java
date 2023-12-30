package com.AlertSystem.backendSiteDiplom.controllers;

import com.AlertSystem.backendSiteDiplom.dto.PeopleDTO;
import com.AlertSystem.backendSiteDiplom.models.People;
import com.AlertSystem.backendSiteDiplom.services.PeopleDetailsService;
import com.AlertSystem.backendSiteDiplom.services.PeopleService;
import com.AlertSystem.backendSiteDiplom.util.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://127.0.0.1:5500", allowCredentials = "true")
@RestController
@RequestMapping("/user")
public class UserController {

    private final JWTUtil jwtUtil;
    private final PeopleService peoplesService;

    public UserController(JWTUtil jwtUtil, PeopleService peoplesService) {
        this.jwtUtil = jwtUtil;
        this.peoplesService = peoplesService;
    }

    @GetMapping("/userInfo")
    public Map userInfo(@CookieValue(value = "jwt-token") String jwtCookie) {
        String username = jwtUtil.validateToken(jwtCookie);
        People people = peoplesService.getByLogin(username);

        return Map.of("username", username, "id", people.getId());
    }

    @PostMapping("/reregister")
    public ResponseEntity<String> reregister(@CookieValue(value = "jwt-token") String jwtCookie,
                                             @RequestBody @Valid PeopleDTO peopleDTO) {
        String username = jwtUtil.validateToken(jwtCookie);
        People people = peoplesService.getByLogin(username);

        people.setLogin(peopleDTO.getLogin());
        people.setPassword(peopleDTO.getPassword());
        people.setEmail(peopleDTO.getEmail());
        people.setTelephone(peopleDTO.getTelephone());
        this.peoplesService.save(people);

        return ResponseEntity.ok("success");
    }
}
