package com.alison.compiler.code;

import org.python.core.*;
import org.python.util.PythonInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * @author peidong.meng
 * @date 2019/6/19
 */
public class JPythonExecutor extends AbstractCodeExecutor {
    private static final Logger log = LoggerFactory.getLogger(JPythonExecutor.class);
    private PyFunction pyFunction;

    public JPythonExecutor(String script, String functionName, String[] paramCode, Class[] paramType) {
        super(script, functionName, paramCode, paramType);
        initialize();
    }

    @Override
    public void initialize() {
        buildSource();

        PythonInterpreter interpreter = new PythonInterpreter();

        interpreter.exec(this.source);
        pyFunction = interpreter.get(functionName, PyFunction.class);

    }

    @Override
    public Object execute(Object... args) {
        Object v = null;
        try {
            PyObject[] py = new PyObject[args.length];
            for (int i = 0; i < paramType.length; i++) {
                py[i] = getPyObject(paramType[i], args[i]);
            }
            PyObject result = pyFunction.__call__(py);
            v = result.__tojava__(Object.class);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return v;
    }


    private void buildSource() {
        this.source = "def ";
        this.source += functionName;
        this.source += " (";
        this.source += String.join(",", paramCode);
        source += "): ";
        source += script;
    }

    private PyObject getPyObject(Class clazz, Object arg) {
        if (clazz == String.class) {
            return new PyString(arg.toString());
        } else if (clazz == Integer.class) {
            return new PyInteger(Integer.valueOf(arg.toString()));
        } else if (clazz == Double.class) {
            return new PyFloat(Double.valueOf(arg.toString()));
        } else if (clazz == Long.class) {
            return new PyLong(Long.valueOf(arg.toString()));
        } else if (clazz == Boolean.class) {
            return new PyBoolean(Boolean.valueOf(arg.toString()));
        } else if (clazz == Collection.class) {
            return new PyList((List) arg);
        } else {
            return new PyString(arg.toString());
        }
    }
}
