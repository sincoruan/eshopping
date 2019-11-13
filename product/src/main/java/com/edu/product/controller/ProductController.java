package com.edu.product.controller;

import com.edu.product.domain.Product;
import com.edu.product.repository.ProductRepository;
import com.edu.product.vo.ResultVO;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ProductController {
    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductRepository productRepository;

    @GetMapping("/")
    public String test(){
        return "this is product module!";
    }

    @PostMapping("/addProduct")
    public ResultVO<Product> addProduct(@RequestBody Product product){
        ResultVO<Product> resultVO = new ResultVO<Product>();
        productRepository.save(product);
        resultVO.setCode(0);
        resultVO.setMsg("success");
        resultVO.setData(product);
        return resultVO;
    }
    @PostMapping("/deleteProduct")
    public long deleteProduct(@PathVariable Long id){
        productRepository.deleteById(id);
        return id;
    }
    @GetMapping("/getProductList")
    public List<Product> getProductList(){

        List<Product> productList = productRepository.findAll();
        return productList;
    }
    @PostMapping("/addProductCountById/{id}/{count}")
    public String addProductCountById( @PathVariable long id,@PathVariable int count){
        productRepository.addProductById(count,id);
        return "success";
    }

    @PostMapping("/sellProductCountById/{id}/{count}")
    public String sellProductCountById(@PathVariable long id, @PathVariable int count){
        Product product  = productRepository.findProductById(id);
        if(product.getCount()<count) {
            return "fail";
        }else{
            productRepository.minusProductById(count,id);
            return "success";
        }
    }
}
