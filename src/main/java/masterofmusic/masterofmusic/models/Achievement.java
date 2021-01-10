package masterofmusic.masterofmusic.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "achievement")
public class Achievement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; //auto generated id

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String classString;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "users_achievements")
    private List<User> users;

    @ManyToOne
    @JoinColumn (name = "game_id")
    private Game game;

    public Achievement(String name) {
        this.name = name;
    }

    public Achievement(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Achievement() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public String getClassString() {
        return classString;
    }

    public void setClassString(String classString) {
        this.classString = classString;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}