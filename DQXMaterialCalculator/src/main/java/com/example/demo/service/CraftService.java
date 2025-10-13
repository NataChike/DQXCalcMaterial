package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Craft;
import com.example.demo.repository.CraftLoader;

@Service
public class CraftService {
	@Autowired
	CraftLoader craftLoader;

	//選択されたアイテム名を引数にcraftsのnameと一致しているものを返す
	public List<Craft> getCrafts(List<String> selectedItems) {
		List<Craft> crafts = new ArrayList<>();
		Set<String> selectedItemSet = new HashSet<>(selectedItems);
		for (Craft craft : craftLoader.getCrafts()) {
			if (selectedItemSet.contains(craft.getName())) {
				crafts.add(craft);
			}
		}
		return crafts;
	}

}
