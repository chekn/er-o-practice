package com.ckn.practice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import junit.framework.TestCase;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chekn.db.MySQLWorkDb;

public class CarSpec extends TestCase {
	
	public static void db() throws Exception {
		
		File dir=new File("c:/car");
		for(File sud: dir.listFiles()) {
			if(sud.isDirectory()) {
				String sb=sud.getName().replaceAll("^\\d*_", "");
				
				for(File ufd: sud.listFiles()) {
					String uf=ufd.getName();

					for(File upd: ufd.listFiles()) {
						String up=upd.getName().replaceAll("^\\d*_", "").replaceAll("\\.txt", "");
						System.out.println(sb + "\n\t" + uf + "\n\t\t"+up);
						
						String fx=FileUtils.readFileToString(upd);
						JSONArray yis=((JSONObject)JSON.parse(fx)).getJSONObject("result").getJSONArray("yearitems");
						
						Connection conn=MySQLWorkDb.getConnection("ckn_ci");
						PreparedStatement ps= conn.prepareStatement("select cs.id from ci_series cs, ci_fac cf where cs.name='"+up+"' and cf.name='"+uf+"' and cs.fid=cf.id");
						ResultSet rs= ps.executeQuery();
						if(!rs.next())
							throw new RuntimeException("!!!! interupt:"+uf+"."+up);
						String sid= rs.getString(1);
						
						for(Object yi:yis.toArray()) {
							JSONObject oyi =(JSONObject) yi;
							JSONArray sis= oyi.getJSONArray("specitems");
							String yn=oyi.getString("name");
							
							String yid=UUID.randomUUID().toString();
							PreparedStatement ps2= conn.prepareStatement("insert into ci_year values('"+yid+"','"+yn+"','"+sid+"')");
							ps2.executeUpdate();
							
							
							for(Object si:sis.toArray()) {
								JSONObject osi =(JSONObject) si;
								String ahid=osi.getInteger("id").toString();
								String yin=osi.getString("name");
								String state=osi.getInteger("state")==20?"1":"0";
								String price=osi.getInteger("maxprice").toString();
								PreparedStatement ps3= conn.prepareStatement("insert into ci_spec(Id,name,yid,aid, state, price) values(REPLACE(UUID(),'-',''),'"+yin+"','"+yid+"','"+ahid+"','"+state+"','"+price+"')");
								ps3.executeUpdate();
								System.out.println("\n\t\t\t yn: "+yn +" yin:"+yin);
							}
						}

						MySQLWorkDb.closeStatement(ps);
						MySQLWorkDb.closeConnection();
					}
				}
				
			}
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		String fx=FileUtils.readFileToString(new File("C:\\car\\36_奔驰\\北京奔驰\\2562_奔驰GLK级.txt"));
		JSONArray yis=((JSONObject)JSON.parse(fx)).getJSONObject("result").getJSONArray("yearitems");
		
		for(Object yi:yis.toArray()) {
			JSONObject oyi =(JSONObject) yi;
			JSONArray sis= oyi.getJSONArray("specitems");
			
			for(Object si:sis.toArray()) {
				JSONObject osi =(JSONObject) si;
				gIP(osi.getInteger("id").toString());
			}
		}
	}
	
	public static void gIP(String csi) throws Exception {
		String cu="http://www.autohome.com.cn/spec/"+csi+"/#pvareaid=10006";
		Document doc=Jsoup.connect(cu).get();
		Element e=doc.select(".cardetail-infor-car").get(0);
		
		Elements is= e.select("ul > li");
		for(Element i:is) {
			String tx=i.text();
			String[] nv=tx.split("：");
			if( !ncare.contains(nv[0]) ) {
				System.out.println(nv[0] +"-"+ nv[1]);
			}
		}
		
		String imu="http://car.autohome.com.cn/photolist/spec/"+csi+"/1/p1/?pvareaid=101474";
		Document idoc=Jsoup.connect(imu).get();
		Elements ims=idoc.select("#pa1 > ul >li");
		
		for(Element im:ims) {
			Element ia= im.child(0);
			String iam=ia.attr("href").replaceAll("^/", "http://car.autohome.com.cn/").replace("photo", "bigphoto");
			Document iadoc= Jsoup.connect(iam).get();
			Element ia1= iadoc.select("#img").get(0);
			
			dldImg(ia1.absUrl("src"), "c:/carshot/"+UUID.randomUUID().toString()+".jpg");
			System.out.println("read "+iam + " out down");
		}
	}
	
	public static List<String> ncare =Arrays.asList("查看详细参数配置>>");
	
	public static void dldImg(String ul, String path) throws Exception {
		URL   url   =   new   URL( ul); 
		URLConnection   uc   =   url.openConnection(); 
		InputStream   is   =   uc.getInputStream(); 
		File   file   =   new   File( path); 
		FileOutputStream   out   =   new   FileOutputStream(file); 
		
		IOUtils.copy(is, out);
		IOUtils.closeQuietly(is);
		IOUtils.closeQuietly(out);
	}
	
	public static void ma() {
		System.out.println(UUID.randomUUID().toString().length());
	}
	
	public void gipdb() throws Exception{
		

		int bp=21040;
		Connection conn=MySQLWorkDb.getConnection("ckn_ci");
		PreparedStatement ps= conn.prepareStatement("select aid from ci_spec order by aid limit "+bp+", 10000");
		ResultSet rs= ps.executeQuery();

		int num=0;
		String csi=null;
		try {
			while( rs.next() ){
				csi=rs.getString(1);
				
				String cu="http://www.autohome.com.cn/spec/"+csi+"/#pvareaid=10006";
				Document doc=null;
				for(int i=1; i<= 10; i++) {
					try{
						doc=Jsoup.connect(cu).get();
						break;
					} catch(Exception ec) {
						System.out.println("x [veb] fail in 1-vipc: "+ csi +", time: "+i);
					}
				}
				if(doc==null)
					throw new RuntimeException("\tx [irp] 1-vipc csi: " +csi);
				
				Element e=doc.select(".cardetail-infor-car").get(0);
				
				String rate=null;
				String uoil=null;
				String measure=null;
				String oil=null;
				String frame=null;
				String insurance=null;
				String engine=null;
				String gearbox=null;
				String drive=null;
				StringBuilder photos = new StringBuilder();
				
				Elements is= e.select("ul > li");
				for(Element i:is) {
					String tx=i.text();
					String[] nv=tx.split("：");
					
					if( !ncare.contains(nv[0]) ) {
						if(nv.length > 1)
							switch(nv[0]) {
							case "用户评分":
								rate=nv[1];
								break;
							case "车主油耗":
								uoil=nv[1];
								break;
							case "车身尺寸":
								measure=nv[1];
								break;
							case "综合油耗":
								oil=nv[1];
								break;
							case "车身结构":
								frame=nv[1];
								break;
							case "整车质保":
								insurance=nv[1];
								break;
							case "发 动 机":
								engine=nv[1];
								break;
							case "变 速 箱":
								gearbox=nv[1];
							case "驱动方式":
								drive=nv[1];
							}
						else {
							System.out.println("x [wrn] csi: "+csi +", ca: "+nv[0]);
							FileUtils.write(new File("c:/wrn.txt"), "x [wrn] csi: "+csi +", ca: "+nv[0] + "\r\n", true);
						}
					}
				}
				
				String imu="http://car.autohome.com.cn/photolist/spec/"+csi+"/1/p1/?pvareaid=101474";
				Document idoc=null;
				for(int i=1; i<= 10; i++) {
					try{
						idoc=Jsoup.connect(imu).get();
						break;
					} catch(Exception ec) {
						System.out.println("x [veb] fail in 2-vipv: "+ csi +", time: "+i);
					}
				}
				if(idoc==null)
					throw new RuntimeException("\tx [irp] 2-vipv csi: " +csi);
				
				Elements ims=idoc.select("#pa1 > ul >li");
				Iterator<Element> imi=ims.iterator();
				
				while(imi.hasNext()) {
					Element ia= imi.next().child(0);
					String iam=ia.attr("href").replaceAll("^/", "http://car.autohome.com.cn/").replace("photo", "bigphoto");
					
					Document iadoc=null;
					for(int i=1; i<= 10; i++) {
						try{
							iadoc=Jsoup.connect(iam).get();
							break;
						} catch(Exception ec) {
							System.out.println("x [veb] fail in 3-vipb: "+ csi +", time: "+i);
						}
					} 
					if(iadoc==null)
						throw new RuntimeException("\tx [irp] 3-vipb csi: " +csi);
					
					if(iadoc.select("#img").size()==0)
						throw new RuntimeException("url:"+iam);
					Element ia1= iadoc.select("#img").get(0);
					
					photos.append(ia1.absUrl("src"));
					if(imi.hasNext()) photos.append(",");
					//System.out.println("read "+iam + " out down");
				}
	
				System.out.println(
						" rate: " +rate
						+"\n uoil: "+uoil
						+"\n measure: "+ measure
						+"\n oil: "+oil
						+"\n frame: "+frame
						+"\n insurance: "+insurance
						+"\n engine: "+engine
						+"\n gearbox: "+gearbox
						+"\n drive: "+drive
						+"\n photos: "+photos.toString());
				
				PreparedStatement ps2= conn.prepareStatement("update ci_spec set rate=?, uoil=?, measure=?, frame=?, insurance=?, engine=?, gearbox=?, drive=?, photos=?, oil=? where aid='"+csi+"'");
				ps2.setString(1, rate);
				ps2.setString(2, uoil);
				ps2.setString(3, measure);
				ps2.setString(4, frame);
				ps2.setString(5, insurance);
				ps2.setString(6, engine);
				ps2.setString(7, gearbox);
				ps2.setString(8, drive);
				ps2.setString(9, photos.toString());
				ps2.setString(10, oil);
				ps2.executeUpdate();
				MySQLWorkDb.closeStatement(ps2);
				System.out.println("x [upd] csi: "+csi);
				
				num++;
			}
		} catch(Exception e) {
			throw new RuntimeException("x [eor] csi:"+ csi +", num: "+(bp+num), e);
		}
		
		MySQLWorkDb.closeResult(rs);
		MySQLWorkDb.closeStatement(ps);
		MySQLWorkDb.closeConnection();
	}
	
}
