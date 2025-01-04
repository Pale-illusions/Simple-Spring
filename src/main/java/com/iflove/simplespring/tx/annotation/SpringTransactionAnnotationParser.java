package com.iflove.simplespring.tx.annotation;

import com.iflove.simplespring.core.annotation.AnnotatedElementUtils;
import com.iflove.simplespring.core.annotation.AnnotationAttributes;
import com.iflove.simplespring.tx.interceptor.RollbackRuleAttribute;
import com.iflove.simplespring.tx.interceptor.RuleBasedTransactionAttribute;
import com.iflove.simplespring.tx.interceptor.TransactionAttribute;

import java.io.Serializable;
import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;

public class SpringTransactionAnnotationParser implements TransactionAnnotationParser, Serializable {


    @Override
    public TransactionAttribute parseTransactionAnnotation(AnnotatedElement element) {
        AnnotationAttributes attributes = AnnotatedElementUtils.findMergedAnnotationAttributes(
                element, Transactional.class, false, false);
        if (null != attributes) {
            return parseTransactionAnnotation(attributes);
        } else {
            return null;
        }

    }

    protected TransactionAttribute parseTransactionAnnotation(AnnotationAttributes attributes) {
        RuleBasedTransactionAttribute rbta = new RuleBasedTransactionAttribute();

        List<RollbackRuleAttribute> rollbackRules = new ArrayList<>();
        for (Class<?> rbRule : attributes.getClassArray("rollbackFor")) {
            rollbackRules.add(new RollbackRuleAttribute(rbRule));
        }

        rbta.setRollbackRules(rollbackRules);
        return rbta;
    }
}
