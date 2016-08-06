package org.hz.app.myeclipse2eclipse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

/**
 * Created by qinhaizong on 2016/8/6.
 */
public class Context {

    private static final Logger LOG = LogManager.getLogger(Context.class);

    private IFileStrategy strategy;

    public Context() {
    }

    public void execute(Path file) {
        LOG.info("handle file:{}", file);
        strategy.execute(file);
    }

    public void setStrategy(IFileStrategy strategy) {
        this.strategy = strategy;
    }
}
