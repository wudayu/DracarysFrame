package com.nfky.yaoyijia.model;

/**
 * Created by David on 10/8/15.
 * <p/>
 * VcControlBlock是控制界面不显示的对象
 */
public class VcControlBlock {

    private String componentName;
    private String failedMessage;

    /**
     * Gets component name.
     *
     * @return the component name
     */
    public String getComponentName() {
        return componentName;
    }

    /**
     * Sets component name.
     *
     * @param componentName the component name
     */
    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    /**
     * Gets failed message.
     *
     * @return the failed message
     */
    public String getFailedMessage() {
        return failedMessage;
    }

    /**
     * Sets failed message.
     *
     * @param failedMessage the failed message
     */
    public void setFailedMessage(String failedMessage) {
        this.failedMessage = failedMessage;
    }

    @Override
    public String toString() {
        return "VcControlBlock{" +
                "componentName='" + componentName + '\'' +
                ", failedMessage='" + failedMessage + '\'' +
                '}';
    }

}
