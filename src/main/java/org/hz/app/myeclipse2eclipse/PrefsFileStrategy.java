package org.hz.app.myeclipse2eclipse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Created by qinhaizong on 2016/8/6.
 */
public class PrefsFileStrategy implements IFileStrategy {

    @Override
    final public void execute(Path file, boolean withBackup) {
        try {
            if (withBackup) {
                Files.move(file, Paths.get(file.toString() + "_bk"), StandardCopyOption.REPLACE_EXISTING);
            }
            Files.copy(ClassLoader.getSystemResourceAsStream("META-INF/.settings/org.eclipse.jdt.core.prefs"), file, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
