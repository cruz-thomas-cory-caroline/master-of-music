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

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    private User owner;

    public Comment(long id, String body,  Post post, User owner) {
        this.id = id;
        this.body = body;
        this.post = post;
        this.owner = owner;
    }

    public Comment(String body, Post post, User owner) {
        this.body = body;
        this.post = post;
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", post=" + post +
                ", owner=" + owner +
                '}';
    }
}
