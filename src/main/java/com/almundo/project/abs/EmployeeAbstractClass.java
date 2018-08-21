package com.almundo.project.abs;
import com.almundo.project.business.EmployeeBusiness;


/**
 * @author Johnattan
 * Clase abstracta encargada de definir los metodos para que sus implementaciones
 * se encargan de crear la cadena de empleados asi como sus respectivos desarrollos 
 * entre ellos y comportamiento y validaciones a nivel de concurrencia.
 *
 */

public abstract class EmployeeAbstractClass {
	
	protected static EmployeeBusiness empleadoPrincipalStatic;
	protected static volatile boolean employeesAavailable = true;
	
	/**
	 * 
	 * @param empleado
	 */
    public abstract void setNext(EmployeeBusiness empleado);
    
    /**
     * @return 
     */
    public abstract EmployeeBusiness getNext();
    
    /**
     * 
     * @throws InterruptedException
     */
    public abstract void takeCall() throws InterruptedException;
	
    
    /**
     * 
     * @param empleadoPrincipal
     * Metodo encargado de asignar el valor del primer Empleado (cabeza de la cadena) encargado
     * de realizar la busqueda de empleados disponibles.
     */
    public static void setEmpleadoPrincipal(EmployeeBusiness empleadoPrincipal){
    	empleadoPrincipalStatic = empleadoPrincipal;
    }
    
    /**
     * @param estado
     * Metodo encargado de modificar la bandera de empleados habilitads para atender 
     * alguna de las llamadas correspondientes
     */
    public static synchronized void setHayEmpleadosDisponibles(boolean estado){
    	employeesAavailable = estado;
    }

}
