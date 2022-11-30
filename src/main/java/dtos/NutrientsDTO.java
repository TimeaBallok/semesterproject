package dtos;

public class NutrientsDTO
{
    String name;
    Integer amount;
    String unit;
    double percentOfDailyNeeds;

    public NutrientsDTO(String name, Integer amount, String unit, double percentOfDailyNeeds)
    {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.percentOfDailyNeeds = percentOfDailyNeeds;
    }
}
