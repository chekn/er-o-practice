package com.ckn.practice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

/**
 * 
 * @author Pactera-NEN
 * @date 2016年5月26日-上午11:28:55
 */
public class CommonsCSVtest {
	
	public static void writeCsvFile(String[] args) throws Exception{
		System.out.println("。。。。。");
		File file=new File("C:\\Users\\Pactera-NEN\\Desktop\\cc.txt");
		
		CSVFormat format= CSVFormat.DEFAULT.withRecordSeparator("\r\n"); // 每条记录间隔符
		Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
		CSVPrinter printer=new CSVPrinter(writer, format);
		
		printer.printRecord(Arrays.asList("id","tel","cc"));
		for(int i=0;i<10;i++) {
			printer.printRecord(Arrays.asList("id_"+i,"tel_"+i,"cc_"+i));
		}
		
		printer.close();
		writer.close();
		System.out.println("write complete!");
	}

	public static void readCsvFile(String fileName) throws IOException {
		String[] colNames=new String[]{"ID",
        		"CARNU","TRADEMARK","CARTYPE",
        		"MOTORID","CARFRAMEID",
        		"YYYY","MM","DD",
        		"INSUREND","SEATS","ONAME",
        		"OTELPHONE1","OTELPHONE2","OIDENTITYCARD",
        		"OSEX","OAGE","OCITY",
        		"ODISTRICT","ODETAILAREA","MODIFYDATE",
        		"STATUS","FINALCAREMAN","MEMBERRANK",
        		"REGDATE","PC","NEXTYEARCHECKDATE",
        		"REGDISTRICT","CREATEDATE","CREATEMAN",
        		"PREDICT_UPKEEP_CEILDRIVERMILE",
        		"PREDICT_NEXT_UPKEEP_CEILDAYS","REFUSECOUNT","REFUSEMAN",
        		"DISTANCE2STORE","RELME","OCCUPATION","SN"};
		
		File file = new File(fileName);
		CSVParser csvFileParser = null;
		//创建CSVFormat（header mapping）
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(colNames);
        
        InputStreamReader reader = new InputStreamReader(new FileInputStream(file),"GB2312");
        //初始化 CSVParser object
        csvFileParser = new CSVParser(reader, csvFileFormat);
        //CSV文件records
        List<CSVRecord> csvRecords = csvFileParser.getRecords();
        int rcdCount=csvRecords.size();
        
        for (int i = 1; i < rcdCount; i++) {
        	CSVRecord record = csvRecords.get(i);
        	String id=record.get("ID");
        	String carnu=record.get("CARNU");
        	String trademark=record.get("TRADEMARK");
        	String cartype=record.get("CARTYPE");
        	String oname=record.get("ONAME");
        	String memberrank=record.get("MEMBERRANK");
        	
        }
        
        reader.close();
        csvFileParser.close();
	}
	
	public static void main(String[] args){
		try {
			readCsvFile("C:\\Users\\CHEKN\\Desktop\\Carinfo.csv");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
