package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ChuckDTO;
import dtos.CombinedDTO;
import dtos.DadDTO;
import facades.RecipeFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/recipe")
public class RecipeResource
{
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    RecipeFacade recipeFacade = RecipeFacade.getRecipeFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello, food junkie\"}";
    }

    @Path("search/{recipeName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String searchRecipeByName(@PathParam("recipeName")String recipeName) throws ExecutionException, InterruptedException
    {
        List<String> recipes = recipeFacade.complexSearch(recipeName);
        return GSON.toJson(recipes);
    }

}

