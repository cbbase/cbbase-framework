package com.cbbase.core.tools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

/**
 * 基于xstream
 * @author changbo
 *
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class XMLUtil{
	private static Converter mapConverter = new Converter(){
		
		public boolean canConvert(Class arg0) {
			return true;
		}
		
		public void marshal(Object arg0, HierarchicalStreamWriter writer, MarshallingContext arg2) {
			Map map = (Map) arg0;
            for (Object obj : map.entrySet()) {
                Entry entry = (Entry) obj;
                String key = (entry.getKey() == null) ? "null" : entry.getKey().toString();
                String value = (entry.getValue() == null) ? "" : entry.getValue().toString();
                writer.startNode(key);
                writer.setValue(value);
                writer.endNode();
            }
		}
		
		public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext arg1) {
            Map<String, String> map = new HashMap<String, String>();
            while(reader.hasMoreChildren()) {
                reader.moveDown();
                map.put(reader.getNodeName(), reader.getValue());
                reader.moveUp();
            }
            return map;
		}
    };
    
    private static DomDriver domDriver = new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_"));
	
	public static String bean2XML(Object obj){
		 XStream xstream = new XStream(domDriver);
		 xstream.processAnnotations(obj.getClass());
		 return xstream.toXML(obj);
	}
	
	public static <T>T xml2Bean(String xmlString, Class<T> cls){
		 XStream xstream = new XStream(domDriver);
		 xstream.processAnnotations(cls);
		 T obj = (T) xstream.fromXML(xmlString);
		 return obj;
	}
	
	public static <T>T xml2Bean(File file, Class<T> cls){
		 XStream xstream = new XStream(domDriver);
		 xstream.processAnnotations(cls);
		 T obj = (T) xstream.fromXML(file);
		 return obj;
	}
	
    public static Map<String, String> xml2Map(String xmlString){
		XStream xstream = new XStream(domDriver);
        xstream.alias("xml", Map.class);
        xstream.registerConverter(mapConverter);
        return (Map<String, String>)xstream.fromXML(xmlString);
    }
    
    public static String map2XML(Map map){
		XStream xstream = new XStream(domDriver);
		xstream.alias("xml", Map.class);
        xstream.registerConverter(mapConverter);
    	return xstream.toXML(map);
    }
    
}
