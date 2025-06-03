package com.primeng.primeng.services;

import com.primeng.primeng.dto.UserSimpleDto;
import com.primeng.primeng.exceptions.NotFoundException;
import com.primeng.primeng.models.Permiso;
import com.primeng.primeng.models.User;
import com.primeng.primeng.models.db.Query;
import com.primeng.primeng.models.db.Result;
import com.primeng.primeng.repositories.DBRepository;
import com.primeng.primeng.repositories.UserRepository;
import com.primeng.primeng.util.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private DBRepository db;

    public Result<UserSimpleDto> findAllSimple(Query query){
        Result<User> result = db.findAll(User.class, query, false);
        List<UserSimpleDto> dtoList = result.getData().stream()
                .map(UserSimpleDto::new)
                .collect(Collectors.toList());
        return new Result<UserSimpleDto>(dtoList, result.getPagination());
    }

    public UserSimpleDto getUserSimpleById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(Type.USUARIO, id));
        return new UserSimpleDto(user);
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(Type.USUARIO, id));
        return user;
    }

    public UserSimpleDto createUser(User user) {
        return new UserSimpleDto(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User cargarMenu(User user) {
        if (user.getPerfil() != null) {
            List<Permiso> menuEstructurado  = perfilService.getMenuEstructuradoPorPerfil(user.getPerfil().getId());
            user.getPerfil().setMenu(menuEstructurado);
        }
        return user;
    }
}
