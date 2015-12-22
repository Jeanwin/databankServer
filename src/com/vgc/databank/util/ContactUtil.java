/**
 * 2015-1-28 10:02:03
 * This class is used for contact us manipulation
 * 
 */
package com.vgc.databank.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Service;

import com.vgc.databank.service.RightService;

/**
 * Contact util
 * save contact person info to contactUs.xml,
 * get contact person info from contactUs.xml
 * 
 * @author Zhao Wei
 *
 */
@Service("ContactUtil")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ContactUtil {
	/**
	 * saveContactUs:save contact us modify info(for administrator)
	 * @param xml
	 * @return
	 * @throws DocumentException
	 * @throws IOException
	 */
	public HashMap saveContactUs(String xml) throws DocumentException, IOException{
		String pathTmp = RightService.class.getResource("/conf/contactUs.xml").getPath();
		String path = pathTmp.substring(pathTmp.indexOf("/"));		//obtain xml file path
		OutputFormat format = OutputFormat.createPrettyPrint();
		format.setEncoding("GBK");		//set encoding 'gbk', display normal when Chinese in file
		XMLWriter writer = null;
		SAXReader reader = new SAXReader();
		File file = new File(path);
		Document rawDoc = reader.read(file);
		
		Document doc = DocumentHelper.parseText(xml);
		String xname = "//contactUs/name";
		String xposition = "//contactUs/position";
		String xaddress = "//contactUs/address";
		String xphone = "//contactUs/phone";
		String xmobile = "//contactUs/mobile";
		String xfax = "//contactUs/fax";
		String xemail = "//contactUs/email";
		
		Node name = doc.selectSingleNode(xname);
		Node rawName = rawDoc.selectSingleNode(xname);
		rawName.setText(name.getText());
		
		Node position = doc.selectSingleNode(xposition);
		Node rawPosition = rawDoc.selectSingleNode(xposition);
		rawPosition.setText(position.getText());
		
		Node address = doc.selectSingleNode(xaddress);
		Node rawAddress = rawDoc.selectSingleNode(xaddress);
		rawAddress.setText(address.getText());
		
		Node phone = doc.selectSingleNode(xphone);
		Node rawPhone = rawDoc.selectSingleNode(xphone);
		rawPhone.setText(phone.getText());
		
		Node mobile = doc.selectSingleNode(xmobile);
		Node rawMobile = rawDoc.selectSingleNode(xmobile);
		rawMobile.setText(mobile.getText());
		
		Node fax = doc.selectSingleNode(xfax);
		Node rawFax = rawDoc.selectSingleNode(xfax);
		rawFax.setText(fax.getText());
		
		Node email = doc.selectSingleNode(xemail);
		Node rawEmail = rawDoc.selectSingleNode(xemail);
		rawEmail.setText(email.getText());
		
		//save info modified into contactUs.xml
		FileOutputStream fos=new FileOutputStream(new File(path));
		writer = new XMLWriter(fos, format);
		writer.write(rawDoc);
		writer.flush();
		writer.close();
		HashMap isSuccess = new HashMap();
		isSuccess.put("isSuccess", "success");
		return isSuccess;
	}
	/**
	 * get contact info
	 * info is xml string
	 * @return
	 * @throws DocumentException
	 */
	public HashMap<String,String> obtainContactInfo() throws DocumentException{
  	    SAXReader reader = new SAXReader();
        String pathTmp = RightService.class.getResource("/conf/contactUs.xml").getPath();
        String path = pathTmp.substring(pathTmp.indexOf("/"));
        File file = new File(path);
        Document doc = reader.read(file);
		
		String xname = "//contactUs/name";
		String xposition = "//contactUs/position";
		String xaddress = "//contactUs/address";
		String xphone = "//contactUs/phone";
		String xmobile = "//contactUs/mobile";
		String xfax = "//contactUs/fax";
		String xemail = "//contactUs/email";
		
		Node name = doc.selectSingleNode(xname);
		Node position = doc.selectSingleNode(xposition);
		Node address = doc.selectSingleNode(xaddress);
		Node phone = doc.selectSingleNode(xphone);
		Node mobile = doc.selectSingleNode(xmobile);
		Node fax = doc.selectSingleNode(xfax);
		Node email = doc.selectSingleNode(xemail);
		
		//build xml string
		StringBuilder xml = new StringBuilder("<contactUs>\n");
		xml.append("<name>"+ name.getText() +"</name>\n");
		xml.append("<position>"+ position.getText() +"</position>\n");
		xml.append("<address>"+ address.getText() +"</address>\n");
		xml.append("<phone>"+ phone.getText() +"</phone>\n");
		xml.append("<mobile>"+ mobile.getText() +"</mobile>\n");
		xml.append("<fax>"+ fax.getText() +"</fax>\n");
		xml.append("<email>"+ email.getText() +"</email>\n");
		xml.append("</contactUs>");
		HashMap<String,String> xmlMap = new HashMap<String,String>();
		xmlMap.put("xml", xml.toString());
		return xmlMap;
	}

}
