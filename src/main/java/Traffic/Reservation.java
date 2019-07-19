package Traffic;

import java.util.ArrayList;
import java.util.*;


public class Reservation {

    private int from;
    private int to;
    protected Parking parking;
    protected int occupiedPlaces;
    protected boolean[] places;
    Hashtable<Integer,ArrayList<Time>> times; // in ith index is list of reservation for ith Place

    public Reservation(){}

    public Reservation(Parking parking)
    {
        this.parking = parking;
        this.occupiedPlaces = 0;
        this.places = new boolean[parking.numberOfPlaces];
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


    public int reservePlace(int numberOfPlace, Time newTime)
    {
        if(this.parking.inRange(numberOfPlace))  // That Place exist
        {
            if (!isReservation(numberOfPlace)) // That Place hasn't been reserved yet
            {
                times.put(numberOfPlace, new ArrayList<Time>());
                times.get(numberOfPlace).add(newTime);
                this.occupiedPlaces++;

                return 1;
            }
            else
                {
                    if(isAvaliable(numberOfPlace, newTime))
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


    public boolean isFreePlace(int numberOfPlace)
    {
        if(this.times.isEmpty())
        {
            return true;
        }
        else if(!this.times.containsKey(numberOfPlace))
        {
            return true;
        }

        else
            return false;
    }


    public void resetParking()
    {
        for(int i = 0; i<this.parking.numberOfPlaces; i++)
        {
            this.places[i] = false;
        }
        this.times.clear();
        this.occupiedPlaces = 0;
    }


    public boolean isEmpty()
    {
        if(this.occupiedPlaces == 0)
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

    protected boolean isReservation(int numberOfPlace)
    {
        if (!times.isEmpty() && times.containsKey(numberOfPlace)) // That Place hasn't been reserved yet
        {
            return true;
        }

        else
            return false;
    }

    protected boolean isAvaliable(int numberOfPlace, Time newTime)
    {
        boolean answer = true;

        if(!times.isEmpty() && times.containsKey(numberOfPlace))
        {
            for (int i = 0; i < times.get(numberOfPlace).size(); i++)
            {
                if (!isPlacePossibleToReserve(times.get(numberOfPlace).get(i), newTime))
                {
                    answer = false; // checking if there is time conflict
                }
            }

            if (answer) {
               // times.get(numberOfPlace).add(newTime);
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


    protected int release(int numberOfPlace, Time timeToRelease)
    {
        if(this.parking.inRange(numberOfPlace))
        {
            if (times.isEmpty() || !times.containsKey(numberOfPlace))
            {
                return 2; // Parking Place hasn't been reserved yet
            }
            else {
                if(times.get(numberOfPlace).contains(timeToRelease)) // Exists that time in that Place
                {
                    times.get(numberOfPlace).remove(timeToRelease);
                    if(times.get(numberOfPlace).isEmpty())
                    {
                        times.remove(numberOfPlace);
                    }
                }
                return 1;
            }
        }
        else
            return 0;
    }
}