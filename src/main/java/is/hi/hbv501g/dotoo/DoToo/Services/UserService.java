package is.hi.hbv501g.dotoo.DoToo.Services;


import is.hi.hbv501g.dotoo.DoToo.Entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User save(User user);
    void delete(User user);
    List<User> findAll();
    Optional<User> findById(String username);
}
