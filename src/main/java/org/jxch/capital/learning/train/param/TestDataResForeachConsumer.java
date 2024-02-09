package org.jxch.capital.learning.train.param;

@FunctionalInterface
public interface TestDataResForeachConsumer {

    void accept(double[][] feature, double prediction, int signal);

}
