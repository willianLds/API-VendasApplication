package io.github.willianlds.rest.controller;

import io.github.willianlds.entity.Product;
import io.github.willianlds.repository.Products;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/products")
public class ProductController {
    private Products products;

    public ProductController(Products products){
        this.products = products;
    }

    @GetMapping("/{id}")
    @ApiOperation("Show product")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Product found"),
            @ApiResponse(code = 404, message = "Product not found"),
    })
    public Product getProductById(@PathVariable("id") Integer id){
        return products
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Show product")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Product save sucessfully"),
            @ApiResponse(code = 400, message = "Error in validation"),
    })
    public Product setProduct(@RequestBody @Valid Product product){
        return products.save(product);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Update product")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Product updated sucessfully"),
            @ApiResponse(code = 404, message = "Product not found"),
    })
    public void updateProduct(@PathVariable("id") Integer id, @RequestBody @Valid Product product){
        products.findById(id)
                .map(productFound -> {
                    product.setId(productFound.getId());
                    products.save(product);
                    return productFound;
                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable("id") Integer id){
        products.findById(id)
                .map(productFound -> {
                    products.delete(productFound);
                    return productFound;
                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @GetMapping("/filterProduct")
    public List<Product> getFilterProduct(Product product){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(product,matcher);
        return products.findAll(example);
    }

}
