package org.hz.app.myeclipse2eclipse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

/**
 * Hello world!
 */
public class App extends SimpleFileVisitor<Path> {

    private static final Logger LOG = LogManager.getLogger(App.class);

    private static Map<String, Class<? extends IFileStrategy>> fileStrategyMap;

    private Context context = new Context();

    private App() {
    }

    static {
        fileStrategyMap = new HashMap<>();
        fileStrategyMap.put(".mymetadata", MymetadataFileStrategy.class);
        fileStrategyMap.put(".classpath", ClasspathFileStrategy.class);
        fileStrategyMap.put(".project", ProjectFileStrategy.class);
        fileStrategyMap.put(".jsdtscope", JsdtscopeFileStrategy.class);
        fileStrategyMap.put("org.eclipse.jdt.core.prefs", PrefsFileStrategy.class);
        fileStrategyMap.put("org.eclipse.wst.common.component", ComponentFileStrategy.class);
        fileStrategyMap.put("org.eclipse.wst.common.project.facet.core.xml", FacetCoreXmlFileStrategy.class);
    }

    public static void main(String[] args) throws IOException {
        LOG.info("程序开始");
        LOG.info(fileStrategyMap);
        Files.walkFileTree(Paths.get("C:\\Users\\qinhaizong\\WXHL\\temp\\"), new App());
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        String name = file.getName(file.getNameCount() - 1).toString();
        Class<? extends IFileStrategy> aClass = fileStrategyMap.get(name);
        if (null != aClass) {
            try {
                context.setStrategy(aClass.newInstance());
                context.execute(file, false);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        String name = dir.getName(dir.getNameCount() - 1).toString();
        LOG.info("dir name: {}", name);
        if ("WebRoot".equals(name)) {
            Files.move(dir, Paths.get(dir.toRealPath(LinkOption.NOFOLLOW_LINKS).toString().replace("WebRoot", "WebContent")), StandardCopyOption.REPLACE_EXISTING);
        }
        if (".myeclipse".equals(name)) {
            Files.delete(dir);
        }
        return FileVisitResult.CONTINUE;
    }
}

