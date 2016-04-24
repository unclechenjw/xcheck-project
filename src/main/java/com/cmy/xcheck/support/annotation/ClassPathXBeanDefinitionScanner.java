package com.cmy.xcheck.support.annotation;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.cmy.xcheck.ExpressionTypeEnum;
import com.cmy.xcheck.support.XBean;
import com.cmy.xcheck.support.XBean.CheckItem;
import com.cmy.xcheck.util.Assert;

public class ClassPathXBeanDefinitionScanner {

//    protected Set<BeanDefinitionHolder> doScan(String[] basePackages) {
//        Assert.notEmpty(basePackages, "At least one base package must be specified");
//        Set beanDefinitions = new LinkedHashSet();
//        for (String basePackage : basePackages) {
//            Set candidates = findCandidateComponents(basePackage);
//            for (BeanDefinition candidate : candidates) {
//                ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(candidate);
//                candidate.setScope(scopeMetadata.getScopeName());
//                String beanName = this.beanNameGenerator.generateBeanName(candidate, this.registry);
//                if (candidate instanceof AbstractBeanDefinition) {
//                    postProcessBeanDefinition((AbstractBeanDefinition) candidate, beanName);
//                }
//                if (candidate instanceof AnnotatedBeanDefinition) {
//                    AnnotationConfigUtils.processCommonDefinitionAnnotations((AnnotatedBeanDefinition) candidate);
//                }
//                if (checkCandidate(beanName, candidate)) {
//                    BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(candidate, beanName);
//                    definitionHolder = AnnotationConfigUtils.applyScopedProxyMode(scopeMetadata, definitionHolder,
//                            this.registry);
//                    beanDefinitions.add(definitionHolder);
//                    registerBeanDefinition(definitionHolder, this.registry);
//                }
//            }
//        }
//        return beanDefinitions;
//    }
    
    public static void main(String[] args) {
        Set<Class<?>> classes = getClasses("com.cmy");
        scanXBean(classes);
//        ArrayList<Class<?>> array = new ArrayList<Class<?>>();
//        array.addAll(classes);
//        Class<?> class1 = array.get(0);
//        Method[] declaredMethods = class1.getDeclaredMethods();
//        System.out.println(declaredMethods);
//        declaredMethods[0].isAnnotationPresent(Check.class);
//        System.out.println(classes);
    }

