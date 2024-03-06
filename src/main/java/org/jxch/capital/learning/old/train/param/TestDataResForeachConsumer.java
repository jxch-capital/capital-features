package org.jxch.capital.learning.old.train.param;

@FunctionalInterface
public interface TestDataResForeachConsumer {

    void accept(double[][] feature, double prediction, int signal);

}
