package Subway10_22;
import java.util.*;
public class Line_Station {
	//用于定义点、线、图的List集合
	//定义构造点、线的方法，定义创建图的方法
	public static class Staition{//定义地铁站点
		String stationName;//线路站点名称
		List<String> Lines;//所在线路<字符串>
		boolean IfTransfer=false;//是否为换乘站
	}
	public static class Graph{//基本线路站点图信息
		public List<Line_Station.Staition> Vertics=new ArrayList<>();//多个点定义集
		public List<List<Edge>> Edges=new ArrayList<List<Edge>>();//多个边定义集
		public Graph() {//构造图的无参方法
		}
		public Graph(List<Line_Station.Staition>verG,List<Edge>edgG) {
			CreatG(verG, edgG);//定义创建图方法
		}
		public static class Edge{//定义
			public int u,v;//定义两点之间连线为边
			public Edge() {//构造边的无参方法
			}
			public Edge(int u,int v) {//定义两点连成边
				this.u=u;
				this.v=v;
			}
			public boolean equal(Edge edges) {//判断是否为两站点边的方法
				if(this.u==edges.u&&this.v==edges.v)return true; //连线两站点确定
				else return false;
			}
		}
		public void CreatG(List<Line_Station.Staition>vertics,List<Edge> edges) {
			//构建地铁图线路图
			this.Vertics=vertics;//传递地铁站点集
			for(int i=0;i<Vertics.size();i++) {//增加地铁线路集
				Edges.add(new ArrayList<Edge>());
			}
//			for(int i=0;i<edges.size();i++) {//遍历站点连线边集，链接创建图
//				Edges.get(edges.get(i).u).add(edges.get(i));
//			}
			for(Edge edge:edges) {//遍历站点连线边集，链接创建图(WB)
				Edges.get(edge.u).add(edge);
			}
		}
	}
}
