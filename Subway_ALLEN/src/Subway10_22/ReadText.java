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
	public static Map<String, List<Staition>> LinesMap =new HashMap<String, List<Staition>>();
	public static Map<String, Integer> StationName_Id=new HashMap<String, Integer>();
	public static Map<Integer, String> stationId_NameMap=new HashMap<Integer, String>();
	public static Map<String, Staition> StationName_Station=new HashMap<String, Staition>();
	public static Graph LoadSubwayMap(String path) {
		System.out.println("正在读取路径"+path+"的内容");
		List<Staition> Vertics = new ArrayList<>();
		List<Line_Station.Graph.Edge> Edges = new ArrayList<>();
		try {
			BufferedReader buffer=new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)),"UTF-8"));
			String Line=null;
			int StationNum=0;
			while((Line=buffer.readLine())!=null) {
				String[] list=Line.split(" ");
				List<Line_Station.Staition> stations=new ArrayList<>();
				String LineName =list[0];
				int bef=0;int now=0;
				for(int i=1;i<list.length;i++) {
					String StationName=list[i];
					Line_Station.Staition station;
					if(!StationName_Id.containsKey(StationName)) {
						station=new Staition();
						StationName_Station.put(StationName, station);
						station.stationName=StationName;
						station.Lines=new ArrayList<>();
						station.Lines.add(LineName);
						Vertics.add(station);
						StationName_Id.put(StationName, StationNum);
						stationId_NameMap.put(StationNum, StationName);
						StationNum+=1;
					}else {
						station =StationName_Station.get(StationName);
						station.Lines.add(LineName);
					}
					if(station.Lines.size()>=0)	station.IfTransfer=true;
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
		graph=new Graph(Vertics, Edges);
		System.out.println("文件已成功生成图！！");
		return graph;
	}
}
