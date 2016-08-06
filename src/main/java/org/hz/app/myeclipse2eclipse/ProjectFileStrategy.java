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

/**
 * Created by qinhaizong on 2016/8/6.
 */
public class ProjectFileStrategy implements IFileStrategy {

    @Override
    public void execute(Path file) {
        String fileName = file.toString();
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(new FileInputStream(new File(fileName)));
            Node node = document.selectSingleNode("//projectDescription/name");
            String name = node.getStringValue();
            Document srcDocument = reader.read(ClassLoader.getSystemResourceAsStream("META-INF/.project"));
            Node node1 = srcDocument.selectSingleNode("//projectDescription/name");
            if(node1 instanceof Element){
                Element element = (Element) node1;
                element.setText(name);
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
