package com.ubosque.DAO;

import java.util.ArrayList;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.UpdateResult;
import com.ubosque.DTO.Consolidados;
import com.ubosque.DTO.Reportes;

public class ConsolidadoDAO {

	MongoClient mongoClient;
	MongoClient mongoClient2;
	MongoClient mongoClient3;
	MongoDatabase database1;
	MongoDatabase database2;
	MongoDatabase database3;
	MongoCollection<Document> ventas;
	MongoCollection<Document> consolidado;
	MongoCollection<Document> clientes;
	ConnectionString connectionString;
	MongoClientSettings settings;

	public ConsolidadoDAO() {
		try {
			connectionString = new ConnectionString(
					"mongodb+srv://admin:admin@cluster0.ykb33.mongodb.net/myFirstDatabase?retryWrites=true&w=majority");
			settings = MongoClientSettings.builder().applyConnectionString(connectionString).build();
			mongoClient = MongoClients.create(settings);
			mongoClient2 = MongoClients.create(settings);
			mongoClient3 = MongoClients.create(settings);

			database1 = mongoClient.getDatabase("db_ventas");
			ventas = database1.getCollection("ventas");

			database2 = mongoClient2.getDatabase("db_consolidado");
			consolidado = database2.getCollection("consolidado");

			database3 = mongoClient3.getDatabase("db_clientes");
			clientes = database3.getCollection("clientes");

			System.out.println("Conexión exitosa");

		} catch (Exception e) {
			e.getMessage();
		}
	}

	public void cerrar() {
		try {
			mongoClient.close();

			System.out.println("Conexión cerrada");
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public ArrayList<Consolidados> lista() {
		ArrayList<Consolidados> ConsolidadosArray = new ArrayList<>();

		try {

			ArrayList<Document> docConsolidado = consolidado.find().into(new ArrayList<>());

			for (Document index : docConsolidado) {
				Consolidados consolidado = new Consolidados();
				consolidado.setCiudad(index.getString("ciudad"));
				consolidado.setValor(index.getDouble("total_venta"));

				ConsolidadosArray.add(consolidado);
			}
			this.cerrar();
		} catch (Exception e) {
			e.getMessage();

		}
		this.cerrar();
		return ConsolidadosArray;
	}

	public void consulta() {

		try {
			BasicDBObject whereQuery1 = new BasicDBObject();
			whereQuery1.put("ciudad", "Bogotá");

			ArrayList<Document> docBogota = ventas.find(whereQuery1).into(new ArrayList<>());
			System.out.println("cantidad Bogotá " + docBogota.size());

			double contador1 = 0;
			for (Document bogota : docBogota) {
				contador1 = contador1 + bogota.getDouble("total_venta");
				System.out.println("Bogotá " + bogota.getDouble("total_venta"));
			}

			BasicDBObject whereQuery2 = new BasicDBObject();
			whereQuery2.put("ciudad", "Medellin");

			ArrayList<Document> docMedellin = ventas.find(whereQuery2).into(new ArrayList<>());
			System.out.println("cantidad Medellin " + docMedellin.size());

			double contador2 = 0;
			for (Document medellin : docMedellin) {
				contador2 = contador2 + medellin.getDouble("total_venta");
				System.out.println("Medellin " + medellin.getDouble("total_venta"));
			}

			BasicDBObject whereQuery3 = new BasicDBObject();
			whereQuery3.put("ciudad", "Cali");

			ArrayList<Document> docCali = ventas.find(whereQuery3).into(new ArrayList<>());
			System.out.println("cantidad cali " + docCali.size());

			double contador3 = 0;
			for (Document cali : docCali) {
				contador3 = contador3 + cali.getDouble("total_venta");
				System.out.println("Cali " + cali.getDouble("total_venta"));
			}

			// Precios por ciudad
			System.out.println(contador1);
			System.out.println(contador2);
			System.out.println(contador3);

			Document documento1 = new Document();
			documento1.append("ciudad", "Bogota");
			documento1.append("total_venta", contador1);

			System.out.println(documento1);

			Document filtro1 = new Document("ciudad", "Bogota");
			UpdateResult estado1 = consolidado.replaceOne(filtro1, documento1);

			Document documento2 = new Document();
			documento2.append("ciudad", "Medellin");
			documento2.append("total_venta", contador2);

			Document filtro2 = new Document("ciudad", "Medellin");
			consolidado.replaceOne(filtro2, documento2);

			Document documento3 = new Document();
			documento3.append("ciudad", "Cali");
			documento3.append("total_venta", contador3);

			Document filtro3 = new Document("ciudad", "Cali");
			UpdateResult estado3 = consolidado.replaceOne(filtro3, documento3);

			this.cerrar();

		} catch (Exception e) {
			e.getMessage();
			this.cerrar();
		}

	}

	public double total() {

		double contador = 0;
		try {

			ArrayList<Document> docCiudades = ventas.find().into(new ArrayList<>());

			for (Document ciudades : docCiudades) {
				contador = contador + ciudades.getDouble("total_venta");
			}

		} catch (Exception e) {
			e.getMessage();
		}

		return contador;

	}

	public ArrayList<Reportes> listarReporte(int cedulaCliente) {
		ArrayList<Reportes> reportes = new ArrayList<Reportes>();
		try {

			BasicDBObject whereQuery = new BasicDBObject();
			whereQuery.put("cedulaCliente", cedulaCliente);

			ArrayList<Document> docReportes = ventas.find(whereQuery).into(new ArrayList<>());
			
			String nombre="";
			
			for (Document documento : docReportes) {
			
			ArrayList<Document> docClientes = clientes.find(whereQuery).into(new ArrayList<>());
			for (Document documento2 : docClientes) {
			
				nombre=documento2.getString("nombreCliente");
				System.out.print("nombre");
			}
		
				Reportes reporte = new Reportes();
				reporte.setCedulaCliente(documento.getInteger("cedulaCliente"));
				reporte.setNombreCliente(nombre);
				reporte.setTotal_venta(documento.getDouble("total_venta"));

				reportes.add(reporte);
			}
			System.out.print("Listando reporte");
			this.cerrar();
		}catch(

	Exception e)
	{
			e.getMessage();
			this.cerrar();
		}return reportes;
}

}
