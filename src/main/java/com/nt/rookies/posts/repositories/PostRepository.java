package com.nt.rookies.posts.repositories;

import com.nt.rookies.posts.entities.PostEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Integer> {
    @Query("SELECT p FROM PostEntity p WHERE LOWER(p.title) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(p.description) LIKE LOWER(CONCAT('%', ?1, '%')) OR LOWER(p.author) LIKE LOWER(CONCAT('%', ?1, '%'))")
    Optional<List<PostEntity>> searchByKeyword(String keyword);

    @Query("SELECT p FROM PostEntity p WHERE p.author.username = ?1")
    List<PostEntity> findByAuthor(String username);
    
    List<PostEntity> findByTitleContainingIgnoreCase(String title);

    List<PostEntity> findTop10ByOrderByCreatedAtDesc();
}
