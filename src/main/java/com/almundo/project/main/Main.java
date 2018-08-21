package com.almundo.project.main;

import com.almundo.project.dispatcher.Dispatcher;

/**
 * Clase Principal de inicio
 * @author Johnattan
 *
 */
public class Main {

	public static void main(String[] args) {
		
		Dispatcher.createRequests(50);

	}
}