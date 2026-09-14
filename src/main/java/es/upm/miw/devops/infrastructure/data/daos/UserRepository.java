package es.upm.miw.devops.infrastructure.data.daos;

import es.upm.miw.devops.infrastructure.data.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
