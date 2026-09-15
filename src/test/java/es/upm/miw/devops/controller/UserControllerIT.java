package es.upm.miw.devops.controller;

import es.upm.miw.devops.Application;
import es.upm.miw.devops.infrastructure.data.daos.UserRepository;
import es.upm.miw.devops.infrastructure.data.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testGetUserById() throws Exception {
        mockMvc.perform(get("/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.firstName").value("Zurine"))
                .andExpect(jsonPath("$.familyName").value("Behobide"))
                .andExpect(jsonPath("$.billable").value(true));
    }

    @Test
    void testGetBillableUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].firstName").value("Zurine"))
                .andExpect(jsonPath("$[0].familyName").value("Behobide"))
                .andExpect(jsonPath("$[0].billable").value(true));
    }

    @Test
    void testDeleteUser() throws Exception {
        User user = new User(
                "Test",
                "Delete",
                "test.delete@example.com",
                "99999999Z",
                "Test Street 1",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        User savedUser = userRepository.save(user);

        mockMvc.perform(delete("/user/" + savedUser.getId()))
                .andExpect(status().isOk());
    }

    @Test
    void testActivateUser() throws Exception {
        User user = new User(
                "Test",
                "Active",
                "test.active@example.com",
                "",
                "Test Street 1",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        user.setActive(false);

        User savedUser = userRepository.save(user);

        mockMvc.perform(put("/user/" + savedUser.getId() + "/active"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/user/" + savedUser.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.active").value(true));
    }
}