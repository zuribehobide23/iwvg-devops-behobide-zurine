package es.upm.miw.devops.services;

import es.upm.miw.devops.infrastructure.data.daos.UserRepository;
import es.upm.miw.devops.infrastructure.data.models.Role;
import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.resources.dtos.UserDTO;
import es.upm.miw.devops.services.criteria.UserFindCriteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Stream;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public Stream<User> find(UserFindCriteria criteria) {
        return this.findByActiveAndMobile(criteria)
                .filter(user -> this.matchBillable(criteria, user));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public void activateUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.setActive(true);
        userRepository.save(user);
    }

    public User updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow();

        user.setFirstName(userDTO.getFirstName());
        user.setFamilyName(userDTO.getFamilyName());
        user.setEmail(userDTO.getEmail());
        user.setIdentity(userDTO.getIdentity());
        user.setAddress(userDTO.getAddress());
        user.setCity(userDTO.getCity());
        user.setProvince(userDTO.getProvince());
        user.setPostalCode(userDTO.getPostalCode());
        user.setMobile(userDTO.getMobile());

        return userRepository.save(user);
    }

    public void updateUsersActive(List<UserDTO> usersDTO) {
        usersDTO.forEach(userDTO -> {
            User user = userRepository.findById(userDTO.getId()).orElseThrow();

            if (user.getRole() != Role.ADMIN || userDTO.isActive()) {
                user.setActive(userDTO.isActive());
                userRepository.save(user);
            }
        });
    }

    private Stream<User> findByActiveAndMobile(UserFindCriteria criteria) {
        if (!criteria.hasActive() && !criteria.hasMobile()) {
            return this.userRepository.findAll().stream();
        }

        if (criteria.hasMobile() && criteria.hasActive()) {
            return this.userRepository
                    .findByMobileAndActive(criteria.getMobile(), criteria.getActive())
                    .stream();
        }

        if (criteria.hasMobile()) {
            return this.userRepository
                    .findByMobile(criteria.getMobile())
                    .stream();
        }

        return this.userRepository
                .findByActive(criteria.getActive())
                .stream();
    }

    private boolean matchBillable(UserFindCriteria criteria, User user) {
        return !criteria.hasBillable()
                || user.isBillable() == criteria.getBillable();
    }
}