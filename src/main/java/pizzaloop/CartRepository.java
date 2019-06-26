package pizzaloop;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CartRepository extends CrudRepository<CartDetails, Integer> {

    List<CartDetails> deleteByCartId(Integer id);

    @Override
    void deleteAll();

}