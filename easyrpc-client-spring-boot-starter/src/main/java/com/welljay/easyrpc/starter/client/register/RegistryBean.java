package com.welljay.easyrpc.starter.client.register;

import com.welljay.easyrpc.annotation.RpcService;
import com.welljay.easyrpc.util.AnnoManageUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.util.List;

/**
 * @description 动态注入bean，并使用FactoryBean来生成接口的动态代理类
 * @author: welljay
 */
//还可以ImportBeanDefinitionRegistrar
public class RegistryBean implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        List<Class<?>> annotationClassList = AnnoManageUtil.getPackageController("com.welljay.easyrpc.test.jar", RpcService.class);

        //动态注入beanDefinition
        annotationClassList.forEach(c -> {
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(c);
            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
            //这儿非常重要！！！否则@Autowired的时候找不到对应的type注入
            definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
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

}
