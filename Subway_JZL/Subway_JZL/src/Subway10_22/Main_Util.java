package Subway10_22;
/*
 * 路径：“/Users/allen666/Desktop/BeijingSubway.txt”
 */
import java.util.Scanner;

import Subway10_22.Line_Station.Graph;

public class Main_Util {
	static Graph graph;
	public static void main(String[] args) throws Exception{
		System.out.println("欢迎使用地铁线路查询(计算1802 金哲仑)");
		while(true) {//大菜单外循环
			Subway_Main.PrintMenu();//菜单输出类型1
			Scanner in=new Scanner(System.in);
			String inner=null;
			inner=in.nextLine();
			if(inner.contains("Exit")||inner.contains("exit")) {//退出操作
				System.out.println("退出系统!谢谢使用！");
				System.exit(0);
			}else if(inner.contains("Loading:")||inner.contains("loading:")) {//获取加载文件地址
				String path=inner.substring(inner.indexOf(':')+1,inner.length());//删除冒号前的内容
				graph=Subway_Main.LoadSubwayMap(path);//根据.txt文本内容生成图
				while(true) {//生成图后的内循环
					Subway_Main.PrintMenu2();//菜单输出类型2
					inner=null;
					inner=in.nextLine();//读取控制台输入
					String string;
					if(inner.contains("Line:")) {//查询线路详情操作
						string=Subway_Main.Lineinformation(inner);
						if(!string.equals(""))//如果获取为非空，执行写出文件操作
							Subway_Main.WriteFileJump(string);
					}else if(inner.contains("Query:")) {//查询两站之间最短线路操作
						string=Subway_Main.StationsQuery(graph,inner);
						if(!string.equals(""))//如果获取为非空，执行写出文件操作
							Subway_Main.WriteFileJump(string);
					}else if(inner.contains("Station:")) {//查询站点详情操作
						string=Subway_Main.StationQ(graph,inner);
						if(!string.equals(""))//如果获取为非空，执行写出文件操作
							Subway_Main.WriteFileJump(string);
					}else if(!inner.contains("Exit")&&!inner.contains("exit")) {//重复循环操作，保留生成图
						System.out.println("请输入菜单所示正确指令");
					}else {//退出循环操作
						System.out.println("退出系统!谢谢使用！");
						System.exit(0);
					}
				}
			}else {//判断是否输入正确路径
				System.out.println("请先输入\"Loading:\"导入线路txt文件");
			}
		}
		
	}
	
}
