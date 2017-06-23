/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package de.uni_kassel.vs.cn.planDesigner.alica;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Capability</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link alica.Capability#getCapValues <em>Cap Values</em>}</li>
 * </ul>
 * </p>
 *
 * @see alica.AlicaPackage#getCapability()
 * @model
 * @generated
 */
public interface Capability extends PlanElement {
	/**
	 * Returns the value of the '<em><b>Cap Values</b></em>' containment reference list.
	 * The list contents are of type {@link alica.CapValue}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cap Values</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cap Values</em>' containment reference list.
	 * @see alica.AlicaPackage#getCapability_CapValues()
	 * @model containment="true"
	 * @generated
	 */
	EList<CapValue> getCapValues();

} // Capability
