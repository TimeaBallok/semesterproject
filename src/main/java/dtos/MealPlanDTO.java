package dtos;

import entities.MealPlan;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * A DTO for the {@link MealPlan} entity
 */
public class MealPlanDTO implements Serializable
{

    private  String userName;

    private Integer recipeId;

//    private MealPlan.MealType type;
//
//    private LocalDate date;

    public MealPlanDTO(String userName, Integer recipeId, MealPlan.MealType type, LocalDate date)
    {
        this.userName = userName;
        this.recipeId = recipeId;
//        this.type = type;
//        this.date = date;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public Integer getRecipeId()
    {
        return recipeId;
    }

    public void setRecipeId(Integer recipeId)
    {
        this.recipeId = recipeId;
    }

//    public MealPlan.MealType getType()
//    {
//        return type;
//    }
//
//    public void setType(MealPlan.MealType type)
//    {
//        this.type = type;
//    }
//
//    public LocalDate getDate()
//    {
//        return date;
//    }
//
//    public void setDate(LocalDate date)
//    {
//        this.date = date;
//    }





}