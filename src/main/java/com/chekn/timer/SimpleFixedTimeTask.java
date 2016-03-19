package com.chekn.timer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.lang.time.DateFormatUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 
 * @author Pactera-NEN
 * @date 2016年1月18日-下午4:03:33
 */
public class SimpleFixedTimeTask {
	
	private Timer timer;
	private List<String> oneDayTimes;
	private NestedLogic nestedLogic;
	
	public SimpleFixedTimeTask(List<String> _HHmmss, NestedLogic nestedLogic){
		this.timer=new Timer();
		this.oneDayTimes=_HHmmss;
		this.nestedLogic=nestedLogic;
	}
	
	public void launch(){
		timer.scheduleAtFixedRate(new lightTask(), 0, 1000);
	}	
	
	public void stop(){
		timer.cancel();
	}
	
	class lightTask extends TimerTask{
		
		@Override
		public void run() {
			String currHHmmss=DateFormatUtils.format(new Date(), "HH:mm:ss");
			if(oneDayTimes.contains(currHHmmss)){
				nestedLogic.doBusi();
			}
		}
		
	}	
	
	//使用示例
	public static void main(String[] args){
		//业务
		NestedLogic nl=new NestedLogic(){

			public void doBusi() {
				//System.out.println("业务逻辑"); 你的逻辑
				Document doc=null;
				try {
					String cookie="AGENTCODE=889; AGENTURL=wide.ksbao.com; USERID=648485; USERNAME=18511056068; GUID=7WgQJyEZRdovvhA8VejAXRaDP72QOOiP; USERPWD=103089f0598f9bd0b39082158afb6450%2C9; APPENAME=ZC_FC; SELECTEDAPP=%5B%7B%22AppName%22%3A%222016%u7248%u4E3B%u6CBB%u533B%u5E08%u8003%u8BD5%u5B9D%u5178%28%u5987%u4EA7%u79D1%29%22%2C%22AppEName%22%3A%22ZC_FC%22%2C%22AppID%22%3A236%7D%5D; ZC_FC_ISHASTALK=true; MIDDLESIZE=22px; DAYMODE=%23FFF; DFONTCOLOR=%23000; 648485_APPNAME=2016%u7248%u4E3B%u6CBB%u533B%u5E08%u8003%u8BD5%u5B9D%u5178%28%u5987%u4EA7%u79D1%29%5B%u6B63%u5F0F%u7248%5D; CHAPTERINFO=%7B%22appEName%22%3A%22ZC_FC%22%2C%22ChapterInfo%22%3A%5B0%2C0%5D%7D; TESTINFOHISTORY=%7B%22AppEName%22%3A%22ZC_FC%22%2C%22TestInfoID%22%3A%5B1%2C%22001001%22%2C%22001002%22%2C%22005001%22%5D%7D; CPTID=1; TESTINFO=%7B%22appEName%22%3A%22ZC_FC%22%2C%22testInfo%22%3A%5B%221%22%2C0%5D%7D";
					//doc = Jsoup.connect("http://wide.ksbao.com/index.html?&isFreeTry=true&isShowAD=false#/Index").data("aid","8a2be07d523b33d0015278911fcd0098").cookie("JSESSIONID", cookie).get();
					doc = Jsoup.connect("http://wide.ksbao.com/index.html?&isFreeTry=true&isShowAD=false#/Index").cookie("JSESSIONID", cookie).get();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(doc);
			}
			
		};
		//时间
		List<String> sls=new ArrayList<String>();
		sls.add("21:25:10");
		sls.add("21:28:10");
		sls.add("13:30:10");
		sls.add("15:30:10");
		sls.add("18:30:10");
		
		//调用
		new SimpleFixedTimeTask(sls,nl).launch();
		
	}
	
	/**
	 * 内嵌逻辑
	 * 
	 * @author Pactera-NEN
	 * @date 2016年2月16日-下午6:20:53
	 */
	public interface NestedLogic{
		public void doBusi();
	}
	
}