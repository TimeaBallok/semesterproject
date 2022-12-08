package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BookmarkDTO;
import dtos.MealPlanDTO;
import dtos.RecipesDTO;
import facades.RecipeFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Path("/bookmark")
public class BookmarkResource
{
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    RecipeFacade recipeFacade = RecipeFacade.getRecipeFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello, mealPlan\"}";
    }


    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String addMealPlan(String input){
        BookmarkDTO bookmarkDTO = GSON.fromJson(input, BookmarkDTO.class);
        BookmarkDTO newBookmarkDTO = recipeFacade.addBookmark(bookmarkDTO);
        return GSON.toJson(newBookmarkDTO);
    }

    @Path("{userName}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
//    @RolesAllowed("user") TODO:HEY INDKOMMENTER MIG DIN ABE
    public String getBookmarksByUsername(@PathParam("userName")String userName) throws ExecutionException, InterruptedException
    {
        List<BookmarkDTO> bookmarks = recipeFacade.getBookmarks(userName);
        return GSON.toJson(bookmarks);
    }
}
