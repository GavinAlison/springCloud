package com.mtech.string;

public class StringDemo {
    public static void main(String[] args) {
        String str1 = "a";
        String str2 = "ab";
        String str3 = str1 + "b";
        // String s2 = (new StringBuilder()).append(s).append("b").toString();
        // 对于字符串引用的 + 号连接问题，由于字符串引用在编译期是无法确定下来的，在程序的运行期动态分配并创建新的地址存储对象。
//  发现 new 了一个 StringBuilder 对象，然后使用 append 方法优化了 + 操作符。new 在堆上创建对象，而 String s1=“ab”则是在常量池中创建对象，两个应用所指向的内存地址是不同的
        System.out.print(str2 == str3);//false
    }

    /**
     * 我们已经知道了字符串引用的 + 号连接问题，其实是在运行期间创建一个 StringBuilder 对象，使用其 append 方法将字符串连接起来。
     * 这个也是我们开发中需要注意的一个问题，就是尽量不要在 for 循环中使用 + 号来操作字符串。看下面一段代码：
     */

    public void testAppend() {
        String s = null;
        for (int i = 0; i < 100; i++) {
            s = s + "a";
            // 在 for 循环中使用 + 连接字符串，每循环一次，就会新建 StringBuilder 对象，append 后就“抛弃”了它。
        }
//---------------------------
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 100; i++) {
            sb.append("a");
        }
    }

    // 使用final修饰的字符串
    public void testFinal() {
        final String str1 = "a";
        String str2 = "ab";
        String str3 = str1 + "b";
//        final 修饰的变量是一个常量，编译期就能确定其值。所以 str1 + "b"就等同于 "a" + "b"，所以结果是 true。
        System.out.print(str2 == str3);//true
    }

    //String对象的intern方法。
    public void testIntern() {
        String s = "ab";
        String s1 = "a";
        String s2 = "b";
        String s3 = s1 + s2;
        System.out.println(s3 == s);//false
        System.out.println(s3.intern() == s);//true
        // s1+s2 实际上在堆上 new 了一个 StringBuilder 对象，而 s 在常量池中创建对象 “ab”，所以 s3 == s 为 false。
        // 但是 s3 调用 intern 方法，返回的是s3的内容（ab）在常量池中的地址值。所以 s3.intern() == s 结果为 true。
    }


}
