package com.nt.rookies.posts.services;

import com.nt.rookies.posts.dtos.Post;
import com.nt.rookies.posts.entities.PostEntity;
import com.nt.rookies.posts.exceptions.BadRequestException;
import com.nt.rookies.posts.exceptions.NotFoundException;
import com.nt.rookies.posts.mappers.AuthorMapper;
import com.nt.rookies.posts.mappers.PostMapper;
import com.nt.rookies.posts.repositories.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PostService {
  private PostRepository repository;

  public PostService(PostRepository repository) {
    this.repository = Objects.requireNonNull(repository);
  }

  public List<Post> getAll() {
    return PostMapper.toDtoList(this.repository.findAll());
  }

  public Post getId(Integer id) {
    return PostMapper.toDto( repository.findById(id).orElseThrow(() -> new NotFoundException("Post Id : " + id + " Not Found")));
  }
  public List<Post> getAllByTitle(String title) {
      return PostMapper.toDtoList(this.repository.findByTitleContainingIgnoreCase(title));
  }

  public List<Post> get10Newest() {
      return PostMapper.toDtoList(this.repository.findTop10ByOrderByCreatedAtDesc());
  }
  public List<Post> getByAuthor(String username) {
      return PostMapper.toDtoList(this.repository.findByAuthor(username));
  }

  public Post save(Post post) {
    try {
      return PostMapper.toDto( repository.save(PostMapper.toEntity(post)));
    } catch (NullPointerException e) {
      throw Objects.nonNull(e.getMessage()) ? new BadRequestException(e.getMessage()) : new BadRequestException(e);
    }
  }
  public List<Post> search(String keyword) {
      return PostMapper.toDtoList((List<PostEntity>)this.repository.searchByKeyword(keyword).orElseThrow(() -> {
          return new NotFoundException("Posts by keyword : " + keyword + " Not Found");
      }));
  }

  public Post update(Post post, Integer id) {
      PostEntity postEntity = (PostEntity)this.repository.findById(id).orElseThrow(() -> {
          return new NotFoundException("Post ID: " + id + " Not Found");
      });
      postEntity.setAuthor(AuthorMapper.toEntity(post.getAuthor()));
      postEntity.setTitle(post.getTitle());
      postEntity.setDescription(post.getDescription());
      postEntity.setContent(post.getContent());

      try {
          return PostMapper.toDto((PostEntity)this.repository.save(postEntity));
      } catch (NullPointerException var5) {
          throw Objects.nonNull(var5.getMessage()) ? new BadRequestException(var5.getMessage()) : new BadRequestException(var5);
      }
  }

  public void delete(Integer id) {
      PostEntity post = (PostEntity)this.repository.findById(id).orElseThrow(() -> {
          return new NotFoundException("Post ID: " + id + " Not Found");
      });
      this.repository.delete(post);
  }

}
