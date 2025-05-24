package com.primeng.primeng.services;

import com.primeng.primeng.models.Perfil;
import com.primeng.primeng.models.Permiso;
import com.primeng.primeng.models.User;
import com.primeng.primeng.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PerfilService perfilService;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User cargarPermisos(User user) {
        if (user.getPerfil() != null) {
           Set<Permiso> permisos = perfilService.getPermisosPorPerfil(user.getPerfil().getId());
           user.getPerfil().setPermisos(permisos);
        }
        return user;
    }

    public User cargarMenu(User user) {
        if (user.getPerfil() != null) {
            List<Permiso> menuEstructurado  = perfilService.getMenuEstructuradoPorPerfil(user.getPerfil().getId());
            user.getPerfil().setMenu(menuEstructurado);
        }
        return user;
    }
}
