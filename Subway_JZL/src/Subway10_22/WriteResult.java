package Subway10_22;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WriteResult {
	public static void SavingResults(String retuslt,String Savingpath)throws IOException{
		StringReader saving=new StringReader(retuslt);
		BufferedReader buffer=new BufferedReader(saving);
		String string;
		if(Savingpath.equals("")) {
			throw new IOException("保存路径为空");
		}else {
			FileWriter file=new FileWriter(Savingpath,true);
			BufferedWriter writer=new BufferedWriter(file);
			writer.newLine();//新建一行
			Date date = new Date();
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			writer.write("查询时间:"+dateFormat.format(date));
			writer.newLine();
			while((string=buffer.readLine())!=null) {
				System.out.println("路径正确，正在保存中...");
				writer.write(string);
				writer.newLine();
			}
			buffer.close();
			writer.close();
			System.out.println("保存成功!");
		}
		
	}
}
