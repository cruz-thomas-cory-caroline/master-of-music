package masterofmusic.masterofmusic.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @OneToOne
    private User owner;

    @ManyToMany(cascade = CascadeType.ALL) @JoinTable(
            name="posts_comments",
            joinColumns = {@JoinColumn(name="post_id")},
            inverseJoinColumns = {@JoinColumn(name="user_id")}
    )
    private List<Comment> comments;

    public Post() {
    }

    public Post(long id, String body, User owner, List<Comment> comments) {
        this.id = id;
        this.body = body;
        this.owner = owner;
        this.comments = comments;
    }

    public Post(String body, User owner, List<Comment> comments) {
        this.body = body;
        this.owner = owner;
        this.comments = comments;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Comment> getCommentList() {
        return comments;
    }

    public void setCommentList(List<Comment> commentList) {
        this.comments = commentList;
    }
}
