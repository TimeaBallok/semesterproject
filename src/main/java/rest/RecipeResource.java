package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.*;
import facades.RecipeFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
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
        RecipesDTO recipesDTO = GSON.fromJson(recipes.get(0), RecipesDTO.class);
        return GSON.toJson(recipesDTO);
    }

    @Path("singleRecipe/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String showSingleRecipe(@PathParam("id")String id) throws ExecutionException, InterruptedException, IOException
    {

        String recipe = recipeFacade.fetchSingleRecipe(id);
        SingleRecipeDTO singleRecipeDTO = GSON.fromJson(recipe, SingleRecipeDTO.class);
        return GSON.toJson(singleRecipeDTO);
    }


}

