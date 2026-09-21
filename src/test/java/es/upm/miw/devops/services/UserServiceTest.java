package es.upm.miw.devops.services;

import es.upm.miw.devops.infrastructure.data.daos.UserRepository;
import es.upm.miw.devops.infrastructure.data.models.Role;
import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.resources.dtos.UserDTO;
import es.upm.miw.devops.services.criteria.UserFindCriteria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserRepository repo;
    private UserService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(UserRepository.class);
        service = new UserService(repo);
    }

    @Test
    void testGetUserById() {
        User user = new User(
                "Zurine",
                "Behobide",
                "zurine@example.com",
                "12345678A",
                "Main Street 1",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000001"
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
        Mockito.when(repo.findById(99L)).thenReturn(Optional.empty());

        User result = service.getUserById(99L);

        assertNull(result);
    }

    @Test
    void testFindAllUsers() {
        User billableUser = new User(
                "Zurine",
                "Behobide",
                "zurine@example.com",
                "12345678A",
                "Main Street 1",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000001"
        );

        User nonBillableUser = new User(
                "Oihana",
                "Example",
                "oihana@example.com",
                "",
                "Main Street 2",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000002"
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
        User billableUser = new User(
                "Zurine",
                "Behobide",
                "zurine@example.com",
                "12345678A",
                "Main Street 1",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000001"
        );

        User nonBillableUser = new User(
                "Oihana",
                "Example",
                "oihana@example.com",
                "",
                "Main Street 2",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000002"
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
        User billableUser = new User(
                "Zurine",
                "Behobide",
                "zurine@example.com",
                "12345678A",
                "Main Street 1",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000001"
        );

        User nonBillableUser = new User(
                "Oihana",
                "Example",
                "oihana@example.com",
                "",
                "Main Street 2",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000002"
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
        User activeUser = new User(
                "Active",
                "User",
                "active@example.com",
                "11111111A",
                "Test Street 1",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000004"
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
                "20300",
                "600000005"
        );

        inactiveUser.setActive(false);

        Mockito.when(repo.findByActive(true))
                .thenReturn(List.of(activeUser));

        UserFindCriteria criteria = new UserFindCriteria();
        criteria.setActive(true);

        List<User> users = service.find(criteria).toList();

        assertEquals(1, users.size());
        assertEquals("Active", users.getFirst().getFirstName());
        assertTrue(users.getFirst().isActive());
    }

    @Test
    void testFindByInactive() {
        User activeUser = new User(
                "Active",
                "User",
                "active@example.com",
                "11111111A",
                "Test Street 1",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000004"
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
                "20300",
                "600000005"
        );

        inactiveUser.setActive(false);

        Mockito.when(repo.findByActive(false))
                .thenReturn(List.of(inactiveUser));

        UserFindCriteria criteria = new UserFindCriteria();
        criteria.setActive(false);

        List<User> users = service.find(criteria).toList();

        assertEquals(1, users.size());
        assertEquals("Inactive", users.getFirst().getFirstName());
        assertFalse(users.getFirst().isActive());
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
                "20300",
                "600000001"
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
                "20300",
                "600000001"
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
                "20300",
                "600000001"
        );

        assertFalse(user.isBillable());
    }

    @Test
    void testDeleteUser() {
        service.deleteUser(1L);

        Mockito.verify(repo).deleteById(1L);
    }

    @Test
    void testActivateUser() {
        User user = new User(
                "Test",
                "Active",
                "test.active@example.com",
                "99999999Z",
                "Test Street 1",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000006"
        );

        user.setActive(false);

        Mockito.when(repo.findById(1L)).thenReturn(Optional.of(user));

        service.activateUser(1L);

        assertTrue(user.isActive());
        Mockito.verify(repo).save(user);
    }

    @Test
    void testActivateUserWhenUserDoesNotExist() {
        Mockito.when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(
                NoSuchElementException.class,
                () -> service.activateUser(99L)
        );

        Mockito.verify(repo, Mockito.never()).save(Mockito.any());
    }

    @Test
    void testUpdateUser() {
        User user = new User(
                "Zurine",
                "Behobide",
                "zurine@example.com",
                "12345678A",
                "Main Street 1",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000001"
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
                false,
                "600000002"
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
        assertEquals("600000002", result.getMobile());

        Mockito.verify(repo).save(user);
    }

    @Test
    void testUpdateUserWhenUserDoesNotExist() {
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
                false,
                "600000002"
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
        User user1 = new User(
                "Zurine",
                "Behobide",
                "zurine@example.com",
                "12345678A",
                "Main Street 1",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000001"
        );

        User user2 = new User(
                "Oihana",
                "Example",
                "oihana@example.com",
                "",
                "Main Street 2",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000002"
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

        service.updateUsersActive(List.of(userDTO1, userDTO2));

        assertTrue(user1.isActive());
        assertTrue(user2.isActive());

        Mockito.verify(repo).save(user1);
        Mockito.verify(repo).save(user2);
    }

    @Test
    void testUpdateUsersActiveWhenUserDoesNotExist() {
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
        User admin = new User(
                "Admin",
                "User",
                "admin@example.com",
                "12345678A",
                "Admin Street",
                "Irun",
                "Gipuzkoa",
                "20300",
                Role.ADMIN,
                "600000007"
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

    @Test
    void testFindByMobile() {
        User user = new User(
                "Oihana",
                "Example",
                "oihana@example.com",
                "",
                "Main Street 2",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000002"
        );

        Mockito.when(repo.findByMobile("600000002"))
                .thenReturn(Optional.of(user));

        UserFindCriteria criteria = new UserFindCriteria();
        criteria.setMobile("600000002");

        List<User> users = service.find(criteria).toList();

        assertEquals(1, users.size());
        assertEquals("Oihana", users.getFirst().getFirstName());
        assertEquals("600000002", users.getFirst().getMobile());
    }

    @Test
    void testFindByMobileNotFound() {
        Mockito.when(repo.findByMobile("699999999"))
                .thenReturn(Optional.empty());

        UserFindCriteria criteria = new UserFindCriteria();
        criteria.setMobile("699999999");

        List<User> users = service.find(criteria).toList();

        assertTrue(users.isEmpty());
    }

    @Test
    void testFindByMobileAndActive() {
        User user = new User(
                "Oihana",
                "Example",
                "oihana@example.com",
                "",
                "Main Street 2",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000002"
        );

        user.setActive(true);

        Mockito.when(repo.findByMobileAndActive("600000002", true))
                .thenReturn(Optional.of(user));

        UserFindCriteria criteria = new UserFindCriteria(true, "600000002");

        List<User> users = service.find(criteria).toList();

        assertEquals(1, users.size());
        assertEquals("Oihana", users.getFirst().getFirstName());
        assertTrue(users.getFirst().isActive());
    }

    @Test
    void testFindByMobileAndBillable() {
        User user = new User(
                "Zurine",
                "Behobide",
                "zurine@example.com",
                "12345678A",
                "Main Street 1",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000001"
        );

        Mockito.when(repo.findByMobile("600000001"))
                .thenReturn(Optional.of(user));

        UserFindCriteria criteria = new UserFindCriteria();
        criteria.setMobile("600000001");
        criteria.setBillable(true);

        List<User> users = service.find(criteria).toList();

        assertEquals(1, users.size());
        assertEquals("Zurine", users.getFirst().getFirstName());
        assertTrue(users.getFirst().isBillable());
    }

    @Test
    void testFindByActiveMobileAndBillable() {
        User user = new User(
                "Zurine",
                "Behobide",
                "zurine@example.com",
                "12345678A",
                "Main Street 1",
                "Irun",
                "Gipuzkoa",
                "20300",
                "600000001"
        );

        user.setActive(false);

        Mockito.when(repo.findByMobileAndActive("600000001", false))
                .thenReturn(Optional.of(user));

        UserFindCriteria criteria = new UserFindCriteria(
                false,
                "600000001",
                true
        );

        List<User> users = service.find(criteria).toList();

        assertEquals(1, users.size());
        assertEquals("Zurine", users.getFirst().getFirstName());
        assertFalse(users.getFirst().isActive());
        assertTrue(users.getFirst().isBillable());
    }

}