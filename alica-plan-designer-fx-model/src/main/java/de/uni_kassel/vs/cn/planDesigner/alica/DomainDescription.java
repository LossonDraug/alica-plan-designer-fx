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
 * A representation of the model object '<em><b>Domain Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link alica.DomainDescription#getFluents <em>Fluents</em>}</li>
 *   <li>{@link alica.DomainDescription#getTypes <em>Types</em>}</li>
 *   <li>{@link alica.DomainDescription#getName <em>Name</em>}</li>
 *   <li>{@link alica.DomainDescription#getConstants <em>Constants</em>}</li>
 * </ul>
 * </p>
 *
 * @see alica.AlicaPackage#getDomainDescription()
 * @model
 * @generated
 */
public interface DomainDescription extends EObject {
	/**
	 * Returns the value of the '<em><b>Fluents</b></em>' containment reference list.
	 * The list contents are of type {@link alica.Fluent}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fluents</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fluents</em>' containment reference list.
	 * @see alica.AlicaPackage#getDomainDescription_Fluents()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Fluent> getFluents();

	/**
	 * Returns the value of the '<em><b>Types</b></em>' attribute list.
	 * The list contents are of type {@link String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Types</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Types</em>' attribute list.
	 * @see alica.AlicaPackage#getDomainDescription_Types()
	 * @model ordered="false"
	 * @generated
	 */
	EList<String> getTypes();

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
	 * @see alica.AlicaPackage#getDomainDescription_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link alica.DomainDescription#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Constants</b></em>' containment reference list.
	 * The list contents are of type {@link alica.Constant}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Constants</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Constants</em>' containment reference list.
	 * @see alica.AlicaPackage#getDomainDescription_Constants()
	 * @model containment="true"
	 * @generated
	 */
	EList<Constant> getConstants();

} // DomainDescription
