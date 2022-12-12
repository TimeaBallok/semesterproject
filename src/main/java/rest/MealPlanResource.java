package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.MealPlanDTO;
import dtos.SingleMealPlanDTO;
import dtos.SingleRecipeDTO;
import facades.RecipeFacade;
import utils.EMF_Creator;
import utils.Utility;

import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.LocalDate;
import java.util.*;

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

//    @Path("/{userName}")
//    @GET
//    @Produces({MediaType.APPLICATION_JSON})
//    public String getUsersMealPlans(@PathParam("userName") String userName)
//    {
//        List<MealPlanDTO> mealPlans = recipeFacade.getAllMealPlans(userName);
//        return GSON.toJson(mealPlans);
//    }

    @Path("/{userName}")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String getUsersMealPlans(@PathParam("userName") String userName)
    {
        List<MealPlanDTO> mealPlans = recipeFacade.getAllMealPlans(userName);
        Set<LocalDate> dates = new HashSet<>();
        mealPlans.forEach(mealplan -> {
            dates.add(mealplan.getDate());
        });
        return GSON.toJson(dates);
    }

    @Path("/{userName}/recipes")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String getMealplanRecipesByUserAndDate(String input, @PathParam("userName")String userName)
    {
        LocalDate date = GSON.fromJson(input, LocalDate.class);
//        LocalDate date = LocalDate.parse(input);
        List<SingleMealPlanDTO> recipeJSONList = recipeFacade.getRecipeAndTypeByUsernameAndDate(userName, date);

        String recipesWithTypes = GSON.toJson(recipeJSONList);
        return recipesWithTypes;
    }

//    @Path("/{userName}/recipes")
//    @POST
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.APPLICATION_JSON)
//    public String getMealplanRecipesByUserAndDate(String input, @PathParam("userName")String userName)
//    {
//        LocalDate date = GSON.fromJson(input, LocalDate.class);
////        LocalDate date = LocalDate.parse(input);
//        List<String> recipeJSONList = recipeFacade.getMealplanRecipesJSONByUserAndDate(userName, date);
//        List<String> fixedRecipeJSONList = new ArrayList<>();
//        String recipeJSON = "";
//        for (int i = 0; i < recipeJSONList.size(); i++) {
//            recipeJSON = Utility.fixDiets(recipeJSONList.get(i));
//            fixedRecipeJSONList.add(recipeJSON);
//        }
//        List<SingleRecipeDTO> singleRecipeDTOList = new ArrayList<>();
//        SingleRecipeDTO singleRecipeDTO;
//        for (int i = 0; i < recipeJSONList.size(); i++) {
//            singleRecipeDTO = GSON.fromJson(fixedRecipeJSONList.get(i), SingleRecipeDTO.class);
//            singleRecipeDTOList.add(singleRecipeDTO);
//        }
//        return GSON.toJson(singleRecipeDTOList);
//    }

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