package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MealPlanDTO;
import facades.RecipeFacade;
import utils.EMF_Creator;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Path("/mealPlan")
public class MealPlanResource
{
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    RecipeFacade recipeFacade = RecipeFacade.getRecipeFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello, mealPlan\"}";
    }

    @Path("/{userName}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getUsersMealPlans(@PathParam("userName") String userName)
    {
        List<MealPlanDTO> mealPlans = recipeFacade.getAllMealPlans(userName);
        return GSON.toJson(mealPlans);
    }



    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public String addMealPlan(String input){
        MealPlanDTO mealPlanDTO = GSON.fromJson(input, MealPlanDTO.class);
        MealPlanDTO newMealPlanDTO = recipeFacade.addMealPlan(mealPlanDTO);
        return GSON.toJson(newMealPlanDTO);
    }

    public enum MealType {
        BREAKFAST,
        LUNCH,
        DINNER
    }


}