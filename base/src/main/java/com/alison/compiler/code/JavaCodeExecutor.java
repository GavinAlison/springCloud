package com.alison.compiler.code;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Method;
import java.util.Arrays;


/**
 * java代码执行器
 *
 * @author yxy
 * @date 2019/1/7
 */
public class JavaCodeExecutor extends AbstractCodeExecutor implements Externalizable {

    private static final Logger logger = LoggerFactory.getLogger(JavaCodeExecutor.class);

    private transient Object instance;

    private transient Method method;

    private Class<?> clazz;

    private String className;

    private boolean pretty;

    public JavaCodeExecutor(String javaCode, String functionName, String[] paramCode, Class<?>[] paramType) {
        super(javaCode, functionName, paramCode, paramType);
        initialize();
    }

    public JavaCodeExecutor(String javaCode, String functionName, String[] paramCode, Class<?>[] paramType, boolean pretty) {
        super(javaCode, functionName, paramCode, paramType);
        this.pretty = pretty;
        initialize();
    }

    public JavaCodeExecutor(String javaCode, String className, String functionName, String[] paramCode, Class<?>[] paramType) {
        super(javaCode, functionName, paramCode, paramType);
        this.className = className;
        initialize();
    }

    /**
     * 按java实例和类，方法进行初始化
     *
     * @param clazz
     * @param instance
     * @param method
     */
    public JavaCodeExecutor(Class<?> clazz, Object instance, Method method) {
        this.clazz = clazz;
        this.instance = instance;
        this.method = method;
    }


    @Override
    public Object execute(Object... args) {
        Object v = null;
        try {
            v = this.getMethod().invoke(instance, args);
        } catch (Exception e) {
            logger.error("调用执行异常, funcName:{},paramCode:{} ,argValue: {}, error:{},err_class:{}", this.functionName, Arrays.toString(paramCode), Arrays.toString(args), e.getMessage(), e.getClass().getSimpleName());
        }
        return v;
    }


    public Method getMethod() {
        if (method == null) {
            Class[] params = new Class[paramCode.length];
            for (int i = 0; i < paramCode.length; i++) {
                params[i] = paramType == null ? Object.class : paramType[i];
            }
            try {
                method = clazz.getMethod(getFunctionName(), params);
            } catch (NoSuchMethodException e) {
                logger.error("实例化java.lang.reflect.Method异常, funcName:{},paramCode:{} ,argValue: {}, error:{}", this.functionName, Arrays.toString(paramCode), Arrays.toString(params), e.getMessage());
            }
        }

        return method;
    }

    @Override
    public String getFunctionName() {
        return functionName;
    }

    @Override
    public void initialize(boolean compile) {

        if (null == className) {
            className = "JIT";
            className += RandomStringUtils.random(6, 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'i', 'j');
            className += System.currentTimeMillis();
        }
        source = wrapClassCode();
        if (compile) {
            compile();
        }
    }

    @Override
    public boolean compile() {
        if (method != null && instance != null) {
            return true;
        }
        ObjectSourceCodeCompiler objectSourceCodeCompiler = new ObjectSourceCodeCompiler(source, className, 1);

        boolean c = objectSourceCodeCompiler.compile();
        if (c) {
            clazz = objectSourceCodeCompiler.getClazz();
            instance = objectSourceCodeCompiler.getInstance();
            try {
                if (paramCode != null) {
                    Class<?>[] params = new Class[paramCode.length];
                    for (int i = 0; i < paramCode.length; i++) {
                        params[i] = paramType == null ? Object.class : paramType[i];
                    }
                    method = clazz.getMethod(getFunctionName(), params);
                } else {
                    method = clazz.getMethod(getFunctionName());
                }
                return true;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                return false;
            }
        }

        return false;
    }

    @Override
    public void initialize() {
        initialize(true);
    }


    /**
     * 默认只导入util 和 lang包
     */
    private String wrapClassCode() {
        StringBuilder code = new StringBuilder("package ");
        code.append(DEFAULT_PKG).append(";")
                .append(pretty ? "\n" : "")
                .append(JavaSourceCodeBuilder.defaultImportPackage(pretty ? "\n" : ""))
                .append("public final class ")
                .append(className)
                .append(" implements Serializable")
                .append(" {")
                .append(pretty ? "\n    " : "")
                .append("private static final Logger logger = LoggerFactory.getLogger(\""+DEFAULT_PKG+"\");")
                .append(pretty ? "\n" : "");
        code.append(JavaSourceCodeBuilder.methodString(this, this.functionName, pretty));
        if (pretty) {
            code.append(JavaSourceCodeBuilder.ENTER);
        }
        code.append("}");
        return code.toString();
    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(className);
        out.writeObject(clazz);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        className = (String) in.readObject();
        clazz = (Class<?>) in.readObject();
        initialize();
    }

    public JavaCodeExecutor() {
    }
}
