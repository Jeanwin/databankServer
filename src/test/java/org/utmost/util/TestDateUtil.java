package test.java.org.utmost.util;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import junit.framework.Assert;

import org.junit.Test;
import org.utmost.util.DateUtil;

import antlr.collections.List;

public class TestDateUtil {
  @Test
  public void testFormatDate(){
	  Date date = new Date();
	  String result = DateUtil.formatDate(date, "yyyy-mm-dd");
	  Assert.assertEquals(new SimpleDateFormat("yyyy-mm-dd").format(date),result);
  }
  
  /**
	 * test modifying string date format:  mm/dd/yyyy to  yyyy-mm-dd
	 * @param list
	 * @param keyStrs
	 */
  @Test
  public void testTransferStrDate(){
	  ArrayList listWithMap = new ArrayList();
	  HashMap hp = new HashMap();
	  hp.put("test1", "04/16/2015");
	  hp.put("test2", "04/17/2015");
	  listWithMap.add(hp);
	  
	  ArrayList list = new ArrayList();
	  list.add("test1");
	  list.add("test2");
	  DateUtil.transferStrDate(listWithMap, list);
	  Assert.assertEquals("2015-04-16", hp.get("test1"));
	  Assert.assertEquals("2015-04-17", hp.get("test2"));
  }
  
  @Test
  public void testTransferDateStrFormat(){
	  String date = "2015-04-17";
	  DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
	  String result = DateUtil.transferDateStrFormat(date, df);
	  Assert.assertEquals("2015/04/17", result);
  }
}
