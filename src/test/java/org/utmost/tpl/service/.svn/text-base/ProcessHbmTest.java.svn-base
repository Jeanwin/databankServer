package test.java.org.utmost.tpl.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.utmost.tpl.service.ProcessHbm;

public class ProcessHbmTest {
	ProcessHbm procescsHbm ;
	@Before
	public void setUp(){
		procescsHbm = PowerMockito.spy(new ProcessHbm());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testProcess(){
		List<HashMap> list = new ArrayList<HashMap>();
		HashMap map1 = new HashMap<String, String>();
		map1.put("ispk","true");
		map1.put("datacode","a");
		map1.put("datalength","b");
		map1.put("datatype","c");
		map1.put("generator","d");
		HashMap map2 = new HashMap<String, String>();
		map2.put("ispk","false");
		map2.put("datacode","a");
		map2.put("datalength","b");
		map2.put("datatype","c");
		map2.put("generator","d");
		list.add(map1);
		list.add(map2);
		String str = procescsHbm.process(list,"123456");
		//System.out.println(str);
		Assert.assertNotNull(str);
	}
}
