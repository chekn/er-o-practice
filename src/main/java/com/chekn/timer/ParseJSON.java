package com.chekn.timer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ParseJSON {
	
	public static void main(String[] args){
		try {
			new ParseJSON().read("1","f9CSNHfQUNPBcngXk99q3lp9ZE1ohPBY");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		//System.out.println(jsonStrMess);
		
		JSONObject jsonObjMess=JSONObject.parseObject(jsonStrMess);
		JSONArray jsonArrStyleItems=jsonObjMess.getJSONArray("StyleItems");
		
		int typeLevel=1;
		for(Object jsonObjStyleItem_O:jsonArrStyleItems.toArray()){	
			JSONObject jsonObjStyleItem=(JSONObject) jsonObjStyleItem_O;
			//System.out.println(jsonObjStyleItem.get("Type"));
			System.out.println(StringUtils.defaultString((String)jsonObjStyleItem.get("SubType")));
			System.out.println(jsonObjStyleItem.get("Explain"));
			//System.out.println(jsonObjStyleItem.get("Style"));

			int itemLevel=1;
			JSONArray jsonArrTestItems=jsonObjStyleItem.getJSONArray("TestItems");
			for(Object jsonObjTestItem_O:jsonArrTestItems.toArray()){

				//三种题解析流程不同
				JSONObject jsonObjTestItem=(JSONObject) jsonObjTestItem_O;
				
				if(typeLevel==1){
					System.out.println(typeLevel+"."+itemLevel+"、"+jsonObjTestItem.get("Title"));
					this.readSelectedItems(jsonObjTestItem);
					System.out.print("\n");
					
					System.out.println(jsonObjTestItem.get("Answer"));
					System.out.println(jsonObjTestItem.get("Explain"));
					System.out.print("\n\n");
				
				}else if(typeLevel==2){
					System.out.println(typeLevel+"."+itemLevel+"、"+jsonObjTestItem.get("FrontTitle"));
					
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
					
				}else {
					JSONArray jsonArrBTestItems=jsonObjTestItem.getJSONArray("BTestItems");
					int nItemLevel=1;
					for(Object jsonObjBTestItem_O:jsonArrBTestItems.toArray()){
						JSONObject jsonObjBTestItem=(JSONObject) jsonObjBTestItem_O;
						System.out.println(typeLevel+"."+itemLevel+"."+nItemLevel+"、"+jsonObjBTestItem.get("Title"));
						this.readSelectedItems(jsonObjTestItem);
						System.out.print("\n");
						
						System.out.println(jsonObjBTestItem.get("Answer"));
						System.out.println(jsonObjBTestItem.get("Explain"));
						nItemLevel++;
					}
					
				}
				
				itemLevel++;
			}
			
			System.out.print("\n\n\n");
			typeLevel++;
		}
		
		//System.out.println(jsonObjMess);
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
