package com.test.sku.pet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class PetService 
{
	
		private HttpServletRequest request;
		private HttpServletResponse response;
		
		public PetService() {}
		
		public PetService(HttpServletRequest request, HttpServletResponse response) {
			this.request = request;
			this.response = response;
		}
		
		public String process() 
		{
			String cmd = request.getParameter("cmd");
			String viewPath = null;
			
			if(cmd==null) {
		         return "/pet/pet_index.jsp";
		      }else if(cmd.equals("addForm")) {
		    	  viewPath = "/pet/petInput.jsp";
		      }else if(cmd.equals("add")) {
		    	  String name = request.getParameter("name");
		    	  String origin = request.getParameter("origin");
		    	  float weight = Float.parseFloat(request.getParameter("weight"));
		    	  String sbirth = request.getParameter("birth");
		    	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			      java.sql.Date birth = null;
			      try {
			         birth = new java.sql.Date(sdf.parse(sbirth).getTime());
			      }catch(Exception e) {
			         e.printStackTrace();
			      }
			      int price = Integer.parseInt(request.getParameter("price"));
			      String pic = request.getParameter("pic");
			      
			      PetDAO  dao = new PetDAO();
			      boolean saved = dao.add(new PetVO(name,origin,weight,birth,price,pic));
			      
			      sendJSON("saved",saved+"");

		      }else if(cmd.equals("list")) {
		    	  PetDAO dao = new PetDAO();
		    	  List<PetVO> list = dao.getList();
		    	  request.setAttribute("list", list);
		    	  viewPath = "/pet/petList.jsp";
		      }else if(cmd.equals("search")) {
		    	  String name = request.getParameter("name");
		    	  
		    	  PetDAO dao = new PetDAO();
		    	  PetVO pet = dao.getPet(name);
		    	 
		    	 if(name == null || name.isEmpty()) {
		    		 int no = Integer.parseInt(request.getParameter("no"));
		    		 dao = new PetDAO();
			    	  pet = dao.getPet(no);
		    	 }
		    	  request.setAttribute("pet", pet);
		    	  
		    	 viewPath = "/pet/petDetail.jsp";
		      }else if(cmd.equals("editForm")) {
		    	  int no = Integer.parseInt(request.getParameter("no"));
		    	  
		    	  PetDAO dao = new PetDAO();
		    	  PetVO pet = dao.getPet(no);
		    	 
		    	  request.setAttribute("pet", pet);
		    	  
		    	  viewPath = "/pet/petEdit.jsp";
		    	  
		      }else if(cmd.equals("edit")) {
		    	  int no = Integer.parseInt(request.getParameter("no"));
		    	  float weight = Float.parseFloat(request.getParameter("weight"));
		    	  int price = Integer.parseInt(request.getParameter("price"));
		    	  String pic = request.getParameter("pic");
		    	  
		    	  PetDAO dao = new PetDAO();
		    	  PetVO pet = new PetVO();
		    	  pet.setNo(no);
		    	  pet.setWeight(weight);
		    	  pet.setPrice(price);
		    	  pet.setPic(pic);
		    	  
		    	  boolean edited = dao.edit(pet);
		    	  
		    	  sendJSON("edited", edited+"");
		    	  
		      }else if(cmd.equals("delete")) {
		    	  int no = Integer.parseInt(request.getParameter("no"));
		    	  PetDAO dao = new PetDAO();
		    	  boolean deleted = dao.delete(no);
		    	  sendJSON("deleted", deleted+"");
		      }
			return viewPath;
		}
		
		private void sendJSON(String key, String value) {
			String json = String.format("{\"%s\":%s}", key, value);
			PrintWriter out = null;
			try {
				out = response.getWriter();
			} catch (IOException e) {			
				e.printStackTrace();
			}
	 		out.print(json);
	 		out.flush(); 
		}
		 private void sendJSON(Map<String,Object> map) {
		      JSONObject jsObj = new JSONObject(map);
		      String js = jsObj.toJSONString();
		      try {
		         PrintWriter out = response.getWriter();
		         out.print(js);
		         out.flush();
		      } catch (IOException e) {
		         e.printStackTrace();
		      }
		   }
}
