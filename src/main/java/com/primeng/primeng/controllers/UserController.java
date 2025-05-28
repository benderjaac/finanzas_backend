package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.UserSimpleDto;
import com.primeng.primeng.models.ResponseApi;
import com.primeng.primeng.models.User;
import com.primeng.primeng.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private String title="Usuarios";
    private Date date = new Date();
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<ResponseApi<List<UserSimpleDto>>> getAllUsers() {
        List<UserSimpleDto> users = userService.getAllUsersSimple();
        ResponseApi<List<UserSimpleDto>> response = new ResponseApi<>(
                this.title, "OK", "Información encontrada", users, this.date
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseApi<User>> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);

        if (user.isEmpty()) {
            ResponseApi<User> response = new ResponseApi<>(
                    this.title,
                    "ERROR",
                    "Información no encontrada",
                    null,
                    this.date
            );
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        ResponseApi<User> response = new ResponseApi<>(
                this.title,
                "OK",
                "Información encontrada",
                user.get(),
                this.date
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ResponseApi<User>> createUser(@RequestBody User user) {
        User newUser =  userService.createUser(user);
        ResponseApi<User> response = new ResponseApi<>(
                this.title,
                "OK",
                "Usuario creado exitosamente",
                newUser,
                this.date
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseApi> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        ResponseApi<User> response = new ResponseApi<>(
                this.title,
                "OK",
                "Usuario eliminado exitosamente",
                null,
                this.date
        );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ResponseApi<?>> getCurrentUser(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            ResponseApi<String> response = new ResponseApi<>(this.title, "ERROR", "Autenticacion requerida", null, this.date);
           return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String token = authHeader.substring(7);
        ResponseApi response = userService.getUserToken(token);
        return ResponseEntity.ok(response);

    }
}
