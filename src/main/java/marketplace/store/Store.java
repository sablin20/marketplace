package marketplace.store;

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
public class Store {

    @Id
    private String id;
    private String name;
    @OneToMany
    private List<Product> product;
}