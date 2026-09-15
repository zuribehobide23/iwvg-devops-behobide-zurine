package es.upm.miw.devops.services;

import es.upm.miw.devops.infrastructure.data.daos.UserRepository;
import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.resources.dtos.UserDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserDTO getUserById(Long id) {
        return repo.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    public List<UserDTO> getBillableUsers() {
        return repo.findAll().stream()
                .filter(User::isBillable)
                .map(this::toDTO)
                .toList();
    }

    private UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFirstName(),
                user.getFamilyName(),
                user.getEmail(),
                user.getIdentity(),
                user.getAddress(),
                user.getCity(),
                user.getProvince(),
                user.getPostalCode(),
                user.isBillable(),
                user.isActive()
        );
    }

    public void deleteUser(Long id) {
        repo.deleteById(id);
    }

    public void activateUser(Long id) {
        User user = repo.findById(id)
                .orElseThrow();

        user.setActive(true);
        repo.save(user);
    }
}