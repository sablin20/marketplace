package marketplace.client;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ClientRepository extends CrudRepository<Client, String> {

    @Query("select '*' from Client where id = ?1")
    Optional<Client> find(String id);

}
