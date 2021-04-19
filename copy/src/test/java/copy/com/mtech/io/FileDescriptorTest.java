package copy.com.mtech.io;

import org.junit.Test;

import java.io.*;

public class FileDescriptorTest {

    private static final String outText = "hi, filedescriptor";
    private static final String FileName = "file";

    /**
     * FileDescriptor.out 的测试程序
     * <br/>
     * =System.out.println("")
     */
    @Test
    public void testStandFD() {
        PrintStream printStream = new PrintStream(new FileOutputStream(FileDescriptor.out));
        printStream.println(outText);
        printStream.close();
        System.out.println();
    }

    @Test
    public void testWrite() {
        try {
            // 新建文件“file.txt”对应的FileOutputStream对象
            FileOutputStream out1 = new FileOutputStream(FileName);
            // 获取文件“file.txt”对应的“文件描述符”
            FileDescriptor fdout = out1.getFD();
            // 根据“文件描述符”创建“FileOutputStream”对象
            FileOutputStream out2 = new FileOutputStream(fdout);

            out1.write('A');    // 通过out1向“file.txt”中写入'A'
            out2.write('a');    // 通过out2向“file.txt”中写入'A'

            if (fdout != null)
                System.out.printf("fdout(%s) is %s\n", fdout, fdout.valid());
            out1.close();
            out2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRead() {
        try {
            // 新建文件“file.txt”对应的FileInputStream对象
            FileInputStream in1 = new FileInputStream(FileName);
            // 获取文件“file.txt”对应的“文件描述符”
            FileDescriptor fdin = in1.getFD();
            // 根据“文件描述符”创建“FileInputStream”对象
            FileInputStream in2 = new FileInputStream(fdin);
            System.out.println("in1.read():" + (char) in1.read());
            System.out.println("in2.read():" + (char) in2.read());

            if (fdin != null)
                System.out.printf("fdin(%s) is %s\n", fdin, fdin.valid());
            in1.close();
            in2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
