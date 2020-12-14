package models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 255)
    private String body;

    @ManyToMany(mappedBy = "comments")
    private List<Post> posts;

    public Comment(long id, String body,  List<Post> posts) {
        this.id = id;
        this.body = body;
        this.posts = posts;
    }

    public Comment(String body,  List<Post> posts) {
        this.body = body;
        this.posts = posts;
    }

    public Comment() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
