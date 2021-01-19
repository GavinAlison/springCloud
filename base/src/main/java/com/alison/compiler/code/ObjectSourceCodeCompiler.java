package com.alison.compiler.code;

import com.alison.compiler.code.jit.JavaStringCompiler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;


/**
 * 源码编译
 *
 * @author yxy
 * @version 2.0
 * @date 2020/3/20
 */
public class ObjectSourceCodeCompiler {

    private static final Logger logger = LoggerFactory.getLogger(ObjectSourceCodeCompiler.class);

    private String javaCode;

    private transient Class<?> clazz;

    private transient Object instance;

    private String className;

    /**方法数量 */
    private int methodCount=0;

    public ObjectSourceCodeCompiler(String javaCode, String className,int methodCount) {
        this.javaCode = javaCode;
        this.className = className;
        this.methodCount = methodCount;
    }

    /**
     * 编译源码
     *
     * @return
     */
    public boolean compile() {
        if(clazz!=null && instance!=null){
            logger.warn("已经编译过，无需再行编译, className:{}",className);
            return true;
        }
        long start = System.currentTimeMillis();
        boolean success = false;
        try {
            JavaStringCompiler javaStringCompiler = new JavaStringCompiler();
            clazz = javaStringCompiler.loadClassByComplie(className + ".java", AbstractCodeExecutor.DEFAULT_PKG + "." + className, javaCode);
            instance = clazz.newInstance();
            success = true;
        } catch (Exception e) {
            logger.error("对象代码编译错误, code:{}, msg: {}, 源码字节数:{}", className, e.getMessage(), javaCode.getBytes().length);
        }
        long consume = (System.currentTimeMillis() - start) / 1000;
        logger.debug("编译源码：{}.{}, 方法数量:{}, 源码字节数:{} kb,  耗时:{} s.", AbstractCodeExecutor.DEFAULT_PKG, className,methodCount,javaCode.getBytes().length/1024, consume);

        return success;
    }

    /**
     * 基于名称返回方法类
     *
     * @param methodName
     * @return
     */
    public Method getMethod(String methodName, Class<?>... params) {
        try {
            if(clazz!=null){
                return clazz.getMethod(methodName, params);
            }
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Object getInstance() {
        return instance;
    }

    public String getJavaCode() {
        return javaCode;
    }

    public String getClassName() {
        return className;
    }
}
