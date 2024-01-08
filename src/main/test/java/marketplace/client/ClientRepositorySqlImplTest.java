package marketplace.client;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ClientRepositorySqlImplTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @Mock
    ClientRepository repository;

    @InjectMocks
    ClientRepositorySqlImpl repositorySql;

    @Test
    void create() {

    }

    @Test
    void findById() {

    }

    @Test
    void removeById() {
    }
}