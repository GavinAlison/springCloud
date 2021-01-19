package com.alison.compiler.code;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author yxy
 * @version 1.0
 * @date 2019/5/14
 */
public class GroovyCodeExecutor extends  AbstractCodeExecutor {

    private static final Logger logger = LoggerFactory.getLogger(GroovyCodeExecutor.class);

    private GroovyObject groovyObject;
    private Method method;
    private Class groovyClass;

    public GroovyCodeExecutor(String script,String functionName,String...paramCode){
        super(script,functionName,paramCode);
        initialize();
    }

    @Override
    public void initialize() {
        buildSource();
        GroovyClassLoader loader = new GroovyClassLoader(getClass().getClassLoader());
        groovyClass = loader.parseClass(source);
        try {
            groovyObject = (GroovyObject) groovyClass.newInstance();
            Class[] params = new Class[this.paramCode.length];
            for (int i = 0; i < paramCode.length; i++) {
                params[i] = Object.class;
            }
            method = groovyClass.getMethod(this.functionName,params);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            logger.error(e.getMessage(),e);
        }

    }

    @Override
    public Object execute(Object... args) {
        Object v = null;
        try {
            v = method.invoke(groovyObject,args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            logger.error(e.getMessage(),e);
        }
        return v;
    }


    private void buildSource(){
        this.source = "def ";
        this.source += functionName;
        this.source += " (";
        this.source +=  paramCode == null ? "" : String.join(",",paramCode);
        source +="){";
        source += script;
        source += "}";
    }
}
