package org.scaler.fakestore.Service;

import org.scaler.fakestore.Exception.ProductNotFound;
import org.scaler.fakestore.Models.Product;

import java.util.List;
import java.util.Map;

public interface ProductService {

   public Product getProductById(long id) throws ProductNotFound;
    public List<Product> getAll();
    Product updateProduct(long id,Product p) throws ProductNotFound;
    Product deleteProduct(long id , Product p) throws ProductNotFound;

    Product createProduct(Product p);

}
