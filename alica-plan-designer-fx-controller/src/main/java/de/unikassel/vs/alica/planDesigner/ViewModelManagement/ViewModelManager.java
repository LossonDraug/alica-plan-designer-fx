package de.unikassel.vs.alica.planDesigner.ViewModelManagement;

import de.unikassel.vs.alica.planDesigner.alicamodel.*;
import de.unikassel.vs.alica.planDesigner.events.ModelEvent;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.BendPoint;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.UiElement;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryViewModel;
import javafx.collections.ObservableList;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ViewModelManager {

    protected ModelManager modelManager;
    protected IGuiModificationHandler guiModificationHandler;
    protected Map<Long, ViewModelElement> viewModelElements;

    public ViewModelManager(ModelManager modelManager, IGuiModificationHandler handler) {
        this.modelManager = modelManager;
        this.guiModificationHandler = handler;
        this.viewModelElements = new HashMap<>();
    }

    public RepositoryViewModel createRepositoryViewModel() {
        return new RepositoryViewModel();
    }

    /**
     * Just returns an existing view model object, if it already exists.
     * Otherwise, it will create one according to the given planElement object.
     *
     * @param planElement The model object that corresponds to the wanted view model object.
     * @return the view model object
     */
    public ViewModelElement getViewModelElement(PlanElement planElement) {
        if (planElement == null) {
            throw new NullPointerException("planElement was null");
        }

        ViewModelElement element = this.viewModelElements.get(planElement.getId());
        if (element != null) {
            return element;
        }

        if (planElement instanceof Behaviour) {
            element = createBehaviourViewModel((Behaviour) planElement);
        } else if (planElement instanceof Task) {
            element = createTaskViewModel((Task) planElement);
        } else if (planElement instanceof TaskRepository) {
            element = createTaskRepositoryViewModel((TaskRepository) planElement);
        } else if (planElement instanceof Plan) {
            element = createPlanViewModel((Plan) planElement);
        } else if (planElement instanceof PlanType) {
            element = createPlanTypeViewModel((PlanType) planElement);
        } else if (planElement instanceof State) {
            element = createStateViewModel((State) planElement);
        } else if (planElement instanceof AnnotatedPlan) {
            element = createAnnotatedPlanViewModel((AnnotatedPlan) planElement);
        } else if (planElement instanceof EntryPoint) {
            element = createEntryPointViewModel((EntryPoint) planElement);
        } else if (planElement instanceof Variable) {
            element = createVariableViewModel((Variable) planElement);
        } else if (planElement instanceof Transition) {
            element = createTransitionViewModel((Transition) planElement);
        } else if (planElement instanceof Synchronisation) {
            element = createSynchronizationViewModel((Synchronisation) planElement);
        } else if (planElement instanceof Quantifier) {
            element = createQuantifierViewModel((Quantifier) planElement);
        } else if (planElement instanceof Condition) {
            element = createConditionViewModel((Condition) planElement);
        } else if (planElement instanceof BendPoint) {
            element = createBendPointViewModel((BendPoint) planElement);
        } else {
            System.err.println("ViewModelManager: getSerializableViewModel for type " + planElement.getClass().toString() + " not implemented!");
        }

        viewModelElements.put(planElement.getId(), element);
        element.registerListener(guiModificationHandler);
        return element;
    }

    private BendPointViewModel createBendPointViewModel(BendPoint bendPoint) {
        BendPointViewModel bendPointViewModel = new BendPointViewModel(bendPoint.getId(), bendPoint.getName(), Types.BENDPOINT);
        bendPointViewModel.setX(bendPoint.getX());
        bendPointViewModel.setY(bendPoint.getY());
        bendPointViewModel.setTransition((TransitionViewModel) getViewModelElement(bendPoint.getTransition()));
        return bendPointViewModel;
    }

    private TaskRepositoryViewModel createTaskRepositoryViewModel(TaskRepository taskRepository) {
        TaskRepositoryViewModel taskRepositoryViewModel = new TaskRepositoryViewModel(taskRepository.getId(), taskRepository.getName(), Types.TASKREPOSITORY);
        taskRepositoryViewModel.setComment(taskRepository.getComment());
        taskRepositoryViewModel.setRelativeDirectory(taskRepository.getRelativeDirectory());
        // we need to put the repo before creating tasks, in order to avoid circles (Task <-> Repo)
        this.viewModelElements.put(taskRepositoryViewModel.getId(), taskRepositoryViewModel);
        for (Task task : taskRepository.getTasks()) {
            taskRepositoryViewModel.addTask((TaskViewModel) getViewModelElement(task));
        }
        return taskRepositoryViewModel;
    }

    private TaskViewModel createTaskViewModel(Task task) {
        TaskViewModel taskViewModel = new TaskViewModel(task.getId(), task.getName(), Types.TASK);
        taskViewModel.setTaskRepositoryViewModel((TaskRepositoryViewModel) getViewModelElement(task.getTaskRepository()));
        taskViewModel.getTaskRepositoryViewModel().addTask(taskViewModel);
        taskViewModel.setParentId(task.getTaskRepository().getId());
        return taskViewModel;
    }

    private BehaviourViewModel createBehaviourViewModel(Behaviour behaviour) {
        BehaviourViewModel behaviourViewModel = new BehaviourViewModel(behaviour.getId(), behaviour.getName(), Types.BEHAVIOUR);
        behaviourViewModel.setComment(behaviour.getComment());
        behaviourViewModel.setRelativeDirectory(behaviour.getRelativeDirectory());
        behaviourViewModel.setFrequency(behaviour.getFrequency());
        behaviourViewModel.setDeferring(behaviour.getDeferring());

        for (Variable variable : behaviour.getVariables()) {
            behaviourViewModel.getVariables().add((VariableViewModel) getViewModelElement(variable));
        }

        if (behaviour.getPreCondition() != null) {
            ConditionViewModel preConditionViewModel = (ConditionViewModel) getViewModelElement(behaviour.getPreCondition());
            preConditionViewModel.setParentId(behaviour.getId());
            behaviourViewModel.setPreCondition(preConditionViewModel);
        }

        if (behaviour.getRuntimeCondition() != null) {
            ConditionViewModel runtimeConditionViewModel = (ConditionViewModel) getViewModelElement(behaviour.getRuntimeCondition());
            runtimeConditionViewModel.setParentId(behaviour.getId());
            behaviourViewModel.setRuntimeCondition(runtimeConditionViewModel);
        }

        if (behaviour.getPostCondition() != null) {
            ConditionViewModel postConditionViewModel = (ConditionViewModel) getViewModelElement(behaviour.getPostCondition());
            postConditionViewModel.setParentId(behaviour.getId());
            behaviourViewModel.setPostCondition(postConditionViewModel);
        }

        return behaviourViewModel;
    }

    private VariableViewModel createVariableViewModel(Variable var) {
        VariableViewModel variableViewModel = new VariableViewModel(var.getId(), var.getName(), Types.VARIABLE);
        variableViewModel.setVariableType(var.getVariableType());
        variableViewModel.setComment(var.getComment());
        return variableViewModel;
    }

    private ConditionViewModel createConditionViewModel(Condition condition) {
        ConditionViewModel conditionViewModel = null;
        if (condition instanceof PreCondition) {
            conditionViewModel = new ConditionViewModel(condition.getId(), condition.getName(), Types.PRECONDITION);
        } else if (condition instanceof RuntimeCondition) {
            conditionViewModel = new ConditionViewModel(condition.getId(), condition.getName(), Types.RUNTIMECONDITION);
        } else if (condition instanceof PostCondition) {
            conditionViewModel = new ConditionViewModel(condition.getId(), condition.getName(), Types.POSTCONDITION);
        }
        conditionViewModel.setConditionString(condition.getConditionString());
        conditionViewModel.setEnabled(condition.getEnabled());
        conditionViewModel.setPluginName(condition.getPluginName());
        conditionViewModel.setComment(condition.getComment());
        for (Variable var : condition.getVariables()) {
            conditionViewModel.getVariables().add((VariableViewModel) getViewModelElement(var));
        }
        for (Quantifier quantifier : condition.getQuantifiers()) {
            // TODO: Quantifier is not very clean or fully implemented, yet.
            conditionViewModel.getQuantifiers().add((QuantifierViewModel) getViewModelElement(quantifier));
        }
        return conditionViewModel;
    }

    private QuantifierViewModel createQuantifierViewModel(Quantifier quantifier) {
        QuantifierViewModel viewModel = new QuantifierViewModel(quantifier.getId(), quantifier.getName(), Types.QUANTIFIER);
        if(quantifier.getScope() != null){
            viewModel.setScope(quantifier.getScope().getId());
        }
        viewModel.setQuantifierType(quantifier.getQuantifierType());
        viewModel.setComment(quantifier.getComment());
        if(quantifier.getSorts() != null) {
            viewModel.setSorts(String.join(" ", quantifier.getSorts()));
        }
        return  viewModel;
    }

    private PlanTypeViewModel createPlanTypeViewModel(PlanType planType) {
        PlanTypeViewModel planTypeViewModel = new PlanTypeViewModel(planType.getId(), planType.getName(),
                Types.PLANTYPE);
        planTypeViewModel.setRelativeDirectory(planType.getRelativeDirectory());
        planTypeViewModel.setComment(planType.getComment());

        // Putting the PlanType into the map, before all fields and related elements are set, prevents an endless
        // recursion, which would otherwise occur, whenever any plan (or, to be precise, a plans state) contains the
        // PlanType, because each PlanType contains all Plans in a list
        viewModelElements.put(planTypeViewModel.getId(), planTypeViewModel);

        for (Plan plan : modelManager.getPlans()) {
            planTypeViewModel.addPlanToAllPlans((PlanViewModel) getViewModelElement(plan));
        }

        for (AnnotatedPlan annotatedPlan : planType.getPlans()) {
            planTypeViewModel.removePlanFromAllPlans(annotatedPlan.getPlan().getId());
            planTypeViewModel.getPlansInPlanType().add((AnnotatedPlanView) getViewModelElement(annotatedPlan));
        }
        return planTypeViewModel;
    }

    private AnnotatedPlanView createAnnotatedPlanViewModel(AnnotatedPlan annotatedPlan) {
        // The AnnotatedPlan may still be holding a place-holder-plan, that was created during deserialization, to get
        // the actual plan the place-holders id can be used
        Plan plan = (Plan) modelManager.getPlanElement(annotatedPlan.getPlan().getId());
        return new AnnotatedPlanView(annotatedPlan.getId(), plan.getName(), Types.ANNOTATEDPLAN, annotatedPlan
                .isActivated(), plan.getId());
    }

    private StateViewModel createStateViewModel(State state) {
        String type;
        if (state instanceof TerminalState) {
            type = ((TerminalState) state).isSuccess() ? Types.SUCCESSSTATE : Types.FAILURESTATE;
        } else {
            type = Types.STATE;
        }
        StateViewModel stateViewModel = new StateViewModel(state.getId(), state.getName(), type);
        stateViewModel.setComment(state.getComment());
        stateViewModel.setParentId(state.getParentPlan().getId());
        UiElement uiElement = modelManager.getPlanUIExtensionPair(state.getParentPlan().getId()).getUiElement(state.getId());
        stateViewModel.setXPosition(uiElement.getX());
        stateViewModel.setYPosition(uiElement.getY());

        for (AbstractPlan abstractPlan : state.getPlans()) {
            stateViewModel.getPlanElements().add((PlanElementViewModel) getViewModelElement(modelManager.getPlanElement(abstractPlan.getId())));
        }
        if (state.getEntryPoint() != null) {
            stateViewModel.setEntryPoint((EntryPointViewModel) getViewModelElement(modelManager.getPlanElement(state.getEntryPoint().getId())));
        }
        return stateViewModel;
    }

    private EntryPointViewModel createEntryPointViewModel(EntryPoint ep) {
        EntryPointViewModel entryPointViewModel = new EntryPointViewModel(ep.getId(), ep.getName(), Types.ENTRYPOINT);
        // we need to put the ep before creating the state, in order to avoid circles (EntryPoint <-> State)
        this.viewModelElements.put(entryPointViewModel.getId(), entryPointViewModel);
        if (ep.getState() != null) {
            StateViewModel entryState = (StateViewModel) getViewModelElement(modelManager.getPlanElement(ep.getState().getId()));
            entryPointViewModel.setState(entryState);
            entryState.setEntryPoint(entryPointViewModel);
        }
        if (ep.getTask() != null) {
            entryPointViewModel.setTask((TaskViewModel) getViewModelElement(ep.getTask()));
        }
        entryPointViewModel.setParentId(ep.getPlan().getId());
        UiElement uiElement = modelManager.getPlanUIExtensionPair(ep.getPlan().getId()).getUiElement(ep.getId());
        entryPointViewModel.setXPosition(uiElement.getX());
        entryPointViewModel.setYPosition(uiElement.getY());
        return entryPointViewModel;
    }

    private TransitionViewModel createTransitionViewModel(Transition transition) {
        TransitionViewModel transitionViewModel = new TransitionViewModel(transition.getId(), transition.getName(), Types.TRANSITION);
        transitionViewModel.setInState((StateViewModel) getViewModelElement(transition.getInState()));
        transitionViewModel.setOutState((StateViewModel) getViewModelElement(transition.getOutState()));
        if (transition.getPreCondition() != null) {
            ConditionViewModel conditionViewModel = (ConditionViewModel) getViewModelElement(transition.getPreCondition());
            conditionViewModel.setParentId(transition.getId());
            transitionViewModel.setPreCondition(conditionViewModel);
        }
        return transitionViewModel;
    }

    private SynchronizationViewModel createSynchronizationViewModel(Synchronisation synchronisation) {
        SynchronizationViewModel synchronizationViewModel = new SynchronizationViewModel(synchronisation.getId(), synchronisation.getName(),
                Types.SYNCHRONISATION);
        for (Transition transition : synchronisation.getSyncedTransitions()) {
            synchronizationViewModel.getTransitions().add((TransitionViewModel) getViewModelElement(transition));
        }
        UiElement uiElement = modelManager.getPlanUIExtensionPair(synchronisation.getPlan().getId()).getUiElement(synchronisation.getId());
        synchronizationViewModel.setXPosition(uiElement.getX());
        synchronizationViewModel.setYPosition(uiElement.getY());
        return synchronizationViewModel;
    }


    private PlanViewModel createPlanViewModel(Plan plan) {
        PlanViewModel planViewModel;
        if (plan.getMasterPlan()) {
            planViewModel = new PlanViewModel(plan.getId(), plan.getName(), Types.MASTERPLAN);
        } else {
            planViewModel = new PlanViewModel(plan.getId(), plan.getName(), Types.PLAN);
        }
        planViewModel.setMasterPlan(plan.getMasterPlan());
        planViewModel.setUtilityThreshold(plan.getUtilityThreshold());
        planViewModel.setComment(plan.getComment());
        planViewModel.setRelativeDirectory(plan.getRelativeDirectory());
        for (State state : plan.getStates()) {
            planViewModel.getStates().add(
                    (StateViewModel) getViewModelElement(state));
        }
        for (EntryPoint ep : plan.getEntryPoints()) {
            planViewModel.getEntryPoints().add((EntryPointViewModel) getViewModelElement(ep));
        }
        for (Transition transition : plan.getTransitions()) {
            planViewModel.getTransitions().add((TransitionViewModel) getViewModelElement(transition));
        }
        for (Synchronisation synchronisation : plan.getSynchronisations()) {
            planViewModel.getSynchronisations().add((SynchronizationViewModel) getViewModelElement(synchronisation));
        }
        for (Variable var : plan.getVariables()) {
            planViewModel.getVariables().add((VariableViewModel) getViewModelElement(var));
        }
        if (plan.getPreCondition() != null) {
            ConditionViewModel conditionViewModel = (ConditionViewModel) getViewModelElement(plan.getPreCondition());
            conditionViewModel.setParentId(plan.getId());
            planViewModel.setPreCondition(conditionViewModel);
        }
        if (plan.getRuntimeCondition() != null) {
            ConditionViewModel conditionViewModel = (ConditionViewModel) getViewModelElement(plan.getRuntimeCondition());
            conditionViewModel.setParentId(plan.getId());
            planViewModel.setRuntimeCondition(conditionViewModel);
        }

        return planViewModel;
    }

    public void removeElement(long parentId, ViewModelElement viewModelElement) {
        switch (viewModelElement.getType()) {
            case Types.TASK:
                ((TaskViewModel) viewModelElement).getTaskRepositoryViewModel().removeTask(parentId);
                break;
            case Types.STATE:
            case Types.SUCCESSSTATE:
            case Types.FAILURESTATE:
                StateViewModel stateViewModel = (StateViewModel) viewModelElement;
                PlanViewModel planViewModel = (PlanViewModel) getViewModelElement(modelManager.getPlanElement(parentId));

                planViewModel.getStates().remove(stateViewModel);
                break;
            case Types.ENTRYPOINT:
                EntryPointViewModel entryPointViewModel = (EntryPointViewModel) viewModelElement;
                planViewModel = (PlanViewModel) getViewModelElement(modelManager.getPlanElement(parentId));

                planViewModel.getEntryPoints().remove(entryPointViewModel);
                if (entryPointViewModel.getState() != null) {
                    StateViewModel entryState = entryPointViewModel.getState();
                    entryState.setEntryPoint(null);
                }
                break;
            case Types.TRANSITION:
                TransitionViewModel transitionViewModel = (TransitionViewModel) viewModelElement;
                planViewModel = (PlanViewModel) getViewModelElement(modelManager.getPlanElement(parentId));

                planViewModel.getTransitions().remove(transitionViewModel);
                break;
            case Types.ANNOTATEDPLAN:
                AnnotatedPlanView annotatedPlanView = (AnnotatedPlanView) viewModelElement;
                PlanTypeViewModel planTypeViewModel = (PlanTypeViewModel) getViewModelElement(modelManager.getPlanElement(parentId));
                planTypeViewModel.getPlansInPlanType().remove(annotatedPlanView);
                break;
            case Types.PLAN:
            case Types.MASTERPLAN:
            case Types.PLANTYPE:
            case Types.BEHAVIOUR:
                PlanElement parentPlanElement = modelManager.getPlanElement(parentId);
                if(parentPlanElement != null) {
                    ViewModelElement parentViewModel = getViewModelElement(parentPlanElement);
                    stateViewModel = (StateViewModel) parentViewModel;
                    PlanElementViewModel viewModel = (PlanElementViewModel) viewModelElement;
                    stateViewModel.getPlanElements().remove(viewModel);
                    planViewModel = (PlanViewModel) getViewModelElement(modelManager.getPlanElement(stateViewModel.getParentId()));

                    // you have duplicates if don't remove and add
                    planViewModel.getStates().remove(stateViewModel);
                    planViewModel.getStates().add(stateViewModel);
                }else if(viewModelElement.getType().equals(Types.PLAN) || viewModelElement.getType().equals(Types.MASTERPLAN)) {
                    updatePlansInPlanViewModels((PlanViewModel) viewModelElement, ModelEventType.ELEMENT_ADDED);
                }
                break;
            case Types.VARIABLE:
                ViewModelElement parentViewModel = getViewModelElement(modelManager.getPlanElement(parentId));
                if ( parentViewModel instanceof HasVariablesView) {
                    ((HasVariablesView) parentViewModel).getVariables().remove(viewModelElement);
                } else {
                    throw new RuntimeException(getClass().getSimpleName() + ": Parent ViewModel object has no variables");
                }
                break;
            case Types.PRECONDITION:
                parentViewModel = getViewModelElement(modelManager.getPlanElement(parentId));
                switch (parentViewModel.getType()){
                    case Types.PLAN:
                    case Types.MASTERPLAN:
                        ((PlanViewModel) parentViewModel).setPreCondition(null);
                        break;
                    case Types.BEHAVIOUR:
                        ((BehaviourViewModel) parentViewModel).setPreCondition(null);
                        break;
                    default:
                }
                break;
            case Types.RUNTIMECONDITION:
                parentViewModel = getViewModelElement(modelManager.getPlanElement(parentId));
                switch (parentViewModel.getType()) {
                    case Types.PLAN:
                    case Types.MASTERPLAN:
                        ((PlanViewModel)parentViewModel).setRuntimeCondition(null);
                        break;
                    case Types.BEHAVIOUR:
                        ((BehaviourViewModel)parentViewModel).setRuntimeCondition(null);
                        break;
                    default:
                }
                break;
            case Types.POSTCONDITION:
                parentViewModel = getViewModelElement(modelManager.getPlanElement(parentId));
                switch (parentViewModel.getType()) {
//                    case Types.SUCCESSSTATE:
//                    case Types.FAILURESTATE:
//
//                        break;
                    case Types.BEHAVIOUR:
                        ((BehaviourViewModel)parentViewModel).setPostCondition(null);
                        break;
                    default:
                }

                break;
            case Types.QUANTIFIER:
                parentViewModel = getViewModelElement(modelManager.getPlanElement(parentId));
                switch (parentViewModel.getType()){
                    case Types.PRECONDITION:
                    case Types.RUNTIMECONDITION:
                    case Types.POSTCONDITION:
                        ((ConditionViewModel) parentViewModel).getQuantifiers().remove(viewModelElement);
                        break;
                    default:
                        throw new RuntimeException(getClass().getSimpleName() + ": ParentViewModel has no Quantifiers");
                }
                break;
            default:
                System.err.println("ViewModelManager: Remove Element not supported for type: " + viewModelElement.getType());
                //TODO: maybe handle other types
        }

        viewModelElements.remove(viewModelElement.getId());
    }

    public void addElement(ModelEvent event) {
        PlanElement parentPlanElement = modelManager.getPlanElement(event.getParentId());
        ViewModelElement parentViewModel = null;
        if(parentPlanElement != null) {
            parentViewModel = getViewModelElement(parentPlanElement);
        }
        ViewModelElement viewModelElement = getViewModelElement(event.getElement());

        if (parentViewModel instanceof  PlanViewModel && !event.getElementType().equals(Types.ABSTRACTPLAN)
                && !event.getElementType().equals(Types.PRECONDITION) && !event.getElementType().equals(Types.RUNTIMECONDITION)) {
            addToPlan((PlanViewModel) parentViewModel, viewModelElement, event);
            return;
        }

        switch (event.getElementType()) {
            case Types.ANNOTATEDPLAN:
                AnnotatedPlanView annotatedPlanView = (AnnotatedPlanView) viewModelElement;
                PlanTypeViewModel planTypeViewModel = (PlanTypeViewModel) parentViewModel;
                planTypeViewModel.getPlansInPlanType().add(annotatedPlanView);
                break;
            case Types.TASK:
                PlanElementViewModel planElementViewModel = (PlanElementViewModel) viewModelElement;
                EntryPointViewModel entryPointViewModel = (EntryPointViewModel) parentViewModel;
                entryPointViewModel.setTask(planElementViewModel);
                break;
            case Types.PLAN:
            case Types.MASTERPLAN:
            case Types.BEHAVIOUR:
            case Types.PLANTYPE:
                if (parentPlanElement != null) {
                    planElementViewModel = (PlanElementViewModel) viewModelElement;
                    StateViewModel stateViewModel = (StateViewModel) parentViewModel;
                    stateViewModel.getPlanElements().add(planElementViewModel);
                }else if(event.getElementType().equals(Types.PLAN) || event.getElementType().equals(Types.MASTERPLAN)) {
                    updatePlansInPlanViewModels((PlanViewModel) viewModelElement, ModelEventType.ELEMENT_ADDED);
                }
                break;
            case Types.VARIABLE:
                ((HasVariablesView) parentViewModel).getVariables().add((VariableViewModel) viewModelElement);
                break;
            case Types.ABSTRACTPLAN:
                PlanViewModel planViewModel = (PlanViewModel) viewModelElement;
                State state = (State) event.getNewValue();
                for (StateViewModel stateViewModel: planViewModel.getStates()) {
                    if(stateViewModel.getId() == state.getId()){
                       stateViewModel.getPlanElements().add((PlanElementViewModel) parentViewModel);
                    }
                }
                break;
            case Types.PRECONDITION:
                switch (parentViewModel.getType()){
                    case Types.PLAN:
                    case Types.MASTERPLAN:
                        ((PlanViewModel)parentViewModel).setPreCondition((ConditionViewModel) viewModelElement);
                        break;
                    case Types.BEHAVIOUR:
                        ((BehaviourViewModel)parentViewModel).setPreCondition((ConditionViewModel) viewModelElement);
                        break;
                    default:
                        System.err.println("ViewModelManager: Add Element not supported for preCondition and " + parentViewModel.getType());
                }
                break;
            case Types.RUNTIMECONDITION:
                switch (parentViewModel.getType()){
                    case Types.PLAN:
                    case Types.MASTERPLAN:
                        ((PlanViewModel)parentViewModel).setRuntimeCondition((ConditionViewModel) viewModelElement);
                        break;
                    case Types.BEHAVIOUR:
                        ((BehaviourViewModel)parentViewModel).setRuntimeCondition((ConditionViewModel) viewModelElement);
                        break;
                    default:
                        System.err.println("ViewModelManager: Add Element not supported for runtimeCondition and " + parentViewModel.getType());
                }
                break;
            case Types.POSTCONDITION:
                switch (parentViewModel.getType()){
                    case Types.BEHAVIOUR:
                        ((BehaviourViewModel)parentViewModel).setPostCondition((ConditionViewModel) viewModelElement);
                        break;
//                    case Types.SUCCESSSTATE:
//                    case Types.FAILURESTATE:
//
//                        break;
                    default:
                        System.err.println("ViewModelManager: Add Element not supported for postCondition and " + parentViewModel.getType());
                }
                break;
            case Types.QUANTIFIER:
                ConditionViewModel conditionViewModel = (ConditionViewModel) parentViewModel;
                conditionViewModel.getQuantifiers().add((QuantifierViewModel) viewModelElement);
                break;
            default:
                System.err.println("ViewModelManager: Add Element not supported for type: " + viewModelElement.getType());
                //TODO: maybe handle other types
        }
    }

    private void addToPlan(PlanViewModel parentPlan, ViewModelElement element, ModelEvent event) {
        switch (event.getElementType()) {
            case Types.STATE:
            case Types.SUCCESSSTATE:
            case Types.FAILURESTATE:
                StateViewModel stateViewModel = (StateViewModel) element;
                stateViewModel.setXPosition(event.getUiElement().getX());
                stateViewModel.setYPosition(event.getUiElement().getY());
                parentPlan.getStates().add(stateViewModel);
                break;
            case Types.TRANSITION:
                Transition transition = (Transition) event.getElement();
                TransitionViewModel transitionViewModel = (TransitionViewModel) element;
                transitionViewModel.setInState((StateViewModel) getViewModelElement(transition.getInState()));
                transitionViewModel.setOutState((StateViewModel) getViewModelElement(transition.getOutState()));
                parentPlan.getTransitions().add((TransitionViewModel) element);
                break;
            case Types.ENTRYPOINT:
                EntryPointViewModel entryPointViewModel = (EntryPointViewModel) element;
                entryPointViewModel.setXPosition(event.getUiElement().getX());
                entryPointViewModel.setYPosition(event.getUiElement().getY());
                parentPlan.getEntryPoints().add((EntryPointViewModel) element);
                break;
            case Types.BENDPOINT:
                transitionViewModel = (TransitionViewModel) element;
                // remove<->put to fire listeners, to redraw
                parentPlan.getTransitions().remove(transitionViewModel);
                parentPlan.getTransitions().add(transitionViewModel);
                break;
            case Types.SYNCHRONISATION: {
                SynchronizationViewModel syncViewModel = (SynchronizationViewModel) element;
                syncViewModel.setXPosition(event.getUiElement().getX());
                syncViewModel.setYPosition(event.getUiElement().getY());
                parentPlan.getSynchronisations().add(syncViewModel);
            } break;
            case Types.INITSTATECONNECTION:
                Plan plan = (Plan) event.getElement();
                EntryPoint entryPoint = null;
                Long entryPointID = (Long) event.getNewValue();
                for (EntryPoint ep: ((Plan) plan).getEntryPoints()) {
                    if(ep.getId() == entryPointID) {
                        entryPoint = ep;
                    }
                }
                PlanViewModel planViewModel = (PlanViewModel) element;
                ObservableList<EntryPointViewModel> entryPointViewModelObservableList = planViewModel.getEntryPoints();
                ObservableList<StateViewModel> stateViewModelObservableList = planViewModel.getStates();

                EntryPointViewModel ent = null;
                StateViewModel state = null;
                for(EntryPointViewModel entryPointsViewModel: entryPointViewModelObservableList){
                    for(StateViewModel stateViewModelTmp: stateViewModelObservableList) {
                        if(entryPointsViewModel.getId() == entryPoint.getId() && stateViewModelTmp.getId() == entryPoint.getState().getId()) {
                            entryPointsViewModel.setState(stateViewModelTmp);
                            stateViewModelTmp.setEntryPoint(entryPointsViewModel);
                            ent = entryPointsViewModel;
                            state = stateViewModelTmp;
                            break;
                        }
                    }
                }

                // remove<->put to fire listeners, to redraw
                planViewModel.getEntryPoints().remove(ent);
                planViewModel.getStates().remove(state);
                planViewModel.getStates().add(state);
                planViewModel.getEntryPoints().add(ent);
                break;
            case Types.VARIABLE:
                parentPlan.getVariables().add((VariableViewModel)element);
                break;
            case Types.PRECONDITION:
            case Types.RUNTIMECONDITION:
                // NO-OP
                break;
            default:
                System.err.println("ViewModelManager: Add Element to plan not supported for type: " + element.getType());
                break;
        }
    }

    public void connectElement(ModelEvent event) {
        PlanElement parentPlanElement = modelManager.getPlanElement(event.getParentId());
        ViewModelElement parentViewModel = getViewModelElement(parentPlanElement);
        ViewModelElement viewModelElement = getViewModelElement(event.getElement());

        switch (event.getElementType()) {
            case Types.SYNCTRANSITION:
                ((SynchronizationViewModel) parentViewModel).getTransitions().add((TransitionViewModel) viewModelElement);
                break;
                default:
                    System.err.println("ViewModelManager: Connect Element not supported for type: " + event.getElementType());
                    break;
        }

    }

    public void disconnectElement(ModelEvent event) {
        PlanElement parentPlanElement = modelManager.getPlanElement(event.getParentId());
        ViewModelElement parentViewModel = getViewModelElement(parentPlanElement);
        ViewModelElement viewModelElement = getViewModelElement(event.getElement());

        switch (event.getElementType()) {
            case Types.SYNCTRANSITION:
                ((SynchronizationViewModel) parentViewModel).getTransitions().remove((TransitionViewModel) viewModelElement);
                break;
            default:
                System.err.println("ViewModelManager: Disconnect Element not supported for type: " + event.getElementType());
                break;
        }
    }

    public void changePosition(PlanElementViewModel planElementViewModel, ModelEvent event) {
        planElementViewModel.setXPosition(event.getUiElement().getX());
        planElementViewModel.setYPosition(event.getUiElement().getY());
    }

    public void changeElementAttribute(ViewModelElement viewModelElement, String changedAttribute, Object newValue) {
        try {
            BeanUtils.setProperty(viewModelElement, changedAttribute,newValue);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }



    public void updatePlansInPlanViewModels(PlanViewModel planViewModel, ModelEventType type) {
        for(PlanType planType : modelManager.getPlanTypes()) {
            // Just updating the already existing PlanTypeViewModels and not creating new ones
            if(viewModelElements.containsKey(planType.getId())) {
                PlanTypeViewModel planTypeViewModel = (PlanTypeViewModel) viewModelElements.get(planType.getId());
                if (type == ModelEventType.ELEMENT_ADDED) {
                    planTypeViewModel.addPlanToAllPlans(planViewModel);
                }else if(type == ModelEventType.ELEMENT_REMOVED) {
                    planTypeViewModel.removePlanFromAllPlans(planViewModel.getId());
                }
            }
        }
    }
}