    public static void scanXBean(Set<Class<?>> classes) {
        
        for (Class<?> clz : classes) {
            Method[] methods = clz.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(Check.class)) {
                    parseXbean(clz, method);
                }
            }
        }
    }
    
    private static void parseXbean(Class<?> clz, Method method) {
        Check check = method.getAnnotation(Check.class);
        String[] values = check.value();
        Map<String, String> fieldAlias = parseMap(check.fieldAlias());
        boolean hint = check.hint();
        boolean required = check.required();
        CheckItem[] checkItems = new CheckItem[values.length];
        for (int i = 0; i < values.length; i++) {
            checkItems[i] = parseExpression(values[i]);
        }
        String identify = clz.getName() + "$" + method.getName();
        XAnnotationConfigApplicationContext.register(identify,
                new XBean(fieldAlias, checkItems, required, hint));
    }

    // 
    private static CheckItem parseExpression(String expression) {
        CheckItem checkItem;
        ExpressionTypeEnum expressType = calcExpressType(expression);
        if (expressType == ExpressionTypeEnum.EXPRESS_TYPE_SIMPLE) {
            checkItem = expressTypeSimple(expression, expressType);
        } else {
            checkItem = expressTypeSimple(expression, expressType);
        }
        return checkItem;
    }
    
    public static CheckItem expressTypeSimple(String expression, ExpressionTypeEnum expressionType) {
        
        Assert.expressionIllegal(expression);
        
        String formula;
        boolean nullable;
        
        int fieldBehindIndex;
        int atIndex = expression.indexOf("@");
        int numberSignIndex = expression.indexOf("#");
        // @校验参数不可空
        if (atIndex != -1) {
            nullable = false;
            fieldBehindIndex = atIndex;
        // #校验参数允许空
        } else if (numberSignIndex != -1) {
            nullable = true;
            fieldBehindIndex = numberSignIndex;
            // 无公式校验,参数不可空
        } else {
            nullable = false;
            fieldBehindIndex = expression.length();
        }

        String[] fields = getField(expression.substring(0, fieldBehindIndex));

        // 自定义错误提示
        String message;
        int tildeIndex = expression.indexOf(":");
        if (tildeIndex == -1) {
            formula = expression.substring(fieldBehindIndex, expression.length());
            message = null;
        } else {
            formula = expression.substring(fieldBehindIndex, tildeIndex);
            message = expression.substring(tildeIndex+1, expression.length());
        }
        return new XBean.CheckItem(
                formula, fields, message, expressionType, nullable);
        
    }

    /**
     * 获取单字段或多字段[]字段名与值
     * @param field
     * @param source
     * @return
     */
    private static String[] getField(String fields) {
        if (fields.startsWith("[")) {
            String[] split = fields.substring(1, fields.length()-1).split(",");
            return split;
        } else {
            return new String[]{fields};
        }
    }
    
    private static Map<String, String> parseMap(String[] fieldAlias) {
        Map<String, String> m = new HashMap<String, String>();
        for (String alias : fieldAlias) {
            String[] split = alias.replaceAll("\\s", "").split(",");
            for (String sp :split) {
                String[] fieldAndName = sp.split("=");
                int flen = fieldAndName.length;
                if (flen == 1) {
                    return m;
                } else if ( flen == 2) {
                    m.put(fieldAndName[0], fieldAndName[1]);
                } else {
                    throw new IllegalArgumentException("");
                }
            }
        }
        return m;
    }
    
    private static ExpressionTypeEnum calcExpressType(String express) {
        ExpressionTypeEnum expressType;
        if (express.startsWith("if")) {
            expressType = ExpressionTypeEnum.EXPRESS_TYPE_IF_FORMULA;
        } else if (express.startsWith("[")) {
            expressType = ExpressionTypeEnum.EXPRESS_TYPE_MULTIATTRIBUTE;
        } else if (express.matches("(.*?)(<=|<|>=|>|==|!=)(.*)")) {
            expressType = ExpressionTypeEnum.EXPRESS_TYPE_LOGICAL_OPERATION;
        } else {
            expressType = ExpressionTypeEnum.EXPRESS_TYPE_SIMPLE;
        }
        return expressType;
    }
    /** 
     * 从包package中获取所有的Class 
     *  
     * @param pack 
     * @return 
     */  
    public static Set<Class<?>> getClasses(String pack) {  
  
        // 第一个class类的集合  
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();  
        // 是否循环迭代  
        boolean recursive = true;  
        // 获取包的名字 并进行替换  
        String packageName = pack;  
        String packageDirName = packageName.replace('.', '/');  
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things  
        Enumeration<URL> dirs;  
        try {  
            dirs = Thread.currentThread().getContextClassLoader().getResources(  
                    packageDirName);  
            // 循环迭代下去  
            while (dirs.hasMoreElements()) {  
                // 获取下一个元素  
                URL url = dirs.nextElement();  
                // 得到协议的名称  
                String protocol = url.getProtocol();  
                // 如果是以文件的形式保存在服务器上  
                if ("file".equals(protocol)) {  
                    System.err.println("file类型的扫描");  
                    // 获取包的物理路径  
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");  
                    // 以文件的方式扫描整个包下的文件 并添加到集合中  
                    findAndAddClassesInPackageByFile(packageName, filePath,  
                            recursive, classes);  
                } else if ("jar".equals(protocol)) {  
                    // 如果是jar包文件  
                    // 定义一个JarFile  
                    System.err.println("jar类型的扫描");  
                    JarFile jar;  
                    try {  
                        // 获取jar  
                        jar = ((JarURLConnection) url.openConnection())  
                                .getJarFile();  
                        // 从此jar包 得到一个枚举类  
                        Enumeration<JarEntry> entries = jar.entries();  
                        // 同样的进行循环迭代  
                        while (entries.hasMoreElements()) {  
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件  
                            JarEntry entry = entries.nextElement();  
                            String name = entry.getName();  
                            // 如果是以/开头的  
                            if (name.charAt(0) == '/') {  
                                // 获取后面的字符串  
                                name = name.substring(1);  
                            }  
                            // 如果前半部分和定义的包名相同  
                            if (name.startsWith(packageDirName)) {  
                                int idx = name.lastIndexOf('/');  
                                // 如果以"/"结尾 是一个包  
                                if (idx != -1) {  
                                    // 获取包名 把"/"替换成"."  
                                    packageName = name.substring(0, idx)  
                                            .replace('/', '.');  
                                }  
                                // 如果可以迭代下去 并且是一个包  
                                if ((idx != -1) || recursive) {  
                                    // 如果是一个.class文件 而且不是目录  
                                    if (name.endsWith(".class")  
                                            && !entry.isDirectory()) {  
                                        // 去掉后面的".class" 获取真正的类名  
                                        String className = name.substring(  
                                                packageName.length() + 1, name  
                                                        .length() - 6);  
                                        try {  
                                            // 添加到classes  
                                            Class<?> clz = Class.forName(packageName + '.' + className);
                                            classes.add(clz);  
//                                        } catch (ClassNotFoundException e) {  
                                        } catch (Exception e) {  
                                            // log  
                                            // .error("添加用户自定义视图类错误 找不到此类的.class文件");  
                                            e.printStackTrace();  
                                        }  
                                    }  
                                }  
                            }  
                        }  
                    } catch (IOException e) {  
                        // log.error("在扫描用户定义视图时从jar包获取文件出错");  
                        e.printStackTrace();  
                    }  
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
  
        return classes;  
    }

    /** 
     * 以文件的形式来获取包下的所有Class 
     *  
     * @param packageName 
     * @param packagePath 
     * @param recursive 
     * @param classes 
     */  
    public static void findAndAddClassesInPackageByFile(String packageName,  
            String packagePath, final boolean recursive, Set<Class<?>> classes) {  
        // 获取此包的目录 建立一个File  
        File dir = new File(packagePath);  
        // 如果不存在或者 也不是目录就直接返回  
        if (!dir.exists() || !dir.isDirectory()) {  
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");  
            return;  
        }  
        // 如果存在 就获取包下的所有文件 包括目录  
        File[] dirfiles = dir.listFiles(new FileFilter() {  
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)  
            public boolean accept(File file) {  
                return (recursive && file.isDirectory())  
                        || (file.getName().endsWith(".class"));  
            }  
        });  
        // 循环所有文件  
        for (File file : dirfiles) {  
            // 如果是目录 则继续扫描  
            if (file.isDirectory()) {  
                findAndAddClassesInPackageByFile(packageName + "."  
                        + file.getName(), file.getAbsolutePath(), recursive,  
                        classes);  
            } else {  
                // 如果是java类文件 去掉后面的.class 只留下类名  
                String className = file.getName().substring(0,  
                        file.getName().length() - 6);  
                try {  
                    // 添加到集合中去  
                    //classes.add(Class.forName(packageName + '.' + className));  
                                         //经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净  
                    classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));    
                } catch (ClassNotFoundException e) {  
                    // log.error("添加用户自定义视图类错误 找不到此类的.class文件");  
                    e.printStackTrace();  
                }  
            }  
        }  
    }  
}
