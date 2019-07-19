package Traffic;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.*;


public class Reservation {

    private int from;
    private int to;
    protected Parking parking;
    protected int occupied_places;
    protected boolean[] places;
    Hashtable<Integer,ArrayList<Time>> times; // in ith index is list of reservation for ith Place

    public Reservation(){}

    public Reservation(Parking parking)
    {
        this.parking = parking;
        this.occupied_places = 0;
        this.places = new boolean[parking.number_of_places];
        times = new Hashtable<Integer, ArrayList<Time>>();
    }


    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public Parking getParking() {
        return parking;
    }


    public int reservPlace(int number_of_Place, Time newTime)
    {
        if(this.parking.inRange(number_of_Place))  // That Place exist
        {
            if (!isReservation(number_of_Place)) // That Place hasn't been reserved yet
            {
                times.put(number_of_Place, new ArrayList<Time>());
                times.get(number_of_Place).add(newTime);
                this.occupied_places++;

                return 1;
            }
            else
                {
                    if(isAvaliable(number_of_Place, newTime))
                        return 1;

                    else
                        return 2;
                }
        }

        else
        {
            return 0; // That Place doesn't exist in parking
        }
    }


    public boolean isFreePlace(int number_of_Place)
    {
        if(this.times.isEmpty())
        {
            return true;
        }
        else if(!this.times.containsKey(number_of_Place))
        {
            return true;
        }

        else
            return false;
    }


    public void resetParking()
    {
        for(int i=0; i<this.parking.number_of_places; i++)
        {
            this.places[i] = false;
        }
        this.times.clear();
        this.occupied_places = 0;
    }


    public boolean isEmpty()
    {
        if(this.occupied_places == 0)
            return true;
        else
            return false;
    }

    protected boolean isPlacePossibleToReserve(Time oldTime, Time newTime)
    {
        if(newTime.valueTo() <= oldTime.valueFrom() || newTime.valueFrom() >= oldTime.valueTo())
        {
            return true;
        }

        else
            return false;

    }

    protected boolean isReservation(int number_of_Place)
    {
        if (!times.isEmpty() && times.containsKey(number_of_Place)) // That Place hasn't been reserved yet
        {
            return true;
        }

        else
            return false;
    }

    protected boolean isAvaliable(int number_of_Place, Time newTime)
    {
        boolean answer = true;

        if(!times.isEmpty() && times.containsKey(number_of_Place))
        {
            for (int i = 0; i < times.get(number_of_Place).size(); i++)
            {
                if (!isPlacePossibleToReserve(times.get(number_of_Place).get(i), newTime))
                {
                    answer = false; // checking if there is time conflict
                }
            }

            if (answer) {
               // times.get(number_of_Place).add(newTime);
                return true; // Avaliable and has other reservations
            }
            else {
                return false; // Occupied in that time
            }

        }
        else
        {
            return true;
        }
    }


    protected int release(int number_of_Place, Time timeToRelease)
    {
        if(this.parking.inRange(number_of_Place))
        {
            if (times.isEmpty() || !times.containsKey(number_of_Place))
            {
                return 2; // Parking Place hasn't been reserved yet
            }
            else {
                if(times.get(number_of_Place).contains(timeToRelease)) // Exists that time in that Place
                {
                    times.get(number_of_Place).remove(timeToRelease);
                    if(times.get(number_of_Place).isEmpty())
                    {
                        times.remove(number_of_Place);
                    }
                }
                return 1;
            }
        }
        else
            return 0;
    }
}