package com.primeng.primeng.repositories;

import com.primeng.primeng.models.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    @EntityGraph(attributePaths = "perfil")
    List<User> findAll();

    @Query("SELECT u FROM User u " +
            "JOIN FETCH u.perfil p " +
            "JOIN FETCH p.permisos " +
            "WHERE u.username = :username")
    Optional<User> findByUsernameWithPermisos(@Param("username") String username);
}
