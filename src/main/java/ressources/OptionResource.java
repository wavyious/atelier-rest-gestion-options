package ressources;

import entities.Option;
import metiers.OptionBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import utilities.Services;

@Path("options")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OptionResource {

    private final OptionBusiness metier = Services.OPTIONS;
    // 1) POST /options
    @POST
    public Response create(Option option) {
        if (option != null && metier.addOption(option)) {
            return Response.ok(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 2) GET /options    3) GET /options?domaine=...
    @GET
    public Response list(@QueryParam("domaine") String domaine) {
        List<Option> result = (domaine == null)
                ? metier.getListeOptions()
                : metier.getOptionsByDomaine(domaine);
        return Response.ok(result).build();
    }

    // 6) GET /options/{code}
    @GET
    @Path("{code}")
    public Response getOne(@PathParam("code") int code) {
        Option o = metier.getOptionByCode(code);
        return (o != null)
                ? Response.ok(o).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    // 5) PUT /options/{code}
    @PUT
    @Path("{code}")
    public Response update(@PathParam("code") int code, Option option) {
        if (option != null && metier.updateOption(code, option)) {
            return Response.ok(option).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 4) DELETE /options/{code}
    @DELETE
    @Path("{code}")
    public Response delete(@PathParam("code") int code) {
        return metier.deleteOption(code)
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}