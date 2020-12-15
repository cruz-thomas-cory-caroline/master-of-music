package masterofmusic.masterofmusic.models;


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

    @OneToOne
    private User owner;

    public Comment(long id, String body,  List<Post> posts, User owner) {
        this.id = id;
        this.body = body;
        this.posts = posts;
        this.owner = owner;
    }

    public Comment(String body,  List<Post> posts, User owner) {
        this.body = body;
        this.posts = posts;
        this.owner = owner;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
