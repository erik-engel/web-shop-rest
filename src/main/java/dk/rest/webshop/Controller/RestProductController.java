package dk.rest.webshop.Controller;

import dk.rest.webshop.Repository.CategoryRepo;
import dk.rest.webshop.Repository.ProductRepo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestProductController {

    CategoryRepo categoryRepo;
    ProductRepo productRepo;

    public RestProductController(CategoryRepo categoryRepo, ProductRepo productRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
    }

    // HTTP Get List
}
