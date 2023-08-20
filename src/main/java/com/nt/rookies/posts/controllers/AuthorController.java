package com.nt.rookies.posts.controllers;

import com.nt.rookies.posts.dtos.Author;
import com.nt.rookies.posts.services.AuthorService;
import java.util.List;
import java.util.Objects;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"authors"})
@CrossOrigin(
    origins = {"*"}
)
public class AuthorController {
    private final AuthorService service;

    public AuthorController(AuthorService service) {
        this.service = (AuthorService)Objects.requireNonNull(service);
    }

    @GetMapping
    public ResponseEntity<List<Author>> getAll() {
        return new ResponseEntity<List<Author>>(this.service.getAll(), HttpStatus.OK);
    }

    @GetMapping({"/{username}"})
    ResponseEntity<Author> getByUsername(@PathVariable String username) {
        return new ResponseEntity<Author>(this.service.getByUsername(username), HttpStatus.OK);
    }

    @PutMapping({"/{username}"})
    public ResponseEntity<Author> update(@RequestBody @Valid Author author, @PathVariable String username) {
        return new ResponseEntity<Author>(this.service.update(author, username), HttpStatus.OK);
    }

    @DeleteMapping({"/{username}"})
    public ResponseEntity<String> delete(@PathVariable String username) {
        this.service.delete(username);
        return new ResponseEntity<String>("Author deleted successfully", HttpStatus.OK);
    }
}