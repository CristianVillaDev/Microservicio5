package com.ubosque.consoli;

import java.util.ArrayList;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ubosque.DAO.ConsolidadoDAO;
import com.ubosque.DTO.Consolidados;
import com.ubosque.DTO.Reportes;

@RestController
@CrossOrigin(origins = "*")
@ComponentScan(basePackages = { "com.ubosque.DAO" })
@RequestMapping("/consolidado")
public class ConsolidadoController {

	@RequestMapping("/listar")
	public ArrayList<Consolidados> lista() {
		ConsolidadoDAO consolidadoDAO = new ConsolidadoDAO();
		return consolidadoDAO.lista();
	}
	
	@RequestMapping("/ejecutar")
	public void consulta() {
		ConsolidadoDAO consolidadoDAO = new ConsolidadoDAO();
		consolidadoDAO.consulta();
	}
	
	@RequestMapping("/total")
	public double total() {
		ConsolidadoDAO consolidadoDAO = new ConsolidadoDAO();
		return consolidadoDAO.total();
	}
	
	@RequestMapping("/reporte/{cedulaCliente}")
	public ArrayList<Reportes> showReporte(@PathVariable("cedulaCliente") int cedulaCliente){
		ConsolidadoDAO consolidadoDAO = new ConsolidadoDAO();
		return consolidadoDAO.listarReporte(cedulaCliente);
	}
	
}
