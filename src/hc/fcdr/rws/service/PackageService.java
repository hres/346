package hc.fcdr.rws.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

import hc.fcdr.rws.db.DbConnection;
import hc.fcdr.rws.db.ImportImageDao;
import hc.fcdr.rws.db.PackageDao;
import hc.fcdr.rws.except.DaoException;
import hc.fcdr.rws.model.pkg.ComponentNameResponse;
import hc.fcdr.rws.model.pkg.GenericList;
import hc.fcdr.rws.model.pkg.ImagesList;
import hc.fcdr.rws.model.pkg.InsertPackageResponse;
import hc.fcdr.rws.model.pkg.NftGetModel;
import hc.fcdr.rws.model.pkg.NftRequest;
import hc.fcdr.rws.model.pkg.NftView;
import hc.fcdr.rws.model.pkg.PackageDataResponse;
import hc.fcdr.rws.model.pkg.PackageInsertRequest;
import hc.fcdr.rws.model.pkg.PackageRequest;
import hc.fcdr.rws.model.pkg.PackageUpdateRequest;
import hc.fcdr.rws.model.pkg.PackageViewResponse;
import hc.fcdr.rws.model.pkg.ResponseGeneric;
import hc.fcdr.rws.util.ContextManager;

@Path("/PackageService")

public class PackageService extends Application {
	static PackageDao packageDao = null;
	static ImportImageDao importImageDao = null;

	@PostConstruct
	public static void initialize() {
		if (packageDao == null) {
			final DbConnection pgConnectionPool = new DbConnection();
			pgConnectionPool.initialize();

			packageDao = new PackageDao(pgConnectionPool.getConnections(),
					pgConnectionPool.getSchema());
			importImageDao = new ImportImageDao(pgConnectionPool.getConnections(),
					pgConnectionPool.getSchema());
		}
	}

	@GET
	@Path("/package/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPackage(@PathParam("id") final long id) {

		// TODO verify
		PackageViewResponse entity = new PackageViewResponse();

		// TODO previous
		// PackageDataResponse entity = new PackageDataResponse();

		try {
			if (packageDao != null)
				entity = packageDao.getPackageResponse(id);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

	@POST
	@Path("/packagefiltered")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response searchLabels(final PackageRequest packageRequest) throws SQLException, IOException, Exception {

		PackageDataResponse entity = new PackageDataResponse();

		try {
			if (packageDao != null)
				entity = packageDao.getPackageResponse(packageRequest);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

	@POST
	@Path("/insert")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insert(final PackageInsertRequest packageInsertRequest)
			throws SQLException, IOException, Exception {
		InsertPackageResponse entity = new InsertPackageResponse();
		try {
			if (packageDao != null)
				entity = packageDao.getPackageInsertResponse(packageInsertRequest);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

	@PUT
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(final PackageUpdateRequest packageUpdateRequest)
			throws SQLException, IOException, Exception {

		InsertPackageResponse entity = new InsertPackageResponse();
		try {
			if (packageDao != null)
				entity = packageDao.getPackageUpdateResponse(packageUpdateRequest);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

	@GET
	@Path("/unitOfMeasure")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUnitOfMeasure() {
		GenericList entity = new GenericList();

		try {
			if (packageDao != null)
				entity = packageDao.getListOfUnits();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		// return null;
		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();

	}

	@POST
	@Path("/insertNft")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response insertNft(final NftRequest nftRequest) throws SQLException, IOException, Exception {

		ResponseGeneric entity = new ResponseGeneric();
		try {
			if (packageDao != null)
				entity = packageDao.getNftInsertResponse(nftRequest);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

	@PUT
	@Path("/updateNft")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateNft(final NftRequest nftRequest) throws SQLException, IOException, Exception {

		ResponseGeneric entity = new ResponseGeneric();
		try {
			if (packageDao != null)
				entity = packageDao.getNftUpdateResponse(nftRequest);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

	@POST
	@Path("/getNft")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getNft(final NftGetModel nftGetModel) throws SQLException, IOException, Exception {

		NftView entity = new NftView();
		try {
			if (packageDao != null)
				entity = packageDao.getNft(nftGetModel.getPackage_id(), nftGetModel.getFlag());
		} catch (final Exception e) {
			e.printStackTrace();
		}

		// return null;

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

	@GET
	@Path("/listofcomponents")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getComponentName() throws SQLException, IOException, Exception {
		ComponentNameResponse entity = new ComponentNameResponse();

		try {
			if (packageDao != null)
				entity = packageDao.getComponents();

		} catch (final DaoException e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") final Integer id) {
		ResponseGeneric entity = new ResponseGeneric();

		try {
			if (packageDao != null)
				entity = packageDao.getLabelDeleteResponse(id);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

	@GET
	@Path("/getLabelImages/{image_path}")
	@Produces({"image/jpeg","image/png"})
	public Response labelImages(@PathParam("image_path") final String image_path) {
		File file = null;

		try {
			if (packageDao != null)
				file = packageDao.getImage(image_path);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		ResponseBuilder response = Response.ok((Object) file);

		response.header("Content-Disposition", "inline; filename=label_image.jpeg");

		return response.build();
	}

	@GET
	@Path("/getListOfImages/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response labelImages(@PathParam("id") final Integer package_id) {
		ImagesList entity = null;

		try {
			if (packageDao != null)
				entity = packageDao.getListOfImages(package_id);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

	@POST
	@Path("/addImage/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addImage(@PathParam("id") final Integer package_id, FormDataMultiPart fileInputStream,
			@FormDataParam("image") FormDataContentDisposition fileDetail) {
		List<FormDataBodyPart> bodyParts = fileInputStream.getFields("image");

		ImagesList entity = null;

		try {
			if (packageDao != null)
				entity = packageDao.addImage(bodyParts, package_id, importImageDao);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();

	}

	@DELETE
	@Path("/deleteImage/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteImage(@PathParam("id") final Integer image_id) {

		ImagesList entity = null;

		try {
			if (packageDao != null)
				entity = packageDao.deleteImage(image_id);
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.OK).type(MediaType.APPLICATION_JSON).entity(entity).build();
	}

}
