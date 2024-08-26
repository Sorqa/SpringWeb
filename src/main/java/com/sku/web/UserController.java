package com.sku.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;



@Controller
@RequestMapping("/user")
@SessionAttributes("user")
public class UserController {
	
	 @Autowired
	 private UserDAO dao;
	   
	    @GetMapping("/login")
	    public String showLoginForm(Model m) {
	       m.addAttribute("user", new User());
	        return "user/login";
	    }
	    
	    @PostMapping("/login")
	    @ResponseBody
	    public Map<String,Boolean> login(@ModelAttribute("user") User user) {
	       Map<String,Boolean> map = new HashMap<>();

	        boolean ok = dao.login2(user);
	        
	        map.put("ok", ok);
	        return map;
	    }
	    
	    @GetMapping("/logout")
	    @ResponseBody
	    public Map<String,Boolean> logout(@SessionAttribute(name="user", required=false) User user,
	          SessionStatus status)
	    {
	       Map<String,Boolean> map = new HashMap<>();
	       if(user==null) {//로그인 실패
	          map.put("ok", false);
	       }else {  //로그아웃 성공
	          status.setComplete();  // 세션에 저장된 모든 데이터를 비움
	          map.put("ok", true);
	       }
	       return map;
	    }
	    
	    @GetMapping("/index")   /*    /user/index   */
	    public String index() {
	       return "index";
	    }
	    
	    @GetMapping("/addForm")   
	    public String addForm() {
	       return "user/addForm";
	    }
	    
	    @PostMapping("/addUser")
	    @ResponseBody
	    public Map<String,Boolean> add(User user)
		    {
		       Map<String,Boolean> map = new HashMap<>();		      

		        boolean added = dao.addUser(user);
		        
		        map.put("added", added);
		        return map;
		       
		    }
	    
	    @PostMapping("/checkDuplicate")
	    @ResponseBody
	    public Map<String,Boolean> check(User user)
	    {
	    	Map<String,Boolean> map = new HashMap<>();
	    	boolean result = dao.checkDuplicate(user);
	    	map.put("result", result);
			return map;
	    	
	    }
		    
	    @GetMapping("/list")
	    public String List(Model m) {
	    	java.util.List<User> list = dao.getList();
	    	m.addAttribute("list", list);
	    	return "user/list";
	    }
	    
	    @GetMapping("/update/{uid}/{pwd}")
	    @ResponseBody
	    public User update(@PathVariable("uid") String uid,
	    				@PathVariable("pwd") String pwd) {
	    	User u = new User(uid,pwd);
	    	boolean updated = dao.update(u);
	    	User u2 = dao.getUserById(uid);
	    	return u2;
	    }
	    
	    @GetMapping("/delete/{uid}")	    
	    public String delete(@PathVariable("uid") String uid) {
	    	
	    	boolean deleted = dao.deleteUser(uid);
	    	
	    	return "redirect:/user/list";
	    }
	    
	    @GetMapping("/json")
	    @ResponseBody
	    public User getJSON() {
	    	return new User("Steve","12345");
	    }
	    
}
/*
@Autowired
private UserDAO udao;
@GetMapping("/login")	//http://localhost/user/login
public String showLoginForm() {
	//model.addAttribute
	return "user/login";	//user디렉토리 밑에 로그인 user/login
}

@PostMapping("/login")
@ResponseBody
public Map<String,Boolean> login(@RequestParam("username") String username,
								@RequestParam("password") String password){
	User user = new User();
	user.setUsername(username);
	user.setPassword(password);
	Map<String,Boolean> map = new HashMap<>();
	
	boolean ok = false;
	if("smith".equals(user.getUsername()) &&
			"smithpwd".equals(user.getPassword()))	{
		ok = true;
	}
map.put("ok", ok);
return map;
}

@PostMapping("/login")
@ResponseBody
public Map<String,Boolean> login(User user){	//modelattribut(사용할려면 전에 model에 세션추가)없어도 가능함 model은 영역표시 근데 지금은 json으로 보내서 상관없음
	//서블릿 을 걸치기 때문에 세션 추출,request도 가능 session은 이용자가 어딜가든 따라다님
	
	Map<String,Boolean> map = new HashMap<>();
	
	boolean ok = false;
	
	if("smith".equals(user.getUsername()) &&
			"smithpwd".equals(user.getPassword()))	{
		ok = true;
		//session.setAttribute("user", user.getUsername());
	}
	UserDAO dao = new UserDAO();
	ok = dao.login(user);
map.put("ok", ok);
return map;
}

@PostMapping("/login")
@ResponseBody
public Map<String,Boolean> login(User user){	//modelattribut(사용할려면 전에 model에 세션추가)없어도 가능함 model은 영역표시 근데 지금은 json으로 보내서 상관없음
	//서블릿 을 걸치기 때문에 세션 추출,request도 가능 session은 이용자가 어딜가든 따라다님
	
	Map<String,Boolean> map = new HashMap<>();
	
	boolean ok = false;
	
	UserDAO dao = new UserDAO();
	ok = dao.login2(user);
map.put("ok", ok);
return map;
}

@GetMapping("/index")	/user/index
public String showLogoutForm() {
	
	return "index";	
}

@GetMapping("/logout")
@ResponseBody
public Map<String,Boolean> logout(@SessionAttribute(name="user", required=false) User user,//세션 없어도 오류 없고 있으면 user에 저장
		SessionStatus status)
{
	Map<String,Boolean> map = new HashMap<>();
	if(user==null) {
		//로그인이 안된 경우
		map.put("ok",false);
	}else {
		status.setComplete();	//세션에 저장된 모든 데이터를 비움
		//로그아웃 성공
		map.put("ok", true);
	}
	return map;
}*/