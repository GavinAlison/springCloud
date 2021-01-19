package com.alison.compiler.code;

import com.alison.compiler.code.util.AviatorUtil;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;

import java.util.HashMap;
import java.util.Map;

/**
 * Aviator脚本执行器
 * @author yxy
 * @date 2019/1/7
 */
public class AviatorCodeExecutor extends AbstractCodeExecutor {

    private Expression expression;


    public AviatorCodeExecutor(String script,String functionName,String... paramCode){
        super(script,functionName,paramCode);
        initialize();
    }

    @Override
    public Object execute(Object... args) {
        Map<String,Object> env  = new HashMap<>(args.length);
        for (int i = 0; i < paramCode.length; i++) {
            env.put(paramCode[i],args[i]);
        }
        return AviatorUtil.compute(expression, env);
    }

    @Override
    public String getFunctionName() {
        return functionName;
    }

    @Override
    public void initialize() {
        expression = AviatorEvaluator.compile(this.script);
        this.source = this.script;
    }
}
