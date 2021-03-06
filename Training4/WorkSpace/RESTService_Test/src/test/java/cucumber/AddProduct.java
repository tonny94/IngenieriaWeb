package cucumber;

import java.util.ArrayList;
import java.util.List;

import com.sever.demo.model.ProductModel;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class AddProduct {
	List<ProductModel> products = new ArrayList<ProductModel>();

    ProductModel product = new ProductModel();
    
    private ProductModel findProductPos(String code) {
	    for(ProductModel prod : products) {
	        if(prod.getCode().equals(code)) {
	            return prod;
	        }
	    }
	    return null;
	}

    @Given("^I have a product <Product>$")
    public void i_have_a_product_Product() throws Throwable {
        product.setCode("CODE001");
        product.setName("Lata de coca-cola");
        product.setDescription("Lata de coca-cola de 33cl");
        product.setPrice(51);
    }

    @When("^Save the product <Product>$")
    public void save_the_product_Product() throws Throwable {
        products.add(product);
    }

    @Then("^Returns <Product>$")
    public void returns_Product() throws Throwable {
        ProductModel product = this.findProductPos("CODE001");
        Assert.assertEquals(product, this.product);
    }
}
