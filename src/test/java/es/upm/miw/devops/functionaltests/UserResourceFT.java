package es.upm.miw.devops.functionaltests;

import es.upm.miw.devops.Application;
import es.upm.miw.devops.infrastructure.data.daos.UserRepository;
import es.upm.miw.devops.infrastructure.data.models.User;
import es.upm.miw.devops.resources.UserResource;
import es.upm.miw.devops.resources.dtos.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = Application.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
@Sql(
        scripts = "file:./init.sql",
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class UserResourceFT {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    private WebTestClient client;

    @BeforeEach
    void setup() {
        client = WebTestClient.bindToServer()
                .baseUrl("http://localhost:" + port)
                .build();
    }

    @Test
    void testGetUserById() {
        this.client.get()
                .uri(UserResource.USER_ID.replace("{id}", "1"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO.class)
                .value(user -> assertThat(user)
                        .extracting(
                                UserDTO::getId,
                                UserDTO::getFirstName,
                                UserDTO::getFamilyName,
                                UserDTO::isBillable,
                                UserDTO::isActive
                        )
                        .containsExactly(
                                1L,
                                "Zurine",
                                "Behobide",
                                true,
                                false
                        ));
    }

    @Test
    void testFindAll() {
        this.client.get()
                .uri(UserResource.USERS)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO[].class)
                .value(users -> assertThat(users)
                        .hasSize(3)
                        .extracting(UserDTO::getFirstName)
                        .containsExactlyInAnyOrder(
                                "Zurine",
                                "Oihana",
                                "Unax"
                        ));
    }

    @Test
    void testFindByBillable() {
        this.client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(UserResource.USERS)
                        .queryParam("billable", true)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO[].class)
                .value(users -> assertThat(users)
                        .singleElement()
                        .extracting(
                                UserDTO::getFirstName,
                                UserDTO::getFamilyName,
                                UserDTO::isBillable
                        )
                        .containsExactly(
                                "Zurine",
                                "Behobide",
                                true
                        ));
    }

    @Test
    void testFindByNotBillable() {
        this.client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(UserResource.USERS)
                        .queryParam("billable", false)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO[].class)
                .value(users -> assertThat(users)
                        .hasSize(2)
                        .extracting(UserDTO::getFirstName)
                        .containsExactlyInAnyOrder(
                                "Oihana",
                                "Unax"
                        ));
    }

    @Test
    void testFindByActive() {
        User user = new User(
                "Active",
                "User",
                "active@example.com",
                "11111111A",
                "Test Street 1",
                "Irun",
                "Gipuzkoa",
                "20300"
        );
        user.setActive(true);
        userRepository.save(user);

        this.client.get()
                .uri(uriBuilder -> uriBuilder
                        .path(UserResource.USERS)
                        .queryParam("active", true)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO[].class)
                .value(users -> assertThat(users)
                        .extracting(UserDTO::getFirstName)
                        .contains("Active"));
    }

    @Test
    void testDelete() {
        User user = new User(
                "Deleted",
                "User",
                "deleted@example.com",
                "22222222B",
                "Test Street 2",
                "Irun",
                "Gipuzkoa",
                "20300"
        );

        User savedUser = userRepository.save(user);

        this.client.delete()
                .uri("/user/" + savedUser.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

        assertThat(userRepository.findById(savedUser.getId())).isEmpty();
    }

    @Test
    void testActivate() {
        User user = new User(
                "Inactive",
                "User",
                "inactive@example.com",
                "",
                "Test Street 3",
                "Irun",
                "Gipuzkoa",
                "20300"
        );
        user.setActive(false);

        User savedUser = userRepository.save(user);

        this.client.put()
                .uri("/user/" + savedUser.getId() + "/active")
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

        this.client.get()
                .uri("/user/" + savedUser.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO.class)
                .value(result -> assertThat(result.isActive()).isTrue());
    }

    @Test
    void testUpdateUser() {
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

        this.client.put()
                .uri("/user/1")
                .bodyValue(userDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO.class)
                .value(result -> assertThat(result)
                        .extracting(
                                UserDTO::getId,
                                UserDTO::getFirstName,
                                UserDTO::getFamilyName,
                                UserDTO::getEmail,
                                UserDTO::getIdentity,
                                UserDTO::getAddress,
                                UserDTO::getCity,
                                UserDTO::getProvince,
                                UserDTO::getPostalCode
                        )
                        .containsExactly(
                                1L,
                                "Oihana",
                                "Example",
                                "oihana@example.com",
                                "87654321B",
                                "Main Street 2",
                                "Donostia",
                                "Gipuzkoa",
                                "20001"
                        ));
    }

    @Test
    void testUpdateUsersActive() {
        UserDTO user1 = new UserDTO();
        user1.setId(1L);
        user1.setActive(true);

        UserDTO user2 = new UserDTO();
        user2.setId(2L);
        user2.setActive(true);

        this.client.patch()
                .uri("/user")
                .bodyValue(List.of(user1, user2))
                .exchange()
                .expectStatus().isOk();

        this.client.get()
                .uri("/user/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO.class)
                .value(user -> assertThat(user.isActive()).isTrue());

        this.client.get()
                .uri("/user/2")
                .exchange()
                .expectStatus().isOk()
                .expectBody(UserDTO.class)
                .value(user -> assertThat(user.isActive()).isTrue());
    }
}