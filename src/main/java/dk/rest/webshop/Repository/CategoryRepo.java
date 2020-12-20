package dk.rest.webshop.Repository;

import dk.rest.webshop.Model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepo extends CrudRepository<Category, Long> {
}
