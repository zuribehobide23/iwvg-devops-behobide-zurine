package es.upm.miw.devops.services;

import es.upm.miw.devops.infrastructure.data.daos.UserRepository;
import es.upm.miw.devops.infrastructure.data.models.Role;
import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.resources.dtos.UserDTO;
import es.upm.miw.devops.services.criteria.UserFindCriteria;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Arrays;

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

        User result = service.getUserById(1L);

        assertNotNull(result);
        assertEquals("Zurine", result.getFirstName());
        assertEquals("Behobide", result.getFamilyName());
        assertTrue(result.isBillable());
    }

    @Test
    void testGetUserByIdNotFound() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        Mockito.when(repo.findById(99L)).thenReturn(Optional.empty());

        User result = service.getUserById(99L);

        assertNull(result);
    }

    @Test
    void testFindAllUsers() {
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

        UserFindCriteria criteria = new UserFindCriteria();

        List<User> users = service.find(criteria).toList();

        assertEquals(2, users.size());
        assertEquals("Zurine", users.get(0).getFirstName());
        assertEquals("Oihana", users.get(1).getFirstName());
    }

    @Test
    void testFindByBillable() {
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

        UserFindCriteria criteria = new UserFindCriteria();
        criteria.setBillable(true);

        List<User> users = service.find(criteria).toList();

        assertEquals(1, users.size());
        assertEquals("Zurine", users.get(0).getFirstName());
        assertTrue(users.get(0).isBillable());
    }

    @Test
    void testFindByNotBillable() {
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

        Mockito.when(repo.findAll()).thenReturn(List.of(billableUser, nonBillableUser));

        UserFindCriteria criteria = new UserFindCriteria();
        criteria.setBillable(false);

        List<User> users = service.find(criteria).toList();

        assertEquals(1, users.size());
        assertEquals("Oihana", users.get(0).getFirstName());
        assertFalse(users.get(0).isBillable());
    }

    @Test
    void testFindByActive() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        User activeUser = new User(
                "Active",
                "User",
                "active@example.com",
                "11111111A",
                "Test Street 1",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        activeUser.setActive(true);

        User inactiveUser = new User(
                "Inactive",
                "User",
                "inactive@example.com",
                "22222222B",
                "Test Street 2",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        inactiveUser.setActive(false);

        Mockito.when(repo.findAll())
                .thenReturn(List.of(activeUser, inactiveUser));

        UserFindCriteria criteria = new UserFindCriteria();
        criteria.setActive(true);

        List<User> users = service.find(criteria).toList();

        assertEquals(1, users.size());
        assertEquals("Active", users.get(0).getFirstName());
        assertTrue(users.get(0).isActive());
    }

    @Test
    void testFindByInactive() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        User activeUser = new User(
                "Active",
                "User",
                "active@example.com",
                "11111111A",
                "Test Street 1",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        activeUser.setActive(true);

        User inactiveUser = new User(
                "Inactive",
                "User",
                "inactive@example.com",
                "22222222B",
                "Test Street 2",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        inactiveUser.setActive(false);

        Mockito.when(repo.findAll()).thenReturn(List.of(activeUser, inactiveUser));

        UserFindCriteria criteria = new UserFindCriteria();
        criteria.setActive(false);

        List<User> users = service.find(criteria).toList();

        assertEquals(1, users.size());
        assertEquals("Inactive", users.get(0).getFirstName());
        assertFalse(users.get(0).isActive());
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

    @Test
    void testUpdateUser() {
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

        UserDTO userDTO = new UserDTO(
                1L,
                "Oihana",
                "Example",
                "oihana@example.com",
                "87654321B",
                "Main Street 2",
                "Donostia",
                "Gipuzkoa",
                "20001",
                true,
                false
        );

        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(repo.save(user)).thenReturn(user);

        User result = service.updateUser(1L, userDTO);

        assertEquals("Oihana", result.getFirstName());
        assertEquals("Example", result.getFamilyName());
        assertEquals("oihana@example.com", result.getEmail());
        assertEquals("87654321B", result.getIdentity());
        assertEquals("Main Street 2", result.getAddress());
        assertEquals("Donostia", result.getCity());
        assertEquals("Gipuzkoa", result.getProvince());
        assertEquals("20001", result.getPostalCode());

        Mockito.verify(repo).save(user);
    }

    @Test
    void testUpdateUserWhenUserDoesNotExist() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        UserDTO userDTO = new UserDTO(
                1L,
                "Oihana",
                "Example",
                "oihana@example.com",
                "87654321B",
                "Main Street 2",
                "Donostia",
                "Gipuzkoa",
                "20001",
                true,
                false
        );

        Mockito.when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.updateUser(99L, userDTO)
        );

        Mockito.verify(repo, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testUpdateUsersActive() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        User user1 = new User(
                "Zurine",
                "Behobide",
                "zurine@example.com",
                "12345678A",
                "Main Street 1",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        User user2 = new User(
                "Oihana",
                "Example",
                "oihana@example.com",
                "",
                "Main Street 2",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        user1.setActive(false);
        user2.setActive(false);

        UserDTO userDTO1 = new UserDTO();
        userDTO1.setId(1L);
        userDTO1.setActive(true);

        UserDTO userDTO2 = new UserDTO();
        userDTO2.setId(2L);
        userDTO2.setActive(true);

        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(user1));
        Mockito.when(repo.findById(2L)).thenReturn(Optional.of(user2));

        service.updateUsersActive(Arrays.asList(userDTO1, userDTO2));

        assertTrue(user1.isActive());
        assertTrue(user2.isActive());

        Mockito.verify(repo).save(user1);
        Mockito.verify(repo).save(user2);
    }

    @Test
    void testUpdateUsersActiveWhenUserDoesNotExist() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(99L);
        userDTO.setActive(true);

        Mockito.when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.updateUsersActive(List.of(userDTO))
        );

        Mockito.verify(repo, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testUpdateUsersActiveDoesNotDeactivateAdmin() {
        UserRepository repo = Mockito.mock(UserRepository.class);
        UserService service = new UserService(repo);

        User admin = new User(
                "Admin",
                "User",
                "admin@example.com",
                "12345678A",
                "Admin Street",
                "Irun",
                "Gipuzkoa",
                "20300",
                Role.ADMIN
        );

        admin.setActive(true);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setActive(false);

        Mockito.when(repo.findById(1L))
                .thenReturn(Optional.of(admin));

        service.updateUsersActive(List.of(userDTO));

        assertTrue(admin.isActive());

        Mockito.verify(repo, Mockito.never()).save(admin);
    }

}