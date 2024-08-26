package com.sku.web.updown;


import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sku.web.User;
@Repository
public class FileDAO {
	 private Connection conn;
	   private PreparedStatement pstmt;
	   private ResultSet rs;
	   
	   @Autowired
	   private JdbcTemplate jdbcTemplate;
	   
	@Transactional 
	public int addUpload(UploadVO u) {
		
	      String sql = "INSERT INTO upload (unum,title,writer,rdate,filedesc) VALUES(upload_seq.NEXTVAL,?,?,?,?)";
	      KeyHolder keyHolder = new GeneratedKeyHolder();
		  jdbcTemplate.update(conn->{
		     PreparedStatement pstmt;
		     pstmt = conn.prepareStatement(sql, new String[] {"unum"});
		     pstmt.setString(1, u.getTitle());
		     pstmt.setString(2, u.getWriter());
		     pstmt.setDate(3, u.getRdate());
		     pstmt.setString(4,u.getDescr());
		     return pstmt;
		  }, keyHolder);
		  
		  //String[] files = u.getFiles();
		  String sql2 = "INSERT INTO upfile (fnum,fname1,unum,fname2,fsize) VALUES(upfile_seq.NEXTVAL,?,?,?,?)";
		  try {
		         int rowsAffected = 0;
		         for(int i = 0; i < u.getAttList().size(); i++) {
		            jdbcTemplate.update(
		                     sql2,
		                     u.getAttList().get(i).getFname1(),
		                     keyHolder.getKey().intValue(),
		                     u.getAttList().get(i).getFname2(),
		                     u.getAttList().get(i).getFsize()
		             ); rowsAffected++;
		         } return keyHolder.getKey().intValue();
		      } catch (Exception e) {
		         e.printStackTrace();
		      } return 0;
		 
		
	   }

	public List<UploadVO> fileList() {
		String sql = "SELECT unum,title,writer,rdate, (SELECT COUNT(unum)FROM upfile WHERE unum=u.unum) AS 첨부파일 FROM upload u";
		 List<UploadVO> list = jdbcTemplate.query(sql, 
	    		  (rs, i)->new UploadVO(rs.getInt("UNUM"),rs.getString("TITLE"), rs.getString("WRITER"),rs.getDate("RDATE"),rs.getInt("첨부파일")));
		return list;
	}

	/*
	public UploadVO getDetail(int unum) {
		String sql = "SELECT * FROM upload u INNER JOIN upfile f  ON u.unum = f.unum WHERE u.unum=?";
		List<AttachVO> list = new ArrayList<>();
		UploadVO uploadVO = null;
		UploadVO up = jdbcTemplate.queryForObject(sql, (rs,i) -> new UploadVO(
	                rs.getInt("UNUM"),
	                rs.getString("TITLE"),
	                rs.getString("WRITER"),
	                rs.getDate("RDATE"),
	                rs.getString("FILEDESC")
	            ),unum);		
		
		list = jdbcTemplate.query(sql,
							(rs,i)->new AttachVO(
						                rs.getInt("FNUM"),
						                rs.getString("FNAME1"),
						                rs.getInt("UNUM"),
						                rs.getString("FNAME2"),
						                rs.getLong("FSIZE")
						            )
						            );
		up.setAttList(list);				        
						    		   
		return up;
	}
	*/
	
	public UploadVO getDetail(int unum) {
	    String sql = "SELECT u.UNUM, u.TITLE, u.WRITER, u.RDATE, u.FILEDESC, f.FNUM, f.FNAME1, f.FNAME2, f.FSIZE " +
	                 "FROM upload u " +
	                 "INNER JOIN upfile f ON u.unum = f.unum " +
	                 "WHERE u.unum = ?";

	    // 리스트 초기화
	    List<AttachVO> list = new ArrayList<>();

	    return jdbcTemplate.query(sql, new Object[]{unum}, rs -> {
	        UploadVO uploadVO = null;

	        while (rs.next()) {
	            // UploadVO는 한번만 생성
	            if (uploadVO == null) {
	                uploadVO = new UploadVO(
	                    rs.getInt("UNUM"),
	                    rs.getString("TITLE"),
	                    rs.getString("WRITER"),
	                    rs.getDate("RDATE"),
	                    rs.getString("FILEDESC")
	                );
	            }

	            // AttachVO 객체 생성 후 리스트에 추가
	            AttachVO attachVO = new AttachVO(
	                rs.getInt("FNUM"),
	                rs.getString("FNAME1"),	                	                
	                rs.getLong("FSIZE")
	            );
	            list.add(attachVO);
	        }

	        // AttachVO 리스트를 UploadVO에 설정
	        if (uploadVO != null) {
	            uploadVO.setAttList(list);
	        }

	        return uploadVO;
	    });
	}

	public String fileDelete(int fnum) {
		String sql = "SELECT fname2 FROM upfile WHERE fnum=?";
		String fname2 = jdbcTemplate.queryForObject(sql, new Object[]{fnum}, String.class);
		String sql2 ="DELETE FROM upfile WHERE fnum=?";
		
		int rows = jdbcTemplate.update(sql2,fnum);
		  
		return fname2;
	}

}
