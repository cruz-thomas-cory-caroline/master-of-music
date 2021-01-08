package masterofmusic.masterofmusic.models;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, length = 50, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "reset_password_token")
    private String resetPasswordToken;

    @Column(nullable = false)
    private boolean isAdmin;

    @Column(nullable = true, columnDefinition = "varchar(255) default 'https://i.pinimg.com/originals/c3/e1/0a/c3e10aeb8ecc1f529d592146eb599ddf.jpg'")
    private String images;

    @Column()
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<PlayerGame> games;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_achievements",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "achievement_id")}
    )
    private List<Achievement> users_achievements;

    public User() {
    }

    public User(long id, String email, String username, String password, boolean isAdmin, String images, List<PlayerGame> games, String description) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.images = images;
        this.games = games;
        this.description = description;
    }

    public User(String email, String username, String password, boolean isAdmin, String images, List<PlayerGame> games, String description) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;
        this.images = images;
        this.games = games;
        this.description = description;
    }

    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        username = copy.username;
        password = copy.password;
        isAdmin = copy.isAdmin;
        images = copy.images;
        games = copy.games;
    }

    public <T> User(String name, List<T> asList) {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }


    public List<PlayerGame> getGames() {
        return games;
    }

    public void setGames(List<PlayerGame> games) {
        this.games = games;
    }

    public List<Achievement> getUsers_achievements() {
        return users_achievements;
    }

    public String getResetPasswordToken() {
        return resetPasswordToken;
    }

    public void setResetPasswordToken(String resetPasswordToken) {
        this.resetPasswordToken = resetPasswordToken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;

    public void setUsers_achievements(List<Achievement> users_achievements) {
        this.users_achievements = users_achievements;

    }
}
