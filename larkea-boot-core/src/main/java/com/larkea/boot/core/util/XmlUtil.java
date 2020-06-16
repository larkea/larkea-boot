package com.larkea.boot.core.util;

import java.util.Iterator;
import java.util.Map;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

@Slf4j
public class XmlUtil {

    private XmlUtil() {
    }

    public static String mapToXml(Map<String, String> map) {
        StringBuilder sb = new StringBuilder("<xml>");
        map.entrySet().stream().forEach(entry -> {
            sb.append(String.format(
                    "<%s><![CDATA[%s]]></%s>",
                    entry.getKey(),
                    entry.getValue(),
                    entry.getKey()));
        });
        sb.append("</xml>");
        return sb.toString();
    }

    public static Object xmlToMap(String xml) {
        try {
            Document doc = DocumentHelper.parseText(xml);
            Element root = doc.getRootElement();
            return visit(root);
        } catch (DocumentException e) {
            LOGGER.warn("parse xml error", e);
        }
        return null;
    }

    public static Object visit(Element elm) {
        if (elm.elements().size() == 0) {
            return elm.getTextTrim();
        }

        Map<String, Object> map = Maps.newHashMap();
        for (Iterator i = elm.elementIterator(); i.hasNext(); ) {
            Element element = (Element) i.next();
            map.put(element.getName(), visit(element));
        }
        return map;
    }
}
