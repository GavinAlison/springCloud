package com.alison.compiler.code;

import lombok.extern.slf4j.Slf4j;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.util.Arrays;

/**
 * 脚本执行器
 * 脚本执行器中的脚本为执行过程，不包含函数封装
 * @author yxy
 * @date 2019/1/7
 */
@Slf4j
public class JavaScriptExecutor extends AbstractCodeExecutor {

    private ScriptEngine scriptEngine;

    public JavaScriptExecutor(String script, String functionName, String... paramCode){
        super(script,functionName,paramCode);
        initialize();
    }

    @Override
    public Object execute(Object... args) {
        try {
            //脚本执行,groovy and javascript
            return ((Invocable)scriptEngine).invokeFunction(functionName,args);
        } catch (Exception e) {
            log.error(e.getMessage()+"functionName:{} , args: {} ,",functionName, Arrays.toString(args));
            throw new ScriptException("脚本执行异常，请检查,functionName: " +functionName);
        }
        //return null;
    }

    @Override
    public String getFunctionName() {
        return functionName;
    }

    @Override
    public void initialize() {
        buildSource();
        ScriptEngineManager engineManager = new ScriptEngineManager();
        scriptEngine = engineManager.getEngineByName(ScriptLanguage.JAVASCRIPT.getLang());
    }


    private void buildSource(){
        this.source = "function ";
        this.source += functionName;
        this.source += " (";
        this.source += String.join(",",paramCode);
        source +="){";
        source += script;
        source += "}";
    }


}
