package com.vgc.databank.junit;


import java.util.HashMap;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.vgc.databank.service.RightService;
import com.vgc.databank.util.FuncUtil;

/**
 * Test RightService class
 * @author bull
 *
 */
public class TestRightService {
	@Autowired
	public static FuncUtil funcUtil;
	/**
	 * load spring context, then obtain bean
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("conf/applicationContext.xml");
		funcUtil = (FuncUtil)applicationContext.getBean("FuncUtil");
	}
	/**
	 * print result, check data from bean
	 */
	@Test
	public void tests(){
		List<HashMap> list = funcUtil.queryChildFuncsByFuncId("2", "020101");
		System.out.println("--------"+list.size());
		for(int i=0;i<list.size(); i++){
			System.out.println(list.get(i).get("funcuuid"));
		}
	}
}