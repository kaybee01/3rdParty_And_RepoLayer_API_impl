package org.scaler.fakestore.Service;

import org.scaler.fakestore.Exception.ProductNotFound;
import org.scaler.fakestore.Models.Category;
import org.scaler.fakestore.Models.Product;
import org.scaler.fakestore.dto.FakeStoreRequestDto;
import org.scaler.fakestore.dto.FakeStoreResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service("FakeStoreProductService")
public class FakeStoreProductService implements ProductService{

    private RestTemplate restTemplate;
    private RedisTemplate redisTemplate;


    FakeStoreProductService(RestTemplate restTemplate,RedisTemplate redisTemplate){
        this.restTemplate=restTemplate;
        this.redisTemplate=redisTemplate;
    }
    @Override
    public Product getProductById(long id) throws ProductNotFound {
        Product p = (Product) redisTemplate.opsForHash().get("PRODUCTS","PRODUCT_"+id);
        if(p!=null){
            return p;
        }
        FakeStoreResponseDto fdto = restTemplate.getForObject("https://fakestoreapi.com/products/"+id,FakeStoreResponseDto.class);

        if(fdto==null){
            throw new ProductNotFound("Prod NotFound");
        }

        p = ConvertFakeStoreDtoToProduct(fdto);
        redisTemplate.opsForHash().put("PRODUCT","PRODUCT_"+id,p);

        return p;
     }

    public Product ConvertFakeStoreDtoToProduct(FakeStoreResponseDto fdto){

        Product p = new Product();
        p.setId(fdto.getId());
        p.setDescription(fdto.getDesc());
        p.setTitle(fdto.getTitle());
        p.setPrice(fdto.getPrice());
        p.setImage(fdto.getImage());

        Category c = new Category();
        c.setTitle(fdto.getCategory());
        p.setCategory(c);

        return p;
    }



    @Override
    public List<Product> getAll() {
        FakeStoreResponseDto[] fdtoArray = restTemplate.getForObject("https://fakestoreapi.com/products",FakeStoreResponseDto[].class);

        if (fdtoArray == null || fdtoArray.length == 0) {
            return Collections.emptyList();
        }

        return ConvertFakeStoreDtoToProductList(fdtoArray);
    }

    public List<Product> ConvertFakeStoreDtoToProductList(FakeStoreResponseDto[] fdtoArray){
        List<Product> prodList = new ArrayList<>();
        for (FakeStoreResponseDto fdto : fdtoArray) {
            Product p = new Product();
            p.setId(fdto.getId());
            p.setDescription(fdto.getDesc());
            p.setTitle(fdto.getTitle());
            p.setPrice(fdto.getPrice());
            p.setImage(fdto.getImage());

            Category c = new Category();
            c.setTitle(fdto.getCategory());
            p.setCategory(c);

            prodList.add(p);
        }

        return prodList;
    }

    @Override
    public Product updateProduct(long id, Product p) {
        FakeStoreRequestDto requestDto = convertProductToFakeStoreRequestDto(p);
        try {
           restTemplate.put("https://fakestoreapi.com/products/" + id, requestDto);
        } catch (Exception e){
            System.out.println("Error occured");
        }



        return p;
    }

    @Override
    public Product deleteProduct(long id, Product p) throws ProductNotFound {

        FakeStoreRequestDto requestDto = convertProductToFakeStoreRequestDto(p);

//        Product p1 = getProductById(id);
//        if (p1 == null){
//            throw new ProductNotFound("Product Not Found");
//        }
        try {
         restTemplate.delete("https://fakestoreapi.com/products/" + id);
        } catch (Exception e) {
            System.out.println("Error");
        }

        return p;
    }

    @Override
    public Product createProduct(Product p) {
        FakeStoreRequestDto fakeStoreRequestDto = convertProductToFakeStoreRequestDto(p);

        FakeStoreResponseDto response = restTemplate.postForObject("https://fakestoreapi.com/products",fakeStoreRequestDto,FakeStoreResponseDto.class);
        if(response==null){
            throw  new RuntimeException("product is null");
        }
        return ConvertFakeStoreDtoToProduct(response);

    }

    public FakeStoreRequestDto convertProductToFakeStoreRequestDto(Product p){
        FakeStoreRequestDto requestDto = new FakeStoreRequestDto();
        requestDto.setTitle(p.getTitle());
        requestDto.setCategory(p.getCategory().getTitle());
        requestDto.setDesc(p.getDescription());
        requestDto.setImage(p.getImage());
        requestDto.setPrice(p.getPrice());

        return  requestDto;
    }
}
