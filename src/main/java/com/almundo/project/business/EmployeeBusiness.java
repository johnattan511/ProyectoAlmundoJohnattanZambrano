package com.almundo.project.business;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.almundo.project.abs.EmployeeAbstractClass;
import com.almundo.project.runnable.ThreadEmployee;
import com.almundo.project.support.EnumRoles;

/**
 * 
 * @author Johnattan
 *
 */
public class EmployeeBusiness extends EmployeeAbstractClass{

	private EmployeeBusiness siguienteEmpleado;
	private String nombre;
	private EnumRoles rol;
	private boolean isBusy;
	private static Lock bloqueo;
	
	static{
	    bloqueo = new ReentrantLock(); 
	}
	
	/**
	 * Constructor publico de la clase EmployeeBusiness
	 * @param nombre
	 * @param rol
	 */
	public EmployeeBusiness(String nombre, EnumRoles rol){
		this.nombre = nombre;
		this.rol = rol;
		isBusy = false;
	}

	/**
	 * Encargado de settear el proximo empleado en la cadena de responsabilidades.
	 * @param empleado
	 */
	@Override
	public void setNext(EmployeeBusiness empleado) {
		this.siguienteEmpleado = empleado;
		
	}
     /**
      * Metoco encargado de retornar el proximo empleado en la cadena de responsabilidades.
      * @return siguienteEmpleado
      */
	@Override
	public EmployeeBusiness getNext() {
		return this.siguienteEmpleado;
	}

	/**
	 * Metodo encargado de realizar la validacion sobre cual trabajador puede
	 * atender al usuario correspondiente. Si no hay ninguno disponible espera 
	 * hasta que uno se desocupe. De lo contrario atiende al usuario.
	 */
	@Override
	public void takeCall() throws InterruptedException {
		bloqueo.lock();
		if(!this.isBusy()){
			
			this.setBusy(!this.isBusy);
			
			long lont = ThreadLocalRandom.current().nextInt(5, 10 + 1)*1000L;
			System.out.println("ROL = " + this.getRol().name() + " ; NOMBRE TRABAJADOR = " 
			+ this.getNombre()+" ; ATENDIENDO A = " + Thread.currentThread().getName() + " ; TIEMPO DE ATENCION = " + (lont/1000) + " SEGUNDOS");
			bloqueo.unlock();

			Thread.sleep(lont);
			
			this.setBusy(!this.isBusy);
			setHayEmpleadosDisponibles(true);
			return;
		}
		
		bloqueo.unlock();
		if(this.getNext() == null){
			setHayEmpleadosDisponibles(false);

			String mensajeEspera = "SEÑOR = " + Thread.currentThread().getName() + " EN ESTE MOMENTO NO HAY TRABAJADORES DISPONIBLES POR FAVOR ESPERE, EN BREVE SERA ATENDIDO";
			
				if(!((ThreadEmployee)Thread.currentThread()).isMessageWaitingExist()){
					System.out.println(mensajeEspera);
					((ThreadEmployee)Thread.currentThread()).setMessageWaitingExist(true);
				}	
			
			while(!employeesAavailable){
				
			}		
			empleadoPrincipalStatic.takeCall();
		}
		else{
			this.getNext().takeCall();
		}
					
	}

	/**
	 * Retorna nombre del trabajador
	 * @return nombre
	 */
	public String getNombre() {
		return nombre;
	}
	/**
	 * Settea el nombre del trabajador
	 * @param nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	/**
	 *  Retorna rol del trabajador
	 * @return rol
	 */
	public EnumRoles getRol() {
		return rol;
	}
	/**
	 * Settea rol del trabajador
	 * @param rol
	 */
	public void setRol(EnumRoles rol) {
		this.rol = rol;
	}
	/**
	 * Retorna un booleano indicando si el trabajador esta ocupado.
	 * @return
	 */
	public boolean isBusy() {
		return isBusy;
	}
	/**
	 * Settea un booleano para cambiar el estado si esta ocupado el trabajador.
	 * @param isBusy
	 */
	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	
}
