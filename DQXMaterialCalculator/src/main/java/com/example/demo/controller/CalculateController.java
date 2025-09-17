package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.CalculateRequest;
import com.example.demo.dto.response.CalculateResponse;
import com.example.demo.service.CalculateService;

@RestController
@RequestMapping("/api")
public class CalculateController {

	@Autowired
	private CalculateService calculateService;

	@PostMapping("/calculate")
	public Map<String, List<CalculateResponse>> calculate(@RequestBody CalculateRequest request) {
		return calculateService.calculateMaterials(request.getItems());
	}
}
