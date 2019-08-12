package com.welljay.easyrpc.starter.client.register;

import com.welljay.easyrpc.annotation.RpcService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RegistryBean implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        //todo 通过注解参数实现
        List<Class> annotationClassList = getAnnotationClass("com.welljay");

        //动态注入beanDefinition
        annotationClassList.forEach(c -> {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(c);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            //definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
            definition.setBeanClass(ProxyFactory.class);
            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);

            String simpleName = classToLowerName(c);
            registry.registerBeanDefinition(simpleName, definition);
        });
    }

    private String classToLowerName(Class<?> cls) {
        String simpleName = cls.getSimpleName();
        return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
    }

    public List<Class> getAnnotationClass(String packagePath) {
        String realPath = packagePath;
        if (packagePath.contains(".")) {
            String path = packagePath.replaceAll("\\.", "\\\\");
            realPath = RegistryBean.class.getResource("/").getPath() + path;
        }

        List<Class> classList = new ArrayList<>();

        File basePackageFile = new File(realPath);
        for (File file : basePackageFile.listFiles()) {
            if (file.isDirectory()) {
                getAnnotationClass(file.getPath());
            } else {
                String className = file.getName().replaceAll(".class", "");
                try {
                    if (!packagePath.contains(".")) {
                        packagePath = packagePath.substring(packagePath.indexOf("classes\\") + 8).replaceAll("\\\\", "\\.");
                    }
                    Class<?> aClass = Class.forName(packagePath + "." + className);
                    if (aClass.isAnnotationPresent(RpcService.class)) {
                        classList.add(aClass);
                    }
                } catch (Exception e) {
                    //System.out.println(file.getName() + "反射失败");
                }
            }
        }
        return classList;
    }

}
