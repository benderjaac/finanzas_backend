package com.primeng.primeng.controllers;

import com.primeng.primeng.dto.UserDto;
import com.primeng.primeng.dto.UserSimpleDto;
import com.primeng.primeng.models.User;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.response.HttpOk;
import com.primeng.primeng.security.CustomUserDetails;
import com.primeng.primeng.services.CustomUserDetailsService;
import com.primeng.primeng.services.UserService;
import com.primeng.primeng.util.Response;
import com.primeng.primeng.util.Type;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private Response response = new Response(Type.USUARIO);

    @Autowired
    private UserService userService;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @PostMapping("/data")
    public ResponseEntity<HttpOk> findAll(
            HttpServletRequest request,
            @RequestBody Query query
    ) {
        return response.find(userService.findAllSimple(query));
    }

    @GetMapping("/{id}")
    public ResponseEntity<HttpOk> getUserSimpleById(
        HttpServletRequest request,
        @PathVariable Long id
    )
    {
        return response.find(userService.getUserSimpleById(id));
    }

    @PostMapping
    public ResponseEntity<HttpOk> createUser(@RequestBody User user) {
        UserSimpleDto newUser =  userService.createUser(user);
        return response.create(newUser.getId().toString(), newUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpOk> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return response.delete(id.toString());
    }

    @GetMapping("/me")
    public ResponseEntity<HttpOk> getCurrentUser(HttpServletRequest request){
        CustomUserDetails usuario = customUserDetailsService.getUserLogueado();
        User user = userService.getUserById(usuario.getId());
        return response.find(new UserDto(userService.cargarMenu(user)));
    }
}
