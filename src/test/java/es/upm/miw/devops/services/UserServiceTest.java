package es.upm.miw.devops.services;

import es.upm.miw.devops.infrastructure.data.daos.UserRepository;
import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.resources.dtos.UserDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void testGetUserById() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        User user = new User("Zurine");
        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(user));

        UserDTO dto = service.getUserById(1L);

        assertNotNull(dto);
        assertEquals("Zurine", dto.getName());
    }
}
