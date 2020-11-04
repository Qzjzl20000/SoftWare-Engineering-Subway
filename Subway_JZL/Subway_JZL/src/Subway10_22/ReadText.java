package Subway10_22;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import Subway10_22.Line_Station.Graph;
import Subway10_22.Line_Station.Staition;
public class ReadText {
	static Graph graph;
	public static Map<String, List<Staition>> LinesMap =new HashMap<String, List<Staition>>();//多条地铁线路组成的哈希图
	public static Map<String, Integer> StationName_Id=new HashMap<String, Integer>();//获取站点名转换站点id的哈希图
	public static Map<Integer, String> stationId_NameMap=new HashMap<Integer, String>();//获取站点id转换站点名的哈希图
	public static Map<String, Staition> StationName_Station=new HashMap<String, Staition>();//获取站点名转换站点的哈希图
	public static Graph LoadSubwayMap(String path) {//生成图函数
		System.out.println("正在读取路径"+path+"的内容");
		List<Staition> Vertics = new ArrayList<>();//设置站点集
		List<Line_Station.Graph.Edge> Edges = new ArrayList<>();//设置边集
		try {
			BufferedReader buffer=new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),"UTF-8"));//初始化字符输入流
			String Line=null;
			int StationNum=0;//记录站点个数
			while((Line=buffer.readLine())!=null) {
				String[] list=Line.split(" ");//以空格为分格
				List<Line_Station.Staition> stations=new ArrayList<>();
				String LineName =list[0];
				int bef=0;//前点
				int now=0;//后点，为记录相连两点
				for(int i=1;i<list.length;i++) {
					String StationName=list[i];
					Line_Station.Staition station;
					if(!StationName_Id.containsKey(StationName)) {//判断新站点，是否初始化站点
						station=new Staition();
						StationName_Station.put(StationName, station);//站点名映射站点
						station.stationName=StationName;
						station.Lines=new ArrayList<>();
						station.Lines.add(LineName);
						Vertics.add(station);
						StationName_Id.put(StationName, StationNum);//站点名映射站点id
						stationId_NameMap.put(StationNum, StationName);//站点id映射站点名
						StationNum+=1;
					}else {//非新站点则记录进入线路信息
						station =StationName_Station.get(StationName);
						station.Lines.add(LineName);
					}
					if(station.Lines.size()>=0)	station.IfTransfer=true;//记录多条线路的站点为换乘站点
					stations.add(station);
					if(i==1) {
						now=StationName_Id.get(StationName);//开始站点
					}else {
						bef=now;
						now=StationName_Id.get(StationName);
						Edges.add(new Line_Station.Graph.Edge(bef,now));//记录边信息
						Edges.add(new Line_Station.Graph.Edge(now,bef));
					}
				}
				LinesMap.put(LineName, stations);//记录线路名与多站点名映射
			}
			buffer.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("文件读取错误："+e);
			System.exit(0);
		}
		graph=new Graph(Vertics, Edges);
		System.out.println("文件已成功生成图！！");
		return graph;
	}
}
