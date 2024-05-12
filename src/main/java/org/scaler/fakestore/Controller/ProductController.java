package org.scaler.fakestore.Controller;

import org.scaler.fakestore.Exception.ProductNotFound;
import org.scaler.fakestore.Models.Product;
import org.scaler.fakestore.Service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService ps;

    public ProductController(@Qualifier("FakeStoreProductService") ProductService productService){
        this.ps = productService;
    }

    @GetMapping("/{id}")

    public ResponseEntity<Product> getProductById(@PathVariable("id") long id) throws ProductNotFound {

      //  try {
            return new ResponseEntity<>(ps.getProductById(id), HttpStatus.OK);
//        }catch (ArithmeticException e){
//            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//        }catch (Exception e){
//            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
    }

    @GetMapping("/")
    public List<Product> getAllProduct(){
        return ps.getAll();
    }

    @PostMapping("/")
    public Product createProduct(@RequestBody Product product){

        return ps.createProduct(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("id") Long id , @RequestBody Product product) throws ProductNotFound {

        return ps.updateProduct(id,product);
    }

    @DeleteMapping ("/{id}")
    public Product deleteProduct(@PathVariable ("id") Long id , @RequestBody Product product) throws ProductNotFound {

        return ps.deleteProduct(id,product);
    }
}
