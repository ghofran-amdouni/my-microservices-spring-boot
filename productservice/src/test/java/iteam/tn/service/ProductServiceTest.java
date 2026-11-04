package iteam.tn.service;

import iteam.tn.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@DisplayName("TodoServiceImpl Unit Tests")
class ProductServiceTest {
    @Mock
    private  ProductRepository productRepository;//implement all the beans inside the ProductService Then i need to mock them(in my case i have only one injection
    @InjectMocks//it allows to inject automatically the beans inside the instance productService
    private ProductService productService;//this is what we want to test
    @Nested // with this annotation we wil be able to executed this class
    @DisplayName("Create Product Tests") //give this class a display name
    class createProductTests {  //it's a nested class to group the scénarios in an organized way /this nested class will not be executed unless we tell Junit that it's a nested class and it needs to be executed


    }




}