package com.example.demo.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.demo.entity.MaterialInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class MaterialInfoLoader {

	private List<MaterialInfo> materialInfos = new ArrayList<>();

	@PostConstruct
	public void loadMaterialInfos() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ClassPathResource resource = new ClassPathResource("data/materials.json");
		materialInfos = mapper.readValue(resource.getInputStream(), new TypeReference<List<MaterialInfo>>() {
		});
	}

    public String findGenreByName(String name) {
        return materialInfos.stream()
                .filter(mi -> mi.getName().equals(name))
                .map(MaterialInfo::getGenre)
                .findFirst()
                .orElse("未分類");
    }
}
