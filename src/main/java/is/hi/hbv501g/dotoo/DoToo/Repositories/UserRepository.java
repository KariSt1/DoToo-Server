package is.hi.hbv501g.dotoo.DoToo.Repositories;

import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    User save(User user);
    void delete(User user);
    List<User> findAll();
    Optional<User> findById(String username);

}
