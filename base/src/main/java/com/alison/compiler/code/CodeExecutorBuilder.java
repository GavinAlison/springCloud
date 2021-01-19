package com.alison.compiler.code;

import lombok.extern.slf4j.Slf4j;

import javax.script.ScriptEngine;

/**
 * 脚本引擎构造器
 *
 * @author yxy
 * @date 2019/1/7
 */
@Slf4j
public class CodeExecutorBuilder {

    /**默认的执行脚本的方法 */
    private static final String DEFAULT_FUNCTION="run";

    public CodeExecutorBuilder(){

    }

    public static ICodeExecutor build(ScriptLanguage scriptLanguage, String script, String[] paramsCode) {
        return build(scriptLanguage,script,DEFAULT_FUNCTION,paramsCode, null);
    }

    public static ICodeExecutor build(ScriptLanguage scriptLanguage, String script, String[] paramsCode, Class[] paramType) {
        return build(scriptLanguage,script,DEFAULT_FUNCTION,paramsCode,paramType);
    }

    public static ICodeExecutor build(ScriptLanguage scriptLanguage, String script,String functionName ,String[] paramsCode, Class[] paramType) {
        return build(scriptLanguage,script,null,functionName,paramsCode,paramType);
    }

    /**
     * 构造脚本执行器，在调用的地方对每个线程保存独立的ICodeExecutor，不要每次调用都build新的实例
     *
     * @param scriptLanguage 使用的语言
     * @param script         待执行脚本
     * @param functionName 调用方法名称
     * @param paramsCode     方法参数标识
     * @return
     */
    public static ICodeExecutor build(ScriptLanguage scriptLanguage, String script,String className,String functionName ,String[] paramsCode, Class[] paramType) {

        ICodeExecutor scriptExecutor = null;
        ScriptEngine scriptEngine = null;

        switch (scriptLanguage) {
            case JAVA:
                scriptExecutor = new JavaCodeExecutor(script,className,functionName,paramsCode, paramType);
                log.debug("java source:{}",scriptExecutor.getSource());
                break;
            case QLEXPRESS:
                throw new ScriptException("don't support QLEXPRESS script now.");
            case AVIATOR:

                scriptExecutor = new AviatorCodeExecutor(script,functionName,paramsCode);
                log.debug("script:{}",scriptExecutor.getSource());
                break;
            case GROOVY:
                scriptExecutor = new GroovyCodeExecutor(script,functionName,paramsCode);
                log.debug("groovy source:{}",scriptExecutor.getSource());
                break;
                /*
                scriptEngine = groovyScriptEngineFactory.getScriptEngine();
                scriptExecutor = new JavaScriptExecutor(scriptEngine, script,functionName);
                try {
                    script = wrapRunMethod(script, scriptLanguage, scriptExecutor.getFunctionName(), paramsCode);
                    log.debug("execute script:{}",script);
                    scriptEngine.eval(script);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    throw new ScriptException("输入脚本异常，请检查！");
                }
                */

            case PYTHON:
                scriptExecutor = new JPythonExecutor(script,functionName,paramsCode,paramType);
                log.debug("jpython script:{}",script);
                break;
            case JAVASCRIPT:
                scriptExecutor = new JavaScriptExecutor(script,functionName,paramsCode);
                log.debug("execute script:{}",script);
                break;
            default:
                break;
        }

        return scriptExecutor;
    }

}
