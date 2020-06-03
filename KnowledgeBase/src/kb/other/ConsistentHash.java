package kb.other;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

//import org.apache.commons.codec.digest.DigestUtils;

public class ConsistentHash {
	
	private TreeMap<Integer, String> serverNodeMap = null;
	
	private final static int VIRTUAL_NODE_NUMBER = 5;
	
	
	public void getServerNodeWithoutVirtualNode(List<String> servers) {
		serverNodeMap = new TreeMap<>();
		for (String string : servers) {
			serverNodeMap.put(hash(string), string);
		}
	}
	
	public void getServerNodeWithVirtualNode(List<String> servers) {
		serverNodeMap = new TreeMap<>();
		for (String string : servers) {
			for (int i = 0; i < VIRTUAL_NODE_NUMBER; i++) {
				String virtualNodeName = string + ":" + i;
				serverNodeMap.put(hash(virtualNodeName), string);
			}
			
		}
	}
	
	public String getServerName(String data) {
		int dataHash = hash(data);
		SortedMap<Integer, String> subMap = serverNodeMap.tailMap(dataHash);
		int serverHash = 0;
		if (subMap == null || subMap.size() == 0) {
			serverHash = serverNodeMap.firstKey();
		}else {
			serverHash = subMap.firstKey();
		}
		
		String serverName = serverNodeMap.get(serverHash);
		return serverName;
		
	}
	
	
	/**
	 * hash计算，这里使用md5后取hashcode，这个md5需要依赖apache的codec包
	 * @param str
	 * @return
	 */
	public int hash(String str) {
//		return DigestUtils.md5Hex(str).hashCode();
		return str.hashCode();
	}
	
	public static void main(String[] args) {
		List<String> servers = new ArrayList<>();
		servers.add("192.168.1.1");
		servers.add("192.168.1.2");
		servers.add("192.168.1.3");
		servers.add("192.168.1.4");
		servers.add("192.168.1.5");
		servers.add("192.168.1.6");
		List<String> datas = new ArrayList<>();
		datas.add("河南");
		datas.add("山东");
		datas.add("天津");
		datas.add("北京");
		datas.add("上海");
		datas.add("广州");
		datas.add("乌海");
		datas.add("武汉");
		datas.add("合肥");
		datas.add("长沙");
		ConsistentHash consistentHash = new ConsistentHash();
		System.out.println("没有虚拟节点的情况：");
		consistentHash.getServerNodeWithoutVirtualNode(servers);
		consistentHash.printDataAndServerNode(servers, datas, consistentHash);
		System.out.println("有虚拟节点的情况：");
		consistentHash.getServerNodeWithVirtualNode(servers);
		consistentHash.printDataAndServerNode(servers, datas, consistentHash);
		
//		servers.remove(0);
//		System.out.println("移除第一个一个节点后：");
//		System.out.println("没有虚拟节点的情况：");
//		consistentHash.getServerNodeWithoutVirtualNode(servers);
//		consistentHash.printDataAndServerNode(servers, datas, consistentHash);
//		System.out.println("有虚拟节点的情况：");
//		consistentHash.getServerNodeWithVirtualNode(servers);
//		consistentHash.printDataAndServerNode(servers, datas, consistentHash);
		
	}

	public void printDataAndServerNode(List<String> servers, List<String> datas,
			ConsistentHash consistentHash) {
		Map<String, String> result = new HashMap<>();
		for (String data : datas) {
			String serverName = consistentHash.getServerName(data);
			if (!result.containsKey(serverName)) {
				result.put(serverName, data);
			}else {
				result.put(serverName, result.get(serverName) + "," + data);
			}
		}
		
		for (Entry<String, String> entry : result.entrySet()) {
			System.out.println(entry.getKey()+":"+entry.getValue());
		}
	}
}