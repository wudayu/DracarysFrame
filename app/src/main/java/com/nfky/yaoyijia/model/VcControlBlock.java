package com.nfky.yaoyijia.model;

/**
 * Created by David on 10/8/15.
 *
 * VcControlBlock是控制界面不显示的对象
 */
public class VcControlBlock {

    private String componentName;
    private String failedMessage;

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getFailedMessage() {
        return failedMessage;
    }

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
