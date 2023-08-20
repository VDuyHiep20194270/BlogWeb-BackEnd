package com.nt.rookies.posts.repositories;

import com.nt.rookies.posts.entities.AuthorEntity;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<AuthorEntity, String> {
    Optional<AuthorEntity> findByUsername(String username);

    Optional<AuthorEntity> findByEmail(String email);
}
