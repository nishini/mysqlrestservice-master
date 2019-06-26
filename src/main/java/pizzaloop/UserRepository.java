package pizzaloop;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserRepository extends CrudRepository<UserDetails, Integer> {

    List<UserDetails> findByEmail(String email);

    List<UserDetails> findByPassword(String password);

}
