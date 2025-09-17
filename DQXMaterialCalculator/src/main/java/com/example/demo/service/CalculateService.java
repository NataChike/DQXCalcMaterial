package com.example.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.response.CalculateResponse;
import com.example.demo.entity.Item;
import com.example.demo.entity.Material;
import com.example.demo.repository.ItemLoader;
import com.example.demo.repository.MaterialInfoLoader;

@Service
public class CalculateService {
	@Autowired
	ItemLoader itemLoader;
	@Autowired
	MaterialInfoLoader materialInfoLoader;

	//作成物一覧から必要素材を集計
	public Map<String, List<CalculateResponse>> calculateMaterials(List<String> selectedItems) {
		List<Item> items = itemLoader.getItems();
		Map<String, Integer> materialMap = new HashMap<>();

		for (String selectedName : selectedItems) {
			items.stream()
					.filter(i -> i.getName().equals(selectedName))
					.findFirst()
					.ifPresent(item -> {
						for (Material m : item.getMaterials()) {
							materialMap.merge(m.getName(), m.getNum(), Integer::sum);
						}
					});
		}
		// Map → DTOリストに変換
		List<CalculateResponse> calculateResponse = materialMap.entrySet().stream()
				.map(e -> new CalculateResponse(e.getKey(), e.getValue(),
						materialInfoLoader.findGenreByName(e.getKey())))
				.collect(Collectors.toList());
		
		return calculateResponse.stream().collect(Collectors.groupingBy(CalculateResponse::getGenre));
	}
}
