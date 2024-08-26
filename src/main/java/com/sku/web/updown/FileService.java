package com.sku.web.updown;

import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
@Service
public class FileService {

	@Autowired
	private FileDAO fileDao;
	
	
	public int addUpload(UploadVO vo) {
		int key = fileDao.addUpload(vo);
		return key;
	}


	public List<UploadVO> List() {
		List<UploadVO> list = fileDao.fileList();
		return list;
	}


	public UploadVO getUploadDetails(int unum) {
		UploadVO up = fileDao.getDetail(unum);
		return up;
	}


	public String fileDelete(int unum) {
		String filename = fileDao.fileDelete(unum);
		return filename;
	}
	
	
}
