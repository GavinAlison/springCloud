package com.alison;

import com.alison.compiler.code.CodeExecutorBuilder;
import com.alison.compiler.code.ICodeExecutor;
import com.alison.compiler.code.ScriptLanguage;
import org.junit.Test;
import org.python.core.PyFunction;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

public class FunctionTest {

    @Test
    public void groovy() {
        String[] paramsCode = new String[]{"num1", "num2", "num3", "num4"};
        String content = "def list = [num1,num2,num3,num4]\n" +
                "return list.max();";
        Class[] paramsType = new Class[]{Double.class, Double.class, Double.class, Double.class};
        ICodeExecutor build = CodeExecutorBuilder.build(
                ScriptLanguage.GROOVY,
                content,
                paramsCode,
                paramsType);

        Object execute = build.execute(1, 2, 3, 4);
        System.out.println(execute);
    }

    @Test
    public void java() {
        String[] paramsCode = new String[]{"dddd", "ssss"};
        String content = " return dddd+ssss;";
        Class[] paramsType = new Class[]{Integer.class, Integer.class};
        ICodeExecutor build = CodeExecutorBuilder.build(
                ScriptLanguage.JAVA,
                content,
                paramsCode,
                paramsType);
        build.compile();
        Object execute = build.execute(1, 2);
        System.out.println(execute);
    }

    @Test
    public void python() {
        String str = "def run(name,age,score): return \"i am \" + name + \", age: \" + str(age) + \", score: \" + str(score)";
        PythonInterpreter interpreter = new PythonInterpreter();
        interpreter.exec(str);
        PyFunction func = interpreter.get("run",
                PyFunction.class);
//        int a1 = 100, b1 = 100;
        String name = "zhangsan";
        Integer age = 18, score = 80;
//        PyObject[] py = {new PyInteger(a1), new PyInteger(b1)};
        PyObject[] py = {new PyString(name), new PyInteger(age), new PyInteger(score)};
        PyObject pyobj = func.__call__(py);
        System.out.println("anwser = " + pyobj.toString());
    }
}
