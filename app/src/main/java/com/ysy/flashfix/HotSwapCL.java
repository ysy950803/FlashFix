package com.ysy.flashfix;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;

public class HotSwapCL extends ClassLoader {

    private String baseDir;
    private HashSet<String> dynamicClassNames;

    public HotSwapCL(String baseDir, String[] dynaClsNames) {
//        super(null);
        this.baseDir = baseDir;
        this.dynamicClassNames = new HashSet<>();
        loadClassCustom(dynaClsNames);
    }

    private void loadClassCustom(String[] classNames) {
        for (String name : classNames) {
            loadDirectly(name);
            dynamicClassNames.add(name);
        }
    }

    private Class loadDirectly(String name) {
        Class cls = null;
        StringBuffer sb = new StringBuffer(baseDir);
        String className = name.replace('.', File.separatorChar) + ".class";
        sb.append(File.separator + className);
        File classFile = new File(sb.toString());
        try {
            cls = instantiateClass(name, new FileInputStream(classFile), classFile.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return cls;
    }

    private Class instantiateClass(String name, InputStream iS, long len) {
        byte[] raw = new byte[(int) len];
        try {
            iS.read(raw);
            iS.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, raw, 0, raw.length);
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class cls;
        cls = findLoadedClass(name);
        if (!dynamicClassNames.contains(name) && cls == null) {
            cls = getSystemClassLoader().loadClass(name);
        }
        if (cls == null) {
            throw new ClassNotFoundException(name);
        }
        if (resolve) {
            resolveClass(cls);
        }
        return cls;
    }
}
