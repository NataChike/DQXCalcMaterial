package com.example.demo.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Item;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class ItemLoader {

    private List<Item> items = new ArrayList<>();

    @PostConstruct
    public void loadItems() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("data/items.json");
        items = mapper.readValue(resource.getInputStream(), new TypeReference<List<Item>>() {});
    }

    public List<Item> getItems() {
        return items;
    }
}
