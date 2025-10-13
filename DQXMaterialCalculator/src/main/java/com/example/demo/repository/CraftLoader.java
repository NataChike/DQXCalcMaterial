package com.example.demo.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Craft;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class CraftLoader {
	private List<Craft> crafts = new ArrayList<>();

	@PostConstruct
	public void loadCrafts() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ClassPathResource resource = new ClassPathResource("data/crafts.json");
		crafts = mapper.readValue(resource.getInputStream(), new TypeReference<List<Craft>>() {
		});
	}

	public List<Craft> getCrafts() {
		return crafts;
	}

}
