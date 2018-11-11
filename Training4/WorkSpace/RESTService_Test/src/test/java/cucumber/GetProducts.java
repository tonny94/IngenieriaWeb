package cucumber;

import java.util.ArrayList;
import java.util.List;

import com.sever.demo.model.ProductModel;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import junit.framework.Assert;

public class GetProducts {
	List<ProductModel> products = new ArrayList<ProductModel>();

    ProductModel product = new ProductModel();
    ProductModel product2 = new ProductModel();
    ProductModel product3 = new ProductModel();
    
    private ProductModel findProductPos(String code) {
	    for(ProductModel prod : products) {
	        if(prod.getCode().equals(code)) {
	            return prod;
	        }
	    }
	    return null;
	}

    @Given("^I have a product <List<Product>>$")
    public void i_have_products_ListProduct() throws Throwable {
        product.setCode("CODE001");
        product.setName("Lata de coca-cola");
        product.setDescription("Lata de coca-cola de 33cl");
        product.setPrice(51);
        
        product2.setCode("CODE002");
        product2.setName("Lata de coca-cola");
        product2.setDescription("Lata de coca-cola de 33cl");
        product2.setPrice(51);
        
        product3.setCode("CODE003");
        product3.setName("Lata de coca-cola");
        product3.setDescription("Lata de coca-cola de 33cl");
        product3.setPrice(51);
        
        products.add(product);
        products.add(product2);
        products.add(product3);
    }

    @When("^Show the products <List<Product>>$")
    public void show_the_products_ListProduct() throws Throwable {
    	ProductModel product = this.findProductPos("CODE001");
    	String nombre = product.getName();
    	System.out.println(nombre);
    	
    	ProductModel product2 = this.findProductPos("CODE002");
    	String nombre2 = product2.getName();
    	System.out.println(nombre2);
    	
    	ProductModel product3 = this.findProductPos("CODE003");
    	String nombre3 = product3.getName();
    	System.out.println(nombre3);
    }

    @Then("^Returns <List<Product>>$")
    public void returns_ListProduct() throws Throwable {
        ProductModel product = this.findProductPos("CODE001");
        Assert.assertEquals(product, this.product);
        
        ProductModel product2 = this.findProductPos("CODE002");
        Assert.assertEquals(product, this.product);
        
        ProductModel product3 = this.findProductPos("CODE003");
        Assert.assertEquals(product, this.product);
    }
}
