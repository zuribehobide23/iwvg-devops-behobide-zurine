package es.upm.miw.devops.services;

import es.upm.miw.devops.infrastructure.data.daos.UserRepository;
import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.services.criteria.UserFindCriteria;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElse(null);
    }

    public Stream<User> find(UserFindCriteria criteria) {
        return userRepository.findAll().stream()
                .filter(user -> !criteria.hasActive() || user.isActive() == criteria.getActive())
                .filter(user -> !criteria.hasBillable() || user.isBillable() == criteria.getBillable());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow();

        user.setActive(true);
        userRepository.save(user);
    }
}