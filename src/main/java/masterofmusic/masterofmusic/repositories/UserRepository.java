package masterofmusic.masterofmusic.repositories;




import masterofmusic.masterofmusic.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);


    @Query("SELECT c FROM User c WHERE c.email = ?1")
    public User findByEmail(String email);

    public User findByResetPasswordToken(String token);

    User findById(long id);

}

