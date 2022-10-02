package seolnavy.point.infra.user;

import org.springframework.data.jpa.repository.JpaRepository;
import seolnavy.point.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
