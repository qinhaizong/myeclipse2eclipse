package org.hz.app.myeclipse2eclipse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by qinhaizong on 2016/8/6.
 */
public class JsdtscopeFileStrategy implements IFileStrategy {

    @Override
    public void execute(Path file) {
        try {
            Files.move(file, Paths.get(file.toString() + "_bk"), StandardCopyOption.REPLACE_EXISTING);
            Files.copy(ClassLoader.getSystemResourceAsStream("META-INF/.settings/.jsdtscope"), file, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
