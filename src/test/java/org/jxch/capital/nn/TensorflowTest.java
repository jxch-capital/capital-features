package org.jxch.capital.nn;

import org.junit.jupiter.api.Test;
import org.tensorflow.ConcreteFunction;
import org.tensorflow.Signature;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;
import org.tensorflow.op.Ops;
import org.tensorflow.op.core.Placeholder;
import org.tensorflow.op.math.Add;
import org.tensorflow.types.TInt32;

public class TensorflowTest {
    // 定义常量
    private static final int EPOCHS = 10; // 训练轮数
    private static final int TRAINING_BATCH_SIZE = 100; // 训练批量大小
    private static final int TESTING_BATCH_SIZE = 1000; // 测试批量大小
    private static final float LEARNING_RATE = 0.001f; // 学习率
    private static final int NUM_CLASSES = 10; // 类别数
    private static final int INPUT_SIZE = 28 * 28; // 输入大小
    private static final int HIDDEN_SIZE = 128; // 隐藏层大小


    @Test
    public void test() throws NoSuchFieldException, IllegalAccessException {

        System.out.println("Hello TensorFlow " + TensorFlow.version());

        try (ConcreteFunction dbl = ConcreteFunction.create(TensorflowTest::dbl);
             TInt32 x = TInt32.scalarOf(10);
             Tensor dblX = dbl.call(x)) {
            System.out.println(x.getInt() + " doubled is " + ((TInt32)dblX).getInt());
        }
    }

    private static Signature dbl(Ops tf) {
        Placeholder<TInt32> x = tf.placeholder(TInt32.class);
        Add<TInt32> dblX = tf.math.add(x, x);
        return Signature.builder().input("x", x).output("dbl", dblX).build();
    }

    @Test
    public void nn() {
        Ops tf = Ops.create();

    }

}
