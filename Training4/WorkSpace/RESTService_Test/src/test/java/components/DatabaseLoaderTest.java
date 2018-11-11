package components;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sever.demo.components.DatabaseLoader;
import com.sever.demo.model.ProductModel;
import com.sever.demo.repository.ProductRepository;

@RunWith(SpringJUnit4ClassRunner.class)
public class DatabaseLoaderTest {
	@Mock
    private ProductRepository productRepository;
    @InjectMocks
    private DatabaseLoader databaseLoader;

    @Before
    public void setUp(){
        databaseLoader = new DatabaseLoader(productRepository);
    }

    @Test
    public void checkLoader(){

        // mock
        when(productRepository.save((ProductModel) any(ProductModel.class))).thenReturn(new ProductModel());

        // then
        databaseLoader.initDatabase();

        // check
        verify(productRepository, times(2)).save((ProductModel) any(ProductModel.class));

    }
}