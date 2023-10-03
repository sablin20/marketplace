package marketplace.store;

import lombok.RequiredArgsConstructor;
import marketplace.product.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StoreRepositorySqlImpl implements StoreRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Store create(Store store) {
        jdbcTemplate.update("INSERT INTO Store VALUES (?,?)",
                                store.getId(),
                                store.getName());

        return jdbcTemplate.queryForObject("SELECT * FROM Store WHERE id = ?",
                new BeanPropertyRowMapper<>(Store.class), store.getId());
    }

    @Override
    public Store findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM Store WHERE id = ?",
                new BeanPropertyRowMapper<>(Store.class), id);
    }

    @Override
    public void addProductForSale(Integer id, Integer storeId, Integer productId) {
        jdbcTemplate.update("INSERT INTO ProductInStock VALUES (?,?,?)",
                id,
                storeId,
                productId);
    }

    @Override
    public void deleteProductSales(Integer storeId, Integer productId) {
        jdbcTemplate.update("DELETE FROM ProductInStock WHERE storeId = ? AND productId = ?", storeId, productId);
    }

    @Override
    public void updateProducts(Integer id, Integer storeId, Product product) {
        // сначала удаляем из списка на продажу продукт который хотели обновить
        jdbcTemplate.update("DELETE FROM ProductInStock WHERE storeId = ? AND productId = ?", storeId, product.getId());

        // потом добавляем в таблицу продуктов новый продукт на основе тех данных которые передали
        jdbcTemplate.update("INSERT INTO Product VALUES (?,?,?,?,?,?)",
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getCategory(),
                product.getBrand(),
                product.getAmount());

        // потом в список на продажу добавляем новую запись с новым товаром, вместо старого
        jdbcTemplate.update("INSERT INTO ProductInStock VALUES (?,?,?)", id, storeId, product.getId());

        // сделал так, для того чтобы таблица продуктов оставалась общей чтобы
        // товар в избранном у клиента оставался вне зависимости обновления продавцом
    }
}