package com.example.demo.dto.response;

public class CalculateResponse {
    private String name;
    private int num;
    private String genre;

    public CalculateResponse(String name, int num, String genre) {
        this.name = name;
        this.num = num;
        this.genre = genre;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

}

