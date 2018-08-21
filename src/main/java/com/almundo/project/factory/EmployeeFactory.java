package com.almundo.project.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.almundo.project.business.EmployeeBusiness;
import com.almundo.project.support.EnumRoles;

/**
 * 
 * @author Johnattan
 *
 */
public class EmployeeFactory {
	

    private static Logger logger = Logger.getLogger(EmployeeFactory.class.getName());

	/**
	 *  Metodo privado de EmployeeFactory
	 */
	private EmployeeFactory(){
		
	}

	/**
	 * 
	 * @return
	 */
	public static EmployeeBusiness sortEmployeesChain() {

		List<EmployeeBusiness> list = getEmployees();
		EmployeeBusiness.setEmpleadoPrincipal(list.get(0));
		for(int rec = 0; rec < list.size(); rec ++){
			if((rec+1) == list.size()) continue;
			list.get(rec).setNext(list.get(rec+1));
		}
		
		return list.get(0);
	

	}

	
	/**
	 * Metodo encargado de cargar el documento XML con los empleados junto a nombres y roles.
	 * @return Lista con los empleados.
	 */
	private static List<EmployeeBusiness> getEmployees(){
		List<EmployeeBusiness> list = new ArrayList<>();
		EmployeeFactory.class.getClassLoader();
		InputStream input = ClassLoader.getSystemResourceAsStream("empleados.xml");
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document documento = db.parse(input);
			documento.getDocumentElement().normalize();

			NodeList childNodes = documento.getElementsByTagName("empleados").item(0).getChildNodes();

			for (int rec = 0; rec < childNodes.getLength(); rec++) {
				Node node = childNodes.item(rec);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					list.add(new EmployeeBusiness(getTagValue("nombre", e),
							EnumRoles.valueOf(getTagValue("rol", e).toUpperCase())));
				}
			}

			Collections.sort(list, new Comparator<EmployeeBusiness>() {

				@Override
				public int compare(EmployeeBusiness employee1, EmployeeBusiness employee2) {
					if (employee1.getRol().getNivel() > employee2.getRol().getNivel()) {
						return 1;
					}
					return -1;
				}

			});

		} catch (ParserConfigurationException | SAXException | IOException e) {

			logger.log(Level.SEVERE, "Error al parsear documento XML", e);
		}
		
		return list;
	}
	
	/**
	 * Obtiene el valor tanto de rol o nombre del archivo XML de acuerdo a lo que se le pase como
	 * parametro
	 * @param nom nombre
	 * @param e elemento en el nodo XML
	 * @return nombre de rol o nombre.
	 */
	private static String getTagValue(String nom, Element e) {
		NodeList lst = e.getElementsByTagName(nom).item(0).getChildNodes();
		Node n = lst.item(0);
		return n.getNodeValue();
	}

}
