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
 * A representation of the model object '<em><b>Role Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link alica.RoleSet#getUsableWithPlanID <em>Usable With Plan ID</em>}</li>
 *   <li>{@link alica.RoleSet#isDefault <em>Default</em>}</li>
 *   <li>{@link alica.RoleSet#getMappings <em>Mappings</em>}</li>
 * </ul>
 * </p>
 *
 * @see alica.AlicaPackage#getRoleSet()
 * @model
 * @generated
 */
public interface RoleSet extends PlanElement {
	/**
	 * Returns the value of the '<em><b>Usable With Plan ID</b></em>' attribute.
	 * The default value is <code>"0"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Usable With Plan ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Usable With Plan ID</em>' attribute.
	 * @see #setUsableWithPlanID(long)
	 * @see alica.AlicaPackage#getRoleSet_UsableWithPlanID()
	 * @model default="0" required="true"
	 * @generated
	 */
	long getUsableWithPlanID();

	/**
	 * Sets the value of the '{@link alica.RoleSet#getUsableWithPlanID <em>Usable With Plan ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Usable With Plan ID</em>' attribute.
	 * @see #getUsableWithPlanID()
	 * @generated
	 */
	void setUsableWithPlanID(long value);

	/**
	 * Returns the value of the '<em><b>Default</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Default</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Default</em>' attribute.
	 * @see #setDefault(boolean)
	 * @see alica.AlicaPackage#getRoleSet_Default()
	 * @model default="false"
	 * @generated
	 */
	boolean isDefault();

	/**
	 * Sets the value of the '{@link alica.RoleSet#isDefault <em>Default</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Default</em>' attribute.
	 * @see #isDefault()
	 * @generated
	 */
	void setDefault(boolean value);

	/**
	 * Returns the value of the '<em><b>Mappings</b></em>' containment reference list.
	 * The list contents are of type {@link alica.RoleTaskMapping}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Mappings</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Mappings</em>' containment reference list.
	 * @see alica.AlicaPackage#getRoleSet_Mappings()
	 * @model containment="true"
	 * @generated
	 */
	EList<RoleTaskMapping> getMappings();

} // RoleSet
