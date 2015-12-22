package org.utmost.util;

import java.lang.reflect.Method;
import java.util.HashMap;
/**
 * class util
 * reflect a class
 * @author bull
 *
 */
public class ClassUtil {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		HashMap hm = new HashMap();
		hm.put("dd", "asdf");
		ClassUtil cu = new ClassUtil();
		ClassUtil.invokeMethod(cu, "testThis", hm);  //test reflect ClassUtil
	}

	public Object testThis(HashMap hm) {
		System.out.println("hm:" + hm);
		return hm;
	}
	

	/**
	 * @param methodObject
	 *            method object
	 * @param methodName
	 *            method name
	 * @param hm
	 *            method name param
	 */
	public static Object invokeMethod(Object methodObject, String methodName,
			Object obj) throws Exception {
		Class ownerClass = methodObject.getClass();
		Method method = null;
		if (obj != null) {
			method = ownerClass.getMethod(methodName, obj.getClass());
			return method.invoke(methodObject, obj);
		} else {
			method = ownerClass.getMethod(methodName);
			return method.invoke(methodObject);
		}
	}

}
