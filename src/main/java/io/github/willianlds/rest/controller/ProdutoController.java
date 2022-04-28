package io.github.willianlds.rest.controller;

import io.github.willianlds.entity.Produto;
import io.github.willianlds.repository.Produtos;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/produtos")
public class ProdutoController {
    private Produtos produtos;

    public ProdutoController(Produtos produtos){
        this.produtos = produtos;
    }

    @GetMapping("/{id}")
    public Produto getProdutoById(@PathVariable("id") Integer id){
        return produtos
                .findById(id)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Produto setProduto(@RequestBody @Valid Produto produto){
        return produtos.save(produto);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProduto(@PathVariable("id") Integer id, @RequestBody @Valid Produto produto){
        produtos.findById(id)
                .map(produtoEncontrado -> {
                    produto.setId(produtoEncontrado.getId());
                    produtos.save(produto);
                    return produtoEncontrado;
                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduto(@PathVariable("id") Integer id){
        produtos.findById(id)
                .map(produtoEncontrado -> {
                    produtos.delete(produtoEncontrado);
                    return produtoEncontrado;
                }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
    }

    @GetMapping("/filterProduto")
    public List<Produto> getFilterProduto(Produto produto){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example = Example.of(produto,matcher);
        return produtos.findAll(example);
    }

}
