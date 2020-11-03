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
		while(true) {
			Subway_Main.PrintMenu();
			Scanner in=new Scanner(System.in);
			String inner=null;
			inner=in.nextLine();
			if(inner.contains("Exit")||inner.contains("exit")) {
				System.out.println("退出系统!谢谢使用！");
				System.exit(0);
			}else if(inner.contains("Loading:")||inner.contains("loading:")) {
				String path=inner.substring(inner.indexOf(':')+1,inner.length());
				graph=Subway_Main.LoadSubwayMap(path);
				while(true) {
					Subway_Main.PrintMenu2();
					inner=null;
					inner=in.nextLine();
					String string;
					if(inner.contains("Line:")) {
						string=Subway_Main.Lineinformation(inner);
						if(!string.equals(""))
							Subway_Main.WriteFileJump(string);
					}else if(inner.contains("Query:")) {
						string=Subway_Main.StationsQuery(graph,inner);
						if(!string.equals(""))
							Subway_Main.WriteFileJump(string);
					}else if(inner.contains("Station:")) {
						string=Subway_Main.StationQ(graph,inner);
						if(!string.equals(""))
							Subway_Main.WriteFileJump(string);
					}else if(!inner.contains("Exit")&&!inner.contains("exit")) {
						System.out.println("请输入菜单所示正确指令");
					}else {
						System.out.println("退出系统!谢谢使用！");
						System.exit(0);
					}
				}
			}else {
				System.out.println("请先输入\"Loading:\"导入线路txt文件");
			}
		}
		
	}
	
}
