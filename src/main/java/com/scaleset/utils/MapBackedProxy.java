package com.scaleset.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class MapBackedProxy<T> implements InvocationHandler {

    private Class<T> type;

    private T proxy;

    private Map<String, Object> values = new HashMap<String, Object>();

    public T proxy() {
        if (proxy == null) {
            proxy = (T) Proxy.newProxyInstance(MapBackedProxy.class.getClassLoader(), new Class[]{type}, this);
        }
        return proxy;
    }

    public MapBackedProxy(Class<T> type) {
        this.type = type;
    }

    public MapBackedProxy(Class<T> type, Map<String, Object> map) {
        this.type = type;
        this.values = map;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object result = null;

        Class<?> declaringClass = method.getDeclaringClass();

        if (declaringClass.equals(Object.class)) {
            result = method.invoke(this, args);
        } else if (isSetter(method, args)) {
            result = doSetter(proxy, method, args);
        } else if (isGetter(method, args)) {
            result = doGetter(proxy, method, args);
        }

        return result;
    }

    /**
     * Get the property name of the variable from the getter/setter method name
     */
    private String getPropertyName(Method method) {
        String result = method.getName();
        if (result.startsWith("set") || result.startsWith("get")) {
            char[] chars = result.substring(3).toCharArray();
            chars[0] = Character.toLowerCase(chars[0]);
            result = String.valueOf(chars);
        }
        return result;
    }

    /**
     * Check whether or not method is a setter with one argument and uses
     * optional default value if null
     */
    private boolean isSetter(Method method, Object[] args) {
        return args != null && args.length == 1;
    }

    private Object doSetter(Object proxy, Method method, Object[] args) {
        Object result = null;
        String propertyName = getPropertyName(method);

        // We know args.length is 1 from isSetter check method
        Object value = args[0];

        values.put(propertyName, value);

        if (method.getReturnType().equals(type)) {
            result = proxy;
        }
        return result;
    }

    /**
     * Check whether or not method is a getter
     */
    private boolean isGetter(Method method, Object[] args) {
        return method.getName().startsWith("get") && (args == null || args.length == 0);
    }

    /**
     * If method is a getter, get the value from the values map for that
     * property, coerce it, and return it
     */
    private Object doGetter(Object proxy, Method method, Object[] args) {
        Object result = null;
        String propertyName = getPropertyName(method);
        result = values.get(propertyName);


        if (result != null && !(method.getReturnType().isAssignableFrom(result.getClass()))) {
            result = Coerce.to(result, method.getReturnType());
            values.put(propertyName, result);
        }
        return result;
    }

}

