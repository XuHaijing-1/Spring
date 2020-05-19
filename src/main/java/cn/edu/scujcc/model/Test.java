package cn.edu.scujcc.model;

import java.util.Date;

public class Test {
	public static void main(String[] args) {
		Date date=new Date();
		System.out.println("距今"+date.getTime()/1000/60/60/24/365+"年!");
	}
}
