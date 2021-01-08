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

    @ManyToMany(mappedBy = "users_achievements")
    private List<User> users;

    public Achievement(String name, List<User> users) {
        this.name = name;
        this.users = users;
    }

    public Achievement(long id, List<User> users, String name) {
        this.id = id;
        this.name = name;
        this.users = users;
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
}