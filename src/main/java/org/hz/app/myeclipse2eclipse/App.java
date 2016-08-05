package org.hz.app.myeclipse2eclipse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    private static final Logger LOG = LogManager.getLogger(App.class);

    public static void main(String[] args) throws IOException {
        LOG.info("程序开始");
        Files.walkFileTree(Paths.get("C:\\Users\\qinhaizong\\WXHL\\temp\\day01\\"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String name = file.getName(file.getNameCount()-1).toString();
                LOG.info("File:\t{}", name);
                if (".classpath".equals(name)) {
                    LOG.info("{}", name);
                    try {
                        Document document = new SAXReader().read(new FileInputStream(new File(file.toString())));
                        List<Node> list = document.selectNodes("//classpathentry");
                        list.stream().filter(node -> node instanceof Element).forEach(node -> {
                            Element element = (Element) node;
                            LOG.info(element.attribute("path"));
                        });
                        /*XMLWriter writer = new XMLWriter(new FileWriter(".classpath_2"), OutputFormat.createPrettyPrint());
                        writer.write(document);
                        writer.close();*/
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });

    }
}

