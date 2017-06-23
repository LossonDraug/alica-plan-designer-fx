/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.uni_kassel.vs.cn.planDesigner.alica;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Behaviour Creator</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link alica.BehaviourCreator#getBehaviours <em>Behaviours</em>}</li>
 * </ul>
 * </p>
 *
 * @see alica.AlicaPackage#getBehaviourCreator()
 * @model
 * @generated
 */
public interface BehaviourCreator extends EObject {
	/**
	 * Returns the value of the '<em><b>Behaviours</b></em>' reference list.
	 * The list contents are of type {@link alica.Behaviour}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Behaviours</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Behaviours</em>' reference list.
	 * @see alica.AlicaPackage#getBehaviourCreator_Behaviours()
	 * @model ordered="false"
	 * @generated
	 */
	EList<Behaviour> getBehaviours();

} // BehaviourCreator
