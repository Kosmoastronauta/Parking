package Traffic;
import java.util.Arrays;

public class Parking
{
    protected static int idGenerator = 0;
    protected String name;
    protected int id;
    protected int numberOfPlaces;
    protected int occupiedPlaces;
   // protected boolean[] places;
    private int priceDayTariff;
    private int priceNightTariff;

    IparkingFree iparkingFree;

    public Parking(int numberOfPlaces, String name)
    {
        this.id = idGenerator;
        idGenerator++;
        this.name = name;
        this.numberOfPlaces = numberOfPlaces;
        occupiedPlaces = 0;
     //   places = new boolean[numberOfPlaces];
      //  Arrays.fill(places,false);
    }

    public Parking(IparkingFree iparkingFree)
    {
        this.iparkingFree = iparkingFree;
    }

    public String toString()
    {
        String out = "Parking Name: Default Name " + "\n"
                + " Places: " + this.occupiedPlaces + "/" + this.numberOfPlaces;
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

    public int getId()
    {
        return this.id;
    }

    public int getNumberOfPlaces()
    {
        return this.numberOfPlaces;
    }

}