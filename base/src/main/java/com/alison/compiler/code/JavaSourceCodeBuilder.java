package com.alison.compiler.code;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Java代码结构构建工具类
 *
 * @author yxy
 * @version 2.0
 * @date 2020/3/20
 */
public class JavaSourceCodeBuilder {

    public static final String INDENT = "    ";
    public static final String ENTER = "\n";


    /**
     * 引入package的内容
     *
     * @param split
     * @return
     */
    public static String defaultImportPackage(String split) {
        StringBuilder builder = new StringBuilder();
        builder.append("import java.util.*;")
                .append(split)
                .append("import java.lang.*;")
                .append(split)
                .append("import java.math.*;")
                .append(split)
                .append("import java.time.LocalDate;")
                .append(split)
                .append("import org.slf4j.Logger;")
                .append(split)
                .append("import org.slf4j.LoggerFactory;")
                .append(split)
                .append("import java.time.LocalDateTime;")
                .append(split)
                .append("import java.time.ZoneId;")
                .append(split)
                .append("import java.time.format.DateTimeFormatter;")
                .append(split)
                .append("import java.time.Period;")
                .append(split)
                .append("import java.time.temporal.ChronoUnit;")
                .append(split)
                .append("import java.io.Serializable;")
                .append(split)
                .append("import java.text.DateFormat;")
                .append(split)
                .append("import java.text.SimpleDateFormat;")
                .append(split)
                .append("import java.util.stream.*;")
                .append(split);
        return builder.toString();
    }


    public static String wrapClassCode(String className, String pkg, String importClass, boolean pretty, String... methodCodes) {
        StringBuilder code = new StringBuilder("package ");
        code.append(pkg).append(";")
                .append(pretty ? "\n" : "")
                .append(importClass)
                .append("public final class ")
                .append(className)
                .append(" implements Serializable")
                .append(" {")
                .append(pretty ? "\n    " : "")
                .append("private static final Logger logger = LoggerFactory.getLogger(\"" + AbstractCodeExecutor.DEFAULT_PKG + "\");")
                .append(pretty ? "\n" : "");

        for (String methodCode : methodCodes) {
            code.append(methodCode);
            if (pretty) {
                code.append(ENTER);
            }
        }//end for

        if (pretty) {
            code.append(ENTER);
        }
        code.append("}");
        return code.toString();
    }

    /**
     * 封装方法，返回脚本方法
     *
     * @param codeExecutor
     * @param methodName
     * @param pretty
     * @return
     */
    public static String methodString(AbstractCodeExecutor codeExecutor, String methodName, boolean pretty) {
        return methodString(codeExecutor, methodName, pretty, false);
    }

    /**
     * 封装方法，返回脚本方法
     *
     * @param codeExecutor
     * @param methodName
     * @param pretty
     * @param paramNullCheck
     * @return
     */
    public static String methodString(AbstractCodeExecutor codeExecutor, String methodName, boolean pretty, boolean paramNullCheck) {
        return methodString(codeExecutor.getParamCode(), codeExecutor.getParamType(), methodName, codeExecutor.getScript(), pretty, paramNullCheck);
    }

    /**
     * 基于方法脚本封装一个方法
     *
     * @param paramCode
     * @param paramType
     * @param methodName
     * @param script
     * @param pretty
     * @param paramNullCheck
     * @return
     */
    public static String methodString(String[] paramCode, Class<?>[] paramType, String methodName, String script, boolean pretty, boolean paramNullCheck) {
        StringBuilder mBuf = new StringBuilder();
        if (pretty) {
            mBuf.append(INDENT);
        }
        //为空添加
        StringBuilder checkFragment = new StringBuilder();
        mBuf.append("public static Object ");
        mBuf.append(methodName);
        mBuf.append("(");
        if (paramCode != null && paramCode.length != 0) {
            for (int i = 0; i < paramCode.length; i++) {
                mBuf.append(paramType == null || paramType[i] == null ? "Object" : paramType[i].getName())
                        .append(" ")
                        .append(paramCode[i]);
                if (i < paramCode.length - 1) {
                    mBuf.append(",");
                }
                if (paramNullCheck) {
                    checkFragment.append(ENTER).append(INDENT).append(INDENT).append(INDENT);
                    checkFragment.append(paramDefaultCheck(paramType[i], paramCode[i]));
                }
            }
        }
        mBuf.append(") {");
        if (pretty) {
            mBuf.append(ENTER);
        }
        mBuf.append(wrapTryCatch(script, paramCode, pretty, checkFragment.toString()));
        if (pretty) {
            mBuf.append(ENTER).append(INDENT);
        }
        mBuf.append("}");
        return mBuf.toString();
    }

