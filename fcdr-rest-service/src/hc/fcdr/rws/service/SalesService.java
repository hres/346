package hc.fcdr.rws.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;

import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import hc.fcdr.rws.db.DbConnection;
import hc.fcdr.rws.db.SalesDao;

import hc.fcdr.rws.model.sales.SalesDataResponse;
import hc.fcdr.rws.model.sales.SalesDataResponseShort;
import hc.fcdr.rws.model.sales.SalesDeleteDataResponse;
import hc.fcdr.rws.model.sales.SalesInsertDataResponse;
import hc.fcdr.rws.model.sales.SalesInsertRequest;
import hc.fcdr.rws.model.sales.SalesRequest;
import hc.fcdr.rws.model.sales.SalesUpdateDataResponse;
import hc.fcdr.rws.model.sales.SalesUpdateRequest;
import hc.fcdr.rws.model.sales.SalesYearsDataResponse;

@Path("/SalesService")
public class SalesService extends Application {
	static SalesDao salesDao = null;

	@PostConstruct
	public static void initialize() throws IOException, Exception {
		if (salesDao == null) {
			final DbConnection pgConnectionPool = new DbConnection();
			pgConnectionPool.initialize();

			salesDao = new SalesDao(pgConnectionPool.getConnections(), pgConnectionPool.getSchema());
		}
	}

	// ==============================

	@GET
	@Path("/salesyears")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSalesYears() {
		SalesYearsDataResponse entity = new SalesYearsDataResponse();

		try {
			if (salesDao != null)
				entity = salesDao.getSalesYearsResponse();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

	@GET
	@Path("/sales/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSales(@PathParam("id") final long id) {
		SalesDataResponse entity = new SalesDataResponse();

		try {
			if (salesDao != null)
				entity = salesDao.getSalesResponse(id);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

	@POST
	@Path("/salesfiltered")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response searchSales(final SalesRequest salesRequest) throws SQLException, IOException, Exception {
		SalesDataResponseShort entity = new SalesDataResponseShort();

		try {
			if (salesDao != null)
				entity = salesDao.getSalesResponse(salesRequest);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

	// ===

	@POST
	@Path("/insert")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(final SalesInsertRequest salesInsertRequest) throws SQLException, IOException, Exception {
		SalesInsertDataResponse entity = new SalesInsertDataResponse();

		try {
			if (salesDao != null)
				entity = salesDao.getSalesInsertResponse(salesInsertRequest);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

	// ===

	@PUT
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(final SalesUpdateRequest salesUpdateRequest) throws SQLException, IOException, Exception {

		System.out.println("kilo share is: " + salesUpdateRequest.kilo_share);
		SalesUpdateDataResponse entity = new SalesUpdateDataResponse();
		try {
			if (salesDao != null)
				entity = salesDao.getSalesUpdateResponse(salesUpdateRequest);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

	// ===

	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") final Integer id) {
		SalesDeleteDataResponse entity = new SalesDeleteDataResponse();

		try {
			if (salesDao != null)
				entity = salesDao.getSalesDeleteResponse(id);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

}
