package io.github.willianlds.rest.controller;

import io.github.willianlds.entity.Client;
import io.github.willianlds.repository.Clients;
import io.swagger.annotations.Api;
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
@RequestMapping("/api/clients")
@Api("Api Clients")
public class ClientController {

    private Clients clients;

    public ClientController(Clients clients){
        this.clients = clients;
    }

    @GetMapping(value = "/{id}")
    @ApiOperation("Get details from a client")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Client found"),
            @ApiResponse(code = 404, message = "Client not found by ID informed"),
    })
    public Client getClientById(@PathVariable("id") Integer id) {
        return clients
                .findById(id)
                .orElseThrow(() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

    @PostMapping(value = "/save")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Save new client")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Client saved successfully"),
            @ApiResponse(code = 400, message = "Error in validation"),
    })
    public Client save(@RequestBody @Valid Client client){
        return clients.save(client);
    }

    @DeleteMapping(value = "/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete client")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Client deleted successfully"),
            @ApiResponse(code = 404, message = "Client not found"),
    })
    public void delete(@PathVariable("id") Integer id){
       clients.findById(id)
               .map(client -> {
                   clients.delete(client);
                   return client;
               }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

    @PutMapping(value = "/update/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Update client")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Client updated successfully"),
            @ApiResponse(code = 404, message = "Client not found"),
    })
    public void update(@PathVariable("id") Integer id, @RequestBody @Valid Client client){
        clients.findById(id).map(existingClient -> {
            client.setId(existingClient.getId());
            clients.save(client);
            return existingClient;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found"));
    }

    @GetMapping("/filterClient")
    @ApiOperation("Get clients by filter")
    @ApiResponse(code = 200, message = "Results found")
    public List<Client> find(Client filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);
        return clients.findAll(example);
    }

}
