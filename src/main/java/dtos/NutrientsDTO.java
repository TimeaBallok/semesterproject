package dtos;

public class NutrientsDTO
{
    String name;
    double amount;
    String unit;
    double percentOfDailyNeeds;

    public NutrientsDTO(String name, double amount, String unit, double percentOfDailyNeeds)
    {
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.percentOfDailyNeeds = percentOfDailyNeeds;
    }

    @Override
    public String toString()
    {
        return "{" +
                "\"name\":\"" + name + "\"" +
                ", \"amount\":" + amount +
                ", \"unit\":\"" + unit + "\"" +
                '}';
    }
}
