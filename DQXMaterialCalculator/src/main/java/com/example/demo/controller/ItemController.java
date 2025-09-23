package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Item;
import com.example.demo.service.ItemService;

@RestController
@RequestMapping("./api/items")
public class ItemController {
	@Autowired
	ItemService itemService;

	// サジェスト用に名前だけ返す
	@GetMapping("/names")
	public List<String> getItemNames() {
		return itemService.getItemNames();
	}

	// 全アイテム返す
	@GetMapping
	public List<Item> getAllItems() {
		return itemService.getItems();
	}

	@GetMapping("/by-job/{job}")
	public List<Item> getItemsByJob(@PathVariable String job) {
		return itemService.getItemByJob(job);
	}
}
