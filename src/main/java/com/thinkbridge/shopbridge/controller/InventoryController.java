package com.thinkbridge.shopbridge.controller;

import com.thinkbridge.shopbridge.exceptions.InvalidRequestException;
import com.thinkbridge.shopbridge.exceptions.ItemNotFoundException;
import com.thinkbridge.shopbridge.filters.ItemSpecification;
import com.thinkbridge.shopbridge.model.Item;
import com.thinkbridge.shopbridge.model.Message;
import com.thinkbridge.shopbridge.model.SearchCriteria;
import com.thinkbridge.shopbridge.repository.InventoryRepository;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequestMapping("/api/inventory")
@RestController
public class InventoryController {

    @Autowired
    private InventoryRepository repository;

    @GetMapping({"", "/items"})
    public Iterable<Item> getAllItems() {
        return repository.findAll();
    }

    @GetMapping("/item/{id}")
    public Item getItemById(@PathVariable long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException(id));
    }

    @PostMapping("/item/add")
    public Item addNewItem(@RequestBody Item item) {
        return repository.save(item);
    }

    @PutMapping("/item/update")
    public Item updateItem(@RequestBody Item item) {
        if(item == null || item.getId() == null) {
            throw new InvalidRequestException("Item or ID must not be null.");
        }

        Optional<Item> optionalItem = repository.findById(item.getId());
        if(optionalItem.isEmpty()) {
            throw new ItemNotFoundException(item.getId());
        }

        Item existingItem = optionalItem.get();
        existingItem.setName(item.getName());
        existingItem.setDescription(item.getDescription());
        existingItem.setPrice(item.getPrice());
        existingItem.setQuantity(item.getQuantity());

        return repository.save(existingItem);
    }

    @PutMapping("/item/update/{id}")
    public Item updateItem(@RequestBody Item item, @PathVariable long id) {

        if(id <= 0) {
            throw new InvalidRequestException("ID must not be null.");
        }

        Optional<Item> optionalItem = repository.findById(id);
        if(optionalItem.isEmpty()) {
            throw new ItemNotFoundException(id);
        }

        Item existingItem = optionalItem.get();
        existingItem.setName(item.getName());
        existingItem.setDescription(item.getDescription());
        existingItem.setPrice(item.getPrice());
        existingItem.setQuantity(item.getQuantity());

        return repository.save(existingItem);
    }

    @DeleteMapping("/item/delete/{id}")
    public Message deleteItem(@PathVariable long id) {
        if(repository.findById(id).isEmpty()) {
            throw new ItemNotFoundException(id);
        }

        try {
            repository.deleteById(id);
            return new Message("Delete Successful");
        }
        catch (Exception ex) {
            return new Message("Error in delete: " + ex.getMessage());
        }
    }

    @GetMapping(value = "/query", params = "name")
    public List<Item> findByName(@RequestParam String name) {
        SearchCriteria criteria = new SearchCriteria("name", ":", name);
        return repository.findAll(new ItemSpecification(criteria));
    }

    @GetMapping(value = "/query", params = "category")
    public List<Item> findByCategory(@RequestParam String category) {
        SearchCriteria criteria = new SearchCriteria("category", ":", category);
        return repository.findAll(new ItemSpecification(criteria));
    }

    @GetMapping(value = "/query", params = {"category", "maxPrice" })
    public List<Item> findByCategoryAndMaxPrice(@RequestParam String category, @RequestParam String maxPrice) {
        ItemSpecification i1 = new ItemSpecification(new SearchCriteria("category", ":", category));
        ItemSpecification i2 = new ItemSpecification(new SearchCriteria("price", "<", maxPrice));

        return repository.findAll(Specification.where(i1).and(i2));
    }

}
