package es.upm.miw.devops.services;

import es.upm.miw.devops.infrastructure.data.daos.UserRepository;
import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.resources.dtos.UserDTO;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void testGetUserById() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        User user = new User(
                "Zurine",
                "Behobide",
                "zurine@example.com",
                "12345678A",
                "Main Street 1",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(user));

        UserDTO dto = service.getUserById(1L);

        assertNotNull(dto);
        assertEquals("Zurine", dto.getFirstName());
        assertEquals("Behobide", dto.getFamilyName());
        assertTrue(dto.isBillable());
    }

    @Test
    void testGetUserByIdNotFound() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        Mockito.when(repo.findById(99L)).thenReturn(Optional.empty());

        UserDTO dto = service.getUserById(99L);

        assertNull(dto);
    }

    @Test
    void testGetBillableUsers() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        User billableUser = new User(
                "Zurine",
                "Behobide",
                "zurine@example.com",
                "12345678A",
                "Main Street 1",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        User nonBillableUser = new User(
                "Oihana",
                "Example",
                "oihana@example.com",
                "",
                "Main Street 2",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        Mockito.when(repo.findAll())
                .thenReturn(List.of(billableUser, nonBillableUser));

        List<UserDTO> users = service.getBillableUsers();

        assertEquals(1, users.size());
        assertEquals("Zurine", users.get(0).getFirstName());
        assertEquals("Behobide", users.get(0).getFamilyName());
        assertTrue(users.get(0).isBillable());
    }

    @Test
    void testGetBillableUsersWhenThereAreNoBillableUsers() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        User nonBillableUser = new User(
                "Oihana",
                "Example",
                "oihana@example.com",
                "",
                "Main Street 2",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        Mockito.when(repo.findAll())
                .thenReturn(List.of(nonBillableUser));

        List<UserDTO> users = service.getBillableUsers();

        assertTrue(users.isEmpty());
    }

    @Test
    void testUserIsBillableWhenAllFieldsHaveContent() {
        User user = new User(
                "Zurine",
                "Behobide",
                "zurine@example.com",
                "12345678A",
                "Main Street 1",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        assertTrue(user.isBillable());
    }

    @Test
    void testUserIsNotBillableWhenAFieldIsEmpty() {
        User user = new User(
                "Zurine",
                "Behobide",
                "zurine@example.com",
                "",
                "Main Street 1",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        assertFalse(user.isBillable());
    }

    @Test
    void testUserIsNotBillableWhenAFieldIsNull() {
        User user = new User(
                "Zurine",
                "Behobide",
                "zurine@example.com",
                null,
                "Main Street 1",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        assertFalse(user.isBillable());
    }

    @Test
    void testDeleteUser() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        service.deleteUser(1L);

        Mockito.verify(repo).deleteById(1L);
    }

    @Test
    void testActivateUser() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        User user = new User(
                "Test",
                "Active",
                "test.active@example.com",
                "99999999Z",
                "Test Street 1",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        user.setActive(false);

        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(user));

        service.activateUser(1L);

        assertTrue(user.isActive());
        Mockito.verify(repo).save(user);
    }

    @Test
    void testActivateUserWhenUserDoesNotExist() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        Mockito.when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.activateUser(99L)
        );

        Mockito.verify(repo, Mockito.never()).save(Mockito.any());
    }
}