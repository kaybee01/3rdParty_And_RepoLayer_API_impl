package org.scaler.fakestore.Controller;

import org.scaler.fakestore.Common.AuthCommon;
import org.scaler.fakestore.Exception.ProductNotFound;
import org.scaler.fakestore.Models.Product;
import org.scaler.fakestore.Service.ProductService;
import org.scaler.fakestore.dto.Role;
import org.scaler.fakestore.dto.UserDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService ps;

    private AuthCommon authCommon;

    public ProductController(@Qualifier("FakeStoreProductService") ProductService productService,AuthCommon authCommon){
        this.ps = productService;
        this.authCommon=authCommon;
    }

    @GetMapping("/{id}")

    public ResponseEntity<Product> getProductById(@PathVariable("id") long id,@RequestHeader("Auth") String auth) throws ProductNotFound {

      //  try {
        UserDTO user = authCommon.validate(auth);
        if (user!=null) {
            for(Role r : user.getRoles()) {
                if(r.getRole()=="ADMIN") {
                    Product p = ps.getProductById(id);
                    return new ResponseEntity<>(p, HttpStatus.OK);
                }
            }
        }
//        }catch (ArithmeticException e){
//            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//        }catch (Exception e){
//            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
        return new ResponseEntity<>(null,HttpStatus.FORBIDDEN);
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
