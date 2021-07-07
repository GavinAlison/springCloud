package copy.init;


import org.junit.Test;

/**
 * Y--X
 * Y--Y
 * X--Y
 * X--X
 * 继承和初始化顺序，遇到new时：
 * 先分配空间（子类中的实例变量和父类中的实例变量）
 * 初始化默认值
 * 调用当前类的<init>（注意<init的结构）
 */
public class InitTest {
    @Test
    public void test1() {
        new X();//XYYX
    }
}

class X extends Y {
    // 2
    {
        System.out.println("X--Y");
    }

    // 4
    X() {
        super();
        System.out.println("X--X");
    }
}

class Y {
    // 1
    {
        System.out.println("Y--X");
    }

    //3
    Y() {
        System.out.println("Y--Y");
    }
}
