package Subway10_22;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Subway10_22.Line_Station.Graph;
import Subway10_22.Line_Station.Staition;

public class Subway_Main {
	public static void PrintMenu() {//外循环大菜单
		System.out.println("1.请输入\"Loading:\"+本地地址,读取本地地铁线路txt文件");
		System.out.println("2.请输入\"Exit\"退出查询系统");
	}
	public static void PrintMenu2() {//图内循环中菜单
		System.out.println("地铁路线已获取，请使用以下关键词：");
		System.out.println("1.请输入\"Query:\"+站点名(空格)站点名,进行路线查询");
		System.out.println("2.请输入\"Line:\"+线路名,查询线路所含站点");
		System.out.println("3.请输入\"Station:\"+站点名,查询该站点具体信息");
		System.out.println("4.请输入\"Exit\"退出查询系统");
	}
	public static void WriteFileJump(String s) throws IOException {//写文件方法
		System.out.println("是否需要保存查询记录：");
		System.out.println("1.请输入\"Saving:\"+本地地址,保存在本地txt文件");
		System.out.println("2.输入\"NO\"不保存");
		Scanner in=new Scanner(System.in);
		String inner="";//写入初始化
		String SavingPath="";//保存路径初始化
		while(true) {
			inner=in.nextLine();
			if(inner.contains("Saving")) {//判断关键词Saving
				SavingPath=inner.substring(inner.indexOf(':')+1,inner.length());//截取冒号以后的内容（路径）
				WriteResult.SavingResults(s, SavingPath);
				break;
			}else if(inner.contains("NO")||inner.contains("no")||inner.contains("No")) {//判断类似关键词no
				System.out.println("放弃保存，结束此次查询");
				break;
			}else {
				System.out.println("请输入正确的关键词");//循环，直至输入正确关键词或选择不保存
			}
		}
	}
	public static Map<String, List<Staition>> LinesMap =new HashMap<String, List<Staition>>();//线路名与站点集的哈希映射
	public static Map<String, Integer> StationName_Id=new HashMap<String, Integer>();//站点名和站点id的哈希映射
	public static Map<Integer, String> stationId_NameMap=new HashMap<Integer, String>();//站点id和站点名的哈希映射
	public static Map<String, Staition> StationName_Station=new HashMap<String, Staition>();//站点名和站点类（自身）的哈希映射
	public static Graph LoadSubwayMap(String path) {//加载地铁生成图的方法
		System.out.println("正在读取路径"+path+"的内容");
		List<Staition> Vertics = new ArrayList<>();//地铁站点集
		List<Line_Station.Graph.Edge> Edges = new ArrayList<>();//站点组成的边集
		try {
			BufferedReader buffer=new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),"UTF-8"));//设置字符输入流
			String Line=null;
			int StationNum=0;
			while((Line=buffer.readLine())!=null) {//判断是否写入
				String[] list=Line.split(" ");//根据空格分割内容
				List<Line_Station.Staition> stations=new ArrayList<>();
				String LineName =list[0];
				int bef=0;int now=0;
				for(int i=1;i<list.length;i++) {
					String StationName=list[i];
					Line_Station.Staition station;
					if(!StationName_Id.containsKey(StationName)) {
						station=new Staition();
						StationName_Station.put(StationName, station);//设置映射关系
						station.stationName=StationName;//赋站点名初值
						station.Lines=new ArrayList<>();//赋站点线路初值
						station.Lines.add(LineName);//赋站点名线路名
						Vertics.add(station);
						StationName_Id.put(StationName, StationNum);//赋站点名和站点id映射关系
						stationId_NameMap.put(StationNum, StationName);//赋站点id和站点名映射关系
						StationNum+=1;
					}else {
						station =StationName_Station.get(StationName);
						station.Lines.add(LineName);
					}
					if(station.Lines.size()>1)
						station.IfTransfer=true;
					stations.add(station);
					
					if(i==1) {
						now=StationName_Id.get(StationName);
					}else {
						bef=now;
						now=StationName_Id.get(StationName);
						Edges.add(new Line_Station.Graph.Edge(bef,now));
						Edges.add(new Line_Station.Graph.Edge(now,bef));
					}
				}
				LinesMap.put(LineName, stations);
			}
			buffer.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("文件读取错误："+e);
			System.exit(0);
		}
		Graph graph=new Graph(Vertics, Edges);
		System.out.println("文件已成功生成图！！");
		return graph;
	}
	public static String Lineinformation(String inner) {//查询线路的方法类
		String QueryLineName=inner.substring(inner.indexOf(':')+1,inner.length());//获取冒号后面的线路名
		String resultString="";//初始化输出字符串
		try {
			if(!LinesMap.containsKey(QueryLineName)) {//若不包含任何线路名
				throw new Exception("查询不到"+QueryLineName);//抛出错误
			}
			System.out.println(QueryLineName+"详情信息:");
			List<Staition> stations=LinesMap.get(QueryLineName);//获取线路名与线路站点集的哈希映射
			resultString=QueryLineName+":";
			for(Staition station:stations) resultString+=" "+station.stationName;//遍历线路站点集，依次输出
			System.out.println(resultString);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return resultString;
	}
	public static String StationQ(Graph graph,String inner) {//查询站点详情信息方法
		String StationName=inner.substring(inner.indexOf(':')+1,inner.length());//获取冒号后面的站点名
		String resultString="";
		try {
			if(!StationName_Id.containsKey(StationName)) {//若不包含任何站点名
				throw new Exception("查询不到"+StationName);//抛出错误
			}
			System.out.println(StationName+"详情信息:");
			Staition stationQ=StationName_Station.get(StationName);//获取站点类实体与站点名的哈希映射
			
			int countTransfer=0;//初始化换乘线路次数
			for(String s:stationQ.Lines) {//遍历站点的线路集内容
				resultString+="所在线路:"+s+"\n";
				countTransfer++;//换乘+1
			}
			if(countTransfer<=1) {//只有一条线路经过
				resultString+="并非换乘站台";
			}else {//多线路经过，可换乘
				resultString+="为换乘站台";
			}
			System.out.println(resultString);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		return resultString;
	}
	public static List<String> FindPublicStations(String station1, String station2){//查找两个站点公共线路上的站点
		List<String> Line1=StationName_Station.get(station1).Lines;//获取站点名与站点实体的映射内容
		List<String> Line2=StationName_Station.get(station2).Lines;//获取站点名与站点实体的映射内容
		List<String> Line2EXIST=new ArrayList(Line2); 
		List<String> Line2NOTEXIST=new ArrayList(Line2); 
		Line2EXIST.removeAll(Line1);//剔除Line1与Line2的公共站点O（=Line2-Line1&&Line2）
		Line2NOTEXIST.removeAll(Line2EXIST);//剔除Line2除公共站点以外的站点（=Line2-(Line2-Line1&&Line2))
		return Line2NOTEXIST;//=Line1&&Line2
	}
	public static String StationsQuery(Graph graph,String inner) {//查询两点间最短路径的核心方法代码
		String Begin=inner.substring(inner.indexOf(':')+1,inner.indexOf(" "));//获取冒号后空格前的内容（站点1）
		String End=inner.substring(inner.indexOf(" ")+1,inner.length());//获取空格后的内容（站点2）
		System.out.println("查询"+Begin+"与"+End+"的最短线路：");
		try {
			if(!StationName_Id.containsKey(Begin)) {//判断是否存在输入的站点1
				throw new Exception("未找到"+Begin+"站");
			}
			if(!StationName_Id.containsKey(End)) {//判断是否存在输入的站点2
				throw new Exception("未找到"+End+"站");
			}
			if(Begin.equals(End)) {////判断站点1与站点2是否相同
				throw new Exception("查询两站相同");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		int TotalVertics=graph.Vertics.size();//总结点长
		int[] AllShortDistance=new int[TotalVertics];//出发站到各站点的最短距离
		int[] AllLeastChansfer=new int[TotalVertics];//出发站到各站点的最少换乘次数
		String[] AllLine=new String[TotalVertics];//出发站到各站点的最短路径途径站记录
		int[] AllBeforeStation=new int[TotalVertics];///出发站到各站点的前一个途径站点id
		boolean[] AllIfVisited = new boolean[TotalVertics];//检查各站点途径记录
		
		for(int i=0; i<TotalVertics; i++) {//各点初始化 
			AllShortDistance[i] = Integer.MAX_VALUE; //初始距离无限大
			AllLeastChansfer[i] = 0;//初始换乘0次
		}
		//起点初始化
		AllShortDistance[StationName_Id.get(Begin)]=0;//起始距离0
		AllBeforeStation[StationName_Id.get(Begin)]=-1;//无途径站点
		
		int CountNum=0;//记录扫描站点
		while(CountNum<TotalVertics) {
			int RecentV=0;//当前查到的最近站点id
			int MinDistance = Integer.MAX_VALUE;//当前最短距离
			for(int i=0;i<TotalVertics; i++) {  //选择当前距离最近的站点
				if((!AllIfVisited[i]) && (AllShortDistance[i]<MinDistance)) {
					RecentV = i;//赋值最近站点id
					MinDistance = AllShortDistance[i];//赋值最近站点距离
				}
//				System.out.print(RecentV+" ");
			}
			AllIfVisited[RecentV] = true;  //将该站点设置为已访问
			CountNum++;
			List<Graph.Edge> Edges=graph.Edges.get(RecentV);//获取该站点邻接关系
			for(Graph.Edge edge:Edges) {//遍历边集，筛选标准线路站点少，且换乘少
				if(!AllIfVisited[edge.v]&&AllShortDistance[RecentV]+1<AllShortDistance[edge.v]) {//寻找非访问过的，且路径小的线路
					List<String> StayLine=FindPublicStations(stationId_NameMap.get(edge.u), stationId_NameMap.get(edge.v));
					AllLine[edge.v]=StayLine.get(0);//更新临近点线路
					if(!stationId_NameMap.get(edge.u).equals(Begin)) {//判断最近点是否为起点
						if(AllLine[edge.u]!=AllLine[edge.v]) {//判断是否在同一线路，不在一条线路，换乘+1
							AllLeastChansfer[edge.v]=AllLeastChansfer[edge.u]+1;
						}
						else {
							AllLeastChansfer[edge.v]=AllLeastChansfer[edge.u];
						}
					}
					AllShortDistance[edge.v]=AllShortDistance[RecentV]+1;//更新最短路径距离（+1）
					AllBeforeStation[edge.v]=edge.u;
				}else if(!AllIfVisited[edge.v]&&AllShortDistance[RecentV]+1==AllShortDistance[edge.v]) {//当距离一致时，考虑最少换乘
					List<String> StayLine=FindPublicStations(stationId_NameMap.get(edge.u), stationId_NameMap.get(edge.v));
					AllLine[edge.v]=StayLine.get(0);
					if(!stationId_NameMap.get(edge.u).equals(Begin)) {
						if(AllLine[edge.u]!=AllLine[edge.v]) {//判断是否在同一条线路
							if(AllLeastChansfer[edge.u]+1<AllLeastChansfer[edge.v]) {//两站点不在同一线路，距离+1，换乘+1，设置前站点
								AllLeastChansfer[edge.v]=AllLeastChansfer[edge.u]+1;
								AllShortDistance[edge.v]=AllShortDistance[RecentV]+1;
								AllBeforeStation[edge.v]=edge.u;
							}
						}else {
							if(AllLeastChansfer[edge.u]<AllLeastChansfer[edge.v]) {//若两站点在同一线路，将距离+1，换乘不+1，设置前站点
								AllLeastChansfer[edge.v]=AllLeastChansfer[edge.u];
								AllShortDistance[edge.v]=AllShortDistance[RecentV]+1;
								AllBeforeStation[edge.v]=edge.u;
							}
						}
					}
				}
			}
		}
		int StationCount=0;//初始化途径站点数
		int TransferCount=0;//初始化换乘次数
		ArrayList<Integer> ReversePath=new ArrayList<>();//记录逆序输出顺序（AllBeforeStation顺序相反）
		int next =StationName_Id.get(End);//获取终点站id
		while(AllBeforeStation[next]!=-1) {//遍历直至起始站点
			StationCount++;//增加途径站
			ReversePath.add(next);//增加在输出站点集里
			next=AllBeforeStation[next];//获取前站，持续循环寻找前站
		}
		
		String ShortPath=Begin+"—>"+stationId_NameMap.get(ReversePath.get(ReversePath.size()-1));//输出格式（起始->终点）
		String PresentLine=FindPublicStations(Begin, stationId_NameMap.get(ReversePath.get(ReversePath.size()-1))).get(0);//获取路线
		
		for(int i=ReversePath.size()-2;i>0;i--) {//输出线路
			String nameString=stationId_NameMap.get(ReversePath.get(i));
			String lastString=stationId_NameMap.get(ReversePath.get(i+1));
			List<String> LocalLine=FindPublicStations(nameString, lastString);
			if(!LocalLine.get(0).equals(PresentLine)) {//判断是否有线路换乘
				ShortPath+="->\n换乘"+LocalLine.get(0)+"\n";
				PresentLine=LocalLine.get(0);
				TransferCount++;
			}
			ShortPath+="->"+nameString;
		}
		StationCount--;
		ShortPath+="->"+End;
		ShortPath+="\n共经过：" + StationCount +"站（不包含查询两站点）\n"
				+ "共换乘：" + TransferCount + "次";
		System.out.println("成功查询"+Begin+"与"+End+"两站间的最短线路:");
		System.out.println(ShortPath);
		return ShortPath;
	}
}