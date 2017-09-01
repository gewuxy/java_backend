package cn.medcn.common.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.springframework.util.Assert;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by lixuan on 2017/1/5.
 */
public class XMLUtils {
        /**
         * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
         * @param strxml
         * @return
         * @throws JDOMException
         * @throws IOException
         */
        public static Map doXMLParse(String strxml) throws JDOMException, IOException {
            //strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
            if(null == strxml || "".equals(strxml)) {
                return null;
            }
            Map m = new HashMap();
            InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(in);
            Element root = doc.getRootElement();
            List list = root.getChildren();
            String ename = "";
            boolean hasSameElements = false;
            for (int i =0 ; i< list.size(); i++){
                Element e = (Element) list.get(i);
                String newename = e.getName();
                if (newename.equals(ename)){
                    hasSameElements = true;
                    break;
                }
                ename = newename;
            }
            if (hasSameElements){
                Iterator it = list.iterator();
                while(it.hasNext()) {
                    Element e = (Element) it.next();
                    String k = e.getName();
                    String v = "";
                    List children = e.getChildren();
                    if (children.isEmpty()) {
                        v = e.getTextNormalize();
                    } else {
                        v = XMLUtils.getChildrenText(children);
                    }
                    if (m.get(k) == null){
                        List list1 = Lists.newArrayList();
                        list1.add(v);
                        m.put(k, list1);
                    }else{
                        if (m.get(k) instanceof List){
                            ((List)m.get(k)).add(v);
                        }else{
                            m.put(k,v);
                        }
                    }
                }
            }else{
                Iterator it = list.iterator();
                while(it.hasNext()) {
                    Element e = (Element) it.next();
                    String k = e.getName();
                    String v = "";
                    List children = e.getChildren();
                    if (children.isEmpty()) {
                        v = e.getTextNormalize();
                    } else {
                        v = XMLUtils.getChildrenText(children);
                    }
                    m.put(k, v);
                }
            }
            //关闭流
            in.close();
            return m;
        }

    /**
     * 根据文件路径解析xml文件
     * @param filePath
     * @return
     * @throws IOException
     * @throws JDOMException
     */
        public static Map doParseXML(String filePath) throws IOException, JDOMException {
            String xmlString = FileUtils.readFromInputStream(new FileInputStream(filePath));
            return doXMLParse(xmlString);
        }

    /**
     * 根据文件路径 元素id和元素名称获取内容
     * @param filePath
     * @param id
     * @param contentName
     * @return
     */
        public static String doParseXML(String filePath, String id, String contentName) throws IOException, JDOMException {
            String xmlString = FileUtils.readFromInputStream(new FileInputStream(filePath));
            InputStream in = new ByteArrayInputStream(xmlString.getBytes("UTF-8"));
            return doParseXML(in,id,contentName);
        }


        public static String doParseXML(InputStream input, String id, String contentName) throws JDOMException, IOException {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(input);
            Element root = doc.getRootElement();
            List list = root.getChildren();
            Iterator it = list.iterator();
            String result = null;
            while(it.hasNext()) {
                Element element = (Element) it.next();
                Attribute nodeAttr = element.getAttribute("id");
                if (nodeAttr!=null && id.equals(nodeAttr.getValue())){
                    result = element.getChild(contentName).getValue();
                    break;
                }
            }
            return result;
        }

        /**
         * 获取子结点的xml
         * @param children
         * @return String
         */
        public static String getChildrenText(List children) {
            StringBuffer sb = new StringBuffer();
            if(!children.isEmpty()) {
                Iterator it = children.iterator();
                while(it.hasNext()) {
                    Element e = (Element) it.next();
                    String name = e.getName();
                    String value = e.getTextNormalize();
                    List list = e.getChildren();
                    sb.append("<" + name + ">");
                    if(!list.isEmpty()) {
                        sb.append(XMLUtils.getChildrenText(list));
                    }
                    sb.append(value);
                    sb.append("</" + name + ">");
                }
            }
            return sb.toString();
        }

    /**
     * 将fastJson格式转换成XML字符串
     * @param jsonObject
     * @return
     */
    public static String jsonToXML(JSONObject jsonObject){
            Assert.notNull(jsonObject);
            StringBuilder builder = new StringBuilder();
            builder.append("<xml>");
            for (String key : jsonObject.keySet()){
                builder.append("<"+key+"><![CDATA["+jsonObject.get(key)+"]]></"+key+">");
                //builder.append("<"+key+">"+jsonObject.get(key)+"</"+key+">");
            }
            builder.append("</xml>");
            return builder.toString();
        }


    public static JSONArray xml2JSONArray(String xml) throws JDOMException, IOException {
        if(null == xml || "".equals(xml)) {
            return null;
        }
        JSONArray jsonArray = new JSONArray();
        InputStream in = new ByteArrayInputStream(xml.getBytes("UTF-8"));
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(in);
        Element root = doc.getRootElement();
        List<Element> list = root.getChildren();
        for (Element element:list){
            if (!element.getChildren().isEmpty()){
                JSONObject jsonObject = new JSONObject();
                for (Object sub:element.getChildren()){
                    Element subElement = (Element) sub;
                    jsonObject.put(subElement.getName(), subElement.getValue());
                }
                jsonArray.add(jsonObject);
            }
        }
        return jsonArray;
    }

}
