package marketplace.store;

import lombok.RequiredArgsConstructor;
import marketplace.product.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StoreRepositorySqlImpl implements StoreRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Store create(Store store) {
        jdbcTemplate.update("INSERT INTO Store VALUES (?)",
                                store.getName());
        return jdbcTemplate.queryForObject("SELECT * FROM Store WHERE name = ?", Store.class, store.getName());
    }

    @Override
    public Store findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Store WHERE id = ?", Store.class, id);
    }

    @Override
    public void addProductForSale(String storeId, String productId) {
        jdbcTemplate.update("INSERT INTO ProductInStock VALUES (?,?)", storeId, productId);
    }

    @Override
    public void deleteProductSales(String storeId, String productId) {
        jdbcTemplate.update("DELETE FROM ProductInStock WHERE storeId = ? AND productId = ?", storeId, productId);
    }

    @Override
    public void updateProducts(String storeId, Product product) {
        // сначала удаляем из списка на продажу продукт который хотели обновить
        jdbcTemplate.update("DELETE FROM ProductInStock WHERE storeId = ? AND productId = ?", storeId, product.getId());

        // потом добавляем в таблицу продуктов новый продукт на основе тех данных которые передали
        jdbcTemplate.update("INSERT INTO Product VALUES (?,?,?,?,?)",
                product.getName(), product.getPrice(), product.getCategory(), product.getBrand(), product.getAmount());

        // потом в список на продажу добавляем новую запись с новым товаром, вместо старого
        jdbcTemplate.update("INSERT INTO ProductInStock VALUES (?,?)", storeId, product.getId());

        // сделал так, для того чтобы таблица продуктов оставалась общей чтобы
        // товар в избранном у клиента оставался вне зависимости обновления продавцом
    }
}