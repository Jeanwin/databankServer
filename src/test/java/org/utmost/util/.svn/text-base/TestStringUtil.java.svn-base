package test.java.org.utmost.util;

import junit.framework.Assert;

import org.junit.Test;
import org.utmost.util.StringUtil;

public class TestStringUtil {
  @Test
  public void testGetChineseCount(){
	  String testString = "hello世界";
	  int result = StringUtil.getChineseCount(testString);
	  Assert.assertEquals(2, result);
  }
  
  @Test
  public void testfChar() throws Exception{
	  String test= "hello世界";
	  String result = StringUtil.fChar(test, "中", 10, 1);
	  Assert.assertEquals("中中中hello世界", result);
  }
  
  @Test
  public void testGetByteLength(){
	  String test= "hello世界";
	  int result = StringUtil.getByteLength(test);
	  Assert.assertEquals(9, result);
  }
}
