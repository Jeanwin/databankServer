package test.java.vgc.databank.service;
/**
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.reflect.Whitebox;

import com.vgc.databank.service.PortalFuncService;
import com.vgc.databank.service.RightService;
import com.vgc.databank.util.FuncUtil;
/**
 * @author BULL
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class PortalFuncServiceTest {

	PortalFuncService portalFuncService ;
	
	RightService rightService;
	
	FuncUtil funcUtil;
	@Before
	public void setUp() throws Exception {
		portalFuncService = PowerMockito.spy(new PortalFuncService());
		rightService = PowerMockito.mock(RightService.class);
		funcUtil = PowerMockito.mock(FuncUtil.class);
		Whitebox.setInternalState(portalFuncService, "rightService", rightService);
		Whitebox.setInternalState(portalFuncService, "funcUtil", funcUtil);
	}
	

	@Test
	public void testAllChildrenByPid() {
		HashMap map = new HashMap();
		map.put("funcpid", "1");
		map.put("nodetype", "2");
		map.put("funcuuid", "1001");
		List list = new ArrayList();
		list.add(map);
		List result = portalFuncService.allChildrenByPid("1",list);
		Assert.assertEquals(1, result.size());
	}
}
