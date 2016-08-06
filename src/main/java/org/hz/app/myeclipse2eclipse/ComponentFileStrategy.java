package org.hz.app.myeclipse2eclipse;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

/**
 * 1. <wb-module deploy-name="demo01"> get name
 * 2. <wb-module deploy-name="demo01"> set name
 * 3. <property name="context-root" value="demo01"/>
 * 4. <property name="java-output-path" value="/demo01/build/classes"/>
 * <p>
 * Created by qinhaizong on 2016/8/6.
 */
public class ComponentFileStrategy implements IFileStrategy {

    @Override
    public void execute(Path file) {
        String fileName = file.toString();
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(new FileInputStream(new File(fileName)));
            Node node = document.selectSingleNode("//wb-module");
            String name = node.valueOf("@deploy-name");
            Document srcDocument = reader.read(ClassLoader.getSystemResourceAsStream("META-INF/.settings/org.eclipse.wst.common.component"));
            Node node1 = srcDocument.selectSingleNode("//wb-module");
            if (node1 instanceof Element) {
                Element element = (Element) node1;
                element.attribute("deploy-name").setText(name);
            }
            List<Node> list = srcDocument.selectNodes("//property");
            for (Node n : list) {
                if (n instanceof Element) {
                    Element element = (Element) n;
                    if ("context-root".equals(element.attributeValue("name"))) {
                        element.attribute("value").setText(name);
                    }
                    if ("java-output-path".equals(element.attributeValue("name"))) {
                        element.attribute("value").setText(String.format("/%s/build/classes", name));
                    }
                }
            }
            Files.move(file, Paths.get(file.toString() + "_bk"), StandardCopyOption.REPLACE_EXISTING);
            XMLWriter writer = new XMLWriter(new FileWriter(fileName), OutputFormat.createPrettyPrint());
            writer.write(srcDocument);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
