package com.rzk.config;

/**
 * @PackageName : com.rzk.config
 * @FileName : GimisXml
 * @Description :
 * @Author : rzk
 * @CreateTime : 2023年 03月 28日 下午8:55
 * @Version : 1.0.0
 */

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class GimisXml {
    public static Map<String, Object> getXMLStringValue(String result) {
        Map<String, Object> rm = new HashMap<>();
        StringReader sr = new StringReader(result);
        InputSource is = new InputSource(sr);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document document = null;
        try {
            document = builder.parse(is);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element rootElement = document.getDocumentElement();
        NodeList nodes = rootElement.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            if (node.getNodeType() == 1) {
                if (node.getFirstChild() != null)
                    rm.put(node.getNodeName(), node.getFirstChild().getNodeValue());
                NodeList nodeList = node.getChildNodes();
                if (nodeList != null)
                    for (int j = 0; j < nodeList.getLength(); j++) {
                        Node childnode1 = nodeList.item(j);
                        if (childnode1 instanceof Element) {
                            if (childnode1.getFirstChild() != null)
                                rm.put(childnode1.getNodeName(), childnode1.getFirstChild().getNodeValue());
                            NodeList nodeList1 = childnode1.getChildNodes();
                            if (nodeList1 != null)
                                for (int k = 0; k < nodeList1.getLength(); k++) {
                                    Node childnode2 = nodeList1.item(k);
                                    if (childnode2 instanceof Element) {
                                        if (childnode2.getFirstChild() != null)
                                            rm.put(childnode2.getNodeName(), childnode2.getFirstChild().getNodeValue());
                                        NodeList nodeList2 = childnode2.getChildNodes();
                                    }
                                }
                        }
                    }
            }
        }
        return rm;
    }
}
