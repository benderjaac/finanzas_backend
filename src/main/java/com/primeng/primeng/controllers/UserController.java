package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.UserSimpleDto;
import com.primeng.primeng.models.ResponseApi;
import com.primeng.primeng.models.User;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.response.HttpOk;
import com.primeng.primeng.services.UserService;
import com.primeng.primeng.util.Response;
import com.primeng.primeng.util.Type;
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

    private Response response = new Response(Type.USUARIO);

    @Autowired
    private UserService userService;

    @PostMapping("/data")
    public ResponseEntity<HttpOk> findAll(
            HttpServletRequest request,
            @RequestBody Query query
    ) {
        return response.find(userService.findAllSimple(query));
    }



    @GetMapping("/{id}")
    public ResponseEntity<HttpOk> getUserById(
        HttpServletRequest request,
        @PathVariable Long id
    )
    {
        return response.find(userService.getUserById(id));
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
