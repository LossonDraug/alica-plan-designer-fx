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
 * A representation of the model object '<em><b>Planners</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link alica.Planners#getPlanners <em>Planners</em>}</li>
 *   <li>{@link alica.Planners#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see alica.AlicaPackage#getPlanners()
 * @model
 * @generated
 */
public interface Planners extends EObject {
	/**
	 * Returns the value of the '<em><b>Planners</b></em>' containment reference list.
	 * The list contents are of type {@link alica.Planner}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Planners</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Planners</em>' containment reference list.
	 * @see alica.AlicaPackage#getPlanners_Planners()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Planner> getPlanners();

	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see alica.AlicaPackage#getPlanners_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link alica.Planners#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

} // Planners
