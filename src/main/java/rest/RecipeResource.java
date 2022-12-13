package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.*;
import entities.Recipe;
import facades.RecipeFacade;
import utils.EMF_Creator;
import utils.Utility;

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
    public String demo()
    {
        return "{\"msg\":\"Hello, food junkie\"}";
    }

    @Path("search/{recipeName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed("user") TODO:HEY INDKOMMENTER MIG DIN ABE
    public String searchRecipeByName(@PathParam("recipeName") String recipeName) throws ExecutionException, InterruptedException
    {
        List<String> recipes = recipeFacade.complexSearch(recipeName);
        RecipesDTO recipesDTO = GSON.fromJson(recipes.get(0), RecipesDTO.class);
        return GSON.toJson(recipesDTO);
    }


    @Path("singleRecipe/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed("user")  //TODO:HEY INDKOMMENTER MIG DIN ABE
    public String showSingleRecipe(@PathParam("id") String id) throws ExecutionException, InterruptedException, IOException
    {

        String recipe = recipeFacade.fetchSingleRecipe(id);
        SingleRecipeDTO singleRecipeDTO = GSON.fromJson(recipe, SingleRecipeDTO.class);
        return GSON.toJson(singleRecipeDTO);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String saveRecipe(String input)
    {
        SingleRecipeDTO singleRecipeDTO = GSON.fromJson(input, SingleRecipeDTO.class);
        Recipe recipe = recipeFacade.saveRecipe(singleRecipeDTO); //TODO: Change returnvalue to a DTO?
//        Recipe recipe = recipeFacade.getRecipe(singleRecipeDTO.getId());
        return GSON.toJson(recipe.getId()); //TODO: return recipeJson?
    }

    @Path("{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getRecipeFromDBById(@PathParam("id") Integer id) throws ExecutionException, InterruptedException, IOException
    {
        String recipeJSON = recipeFacade.getRecipeById(id);
        recipeJSON = Utility.fixDiets(recipeJSON);
        SingleRecipeDTO singleRecipeDTO = GSON.fromJson(recipeJSON, SingleRecipeDTO.class);
        return GSON.toJson(singleRecipeDTO);
    }

    @Path("bookmark/{userName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getBookmarkedRecipeFromDBByUser(@PathParam("userName") String userName) throws ExecutionException, InterruptedException, IOException
    {
        List<String> recipeJSONList = recipeFacade.getBookmarkedRecipeJSONFromDbByUser(userName);
        List<String> fixedRecipeJSONList = new ArrayList<>();
        String recipeJSON = "";
        for (int i = 0; i < recipeJSONList.size(); i++) {
            recipeJSON = Utility.fixDiets(recipeJSONList.get(i));
            fixedRecipeJSONList.add(recipeJSON);
        }
        List<SingleRecipeDTO> singleRecipeDTOList = new ArrayList<>();
        SingleRecipeDTO singleRecipeDTO;
        for (int i = 0; i < recipeJSONList.size(); i++) {
            singleRecipeDTO = GSON.fromJson(fixedRecipeJSONList.get(i), SingleRecipeDTO.class);
            singleRecipeDTOList.add(singleRecipeDTO);
        }
        return GSON.toJson(singleRecipeDTOList);
    }

    @Path("bookmark/{userName}/{recipeId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteBookmark(@PathParam("userName") String userName, @PathParam("recipeId") Integer recipeId) throws ExecutionException, InterruptedException, IOException
    {
        int amountOfDeletedBookmarks = recipeFacade.deleteBookmark(userName,recipeId);
        return GSON.toJson(amountOfDeletedBookmarks);
    }


}

