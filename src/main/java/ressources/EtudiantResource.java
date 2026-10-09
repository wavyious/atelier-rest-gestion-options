package ressources;

import entities.Etudiant;
import entities.EtudiantList;
import entities.Option;
import metiers.EtudiantBusiness;
import metiers.OptionBusiness;
import utilities.Services;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("etudiants")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EtudiantResource {

    private final EtudiantBusiness metier = Services.ETUDIANTS;
    private final OptionBusiness optionMetier = Services.OPTIONS;

    // 1) POST /etudiants
    @POST
    public Response create(Etudiant etudiant) {
        if (etudiant == null || etudiant.getOption() == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        // addEtudiant returns false if the option does not exist
        if (metier.addEtudiant(etudiant)) {
            return Response.ok(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 2) GET /etudiants
    @GET
    public Response list() {
        return Response.ok(metier.getAllEtudiants()).build();
    }

    // 6) GET /etudiants/option?codeOption=1  -> XML
    @GET
    @Path("option")
    @Produces(MediaType.APPLICATION_XML)
    public Response byOption(@QueryParam("codeOption") int codeOption) {
        Option option = optionMetier.getOptionByCode(codeOption);
        if (option == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new EtudiantList(metier.getEtudiantsByOption(option))).build();
    }

    // 3) GET /etudiants/{id}
    @GET
    @Path("{id}")
    public Response getOne(@PathParam("id") String id) {
        Etudiant e = metier.getEtudiantByIdentifiant(id);
        return (e != null)
                ? Response.ok(e).build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }

    // 5) PUT /etudiants/{id}
    @PUT
    @Path("{id}")
    public Response update(@PathParam("id") String id, Etudiant etudiant) {
        if (etudiant != null && metier.updateEtudiant(id, etudiant)) {
            return Response.ok(etudiant).build();
        }
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    // 4) DELETE /etudiants/{id}
    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") String id) {
        return metier.deleteEtudiant(id)
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
    }
}