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
		StringReader saving=new StringReader(retuslt);//记录的文件路径
		BufferedReader buffer=new BufferedReader(saving);//创建字符输入流
		String string;
		if(Savingpath.equals("")) {//判断文件路径是否为空
			throw new IOException("保存路径为空");
		}else {
			FileWriter file=new FileWriter(Savingpath,true);//创建写文件方法
			BufferedWriter writer=new BufferedWriter(file);
			writer.newLine();//新建一行
			Date date = new Date();//记录本机本地时间
			SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//设置时间输出格式
			writer.write("查询时间:"+dateFormat.format(date));//写入查询时间
			writer.newLine();//换行
			while((string=buffer.readLine())!=null) {//设置
				System.out.println("路径正确，正在保存中...");
				writer.write(string);//分行写入
				writer.newLine();
			}
			buffer.close();
			writer.close();
			System.out.println("保存成功!");
		}
	}
}
