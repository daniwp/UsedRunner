package rest;

import facade.DBAAdFacade;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import jsonconverter.DBAAdConverter;

/**
 * REST Web Service
 *
 * @author Daniel
 */
@Path("ad")
public class AdResource {

    @Context
    private UriInfo context;

    public AdResource() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{text}")
    public Response getSearchResults(@PathParam("text") String text) {
        String[] arr = text.split("&");
        String searchText = "";
        String priceRange = "";

        for (String s : arr) {
            System.out.println(s);
            if (s.substring(0, 1).equals("q")) {
                searchText = s.substring(2, s.length());
            }
            if (s.substring(0, 1).equals("p")) {
                priceRange = s.substring(2, s.length());
            }
        }

        return Response.ok(DBAAdConverter.getClassInstance().getJsonFromAds(DBAAdFacade.getClassInstance().getSpecificAds(searchText, priceRange))).build();
    }
}
