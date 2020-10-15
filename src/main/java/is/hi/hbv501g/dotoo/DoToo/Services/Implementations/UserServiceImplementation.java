package is.hi.hbv501g.dotoo.DoToo.Services.Implementations;

import is.hi.hbv501g.dotoo.DoToo.Entities.User;
import is.hi.hbv501g.dotoo.DoToo.Repositories.UserRepository;
import is.hi.hbv501g.dotoo.DoToo.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {

    UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {this.userRepository = userRepository;}

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(String username) {
        return userRepository.findById(username);
    }

    @Override
    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User login(User user) {
        User exists = findByUserName(user.username);
        if(exists != null){
            if(exists.password.equals(user.password)){
                return user;
            }
        }
        return null;
    }
}
