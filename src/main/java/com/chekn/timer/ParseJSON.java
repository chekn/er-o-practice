package com.chekn.timer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ParseJSON {
	
	public static void main(String[] args){
		try {
			ParseJSON pj=new ParseJSON();
			int i=0;
			for(String cptid:pj.getCptId()){
				pj.read(cptid,"QQWalc2TFzew5nT6ZuqQIbheEzYMCbFG");
				System.out.println((++i)+"、CptID:"+cptid+":******************************************");
				//break;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> getCptId() throws IOException{
		List<String> cptIds=new ArrayList<String>();
		
		String url="http://wide.ksbao.com:8001/api/chapterMenu/getCptID/ZC_FC/648485";
		Document doc= Jsoup.connect(url).get();
		String jsonStrData=doc.body().text();
		JSONObject jsonObjData=JSONObject.parseObject(jsonStrData);
		JSONArray jsonArrDataItems=jsonObjData.getJSONArray("data");
		for(Object jsonObjDataItem_o:jsonArrDataItems.toArray()){
			JSONObject jsonObjDataItem=(JSONObject) jsonObjDataItem_o;
			cptIds.add ( jsonObjDataItem.getString("CptID") );
		}
		
		return cptIds;
	}
	
	public void read(String cptID,String guid) throws IOException{
		String url="http://wide.ksbao.com:8001/api/exam/getChapterTest";
		Map<String,String> params=new HashMap<String,String>();
		params.put("appID", "236");
		params.put("cptID",cptID);//"1"
		params.put("guid", guid);//"c44WGs72pluDnAB9vnpIM1usfmZHAukm"
		params.put("userID", "648485");
		params.put("userName", "18511056068");
		Document doc= Jsoup.connect(url).data(params).post();
		String jsonStrData=doc.body().text();
		JSONObject jsonObjData=JSONObject.parseObject(jsonStrData);
		String jsonStrMess=jsonObjData.getJSONObject("data").getString("test");
		
		JSONObject jsonObjMess=JSONObject.parseObject(jsonStrMess);
		JSONArray jsonArrStyleItems=jsonObjMess.getJSONArray("StyleItems");
		
		int typeLevel=1;
		for(Object jsonObjStyleItem_O:jsonArrStyleItems.toArray()){	
			JSONObject jsonObjStyleItem=(JSONObject) jsonObjStyleItem_O;
			String itemType=jsonObjStyleItem.getString("Type");
			String itemStyle=jsonObjStyleItem.getString("Style");
			System.out.println(StringUtils.defaultString((String)jsonObjStyleItem.get("SubType")));
			System.out.println(jsonObjStyleItem.get("Explain"));

			int itemLevel=1;
			JSONArray jsonArrTestItems=jsonObjStyleItem.getJSONArray("TestItems");
			for(Object jsonObjTestItem_O:jsonArrTestItems.toArray()){

				//三种题解析流程不同
				JSONObject jsonObjTestItem=(JSONObject) jsonObjTestItem_O;
				
				switch(itemType){
					case "ATEST": {
							System.out.println(typeLevel+"."+itemLevel+"、"+jsonObjTestItem.get("Title")+"("+itemStyle+")");
							this.readSelectedItems(jsonObjTestItem);
							System.out.print("\n");
							
							System.out.println(jsonObjTestItem.get("Answer"));
							System.out.println(jsonObjTestItem.get("Explain"));
							System.out.print("\n\n");
						}
						break;
					case "A3TEST":{ 
							System.out.println(typeLevel+"."+itemLevel+"、"+jsonObjTestItem.get("FrontTitle")+"("+itemStyle+")");
							
							int nItemLevel=1;
							JSONArray jsonArrA3TestItems=jsonObjTestItem.getJSONArray("A3TestItems");
							for(Object jsonObjA3TestItem_O:jsonArrA3TestItems.toArray()){
								JSONObject jsonObjA3TestItem=(JSONObject) jsonObjA3TestItem_O;
								System.out.println(typeLevel+"."+itemLevel+"."+nItemLevel+"、"+jsonObjA3TestItem.get("Title"));
								this.readSelectedItems(jsonObjA3TestItem);
								System.out.print("\n");
								
								System.out.println(jsonObjA3TestItem.get("Answer"));
								System.out.println(jsonObjA3TestItem.get("Explain"));
								nItemLevel++;
							}
							System.out.println(jsonObjTestItem.get("Explain"));
						}
						break;
					case "BTEST":{
							JSONArray jsonArrBTestItems=jsonObjTestItem.getJSONArray("BTestItems");
							int nItemLevel=1;
							for(Object jsonObjBTestItem_O:jsonArrBTestItems.toArray()){
								JSONObject jsonObjBTestItem=(JSONObject) jsonObjBTestItem_O;
								System.out.println(typeLevel+"."+itemLevel+"."+nItemLevel+"、"+jsonObjBTestItem.get("Title")+"("+itemStyle+")");
								this.readSelectedItems(jsonObjTestItem);
								System.out.print("\n");
								
								System.out.println(jsonObjBTestItem.get("Answer"));
								System.out.println(jsonObjBTestItem.get("Explain"));
								nItemLevel++;
							}
						}
						break;
					
				}
				itemLevel++;
			}
			
			System.out.print("\n\n\n");
			typeLevel++;
		}
		
		//System.out.println(jsonObjMess);
	}
	
	public void appendFile(){
		
	}
	
	public void readSelectedItems(JSONObject jsonObj){
		JSONArray jsonArrSelectedItems=jsonObj.getJSONArray("SelectedItems");
		if(jsonArrSelectedItems!=null)
			for(Object jsonObjSelectedItem_O:jsonArrSelectedItems.toArray()){
				JSONObject jsonObjSelectedItem=(JSONObject) jsonObjSelectedItem_O;
				System.out.println(jsonObjSelectedItem.getString("ItemName")+" : "+jsonObjSelectedItem.getString("Content"));
			}
	}
}
