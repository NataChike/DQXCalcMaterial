package com.example.demo.controller.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.SelectedItemsRequest;
import com.example.demo.dto.response.CalculateResponse;
import com.example.demo.service.CalculateService;
import com.example.demo.session.SelectedItemSession;

@RestController
@RequestMapping("/api/calculate")
public class CalculateApiController {

	@Autowired
	private CalculateService calculateService;
	@Autowired
	private SelectedItemSession sessionBean;
	
	@PostMapping("")
	public Map<String, List<CalculateResponse>> calculate(@RequestBody SelectedItemsRequest request) {
		sessionBean.setItems(request.getItems());
		return calculateService.calculateMaterials(sessionBean.getItems());
	}
}
