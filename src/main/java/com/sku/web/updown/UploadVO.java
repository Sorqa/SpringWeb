package com.sku.web.updown;

import java.sql.Date;
import java.util.List;

public class UploadVO {
    private int unum;
    private String title;
    private String writer;
    private Date rdate;
    private String descr;
    private List<AttachVO> attList;  
     private int count;
     
    public UploadVO() {}
    public UploadVO(int unum, String title, String writer, Date rdate, int count) {
		this.unum = unum;
		this.title = title;
		this.writer = writer;
		this.rdate = rdate;
		this.count = count;
	}

	public UploadVO(int unum, String title, String writer, Date rdate, String descr) {
		this.unum = unum;
		this.title = title;
		this.writer = writer;
		this.rdate = rdate;
		this.descr = descr;
	}
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public List<AttachVO> getAttList() {
		return attList;
	}

	public void setAttList(List<AttachVO> attList) {
		this.attList = attList;
	}

	public int getUnum() {
        return unum;
    }

    public void setUnum(int unum) {
        this.unum = unum;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Date getRdate() {
        return rdate;
    }

    public void setRdate(Date rdate) {
        this.rdate = rdate;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

   
}
