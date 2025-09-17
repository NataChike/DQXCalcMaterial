package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Item;
import com.example.demo.repository.ItemLoader;

@Service
public class ItemService {
	@Autowired
	ItemLoader itemLoader;

	// サジェスト用に名前だけ返すメソッドも追加できる
	public List<String> getItemNames() {
		List<Item> items = itemLoader.getItems();
		return items.stream().map(Item::getName).toList();
	}

	public List<Item> getItemByJob(String job) {
		return itemLoader.getItems().stream()
				.filter(i -> i.getJob().equals(job))
				.toList();
	}

	public List<Item> getItems() {
		return itemLoader.getItems();
	}
}
