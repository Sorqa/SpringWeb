package com.sku.web.updown;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.ArrayList;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/files")
public class FileController 
{
   @Autowired
   ResourceLoader resourceLoader;
   
   @Autowired
   ServletContext context;
   
   @Autowired
   private FileService fileService;

   @GetMapping("/upload")
   public String getForm() 
   {
      return "updown/upload_form";
   }
   
   @PostMapping("/upload")
   @ResponseBody
   public Map<String, Object> upload(@RequestParam("files")MultipartFile[] mfiles,
                     HttpServletRequest request,
                     @RequestParam("author") String author,
                     @RequestParam("title") String title,
                     @RequestParam("filedesc") String filedesc) 
   {
	   Map<String, Object> map = new HashMap<>();

      ServletContext context = request.getServletContext();
      String savePath = context.getRealPath("/WEB-INF/files");
      
      /* static/upload 디렉토리에 업로드하려면, 아래처럼 절대경로를 구하여 사용하면 된다
      * Resource resource = resourceLoader.getResource("classpath:/static");
      * String absolutePath = resource.getFile().getAbsolutePath();
      */ 
      try {
    	  UploadVO vo = new UploadVO();
          vo.setTitle(title);
          vo.setWriter(author);
          java.util.Date uDate = new java.util.Date();
	    	java.sql.Date sDate = new java.sql.Date(uDate.getTime());
          vo.setRdate(sDate);
          vo.setDescr(filedesc);
          
          List<AttachVO> list = new ArrayList<>();
         for(int i=0;i<mfiles.length;i++) {
            if(mfiles[0].getSize()==0) continue;
            
                                   
            String fname1 =	mfiles[i].getOriginalFilename();;
                                   
            UUID uuid = UUID.randomUUID();
            int dotIndex = fname1.lastIndexOf(".");
            String s = fname1.substring(dotIndex);
            String fname2 = fname1 + "_" + uuid.toString()+s;
            mfiles[i].transferTo(
                    new File(savePath+"/"+fname2));
            /*mfiles[i].transferTo(
              new File(savePath+"/"+mfiles[i].getOriginalFilename()));
             * 
             */
            //String[] files = request.getParameterValues("hobby");
            
            long fSize = mfiles[i].getSize();
           
            AttachVO at	 = new AttachVO();
            at.setFname1(fname1);
            at.setFname2(fname2);
            at.setFsize(fSize);
            list.add(at);
                                                                    
            /* MultipartFile 주요 메소드            
            String cType = mfiles[i].getContentType();
            String pName = mfiles[i].getName();
            Resource res = mfiles[i].getResource();
            long fSize = mfiles[i].getSize();
            boolean empty = mfiles[i].isEmpty();
            */
           
         }
         vo.setAttList(list);
         int key = fileService.addUpload(vo);
         System.out.print("키 확인" +key);
         //String msg = String.format("파일(%d)개 저장성공 (작성자:%s) ", mfiles.length,author);
         if(key>0) {map.put("status", "success");}
         else {map.put("status", "false");}
         map.put("unum", key); 
         return map;

      } catch (Exception e) {
         e.printStackTrace();        
      }
	return map;
	
   }
   
   @GetMapping("/download/{filename}")
   public ResponseEntity<Resource> download(
                              HttpServletRequest request,
                              @PathVariable String filename)
   {
      Resource resource = resourceLoader.getResource("WEB-INF/files/"+filename);
      System.out.println("파일명:"+resource.getFilename());
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        if(contentType == null) {
            contentType = "application/octet-stream";
        }
       
        /* 파일명이 한글로 되어 있을 때 다운로드가 안되는 경우... */
        try {
         filename = new String(filename.getBytes("UTF-8"), "ISO-8859-1");	//utf뒤에 로마자입 파일네임 영문자 조합
      } catch (UnsupportedEncodingException e) {
         e.printStackTrace();
      }
        System.out.print(filename);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(resource);
       
   }
   
   @GetMapping("/List")
   public String getList(Model model) {
	  List<UploadVO> list = fileService.List();
	  model.addAttribute("list", list);
	return "updown/fileList";
	   
   }
   
   @GetMapping("/uploadDetails")
   public String getUploadDetails(@RequestParam("unum") int unum, Model model) {
       UploadVO upload = fileService.getUploadDetails(unum);
       model.addAttribute("upload", upload);
       return "/updown/uploadDetails";
   }
   
   @PostMapping("/delete/{fnum}")
   @ResponseBody
   @Transactional
   public Map<String, Object> delete(@PathVariable("fnum") int unum) throws Exception {
	   System.out.print("삭제왔음");
	   String filename = fileService.fileDelete(unum);
	   System.out.print(filename);
	   //File f = new File("C:\\eclipse_projects\\SpringWeb\\src\\main\\webapp\\WEB-INF\\files\\"+ filename);
	   String filePath = context.getRealPath("/WEB-INF/files/" + filename);
	    File f = new File(filePath);
	   Map<String, Object> map = new HashMap<>();
	   if(f.exists()) {
		   boolean deleted = f.delete();		  
		   map.put("deleted", deleted);
		   throw new Exception("강제로 예외 발생");
	   }else {
		   map.put("deleted", false);
		   
	   }
	   return map;
   }
}