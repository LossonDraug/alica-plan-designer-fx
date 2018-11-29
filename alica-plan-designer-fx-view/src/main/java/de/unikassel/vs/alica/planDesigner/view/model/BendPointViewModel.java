package de.unikassel.vs.alica.planDesigner.view.model;

public class BendPointViewModel extends PlanElementViewModel {
    private double x;
    private double y;
    private TransitionViewModel transition;

    public BendPointViewModel(long id, String name, String type) {
        super(id, name, type);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public TransitionViewModel getTransition() {
        return transition;
    }

    public void setTransition(TransitionViewModel transition) {
        this.transition = transition;
    }
}
