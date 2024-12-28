package com.iflove.simplespring.context.annotation;

import cn.hutool.core.util.ClassUtil;
import com.iflove.simplespring.beans.factory.config.BeanDefinition;
import com.iflove.simplespring.stereotype.Component;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 * A component provider that scans the classpath from a base package. It then
 * applies exclude and include filters to the resulting classes to find candidates.
 */

public class ClassPathScanningCandidateComponentProvider {

    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(basePackage, Component.class);
        for (Class<?> clazz : classes) {
            candidates.add(new BeanDefinition(clazz));
        }
        return candidates;
    }
}
