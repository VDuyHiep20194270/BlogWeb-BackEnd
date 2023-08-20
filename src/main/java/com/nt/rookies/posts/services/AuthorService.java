package com.nt.rookies.posts.services;

import com.nt.rookies.posts.dtos.Author;
import com.nt.rookies.posts.entities.AuthorEntity;
import com.nt.rookies.posts.exceptions.BadRequestException;
import com.nt.rookies.posts.exceptions.NotFoundException;
import com.nt.rookies.posts.mappers.AuthorMapper;
import com.nt.rookies.posts.repositories.AuthorRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

@Service
public class AuthorService {
    private AuthorRepository repository;


    public AuthorService(AuthorRepository repository) {
        this.repository = repository;
    }

    public List<Author> getAll() {
        return AuthorMapper.toDtoList(this.repository.findAll());
    }

    public Author getByUsername(String username) {
        return AuthorMapper.toDto((AuthorEntity)this.repository.findByUsername(username).orElseThrow(() -> {
            return new NotFoundException("Author with username: " + username + " Not Found");
        }));
    }

    public Author save(Author author) {
        try {
            author.setPassword(author.getPassword());
            author.setCreatedAt(LocalDateTime.now());
            return AuthorMapper.toDto((AuthorEntity)this.repository.save(AuthorMapper.toEntity(author)));
        } catch (NullPointerException var3) {
            throw Objects.nonNull(var3.getMessage()) ? new BadRequestException(var3.getMessage()) : new BadRequestException(var3);
        }
    }

    public Author update(Author author, String username) {
        AuthorEntity authorEntity = (AuthorEntity)this.repository.findByUsername(username).orElseThrow(() -> {
            return new NotFoundException("Author with username: " + username + " Not Found");
        });
        authorEntity.setEmail(author.getEmail());
        authorEntity.setFirstName(author.getFirstName());
        authorEntity.setLastName(author.getLastName());
        authorEntity.setBirthDate(author.getBirthDate());

        try {
            return AuthorMapper.toDto((AuthorEntity)this.repository.save(authorEntity));
        } catch (NullPointerException var5) {
            throw Objects.nonNull(var5.getMessage()) ? new BadRequestException(var5.getMessage()) : new BadRequestException(var5);
        }
    }

    public void delete(String username) {
        AuthorEntity author = (AuthorEntity)this.repository.findByUsername(username).orElseThrow(() -> {
            return new NotFoundException("Author with username: " + username + " Not Found");
        });
        this.repository.delete(author);
    }
}