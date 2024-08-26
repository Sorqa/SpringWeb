package com.sku.web.jdbc;

import java.sql.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController	//모든 메소드들이 responsebody
@RequestMapping("/oracle")
public class OracleJDBCController 
{
   private Connection conn;
   private Statement stmt;
   private ResultSet rs;
   
   @GetMapping("/test")
   public void test()
   {
      try {
         Class.forName("oracle.jdbc.OracleDriver");
         conn = DriverManager.getConnection(
                   "jdbc:oracle:thin:@localhost:1521:xe", "SCOTT", "TIGER");
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT * FROM emp");
         
         while(rs.next())
         {
            int empno = rs.getInt("EMPNO");
            String ename = rs.getString("ENAME");
            java.sql.Date hiredate = rs.getDate("HIREDATE");
            float salary = rs.getFloat("SAL");
            
            System.out.printf("%d\t%s\t%s\t%f \n", empno,ename,hiredate,salary);
         }
         
         rs.close();
         stmt.close();
         conn.close();
         
        } catch (Exception e) {
            e.printStackTrace();;
        }
   }
}