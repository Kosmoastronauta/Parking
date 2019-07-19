package Traffic;
import java.util.Arrays;

public class Parking
{
    protected int numberOfPlaces;
    protected int occupiedPlaces;
    protected boolean[] places;
    private int priceDayTariff;
    private int priceNightTariff;

    IparkingFree iparkingFree;

    public Parking(int numberOfPlaces)
    {
        this.numberOfPlaces = numberOfPlaces;
        occupiedPlaces = 0;
        places = new boolean[numberOfPlaces];
        Arrays.fill(places,false);
    }

    public Parking(IparkingFree iparkingFree)
    {
        this.iparkingFree = iparkingFree;
    }

    public String toString()
    {
        String out = "Parking Name: Default Name " + "\n"
                + " Places: " + this.occupiedPlaces + "/" + this.numberOfPlaces;

        for(int i = 0; i <this.numberOfPlaces; i++)
        {
            if(this.places[i])
                System.out.println(i + " [Occupied]");

            else
                System.out.println(i + " [Free]");
        }
        return out;
    }

    public boolean inRange(int number_of_place)
    {
        if(number_of_place < this.numberOfPlaces && number_of_place >= 0)
        {
            return true;
        }

        else
            return false;
    }
}