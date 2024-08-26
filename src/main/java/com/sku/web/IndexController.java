package com.sku.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class IndexController 
{
	@GetMapping("")/*	http://localhost/ */
	public String index() {
		return "index";
	}
	//http://localhost/gugu?dan=3
	@GetMapping("gugu")
	public String gugu(@RequestParam("dan") int dan, Model model) {	
		
		String str = "";
		List<String> list = new ArrayList<>();
		for(int i =1; i<=9;i++) {
			str = dan + " * " + i + " = " + dan*i + "\n";
			list.add(str);
		}
		//System.out.println(str);
		model.addAttribute("list",list);
		return "gugu";	//gugu.jsp
	}
	
	//http://localhost/gugu/5
		@GetMapping("gugu/{dan}")
		public String gugu_b(@PathVariable int dan, Model model) {	//경로의 dan과 같아야한다
						
		List<String> list = new ArrayList<>();
			for(int i =1; i<=9;i++) {				
				list.add(dan + " * " + i + " = " + dan*i);
			}
				//System.out.println(str);
			model.addAttribute("list",list);
			return "gugu";	//gugu.jsp
		}
		
		//http://localhost/gugu/5
				@GetMapping({"gugu/","gugu/{dan}"})
				public String gugu_c(@PathVariable("dan") Optional <Integer> opt, Model model) {	//Optional:값이 있을수도 없을수도
				if(opt.isEmpty()) {
					//파라미터 없는 경우
				}else {
					//파라미터 있는 경우
				
					//opt.isPresent();
					int dan = opt.get();
				}
				/*List<String> list = new ArrayList<>();
					for(int i =1; i<=9;i++) {				
						list.add(dan + " * " + i + " = " + dan*i);
					}
						//System.out.println(str);
					model.addAttribute("list",list);*/
					return "gugu";	//gugu.jsp
				}
				
		@GetMapping("login/{uid}/{pwd}")
		public String login(@PathVariable("uid") String uid,
							@PathVariable("pwd") String pwd)
		{
			return null;
		}
		/*
		//map
		@GetMapping("login/{uid}/{pwd}")
		public String login(@PathVariable Map<String,String>map)
		{
			return null;
		}
		*/
		@GetMapping("getjson")
		@ResponseBody				//이게 응답 자체야(바디)
		public Map<String,String> jsonTest()
		{
			Map<String,String> map = new HashMap<>();
			map.put("uid","smith");
			map.put("email", "smith@gamil.com");
			map.put("phone", "010-2345-8952");
			return map;
		}
		@GetMapping("add")
		public String addForm() {
			return "sum";
		}
		@PostMapping("add")
		@ResponseBody
	    public Map<String,Integer> add(@RequestParam("a") int a,@RequestParam("b") int b ) {
	       Map<String,Integer> map = new HashMap<>();
	       map.put("ans", a+b);
	       return map; 
	    }
}
