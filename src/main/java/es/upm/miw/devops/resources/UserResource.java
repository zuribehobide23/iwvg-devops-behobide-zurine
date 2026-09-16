package es.upm.miw.devops.resources;

import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.resources.dtos.UserDTO;
import es.upm.miw.devops.services.UserService;
import es.upm.miw.devops.services.criteria.UserFindCriteria;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserResource {

    public static final String USER = "/user";
    public static final String USERS = "/users";
    public static final String USER_ID = USER + "/{id}";
    public static final String USER_ACTIVE = USER_ID + "/active";

    private final UserService userService;

    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(USER_ID)
    public UserDTO getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return user == null ? null : new UserDTO(user);
    }

    @GetMapping(USERS)
    public List<UserDTO> find(@ModelAttribute UserFindCriteria criteria) {
        return userService.find(criteria)
                .map(UserDTO::new)
                .toList();
    }

    @DeleteMapping(USER_ID)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping(USER_ACTIVE)
    public void activateUser(@PathVariable Long id) {
        userService.activateUser(id);
    }

    @PutMapping(USER_ID)
    public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        return new UserDTO(userService.updateUser(id, userDTO));
    }

    @PatchMapping(USER)
    public void updateUsersActive(@RequestBody List<UserDTO> users) {
        userService.updateUsersActive(users);
    }
}