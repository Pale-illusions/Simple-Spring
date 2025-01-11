package com.iflove.simplespring.context.support;

/**
 * @author 苍镜月
 * @version 1.0
 * @implNote
 */

public abstract class AbstractRefreshableConfigApplicationContext extends AbstractRefreshableApplicationContext {

    private String[] configLocations;

    public void setConfigLocations(String... locations) {
        this.configLocations = locations;
    }

    protected String[] getConfigLocations() {
        return (this.configLocations != null ? this.configLocations : getDefaultConfigLocations());
    }

    protected String[] getDefaultConfigLocations() {
        return null;
    }
}
