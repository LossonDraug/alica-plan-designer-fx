/**
 */
package de.uni_kassel.vs.cn.planDesigner.alica.impl;

import de.uni_kassel.vs.cn.planDesigner.alica.*;

import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class AlicaFactoryImpl extends EFactoryImpl implements AlicaFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AlicaFactory init() {
		try {
			AlicaFactory theAlicaFactory = (AlicaFactory)EPackage.Registry.INSTANCE.getEFactory(AlicaPackage.eNS_URI);
			if (theAlicaFactory != null) {
				return theAlicaFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new AlicaFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlicaFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case AlicaPackage.TRANSITION: return createTransition();
			case AlicaPackage.PRE_CONDITION: return createPreCondition();
			case AlicaPackage.ENTRY_POINT: return createEntryPoint();
			case AlicaPackage.SUCCESS_STATE: return createSuccessState();
			case AlicaPackage.FAILURE_STATE: return createFailureState();
			case AlicaPackage.BEHAVIOUR: return createBehaviour();
			case AlicaPackage.STATE: return createState();
			case AlicaPackage.PLAN: return createPlan();
			case AlicaPackage.PLAN_TYPE: return createPlanType();
			case AlicaPackage.RATING: return createRating();
			case AlicaPackage.POST_CONDITION: return createPostCondition();
			case AlicaPackage.RUNTIME_CONDITION: return createRuntimeCondition();
			case AlicaPackage.TASK: return createTask();
			case AlicaPackage.ESTRING_TO_ESTRING_MAP_ENTRY: return (EObject)createEStringToEStringMapEntry();
			case AlicaPackage.ROLE: return createRole();
			case AlicaPackage.ROLE_SET: return createRoleSet();
			case AlicaPackage.ELONG_TO_DOUBLE_MAP_ENTRY: return (EObject)createELongToDoubleMapEntry();
			case AlicaPackage.ROLE_DEFINITION_SET: return createRoleDefinitionSet();
			case AlicaPackage.ROLE_TASK_MAPPING: return createRoleTaskMapping();
			case AlicaPackage.CHARACTERISTIC: return createCharacteristic();
			case AlicaPackage.TASK_GRAPH: return createTaskGraph();
			case AlicaPackage.EDGE: return createEdge();
			case AlicaPackage.TASK_WRAPPER: return createTaskWrapper();
			case AlicaPackage.INTERNAL_ROLE_TASK_MAPPING: return createInternalRoleTaskMapping();
			case AlicaPackage.NODE: return createNode();
			case AlicaPackage.TASK_REPOSITORY: return createTaskRepository();
			case AlicaPackage.SYNCHRONISATION: return createSynchronisation();
			case AlicaPackage.VARIABLE: return createVariable();
			case AlicaPackage.PARAMETRISATION: return createParametrisation();
			case AlicaPackage.ANNOTATED_PLAN: return createAnnotatedPlan();
			case AlicaPackage.FORALL_AGENTS: return createForallAgents();
			case AlicaPackage.CAPABILITY: return createCapability();
			case AlicaPackage.CAP_VALUE: return createCapValue();
			case AlicaPackage.CAPABILITY_DEFINITION_SET: return createCapabilityDefinitionSet();
			case AlicaPackage.PLANNING_PROBLEM: return createPlanningProblem();
			case AlicaPackage.PLANNER: return createPlanner();
			case AlicaPackage.FLUENT: return createFluent();
			case AlicaPackage.DOMAIN_DESCRIPTION: return createDomainDescription();
			case AlicaPackage.PLANNERS: return createPlanners();
			case AlicaPackage.ESTRING_TO_EOBJECT_MAP_ENTRY: return (EObject)createEStringToEObjectMapEntry();
			case AlicaPackage.FLUENT_PARAMETERS: return createFluentParameters();
			case AlicaPackage.CONSTANT: return createConstant();
			case AlicaPackage.BEHAVIOUR_CREATOR: return createBehaviourCreator();
			case AlicaPackage.CONDITION_CREATOR: return createConditionCreator();
			case AlicaPackage.UTILITY_FUNCTION_CREATOR: return createUtilityFunctionCreator();
			case AlicaPackage.CONSTRAINT_CREATOR: return createConstraintCreator();
			case AlicaPackage.DOMAIN_BEHAVIOUR: return createDomainBehaviour();
			case AlicaPackage.DOMAIN_CONDITION: return createDomainCondition();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case AlicaPackage.PLANNING_TYPE:
				return createPlanningTypeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case AlicaPackage.PLANNING_TYPE:
				return convertPlanningTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Transition createTransition() {
		TransitionImpl transition = new TransitionImpl();
		return transition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreCondition createPreCondition() {
		PreConditionImpl preCondition = new PreConditionImpl();
		return preCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EntryPoint createEntryPoint() {
		EntryPointImpl entryPoint = new EntryPointImpl();
		return entryPoint;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SuccessState createSuccessState() {
		SuccessStateImpl successState = new SuccessStateImpl();
		return successState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FailureState createFailureState() {
		FailureStateImpl failureState = new FailureStateImpl();
		return failureState;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Behaviour createBehaviour() {
		BehaviourImpl behaviour = new BehaviourImpl();
		return behaviour;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public State createState() {
		StateImpl state = new StateImpl();
		return state;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Plan createPlan() {
		PlanImpl plan = new PlanImpl();
		return plan;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlanType createPlanType() {
		PlanTypeImpl planType = new PlanTypeImpl();
		return planType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Rating createRating() {
		RatingImpl rating = new RatingImpl();
		return rating;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PostCondition createPostCondition() {
		PostConditionImpl postCondition = new PostConditionImpl();
		return postCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RuntimeCondition createRuntimeCondition() {
		RuntimeConditionImpl runtimeCondition = new RuntimeConditionImpl();
		return runtimeCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Task createTask() {
		TaskImpl task = new TaskImpl();
		return task;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, String> createEStringToEStringMapEntry() {
		EStringToEStringMapEntryImpl eStringToEStringMapEntry = new EStringToEStringMapEntryImpl();
		return eStringToEStringMapEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Role createRole() {
		RoleImpl role = new RoleImpl();
		return role;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoleSet createRoleSet() {
		RoleSetImpl roleSet = new RoleSetImpl();
		return roleSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<Long, Double> createELongToDoubleMapEntry() {
		ELongToDoubleMapEntryImpl eLongToDoubleMapEntry = new ELongToDoubleMapEntryImpl();
		return eLongToDoubleMapEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoleDefinitionSet createRoleDefinitionSet() {
		RoleDefinitionSetImpl roleDefinitionSet = new RoleDefinitionSetImpl();
		return roleDefinitionSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoleTaskMapping createRoleTaskMapping() {
		RoleTaskMappingImpl roleTaskMapping = new RoleTaskMappingImpl();
		return roleTaskMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Characteristic createCharacteristic() {
		CharacteristicImpl characteristic = new CharacteristicImpl();
		return characteristic;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskGraph createTaskGraph() {
		TaskGraphImpl taskGraph = new TaskGraphImpl();
		return taskGraph;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Edge createEdge() {
		EdgeImpl edge = new EdgeImpl();
		return edge;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskWrapper createTaskWrapper() {
		TaskWrapperImpl taskWrapper = new TaskWrapperImpl();
		return taskWrapper;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public InternalRoleTaskMapping createInternalRoleTaskMapping() {
		InternalRoleTaskMappingImpl internalRoleTaskMapping = new InternalRoleTaskMappingImpl();
		return internalRoleTaskMapping;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Node createNode() {
		NodeImpl node = new NodeImpl();
		return node;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TaskRepository createTaskRepository() {
		TaskRepositoryImpl taskRepository = new TaskRepositoryImpl();
		return taskRepository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Synchronisation createSynchronisation() {
		SynchronisationImpl synchronisation = new SynchronisationImpl();
		return synchronisation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Variable createVariable() {
		VariableImpl variable = new VariableImpl();
		return variable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parametrisation createParametrisation() {
		ParametrisationImpl parametrisation = new ParametrisationImpl();
		return parametrisation;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AnnotatedPlan createAnnotatedPlan() {
		AnnotatedPlanImpl annotatedPlan = new AnnotatedPlanImpl();
		return annotatedPlan;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ForallAgents createForallAgents() {
		ForallAgentsImpl forallAgents = new ForallAgentsImpl();
		return forallAgents;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Capability createCapability() {
		CapabilityImpl capability = new CapabilityImpl();
		return capability;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CapValue createCapValue() {
		CapValueImpl capValue = new CapValueImpl();
		return capValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CapabilityDefinitionSet createCapabilityDefinitionSet() {
		CapabilityDefinitionSetImpl capabilityDefinitionSet = new CapabilityDefinitionSetImpl();
		return capabilityDefinitionSet;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlanningProblem createPlanningProblem() {
		PlanningProblemImpl planningProblem = new PlanningProblemImpl();
		return planningProblem;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Planner createPlanner() {
		PlannerImpl planner = new PlannerImpl();
		return planner;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Fluent createFluent() {
		FluentImpl fluent = new FluentImpl();
		return fluent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainDescription createDomainDescription() {
		DomainDescriptionImpl domainDescription = new DomainDescriptionImpl();
		return domainDescription;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Planners createPlanners() {
		PlannersImpl planners = new PlannersImpl();
		return planners;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Map.Entry<String, Object> createEStringToEObjectMapEntry() {
		EStringToEObjectMapEntryImpl eStringToEObjectMapEntry = new EStringToEObjectMapEntryImpl();
		return eStringToEObjectMapEntry;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FluentParameters createFluentParameters() {
		FluentParametersImpl fluentParameters = new FluentParametersImpl();
		return fluentParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Constant createConstant() {
		ConstantImpl constant = new ConstantImpl();
		return constant;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BehaviourCreator createBehaviourCreator() {
		BehaviourCreatorImpl behaviourCreator = new BehaviourCreatorImpl();
		return behaviourCreator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConditionCreator createConditionCreator() {
		ConditionCreatorImpl conditionCreator = new ConditionCreatorImpl();
		return conditionCreator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UtilityFunctionCreator createUtilityFunctionCreator() {
		UtilityFunctionCreatorImpl utilityFunctionCreator = new UtilityFunctionCreatorImpl();
		return utilityFunctionCreator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConstraintCreator createConstraintCreator() {
		ConstraintCreatorImpl constraintCreator = new ConstraintCreatorImpl();
		return constraintCreator;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainBehaviour createDomainBehaviour() {
		DomainBehaviourImpl domainBehaviour = new DomainBehaviourImpl();
		return domainBehaviour;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DomainCondition createDomainCondition() {
		DomainConditionImpl domainCondition = new DomainConditionImpl();
		return domainCondition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PlanningType createPlanningTypeFromString(EDataType eDataType, String initialValue) {
		PlanningType result = PlanningType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPlanningTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AlicaPackage getAlicaPackage() {
		return (AlicaPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static AlicaPackage getPackage() {
		return AlicaPackage.eINSTANCE;
	}

} //AlicaFactoryImpl
