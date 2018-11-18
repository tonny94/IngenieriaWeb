package com.example.training3.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.example.training3.models.Error;
import com.example.training3.models.Product;

@Controller
public class ProductList {
	@Autowired
    private RestTemplate restTemplate = new RestTemplate();

    @Value("${rest.server.url}")
    private String restServerUrl;
	
	@RequestMapping(value = {"/","/login"})
	public String login() {
		return "index";
	}
	
	@RequestMapping(value = {"/logout"})
	public String logout() {
		return "redirect:/login";
	}
	
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	@GetMapping("/list")
	public String list(Model model) {
		@SuppressWarnings("unchecked")
		List<Product> productos = restTemplate.getForObject(restServerUrl + "list",List.class);
        model.addAttribute("productos", productos);
		return "list";
	}
	
	@Secured({"ROLE_USER","ROLE_ADMIN"})
	@GetMapping("/show/{id}")
	public String show(@PathVariable String id, Model model) {
		Product prod = restTemplate.getForObject(restServerUrl + "show/" + id,Product.class);
		model.addAttribute("product",prod);
		return "resume";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/remove/{id}")
	public String delete(@PathVariable String id, Model model) {
		restTemplate.exchange(restServerUrl + "remove/"+id, HttpMethod.DELETE, new HttpEntity<>(id),String.class);
		return "redirect:/list";
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/addForm")
	public String addProd(@ModelAttribute Product product) {
		return "addForm";
	}
	
	@Secured({"ROLE_ADMIN"})
	@PostMapping(value = "/add")
	public String newProd(@ModelAttribute("product") Product product,BindingResult result, ModelMap model) {
		if(product != null) {
			model.addAttribute("code", product.getCode());
	        model.addAttribute("name", product.getName());
	        model.addAttribute("description", product.getDescription());
	        model.addAttribute("price", product.getPrice());
	        restTemplate.exchange(restServerUrl + "add", HttpMethod.POST, new HttpEntity<>(product),Product.class);
	        return "modify";
		} else {
			Error error = new Error("Error al Crear el Producto","El codigo introducido coincide con un producto existente","addForm","Volver al formulario");
			model.addAttribute("error",error);
			return "aviso";
		}
	}
	
	@Secured({"ROLE_ADMIN"})
	@GetMapping("/modifyForm/{id}")
	public String modifyProd(@PathVariable String id, Model model,@ModelAttribute Product product) {
		Product oldprod = restTemplate.getForObject(restServerUrl + "show/" + id,Product.class);
		model.addAttribute("oldprod",oldprod);
		return "modifyForm";
	}
	
	@Secured({"ROLE_ADMIN"})
	@PutMapping(value = "/modify")
	public String modifyProd(@ModelAttribute("product") Product product,BindingResult result, ModelMap model) {
		if(product != null) {
			model.addAttribute("code", product.getCode());
	        model.addAttribute("name", product.getName());
	        model.addAttribute("description", product.getDescription());
	        model.addAttribute("price", product.getPrice());
	        restTemplate.exchange(restServerUrl + "add", HttpMethod.PUT, new HttpEntity<>(product),Product.class);
	        return "resume";
		} else {
			Error error = new Error("Error al Crear el Producto","El codigo introducido coincide con un producto existente","addForm","Volver al formulario");
			model.addAttribute("error",error);
			return "aviso";
		}
	}
}