package es.upm.miw.devops.services;

import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.infrastructure.data.daos.UserRepository;
import es.upm.miw.devops.resources.dtos.UserDTO;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public UserDTO getUserById(Long id) {
        return repo.findById(id)
                .map(u -> new UserDTO(u.getId(), u.getName()))
                .orElse(null);
    }
}

