package org.scaler.fakestore.Repository;

import org.scaler.fakestore.Models.Category;
import org.scaler.fakestore.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {

    @Override
    Optional<Product> findById(Long id);
    Optional<Product> findByTitle(String title);

    List<Product> findByTitleAndDescription(String title , String Description);
    Product save(Product product);

}
