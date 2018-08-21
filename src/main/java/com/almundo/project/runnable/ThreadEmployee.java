package com.almundo.project.runnable;

import com.almundo.project.dispatcher.Dispatcher;

/**
 * 
 * @author Johnattan
 *
 */
public class ThreadEmployee extends Thread {

	private boolean isMessageWaitingExist = false;

	/**
	 * 
	 */
	@Override
	public void run() {

		super.run();
		Dispatcher.dispatchCall();

	}

	/**
	 * Retorna atributo isMessageWaitingExist
	 * @return
	 */
	public boolean isMessageWaitingExist() {
		return isMessageWaitingExist;
	}

	/**
	 * Asigna atributo isMessageWaitingExist
	 * @param isMessageWaitingExist
	 */
	public void setMessageWaitingExist(boolean isMessageWaitingExist) {
		this.isMessageWaitingExist = isMessageWaitingExist;
	}

}
