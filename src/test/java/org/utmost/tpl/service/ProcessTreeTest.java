package test.java.org.utmost.tpl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.utmost.tpl.service.ProcessTree;

public class ProcessTreeTest {
	ProcessTree processTree ;
	@Before
	public void setUp(){
		processTree = PowerMockito.mock(ProcessTree.class);
	}
	
	@Test
	public void testProcessTree(){
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ispk","true");
		map1.put("datacode","a");
		map1.put("datalength","b");
		map1.put("datatype","c");
		map1.put("generator","d");
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("ispk","false");
		map2.put("datacode","a");
		map2.put("datalength","b");
		map2.put("datatype","c");
		map2.put("generator","d");
		list.add(map1);
		list.add(map2);
		new ProcessTree(list,"123456");
	}
	
	@Test
	public void testToTree(){
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map1 = new HashMap<String, Object>();
		map1.put("ispk","true");
		map1.put("datacode","a");
		map1.put("datalength","b");
		map1.put("datatype","c");
		map1.put("generator","d");
		HashMap<String, Object> map2 = new HashMap<String, Object>();
		map2.put("ispk","false");
		map2.put("datacode","a");
		map2.put("datalength","b");
		map2.put("datatype","c");
		map2.put("generator","d");
		list.add(map1);
		list.add(map2);
		String str = new ProcessTree(list,"123456").toTree();
		Assert.assertNotNull(str);
	}
}
