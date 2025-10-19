package com.example.demo.controller.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Craft;
import com.example.demo.service.CraftService;
import com.example.demo.session.SelectedItemSession;

@Controller("/")
public class ViewController {
	@Autowired
	SelectedItemSession sessionBean;
	@Autowired
	private CraftService craftService;

	@GetMapping("")
	public String index() {
		return "index";
	}

	@GetMapping("craft")
	public String craftCalculator(Model model) {
		List<Craft> crafts = craftService.getCrafts(sessionBean.getItems());
		model.addAttribute("crafts", crafts);
		return "craft";
	}

	@GetMapping("about")
	public String about() {
		return "about";
	}
}
