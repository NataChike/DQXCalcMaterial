package com.example.demo.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.example.demo.entity.UpdateLog;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;

@Component
public class UpdateLogLoader {

	private List<UpdateLog> updateLogs = new ArrayList<>();

	@PostConstruct
	public void loadUpdateLogs() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		ClassPathResource resource = new ClassPathResource("data/updates.json");
		updateLogs = mapper.readValue(resource.getInputStream(), new TypeReference<List<UpdateLog>>() {
		});
		// 日付の新しい順に並び替え（date は yyyy-MM-dd 形式のため文字列比較で降順ソート可能）
		updateLogs.sort(Comparator.comparing(UpdateLog::getDate).reversed());
	}

	public List<UpdateLog> getUpdateLogs() {
		return updateLogs;
	}

	public List<UpdateLog> getLatest(int count) {
		return updateLogs.stream().limit(count).toList();
	}
}
