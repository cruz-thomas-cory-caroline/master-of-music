package masterofmusic.masterofmusic.repositories;

import masterofmusic.masterofmusic.models.Post;
import masterofmusic.masterofmusic.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("FROM Post p WHERE p.body LIKE %:search%")
    List<Post> findPostsByBodyContaining(@Param("search") String search);
    List<Post> findPostsByOwnerEquals(User user);
    Post findByTitle(String title);
}
