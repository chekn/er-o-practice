package com.ckn.practice;
public class Lamda {

	public static void main(String[] args) {
		
		Runnable runn= ()->System.out.println("cccc");
		new Thread(runn).start();
		
	}
	
}
