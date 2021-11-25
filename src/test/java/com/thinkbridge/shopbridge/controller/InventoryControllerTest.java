package com.thinkbridge.shopbridge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thinkbridge.shopbridge.exceptions.InvalidRequestException;
import com.thinkbridge.shopbridge.exceptions.ItemNotFoundException;
import com.thinkbridge.shopbridge.filters.ItemSpecification;
import com.thinkbridge.shopbridge.model.Item;
import com.thinkbridge.shopbridge.repository.InventoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    InventoryRepository repository;

    @Autowired
    ObjectMapper mapper;

    @Test
    public void getAllItemsTest() throws Exception {
        Item item1 = Item.builder()
                .id(1L)
                .name("Cookies")
                .description("Biscuits")
                .category("Snacks")
                .price(5)
                .quantity(10)
                .build();

        List<Item> items = new ArrayList<>(List.of(item1));

        Mockito.when(repository.findAll()).thenReturn(items);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/inventory/items")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Cookies")))
                .andExpect(jsonPath("$[0].description", is("Biscuits")))
                .andExpect(jsonPath("$[0].price", is(5)))
                .andExpect(jsonPath("$[0].quantity", is(10)))
        ;
    }

    @Test
    public void getItemByIdTest() throws Exception {

        Item item1 = Item.builder()
                .id(5L)
                .name("Nirma")
                .description("Washing Powder")
                .price(5)
                .quantity(10)
                .build();

        Mockito.when(repository.findById((long) 5)).thenReturn(Optional.of(item1));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/inventory/item/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Nirma")));
    }

    @Test
    public void addNewItemTest() throws Exception {
        Item inputItem = Item.builder()
                .name("Acer Aspire 5")
                .description("Laptop")
                .price(50000)
                .quantity(5)
                .build();

        Item outputItem = Item.builder()
                .id(56L)
                .name("Acer Aspire 5")
                .description("Laptop")
                .price(50000)
                .quantity(5)
                .build();

        Mockito.when(repository.save(inputItem)).thenReturn(outputItem);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/inventory/item/add")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(inputItem));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.name", is("Acer Aspire 5")));
    }

    @Test
    public void updateItem() throws Exception {

        Item existingItem = Item.builder()
                .id(56L)
                .name("Acer Aspire 5")
                .description("Laptop")
                .price(50000)
                .quantity(5)
                .build();

        Item toUpdateItem = Item.builder()
                .id(56L)
                .name("Acer Aspire 5")
                .description("Laptop")
                .price(50000)
                .quantity(3)
                .build();

        Mockito.when(repository.findById(56L)).thenReturn(Optional.of(existingItem));
        Mockito.when(repository.save(toUpdateItem)).thenReturn(toUpdateItem);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/inventory/item/update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(toUpdateItem));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.quantity", is(3)));
    }

    @Test
    public void updateItemById() throws Exception {
        Item existingItem = Item.builder()
                .id(56L)
                .name("Acer Aspire 5")
                .description("Laptop")
                .price(50000)
                .quantity(5)
                .build();

        Item toUpdateItem = Item.builder()
                .id(56L)
                .name("Acer Aspire 5")
                .description("Laptop")
                .price(50000)
                .quantity(4)
                .build();


        Mockito.when(repository.findById(56L)).thenReturn(Optional.of(existingItem));
        Mockito.when(repository.save(toUpdateItem)).thenReturn(toUpdateItem);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/inventory/item/update/56")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(toUpdateItem));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.quantity", is(4)));
    }

    @Test
    public void updateItemNullId() throws Exception {

        Item toUpdateItem = Item.builder()
                .name("Acer Aspire 5")
                .description("Laptop")
                .price(50000)
                .quantity(4)
                .build();

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/inventory/item/update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(toUpdateItem));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof InvalidRequestException))
                .andExpect(result -> Assertions.assertEquals("Item or ID must not be null.", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void updateItemWithNonExistingId() throws Exception {

        Item toUpdateItem = Item.builder()
                .id(74L)
                .name("Wheel")
                .description("Washing Soda")
                .price(10)
                .quantity(50)
                .build();

        Mockito.when(repository.findById(74L)).thenReturn(Optional.empty());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/inventory/item/update")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(toUpdateItem));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ItemNotFoundException))
                .andExpect(result -> Assertions.assertEquals("No item available with id = 74", Objects.requireNonNull(result.getResolvedException()).getMessage()));
    }

    @Test
    public void deleteItem() throws Exception {

        Mockito.when(repository.findById(12L)).thenReturn(Optional.of(Item.builder().build()));

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/inventory/item/delete/12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Delete Successful")));
    }

    @Test
    public void deleteNonExistingItem() throws Exception {

        Mockito.when(repository.findById(12L)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/inventory/item/delete/12")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> Assertions.assertTrue(result.getResolvedException() instanceof ItemNotFoundException));
    }

    @Test
    public void findByNameTest() throws Exception {

        Item item = Item.builder().id(1L).name("iPhone").price(10000).quantity(1).build();

        Mockito.when(repository.findAll(Mockito.any(ItemSpecification.class))).thenReturn(List.of(item));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/inventory/query?name=\"iPhone\"")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].name", is("iPhone")));
    }

    @Test
    public void findByCategoryTest() throws Exception {
        Item item = Item.builder().id(2L).name("Acer").category("Laptop").price(10000).quantity(6).build();

        Mockito.when(repository.findAll(Mockito.any(ItemSpecification.class))).thenReturn(List.of(item));

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/inventory/query?category=\"Laptop\"")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].category", is("Laptop")));
    }

    @Test
    public void findByCategoryAndMaxPriceTest() throws Exception {

        Mockito.when(repository.findAll(Mockito.any(ItemSpecification.class))).thenReturn(List.of());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/inventory/query?category=\"Laptop\"&maxPrice=\"7000\"")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}
