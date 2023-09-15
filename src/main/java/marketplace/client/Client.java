package marketplace.client;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import marketplace.product.Product;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    private String id;

    private String name;

    private String last_name;

    @OneToMany
    List<Product> favorites;

    @OneToMany
    List<Product> history;
}