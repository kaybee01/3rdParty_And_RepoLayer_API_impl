package org.scaler.fakestore.Service;

import org.scaler.fakestore.Exception.BadRequestException;
import org.scaler.fakestore.Exception.ProductNotFound;
import org.scaler.fakestore.Models.Category;
import org.scaler.fakestore.Models.Product;
import org.scaler.fakestore.Repository.CategoryRepo;
import org.scaler.fakestore.Repository.CategoryRepo;
import org.scaler.fakestore.Repository.ProductRepo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Primary
@Service("SelfProductService")
public class SelfProductService implements ProductService{

    private ProductRepo productRepo;
    private CategoryRepo catgoryRepo;

    SelfProductService(ProductRepo productRepo ,CategoryRepo catgoryRepo){
        this.productRepo=productRepo;
        this.catgoryRepo=catgoryRepo;
    }
    @Override
    public Product getProductById(long id) throws ProductNotFound {
        Optional<Product> optionalProduct = productRepo.findById(id);

        if(optionalProduct.isEmpty()){
            throw new ProductNotFound("Product Not Found");

        }

        return optionalProduct.get();
    }

    @Override
    public List<Product> getAll() {
        List<Product> productList = productRepo.findAll();
        return productList;
    }

    @Override
    public Product updateProduct(long id, Product p) throws ProductNotFound {
        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isEmpty()){
            throw  new ProductNotFound("Product Not Found");
        }
        Product existingProduct = optionalProduct.get();
        existingProduct.setTitle(p.getTitle());
        existingProduct.setDescription(p.getDescription());
        existingProduct.setPrice(p.getPrice());
        existingProduct.setCategory(p.getCategory());
        existingProduct.setImage(p.getImage());

        Product updatedProduct = productRepo.save(existingProduct);
        return updatedProduct;
    }

    @Override
    public Product deleteProduct(long id, Product p) throws ProductNotFound {

        Optional<Product> optionalProduct = productRepo.findById(id);
        if (optionalProduct.isEmpty()){
            throw new ProductNotFound("Product Not Found");
        }

        Product deletePro = optionalProduct.get();
        productRepo.delete(deletePro);

        return deletePro;
    }

    @Override
    public Product createProduct(Product p) {
        Category category = p.getCategory();

        if (category.getId()==null){
            Category savedCategory = catgoryRepo.save(category);
            p.setCategory(savedCategory);
        }else {
            Optional<Category> categoryOptional = catgoryRepo.findById(category.getId());
            if (categoryOptional.isEmpty()){
                throw new  RuntimeException("Category is empty");
            }else {
                p.setCategory(categoryOptional.get());
            }
        }

        Product saveProduct = productRepo.save(p);
        return saveProduct;
    }
}
