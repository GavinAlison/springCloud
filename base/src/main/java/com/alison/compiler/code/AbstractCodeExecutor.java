package com.alison.compiler.code;


/**
 * @author yxy
 * @version 1.0
 * @date 2019/5/14
 */
public abstract class AbstractCodeExecutor implements ICodeExecutor {


    /**
     * 默认包名称
     */
    protected static final String DEFAULT_PKG="com.alison.compiler.code";

    /**
     * 方法
     */
    protected String functionName;
    /**
     * 配置的脚本
     */
    protected String script;

    /**
     * 根据脚本生成的源码
     */
    protected String source;

    /**
     * 参数标识
     */
    protected String[] paramCode;

    /**
     * 参数类型
     */
    protected Class[] paramType;

    public AbstractCodeExecutor(String script,String functionName,String[] paramCode, Class[] paramType){
        this.script = script;
        this.functionName = functionName;
        this.paramCode = paramCode;
        this.paramType = paramType;
    }

    public AbstractCodeExecutor(String script,String functionName,String[] paramCode){
        this.script = script;
        this.functionName = functionName;
        this.paramCode = paramCode;
    }

    @Override
    public String getFunctionName() {
        return functionName;
    }

    /**
     * 初始化
     */
    protected abstract void initialize();

    /**
     * 是否对脚本做编译
     * @param compile
     */
    public void initialize(boolean compile){
    }

    @Override
    public boolean compile() {
        return false;
    }

    @Override
    public String getSource() {
        return source;
    }


    public AbstractCodeExecutor() {
    }

    public String getScript() {
        return script;
    }

    public String[] getParamCode() {
        return paramCode;
    }

    public Class[] getParamType() {
        return paramType;
    }
}
