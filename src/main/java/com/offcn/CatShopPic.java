package com.offcn;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import com.offcn.po.product;

/**
 * 
 * asdkjs
 * @author Administrator
 *
 */
public class CatShopPic {

	public static void main(String[] args) {
		for (int i = 1; i < 38; i++) {
			
		
		
		String url="https://www.168.com/gallery-1149--1-0-"+i+"--grid-g.html";
		String html = catUrl(url);
		//System.out.println("html"+html);
		List<product>list=partsHtml(html);
		for (product p : list) {
			downloadPic(p.getPic());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
	}
	
	//抓取指定网址的源码
	public static String catUrl(String url){
		String html=null;
		CloseableHttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(url);
		try {
			CloseableHttpResponse response = client.execute(get);
			int statusCode = response.getStatusLine().getStatusCode();
			System.out.println(statusCode);
			if(statusCode==200){
				HttpEntity entity = response.getEntity();
				
				html=EntityUtils.toString(entity, "utf-8");
				
			}
			
			
		
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return html;
		
	}
	
	//jsoup解析指定的html字符串生成product对象
		public static List<product> partsHtml(String html){
			List<product> list = new ArrayList<product>();
			Document doc = Jsoup.parse(html);
			Elements elesps = doc.select(".items-gallery");
			for (Element e : elesps) {
				//抓取标题
				Element a = e.select("a[class=entry-title]").first();
				String title=a.text();
				String urlDetail="https://www.168.com"+a.attr("href");
				//抓取商品价格
				Element em = e.select("em[class=sell-price]").first();
				float price=Float.parseFloat(em.text().substring(1));
				//抓取商品图片
				Element img = e.select("img[class=img-lazyload]").first();
				String pic=img.attr("lazyload");
				product p = new product();
				p.setName(title);
				p.setPic(pic);
				p.setPrice(price);
				p.setUrl(urlDetail);
				System.out.println("抓取商品:"+p);
				list.add(p);
			}
			return list;
	
	}
		
		//下载图片，传入图片地址
		public static void downloadPic(String picurl){
			CloseableHttpClient client = HttpClients.createDefault();
			HttpGet get = new HttpGet(picurl);
			try {
				CloseableHttpResponse response = client.execute(get);
				int statusCode = response.getStatusLine().getStatusCode();
				if(statusCode==200){
					HttpEntity entity = response.getEntity();
					InputStream in = entity.getContent();
					
					//随机生成文件名称
					Random rd = new Random();
					int num = rd.nextInt(100000000);
					System.out.println("图片下载中"+num+".jpg");
					
					FileOutputStream out = new FileOutputStream("D:\\picture\\pic\\"+num+".jpg");
				
					byte b[]= new byte[4096];
					int len=-1;
					while ((len=in.read(b))!=-1) {
						out.write(b, 0, len);
						
					}
					out.close();
					in.close();
					
					
					
				}
			
			
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				client.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		public void www(){
			System.out.println("112121");
		}
		
		
		
		
		
		

}
