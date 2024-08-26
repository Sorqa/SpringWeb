package com.sku.web.updown;

public class AttachVO {
	private int fnum;
    private String fname1;
    private int unum;
    private String fname2;
    private long fsize;

    public AttachVO(int fnum, String fname1, int unum, String fname2, long fsize) {
		this.fnum = fnum;
		this.fname1 = fname1;
		this.unum = unum;
		this.fname2 = fname2;
		this.fsize = fsize;
	}

	public AttachVO() {	}

	public AttachVO(int fnum, String fname1, long fsize) {
		this.fnum = fnum;
		this.fname1 = fname1;
		this.fsize = fsize;
	}

	public int getFnum() {
        return fnum;
    }

    public void setFnum(int fnum) {
        this.fnum = fnum;
    }

    public String getFname1() {
        return fname1;
    }

    public void setFname1(String fname1) {
        this.fname1 = fname1;
    }

    public int getUnum() {
        return unum;
    }

    public void setUnum(int unum) {
        this.unum = unum;
    }

    public String getFname2() {
        return fname2;
    }

    public void setFname2(String fname2) {
        this.fname2 = fname2;
    }

    public long getFsize() {
        return fsize;
    }

    public void setFsize(long fsize) {
        this.fsize = fsize;
    }
}
