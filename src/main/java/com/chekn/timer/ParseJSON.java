package com.chekn.timer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ParseJSON {
	
	private File file=new File("D:\\Fx.txt");
	
	public static void main(String[] args) {
		try {
			ParseJSON pj=new ParseJSON();
			
			Map<String,String> cptInfo=pj.getCptDepMenu();
			for(String cptid:cptInfo.keySet()){
				String cptName=cptInfo.get(cptid);
				String cptContent=pj.read(cptid,"OijJvpQJ8k8zBOA1ji6tRKSaWPnLAA8U");
				
				String cptFileName=cptName.replaceAll("[\\\\/:*?\"<>|]", "");
				FileUtils.writeStringToFile(new File("D:\\codeForAunt\\"+cptFileName+".txt"), cptContent, "UTF-8");
				TimeUnit.SECONDS.sleep(1);
				System.out.println("完成>"+cptid+":"+cptName+"->"+cptFileName);
				//break;
			}
			
		} catch (Exception e) {
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
	
	public Map<String,String> getCptDepMenu() throws IOException{
		Map<String,String> storeCptsMap=new LinkedHashMap<String,String>();
		
		String url="http://wide.ksbao.com:8001/api/chapterMenu/getChapterMenu/ZC_FC";
		Document doc= Jsoup.connect(url).get();
		String jsonStrData=doc.body().text();
		JSONObject jsonObjData=JSONObject.parseObject(jsonStrData);
		String jsonStrMess=jsonObjData.getJSONObject("data").getString("ChapterMenuJson");
		
		JSONObject jsonObjMess=JSON.parseObject(jsonStrMess);
		JSONArray jsonArrChilds=jsonObjMess.getJSONArray("Childs");
		this.depMenuReadCpt(jsonArrChilds, "", storeCptsMap);
		/*for(Entry<String,String> cptMap:storeCptsMap.entrySet()){
			System.out.println(cptMap.getKey()+"->"+cptMap.getValue());
		}*/
		
		return storeCptsMap;
	}
	
	public void depMenuReadCpt(JSONArray jsonArrChilds, String warpLineStr, Map<String,String> storeCptsMap){
		
		for(Object jsonObjChild_o:jsonArrChilds.toArray()){
			JSONObject jsonObjChild=(JSONObject) jsonObjChild_o;
			String id=jsonObjChild.getString("ID");
			String nodeType=jsonObjChild.getString("NodeType");
			String name=jsonObjChild.getString("Name");
			System.out.println(warpLineStr+name);
			
			if(!nodeType.equals("Chapter")){
				JSONArray jsonArrSubChilds=jsonObjChild.getJSONArray("Childs");
				depMenuReadCpt(jsonArrSubChilds,warpLineStr+"\t",storeCptsMap);
			}else{
				storeCptsMap.put(id, name);
			}
		}
		
	}
	
	public String read(String cptID,String guid) throws IOException{
		StringBuilder strBu=new StringBuilder();
		
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
			//strBu.append(StringUtils.defaultString((String)jsonObjStyleItem.get("SubType"))+"\r\n");
			strBu.append(jsonObjStyleItem.get("Explain")+"\r\n");

			int itemLevel=1;
			JSONArray jsonArrTestItems=jsonObjStyleItem.getJSONArray("TestItems");
			for(Object jsonObjTestItem_O:jsonArrTestItems.toArray()){

				//三种题解析流程不同
				JSONObject jsonObjTestItem=(JSONObject) jsonObjTestItem_O;
				
				switch(itemType){
					case "ATEST": {
							strBu.append(typeLevel+"."+itemLevel+"、"+jsonObjTestItem.get("Title")+"("+itemStyle+")"+"\r\n");
							strBu.append(this.readSelectedItems(jsonObjTestItem));
							strBu.append(("\r\n"));
							
							strBu.append(jsonObjTestItem.get("Answer")+"\r\n");
							strBu.append(jsonObjTestItem.get("Explain")+"\r\n");
							strBu.append(("\r\n"));
						}
						break;
					case "A3TEST":{ 
							strBu.append(typeLevel+"."+itemLevel+"、"+jsonObjTestItem.get("FrontTitle")+"("+itemStyle+")"+"\r\n");
							
							int nItemLevel=1;
							JSONArray jsonArrA3TestItems=jsonObjTestItem.getJSONArray("A3TestItems");
							for(Object jsonObjA3TestItem_O:jsonArrA3TestItems.toArray()){
								JSONObject jsonObjA3TestItem=(JSONObject) jsonObjA3TestItem_O;
								strBu.append(typeLevel+"."+itemLevel+"."+nItemLevel+"、"+jsonObjA3TestItem.get("Title")+"\r\n");
								strBu.append(this.readSelectedItems(jsonObjA3TestItem)+"\r\n");
								strBu.append("\r\n");
								
								strBu.append(jsonObjA3TestItem.get("Answer")+"\r\n");
								strBu.append(jsonObjA3TestItem.get("Explain")+"\r\n");
								nItemLevel++;
							}
							strBu.append(jsonObjTestItem.get("Explain")+"\r\n");
						}
						break;
					case "BTEST":{
							JSONArray jsonArrBTestItems=jsonObjTestItem.getJSONArray("BTestItems");
							int nItemLevel=1;
							for(Object jsonObjBTestItem_O:jsonArrBTestItems.toArray()){
								JSONObject jsonObjBTestItem=(JSONObject) jsonObjBTestItem_O;
								strBu.append(typeLevel+"."+itemLevel+"."+nItemLevel+"、"+jsonObjBTestItem.get("Title")+"("+itemStyle+")"+"\r\n");
								strBu.append(this.readSelectedItems(jsonObjTestItem)+"\r\n");
								strBu.append("\r\n");
								
								strBu.append(jsonObjBTestItem.get("Answer")+"\r\n");
								strBu.append(jsonObjBTestItem.get("Explain")+"\r\n");
								nItemLevel++;
							}
						}
						break;
					
				}
				itemLevel++;
			}
			
			strBu.append("\r\n");
			typeLevel++;
		}
		
		//System.out.println(jsonObjMess);
		return strBu.toString();
	}
	
	public void writeStr2File(String data) throws IOException{
		System.out.println(data);
		FileUtils.writeStringToFile(file, data, "UTF-8", true);
	}
	
	public String readSelectedItems(JSONObject jsonObj){
		StringBuilder strBu=new StringBuilder();
		
		JSONArray jsonArrSelectedItems=jsonObj.getJSONArray("SelectedItems");
		if(jsonArrSelectedItems!=null)
			for(Object jsonObjSelectedItem_O:jsonArrSelectedItems.toArray()){
				JSONObject jsonObjSelectedItem=(JSONObject) jsonObjSelectedItem_O;
				strBu.append(jsonObjSelectedItem.getString("ItemName")+" : "+jsonObjSelectedItem.getString("Content")+"\r\n");
			}
		
		return strBu.toString();
	}
}
