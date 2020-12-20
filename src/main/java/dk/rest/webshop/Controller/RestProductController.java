package dk.rest.webshop.Controller;

import dk.rest.webshop.Model.Category;
import dk.rest.webshop.Model.Product;
import dk.rest.webshop.Repository.CategoryRepo;
import dk.rest.webshop.Repository.ProductRepo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.Set;

@RestController
public class RestProductController {

    CategoryRepo categoryRepo;
    ProductRepo productRepo;

    public RestProductController(CategoryRepo categoryRepo, ProductRepo productRepo) {
        this.categoryRepo = categoryRepo;
        this.productRepo = productRepo;
    }

    // HTTP Get List

    @GetMapping("/product")
    public Iterable<Product> findAll(){
        System.out.println(productRepo.findAll());
        return productRepo.findAll();
    }

    // HTTP Get By ID
    @GetMapping("/product/{id}")
    public ResponseEntity<Optional<Product>> findById(@PathVariable Long id){
        Optional<Product> product = productRepo.findById(id);
        if (product.isPresent()){
            return ResponseEntity.status(200).body(product); //OK
        } else {
            return ResponseEntity.status(404).body(product); //Not Found
        }
    }

    // HTTP Post, ie. create
    @CrossOrigin(origins = "*", exposedHeaders = "Location")
    @PostMapping(value = "/product", consumes = {"application/json"})
    public ResponseEntity<String> create(@RequestBody Product p){
        Product product = new Product(p.getName(), p.getPrice(), p.getDescription());
        productRepo.save(product);

        Set<Category> categories = p.getCategories();
        for (Category category: categories) {
            Optional<Category> optCategory = categoryRepo.findById(category.getId());
            if (optCategory.isPresent()){
                Category cat = optCategory.get();
                cat.getProducts().add(product);
                categoryRepo.save(cat);
            } else {
                System.out.println("Ukendt category ID");
            }
        }
        product.setCategories(categories);
        productRepo.save(product);

        return ResponseEntity.status(201).header("Location", "/product/" + p.getId()).body("{'Msg': 'post created'}");
    }

    // HTTP PUT, ie. update
    @PutMapping("/product/{id}")
    public ResponseEntity<String> update(@PathVariable("id") Long id, @RequestBody Product p){
        //get recipeById
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (!optionalProduct.isPresent()){
            //Recipe id findes ikke
            return ResponseEntity.status(404).body("{'msg':'Not found'");
        }

        //opdater category, ingredient og notes sker automatisk - nu er relationen oprettet
        //save recipe
        productRepo.save(p);
        return ResponseEntity.status(204).body("{'msg':'Updated'}");
    }

    // HTTPDelete
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id){
        Optional<Product> product = productRepo.findById(id);
        //check at opskriften findes
        if(!product.isPresent()){
            return ResponseEntity.status(404).body("{'msg':'Not found'"); // Not found
        }

        Product p = product.get();
        //slet først referencerne til recipe i categories
        for (Category category: p.getCategories()){
            category.getProducts().remove(p);
        }
        //derefter kan categories slettes fra recipe
        p.setCategories(null);

        //og opdateres (nu uden category mappings)
        productRepo.save(p);

        //til sidst kan recipe slettes uden at bryde referentiel integritet
        productRepo.deleteById(id);

        return ResponseEntity.status(200).body("{'msg':'Deleted'}");
    }
}
