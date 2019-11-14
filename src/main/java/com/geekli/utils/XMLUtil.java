package com.geekli.utils;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class XMLUtil {
    public static Map doXMLParse(String strxml) throws JDOMException, IOException {
        strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
        if (null == strxml || "".equals(strxml)) {
            return null;
        }
        Map m = new HashMap();
        InputStream inputStream = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document document = builder.build(inputStream);
        Element element = document.getRootElement();
        List list = element.getChildren();
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
            Element ele = (Element) iterator.next();
            String k = ele.getName();
            String v = "";
            List children = ele.getChildren();
            if (children.isEmpty()) {
                v = ele.getTextNormalize();
            } else {
                v = XMLUtil.getChildrenText(children);
            }
            m.put(k, v);
        }
        inputStream.close();
        return m;
    }

    public static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element ele = (Element) it.next();
                String name = ele.getName();
                String value = ele.getTextNormalize();
                List list = ele.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(XMLUtil.getChildrenText(list));
                }
                sb.append(value);
                sb.append("<" + name + ">");
            }
        }
        return sb.toString();
    }
}
