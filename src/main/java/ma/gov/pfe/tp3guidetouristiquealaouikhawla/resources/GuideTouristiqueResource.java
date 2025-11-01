package ma.gov.pfe.tp3guidetouristiquealaouikhawla.resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ma.gov.pfe.tp3guidetouristiquealaouikhawla.llm.LlmClient;
import ma.gov.pfe.tp3guidetouristiquealaouikhawla.model.InfosTouristiques;

@Path("/guide")
public class GuideTouristiqueResource {

    @Inject
    private LlmClient llmClient;

    @GET
    @Path("lieu/{ville_ou_pays}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response guideVilleOuPays(
            @PathParam("ville_ou_pays") String villeOuPays,
            @QueryParam("nb") @DefaultValue("2") int nb) {

        // Appel au LLM
        InfosTouristiques reponse = llmClient.envoyer(villeOuPays, nb);

        // Construction de la réponse avec headers CORS et cache
        Response.ResponseBuilder responseBuilder = Response.ok(reponse);
        responseBuilder.header("Access-Control-Allow-Origin", "*");
        responseBuilder.header("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        responseBuilder.header("Pragma", "no-cache");
        responseBuilder.header("Expires", "0");

        return responseBuilder.build();
    }
}