package de.uni_kassel.vs.cn.planDesigner.model;

public class AbstractPlan extends PlanElement {

    protected String destinationPath;

    public String getDestinationPath() {
        return destinationPath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }
}
