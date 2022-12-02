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
    @NotNull
    private final String userName;
    private final Integer recipeId;
    @Size(max = 1073741824)
    @NotNull
    private final String recipeJson;
    @Size(max = 1073741824)
    private final String ingredientsJson;
    @NotNull
    private final MealType type;
    @NotNull
    private final LocalDate date;

    public MealPlanDTO(String userName, Integer recipeId, String recipeJson, String ingredientsJson, MealType type, LocalDate date)
    {
        this.userName = userName;
        this.recipeId = recipeId;
        this.recipeJson = recipeJson;
        this.ingredientsJson = ingredientsJson;
        this.type = type;
        this.date = date;
    }

    public String getUserName()
    {
        return userName;
    }

    public Integer getRecipeId()
    {
        return recipeId;
    }

    public String getRecipeJson()
    {
        return recipeJson;
    }

    public String getIngredientsJson()
    {
        return ingredientsJson;
    }

    public MealType getType()
    {
        return type;
    }

    public LocalDate getDate()
    {
        return date;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "(" +
                "userNameUserName = " + userName + ", " +
                "recipeId = " + recipeId + ", " +
                "recipeRecipeJson = " + recipeJson + ", " +
                "recipeIngredientsJson = " + ingredientsJson + ", " +
                "type = " + type + ", " +
                "date = " + date + ")";
    }

    public enum MealType {
        BREAKFAST,
        LUNCH,
        DINNER
    }




}