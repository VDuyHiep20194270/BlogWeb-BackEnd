package com.nt.rookies.posts.controllers;

import com.nt.rookies.posts.dtos.Post;
import com.nt.rookies.posts.services.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("posts")
@CrossOrigin(
	    origins = {"*"}
	)
public class PostController {
  private PostService service;
  public PostController(PostService service) {
    this.service = Objects.requireNonNull(service);
  }

  @GetMapping
  public ResponseEntity<List<Post>> getAll() {
    return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> getById(@PathVariable Integer id) {
    return new ResponseEntity<>(service.getId(id), HttpStatus.OK);
  }
  @GetMapping({"/top10posts"})
  public ResponseEntity<List<Post>> get10Newest() {
      return new ResponseEntity<List<Post>>(this.service.get10Newest(), HttpStatus.OK);
  }

  @GetMapping({"/title/{title}"})
  public ResponseEntity<List<Post>> getAllByTitle(@PathVariable String title) {
      return new ResponseEntity<List<Post>>(this.service.getAllByTitle(title), HttpStatus.OK);
  }

  @GetMapping({"/author/{username}"})
  public ResponseEntity<List<Post>> getByAuthor(@PathVariable String username) {
      return new ResponseEntity<List<Post>>(this.service.getByAuthor(username), HttpStatus.OK);
  }
  @PostMapping
  public ResponseEntity<Post> save(@RequestBody @Valid Post post) {
    return new ResponseEntity<>(service.save(post), HttpStatus.OK);
  }
  @PutMapping("/{id}")
  public ResponseEntity<Post> update(@RequestBody @Valid Post post, @PathVariable Integer id){
    return new ResponseEntity<>(service.update(post, id), HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<String> delete(@PathVariable Integer id){
    service.delete(id);
    return new ResponseEntity<>("Post deleted successfully", HttpStatus.OK);
  }

}
