package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nimbusds.jose.shaded.json.JSONObject;
import dtos.*;
import entities.Recipe;
import facades.RecipeFacade;
import utils.EMF_Creator;

import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
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
//    @RolesAllowed("user") TODO:HEY INDKOMMENTER MIG DIN ABE
    public String searchRecipeByName(@PathParam("recipeName")String recipeName) throws ExecutionException, InterruptedException
    {
        List<String> recipes = recipeFacade.complexSearch(recipeName);
        RecipesDTO recipesDTO = GSON.fromJson(recipes.get(0), RecipesDTO.class);
        return GSON.toJson(recipesDTO);
    }


    @Path("singleRecipe/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed("user")  TODO:HEY INDKOMMENTER MIG DIN ABE
    public String showSingleRecipe(@PathParam("id")String id) throws ExecutionException, InterruptedException, IOException
    {

        String recipe = recipeFacade.fetchSingleRecipe(id);
        SingleRecipeDTO singleRecipeDTO = GSON.fromJson(recipe, SingleRecipeDTO.class);
        return GSON.toJson(singleRecipeDTO);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String saveRecipe(String input){
        String temp2  = input.replace("\n", "").replace("\r", "");
        SingleRecipeDTO singleRecipeDTO = GSON.fromJson(temp2, SingleRecipeDTO.class);
        recipeFacade.saveRecipe(singleRecipeDTO);
        Recipe recipe = recipeFacade.getRecipe(singleRecipeDTO.getId());
        String temp = recipe.getRecipeJson().replace("\n", "").replace("\r", "");
        return "{\"msg\":\"Recipe saved\"}";
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getRecipe(@PathParam("id")Integer id) throws ExecutionException, InterruptedException, IOException
    {
        Recipe recipe = recipeFacade.getRecipe(id);
        SingleRecipeDTO singleRecipeDTO = GSON.fromJson(recipe.getRecipeJson(), SingleRecipeDTO.class);
        String temp = GSON.toJson(recipe.getRecipeJson(), String.class);
        return temp;
    }



}

