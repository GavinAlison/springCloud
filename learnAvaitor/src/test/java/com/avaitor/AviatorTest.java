package com.avaitor;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBigInt;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class AviatorTest {

    @Test
    public void test() {
        String expression = "1+2+3";
        Long result = (Long) AviatorEvaluator.execute(expression);
        System.out.println(result);
    }

    @Test
    public void testArg() {
        String name = "唐简";
        Map<String, Object> env = new HashMap<>();
        env.put("name", name);
        String result = (String) AviatorEvaluator.execute(" 'Hello ' + name ", env);
        System.out.println(result);
    }

    @Test
    public void testFun() {
        String str = "使用Aviator";
        Map<String, Object> env = new HashMap<>();
        env.put("str", str);
        String expression = "string.length(str)";
        AviatorEvaluator.compile(expression);
        Long length = (Long) AviatorEvaluator.execute(expression, env);
        System.out.println(length);
    }

    @Test
    public void testComile() {
        String expression = "a-(b-c)>100";
        Expression compiledExp = AviatorEvaluator.compile(expression);
        Map<String, Object> env = new HashMap<>();
        env.put("a", 100.3);
        env.put("b", 45);
        env.put("c", -199.100);
        Boolean result = (Boolean) compiledExp.execute(env);
        System.out.println(result);
    }

    @Test
    public void testAddCustomFun() {
        AviatorEvaluator.addFunction(new MinFunction());
        String expression = "min(a,b)";
        Expression compiledExp = AviatorEvaluator.compile(expression, true);
        Map<String, Object> env = new HashMap<>();
        env.put("a", 100.3);
        env.put("b", 45);
        Double result = (Double) compiledExp.execute(env);
        System.out.println(result);
    }

    static class MinFunction extends AbstractFunction {
        @Override
        public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
            Number left = FunctionUtils.getNumberValue(arg1, env);
            Number right = FunctionUtils.getNumberValue(arg2, env);
            return new AviatorBigInt(Math.min(left.doubleValue(), right.doubleValue()));
        }

        public String getName() {
            return "min";
        }
    }
}
