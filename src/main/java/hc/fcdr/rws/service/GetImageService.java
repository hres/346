

package hc.fcdr.rws.service;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import hc.fcdr.rws.db.DbConnection;
import hc.fcdr.rws.db.PackageDao;


@Path("/GetImageService")

public class GetImageService  {
	static PackageDao packageDao = null;

	@PostConstruct
	public static void initialize() {
		if (packageDao == null) {
			 DbConnection pgConnectionPool = new DbConnection();

			packageDao = new PackageDao(pgConnectionPool.getConnections(),
					pgConnectionPool.getSchema());

		}
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






}

