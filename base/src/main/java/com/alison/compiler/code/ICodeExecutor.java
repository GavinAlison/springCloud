package com.alison.compiler.code;

import java.io.Serializable;

/**
 * 代码执行器
 * @author yxy
 * @date 2019/1/7
 */
public interface ICodeExecutor extends Serializable {

    /**
     * 执行数据
     * @param args 传入参数
     * @return
     */
    Object execute(Object...args);

    /** 调用执行的方法名称
     * @return
     */
    String getFunctionName();

    /**
     * 返回源码
     * @return
     */
    String getSource();

    /**编译处理，对执行器做编译处理 */
    boolean compile();
}
