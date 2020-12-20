package dk.rest.webshop.Repository;

import dk.rest.webshop.Model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository<Product, Long> {
}
