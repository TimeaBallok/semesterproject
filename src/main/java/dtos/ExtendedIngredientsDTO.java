package dtos;

import java.util.Objects;

public class ExtendedIngredientsDTO
{
    private Integer id;
    private String nameClean;
    private double amount;
    private String unit;

    public ExtendedIngredientsDTO(Integer id, String nameClean, double amount, String unit)
    {
        this.id = id;
        this.nameClean = nameClean;
        this.amount = amount;
        this.unit = unit;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getNameClean()
    {
        return nameClean;
    }

    public void setNameClean(String nameClean)
    {
        this.nameClean = nameClean;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }

    public String getUnit()
    {
        return unit;
    }

    public void setUnit(String unit)
    {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof ExtendedIngredientsDTO)) return false;
        ExtendedIngredientsDTO that = (ExtendedIngredientsDTO) o;
        return Double.compare(that.getAmount(), getAmount()) == 0 && Objects.equals(getId(), that.getId()) && Objects.equals(getNameClean(), that.getNameClean()) && Objects.equals(getUnit(), that.getUnit());
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(getId(), getNameClean(), getAmount(), getUnit());
    }

    @Override
    public String toString()
    {
        return "{" +
                "\"id\":" + id +
                ", \"nameClean\":\"" + nameClean + "\"" +
                ", \"amount\":" + amount +
                ", \"unit\":\"" + unit + "\"" +
                '}';
    }
}
