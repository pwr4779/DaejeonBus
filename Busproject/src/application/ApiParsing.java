package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.gson.JsonParser;

public class ApiParsing {
	String apikey = "u97rBxI2lakyVFjJGuC1jlhMNARjbTXJ8fcFDh9eC6OwNviKNo9vc2tU5AO1W6XOz8K32GCkXRe3HsVUsRmKAw%3D%3D";
	//String apikey = "AXGKHt9UEDepVZXg8NVjcKhYkocLKAeoi8PiBgJs42yKpgsnr84gqVD%2BRKjMcoHuHjhbP0UV6kCXTWtr9vsm4g";
	String geolocationapikey = "AIzaSyDXsdmbcq_hJWDGiXhzpssl4HPiK1bkQns";
	RouteTable mRouteTable = RouteTable.getInstance();
	BusStationTable mBusStationTable = BusStationTable.getInstance();
	Map<String, Route> routeTable = mRouteTable.getRouteTable();
	Map<String, BusStation> busStationTable = mBusStationTable.getBusStationTable();
	
	private static class _Holder {
	
		private static final ApiParsing instance = new ApiParsing();
	}

	public static ApiParsing getInstance() {
		return _Holder.instance;
	}

	public void initData() {
		try {
            BufferedReader br = new BufferedReader(new FileReader("RouteData.txt"));
            String line;
            String[] splitdata = null;
			while ((line = br.readLine()) != null) {
				splitdata = line.split(",");
				String ROUTE_CD = splitdata[0];
				String ROUTE_NO = splitdata[1];
				String ROUTE_TP = splitdata[2];
				String START_NODE_ID = splitdata[3];
				String END_NODE_ID = splitdata[4];
				String TURN_NODE_ID = splitdata[5];
				routeTable.put(ROUTE_CD,new Route(ROUTE_CD, ROUTE_NO, ROUTE_TP, START_NODE_ID, END_NODE_ID,TURN_NODE_ID));
			}
			br.close();

			br = new BufferedReader(new FileReader("BusStationData.txt"));

			splitdata = null;
			while ((line = br.readLine()) != null) {
				splitdata = line.split(",");
				String BUS_NODE_ID = splitdata[0];
				String BUSSTOP_NM = splitdata[1];
				String GPS_LATI = splitdata[2];
				String GPS_LONG = splitdata[3];
				//System.out.println(BUSSTOP_NM);
				busStationTable.put(BUS_NODE_ID, new BusStation(BUS_NODE_ID, BUSSTOP_NM, GPS_LATI, GPS_LONG));
			}
			System.out.println("busStationTable setting");
			br.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		
		}
		
		
		/*String GetAllRouteUrl = "http://openapitraffic.daejeon.go.kr/api/rest/busRouteInfo/getRouteInfoAll?serviceKey="
				+ apikey + "&reqPage=1";
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(GetAllRouteUrl);
			doc.getDocumentElement().normalize();
			System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			NodeList nList = doc.getElementsByTagName("itemList");
			// System.out.println("�Ľ��� ����Ʈ �� : "+ nList.getLength());
			File file = new File("RouteData.txt");

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;

						String w = getTagValue("ROUTE_CD", eElement).trim() + ","
								+ getTagValue("ROUTE_NO", eElement).trim() + ","
								+ getTagValue("ROUTE_TP", eElement).trim() + ","
								+ getTagValue("START_NODE_ID", eElement).trim() + ","
								+ getTagValue("END_NODE_ID", eElement).trim();
						bw.write(w);
						bw.newLine();

						routeTable.put(getTagValue("ROUTE_CD", eElement).trim(), new Route(
								getTagValue("ROUTE_CD", eElement).trim(), getTagValue("ROUTE_NO", eElement).trim(),
								getTagValue("ROUTE_TP", eElement).trim(), getTagValue("START_NODE_ID", eElement).trim(),
								getTagValue("END_NODE_ID", eElement).trim()));
					} // for end
				} // if end
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			file = new File("BusStationData.txt");

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));

				for (String key : routeTable.keySet()) {
					System.out.println("finish");
					Route routevalue = routeTable.get(key);
					// System.out.println(routevalue.RouteID);
					String RouteID = routevalue.getRouteID();
					// ��ü�뼱
					String GetRouteBusStationlistUrl = "http://openapitraffic.daejeon.go.kr/api/rest/busRouteInfo/getStaionByRoute?busRouteId="
							+ RouteID + "&serviceKey=" + apikey;
					doc = dBuilder.parse(GetRouteBusStationlistUrl);
					doc.getDocumentElement().normalize();
					System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
					nList = doc.getElementsByTagName("itemList");

					for (int temp = 0; temp < nList.getLength(); temp++) {
						Node nNode = nList.item(temp);
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) nNode;

							String mBusStation = getTagValue("BUS_NODE_ID", eElement).trim();
							routevalue.addBusStationList(mBusStation);

							String w = getTagValue("BUS_NODE_ID", eElement).trim() + ","
									+ getTagValue("BUSSTOP_NM", eElement).trim() + ","
									+ getTagValue("GPS_LATI", eElement).trim() + ","
									+ getTagValue("GPS_LONG", eElement).trim();
							bw.write(w);
							bw.newLine();

							busStationTable.put(mBusStation,
									new BusStation(mBusStation, getTagValue("BUSSTOP_NM", eElement).trim(),
											getTagValue("GPS_LATI", eElement).trim(),
											getTagValue("GPS_LONG", eElement).trim()));
						} // for end

					} // if end
				}
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.out.println("++++++++++++++++++++++++++++");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			*/
			
		return;
	}
	
	public void myLocation() { 
		
		String LocationUrl ="https://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&key="+geolocationapikey;
		JsonParser jsonParser = new JsonParser();
		
	}
	
	private static String getTagValue(String tag, Element eElement) {
	    NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
	    Node nValue = (Node) nlList.item(0);
	    if(nValue == null) 
	        return null;
	    return nValue.getNodeValue();
	}
	
	
	public ArrayList<String> RealtimeRouteLocationParsing(Route routevalue, String RouteId,String type) {
		List<String> Location = new ArrayList<>(); 
		String Url = "http://openapitraffic.daejeon.go.kr/api/rest/busposinfo/getBusPosByRtid?busRouteId="+RouteId+"&serviceKey="+apikey;
		DocumentBuilderFactory dbFactoty = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactoty.newDocumentBuilder();
			Document doc = dBuilder.parse(Url);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("itemList");
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String mBusStation = getTagValue("BUS_NODE_ID", eElement).trim();
					Location.add(mBusStation);
					/*if((getTagValue("ud_type", eElement).trim()).equals(type)) {
						Location.add(new RouteOfBusLocation(getTagValue("GPS_LATI", eElement).trim(),
								getTagValue("GPS_LONG", eElement).trim()));
					}*/
					
				} // for end
			} // if end
			
			String GetRouteBusStationlistUrl = "http://openapitraffic.daejeon.go.kr/api/rest/busRouteInfo/getStaionByRoute?busRouteId="
					+ RouteId + "&serviceKey=" + apikey;
			doc = dBuilder.parse(GetRouteBusStationlistUrl);
			doc.getDocumentElement().normalize();
			//System.out.println("Root element: " + doc.getDocumentElement().getNodeName());
			nList = doc.getElementsByTagName("itemList");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String mBusStation = getTagValue("BUS_NODE_ID", eElement).trim();
					routevalue.addBusStationList(mBusStation);
					
				} // for end

			} // if end
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (ArrayList<String>) Location;
		// TODO Auto-generated method stub
		
	}
	
		
	
}
