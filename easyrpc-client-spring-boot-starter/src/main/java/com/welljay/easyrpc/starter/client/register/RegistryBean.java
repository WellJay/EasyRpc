package com.welljay.easyrpc.starter.client.register;

import com.welljay.easyrpc.service.EchoService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

public class RegistryBean implements BeanDefinitionRegistryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
	}
	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
		// 需要被代理的接口
		//todo 自动扫描配置中jar包中带有RpcService的接口并循环生成代理
		Class<?> cls = EchoService.class;
		BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(cls);
		GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
		definition.getPropertyValues().add("interfaceClass", definition.getBeanClassName());
		definition.setBeanClass(ProxyFactory.class);
		definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
		// 注册bean名,一般为类名首字母小写
		beanDefinitionRegistry.registerBeanDefinition("echoService", definition);
	}

}
