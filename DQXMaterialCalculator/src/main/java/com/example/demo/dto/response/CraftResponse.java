package com.example.demo.dto.response;

import java.util.List;

import com.example.demo.entity.Craft;

public class CraftResponse {
	private List<Craft> crafts;

	public List<Craft> getCrafs() {
		return crafts;
	}

	public void setCrafs(List<Craft> crafts) {
		this.crafts = crafts;
	}
	
}
