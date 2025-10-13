package com.example.demo.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.request.SelectedItemsRequest;
import com.example.demo.entity.Craft;
import com.example.demo.service.CraftService;
import com.example.demo.session.SelectedItemSession;

@RestController
@RequestMapping("/api/craft")
public class CraftApiController {
	@Autowired
	SelectedItemSession sessionBean;
	@Autowired
	CraftService craftService;
	
	@PostMapping("")
    public void craft(@RequestBody SelectedItemsRequest request) {
        sessionBean.setItems(request.getItems());
    }
	@GetMapping("/get")
	public List<Craft> getSelectedItemCrafts(){
		List<Craft> crafts = craftService.getCrafts(sessionBean.getItems());
		return crafts;
	}
}
