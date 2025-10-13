package com.example.demo.dto.request;

import java.util.List;

//計算ボタンを押した際に作成物一覧をバックエンドへ
public class SelectedItemsRequest {
	private List<String> items;

	public List<String> getItems() {
		return items;
	}

	public void setItems(List<String> items) {
		this.items = items;
	}
}