    public static String paramDefaultCheck(Class<?> paramType, String paramCode) {
        StringBuilder frag = new StringBuilder();
        frag.append("if (");
        frag.append(paramCode);
        frag.append(" == null) {");
        frag.append(ENTER).append(INDENT).append(INDENT).append(INDENT).append(INDENT);
        frag.append(paramCode);
        frag.append(" = ");
        if (String.class.equals(paramType)) {
            frag.append("\"\"");
        } else if (BigDecimal.class.equals(paramType)) {
            frag.append("BigDecimal.ZERO");
        } else if (Integer.class.equals(paramType)) {
            frag.append("0");
        } else if (Float.class.equals(paramType)) {
            frag.append("0f");
        } else if (Double.class.equals(paramType)) {
            frag.append("0d");
        } else if (Boolean.class.equals(paramType)) {
            frag.append("false");
        } else if (Long.class.equals(paramType)) {
            frag.append("0l");
        } else if (Date.class.equals(paramType)) {
            frag.append("null");
        } else if (List.class.equals(paramType)) {
            frag.append("new ArrayList()");
        } else if (Map.class.equals(paramType)) {
            frag.append("new HashMap<>()");
        } else {
            frag.append("null");
        }
        frag.append(";");
        frag.append(ENTER).append(INDENT).append(INDENT).append(INDENT);
        frag.append("}");

        return frag.toString();
    }

    /**
     * 封装方法的try catch语句
     *
     * @param script
     * @param paramCode
     * @param pretty
     * @param checkFragment
     * @return
     */
    public static String wrapTryCatch(String script, String[] paramCode, boolean pretty, String checkFragment) {
        StringBuilder wrap = new StringBuilder();
        if (pretty) {
            wrap.append(INDENT).append(INDENT);
        }
        wrap.append("try {");
        if (checkFragment != null && !checkFragment.isEmpty()) {
            wrap.append(checkFragment);
        }
        if (pretty) {
            wrap.append(ENTER).append(INDENT).append(INDENT).append(INDENT);
        }

        wrap.append("/*-s-*/");
        if (pretty) {
            wrap.append(ENTER).append(INDENT).append(INDENT).append(INDENT);
        }

        if (StringUtils.isNotEmpty(script)) {
            wrap.append(script);
        } else {
            wrap.append("return null;");
        }
        if (pretty) {
            wrap.append(ENTER).append(INDENT).append(INDENT).append(INDENT);
        }
        wrap.append("/*-e-*/");

        if (pretty) {
            wrap.append(ENTER).append(INDENT).append(INDENT);
        }
        wrap.append("} catch (Exception e) {");
        if (pretty) {
            wrap.append(ENTER).append(INDENT).append(INDENT).append(INDENT);
        }
        if (paramCode != null && paramCode.length != 0) {
            String paramValues = Arrays.stream(paramCode).map(c -> "String.valueOf(" + c + ")").collect(Collectors.joining("+\",\"+"));
            wrap.append("String paramValue = " + (StringUtils.isNotEmpty(paramValues) ? paramValues : "\"\"") + ";");

        }
        if (pretty) {
            wrap.append(ENTER).append(INDENT).append(INDENT).append(INDENT);
        }
        wrap.append("if(logger.isWarnEnabled()) {");
        if (pretty) {
            wrap.append(ENTER).append(INDENT).append(INDENT).append(INDENT).append(INDENT);
        }
        wrap.append("logger.warn(\"调用执行异常:params:" + Arrays.toString(paramCode) + ",paramValue:{},error:{}\",");
        wrap.append(" paramValue");
        wrap.append(",");
        wrap.append(" e);");
        if (pretty) {
            wrap.append(ENTER).append(INDENT).append(INDENT).append(INDENT).append(INDENT);
        }
//        wrap.append("throw new Error(e);");
        //wrap.append("throw new RuntimeException(e);");
        if (pretty) {
            wrap.append(ENTER).append(INDENT).append(INDENT).append(INDENT);
        }
        wrap.append("}");
        if (pretty) {
            wrap.append(ENTER).append(INDENT).append(INDENT).append(INDENT);
        }
        wrap.append("return null;");
        if (pretty) {
            wrap.append(ENTER).append(INDENT).append(INDENT);
        }
        wrap.append("}");
        return wrap.toString();
    }

}
