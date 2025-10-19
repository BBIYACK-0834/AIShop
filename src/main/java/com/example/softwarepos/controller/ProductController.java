package com.example.softwarepos.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.softwarepos.entity.ProductEntity;
import com.example.softwarepos.repository.ProductRepository;
import com.example.softwarepos.entity.ProductEntity;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductRepository productRepository;
    
    @GetMapping("/list") // 상품 확인
    public List<ProductEntity> getProducts() {
    return productRepository.findAll();
}


    @PostMapping("/add")
    public Map<String, Object> addProduct(@RequestBody ProductEntity productRequest) {
        Map<String, Object> result = new HashMap<>();

        try {

            ProductEntity savedProduct = productRepository.save(productRequest);

            result.put("success", true);
            result.put("message", "상품이 성공적으로 추가되었습니다.");
            result.put("product", savedProduct);
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "상품 추가 중 오류가 발생했습니다.");
        }

        return result;
    }
    @PutMapping("/update/{id}")
    public Map<String, Object> updateProduct(@PathVariable Long id,
        @RequestBody ProductEntity productRequest) {
        Map<String, Object> result = new HashMap<>();

        productRepository.findById(id).ifPresentOrElse(product -> {
            product.setProname(productRequest.getProname());
            product.setProprice(productRequest.getProprice());
            product.setProsub(productRequest.getProsub());
            product.setProintro(productRequest.getProintro());

            ProductEntity updated = productRepository.save(product);
            result.put("message", "상품이 수정되었습니다.");
            result.put("product", updated);
        }, () -> {
            
            result.put("message", "존재하지 않는 상품입니다.");
        });

        return result;
    }

    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deleteProduct(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();

        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
        
            result.put("message", "상품이 삭제되었습니다.");
        } else {
            
            result.put("message", "존재하지 않는 상품입니다.");
        }

        return result;
    }
    
    }