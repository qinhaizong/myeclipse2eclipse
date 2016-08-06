package org.hz.app.myeclipse2eclipse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by qinhaizong on 2016/8/6.
 */
public class MymetadataFileStrategy implements IFileStrategy {

    @Override
    public void execute(Path file) {
        try {
            Files.delete(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
