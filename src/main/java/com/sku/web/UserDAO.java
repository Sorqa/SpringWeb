package com.sku.web;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class UserDAO 
{
   private Connection conn;
   private PreparedStatement pstmt;
   private ResultSet rs;
   
   @Autowired
   private JdbcTemplate jdbcTemplate;	//Dependency Injection, autowired로 new하지 않고 쓸수 있음,원래는 널인데 오토 덕분에 값이 있음
   private Connection getConn() 
   {
      try {
         Class.forName("oracle.jdbc.OracleDriver");
         conn = DriverManager.getConnection(
                   "jdbc:oracle:thin:@localhost:1521:xe", "SCOTT", "TIGER");
         return conn;
      }catch(Exception e) {
         e.printStackTrace();
      }
      return null;
   }
   
   public boolean login(User user) {
      String sql = "SELECT * FROM users WHERE userid=? AND userpwd=?";
      conn = getConn();
      try {
         pstmt = conn.prepareStatement(sql);
         pstmt.setString(1, user.getUsername());
         pstmt.setString(2,  user.getPassword());
         rs = pstmt.executeQuery();
         if(rs.next()) {
            return true;
         }
      }catch(SQLException sqle) {
         sqle.printStackTrace();
      }
      return false;
   }
   public boolean login2(User user) {
	      String sql = "SELECT * FROM users WHERE userid=? AND userpwd=?";
	 try {
	  User u = jdbcTemplate.queryForObject(sql, 	            
	          (rs,i)->new User(rs.getString("USERID"), rs.getString("USERPWD")),
	          user.getUsername(),user.getPassword());
	  if(u!=null) return true;
	 	}catch(Exception ex) {
	    	  ex.printStackTrace();
	    }
	 	return false;
   }
   
   //insert -> queryXXX()	: 가져오는 값이 있다
   //insert, update, delete	: sql묹장의 영향을 받은 행수	-> update()
   public boolean addUser(User u) {
	   String sql = "INSERT INTO users VALUES (?,?)";
	   int rows = jdbcTemplate.update(sql, u.getUsername(), u.getPassword());
	   return rows>0;
   }
   
   //select -> queryForObject()	:가져오는 값이 있다
   //select	->	query()			:목록 집합 리턴
   //insert,update,delete 		:sql 문장의 영향을 받은 함수	->update()
   public List<User> getList() {
	      String sql = "SELECT * FROM users";
	      List<User> list = jdbcTemplate.query(sql, 
	    		  (rs, i)->new User(rs.getString("USERID"), rs.getString("USERPWD")));
	      return list;
	}
   public List<User> getList2() {
	      String sql = "SELECT * FROM users";
	      List<User> list = jdbcTemplate.query(sql, 
	    		  (rs, i)->{
	    			  User u = new User();
	    			  u.setUsername(rs.getString("USERID"));
	    			  u.setPassword(rs.getString("USERPWD"));
	    			  return u;
	    		  });	    		  
	      return list;
	}
   
   public boolean checkDuplicate(User u) {	   
	   String sql= "SELECT * FROM users WHERE userid=?";
		 		  
		   boolean result = jdbcTemplate.query(sql, 
				    (rs) -> {
				        if (rs.next()) {
				            return false;
				        }
				        return true;
				    },  u.getUsername()
				);
		return result;				   	 	 
   }
   
   public boolean deleteUser(String uid) {
	   String sql = "DELETE FROM users WHERE userid=?";
	   int rows = jdbcTemplate.update(sql,uid);
	   return rows>0;
   }
   

	public boolean update(User u) {
		String sql = "UPDATE users SET userpwd=? WHERE userid=?";
		int rows = jdbcTemplate.update(sql,u.getPassword(),u.getUsername());
		return rows>0;
	}

	public User getUserById(String uid) {
		String sql = "SELECT * FROM users WHERE userid=?";
		User user = jdbcTemplate.queryForObject(sql,
				(rs,i)->new User(rs.getString("USERID"),rs.getString("USERPWD")),
				uid);		
		return user;
	}
}