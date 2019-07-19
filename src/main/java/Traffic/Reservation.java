package Traffic;

import java.util.ArrayList;
import java.util.*;


public class Reservation {

    private int from;
    private int to;
   // protected Parking parking;
    protected Hashtable<Integer, Integer> occupiedPlaces; // <id of parking, occupiedPlaces>
    protected ArrayList<boolean[]> places;
    
    ArrayList<Parking> parkings;
    Hashtable<Integer, Hashtable<Integer,ArrayList<Time>>> times; // each parking has list of times for each place in it

    public Reservation(){}

    public Reservation(Parking parking)
    {
        this.places = new ArrayList<>();
        this.places.add(new boolean[parking.getNumberOfPlaces()]);
        parkings = new ArrayList<>();
        parkings.add(parking);
        this.occupiedPlaces = new Hashtable<>();
        this.occupiedPlaces.put(parking.getId(),0);

        times = new Hashtable<>();
        Hashtable<Integer, ArrayList<Time>> tempHashTable = new Hashtable<>(); // parking and list of times
        tempHashTable.put(0, new ArrayList<>());
        tempHashTable.remove(0);

        times.put(parking.getId(),tempHashTable);

        Arrays.fill(this.places.get(parking.getId()),false);
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public int reservePlace(int parkingId, int numberOfPlace, Time newTime)
    {
        if(this.parkings.get(parkingId).inRange(numberOfPlace))  // That Place exist
        {
            if (!isReservation(parkingId,numberOfPlace)) // That Place hasn't been reserved yet
            {
                //times.put(numberOfPlace, new ArrayList<>());
                ArrayList<Time> tempList = new ArrayList<>(); // list of times for specyfic place on specyfic parking
                tempList.add(newTime); // Adding new time to this list of times
                Hashtable<Integer,ArrayList<Time>> tempHashTable;
                tempHashTable = times.get(parkingId); //getting hashtable <Place, times> for specyfic parking
                tempHashTable.put(numberOfPlace, tempList); // Updating that hashtable with new list of times
                times.put(parkingId,tempHashTable); // updating info for specyfic parking

                occupiedPlaces.put(parkingId, occupiedPlaces.get(parkingId) + 1);
                this.places.get(parkingId)[numberOfPlace] = true;

                return 1;
            }
            else
                {
                    if(isAvaliable(parkingId,numberOfPlace, newTime))
                        return 1;

                    else
                        return 2; // is not avaliable Time Conflict
                }
        }
        else
        {
            return 0; // That Place doesn't exist in parking
        }
    }


    public boolean isFreePlace(int parkingId, int numberOfPlace)
    {
        if(this.times.get(parkingId).isEmpty())
        {
            return true;
        }
        else if(!this.times.get(parkingId).containsKey(numberOfPlace))
        {
            return true;
        }

        else
            return false;
    }


    public void resetParking(int parkingId)
    {
        for(int i = 0; i < this.parkings.get(parkingId).numberOfPlaces; i++)
        {
            this.places.get(parkingId)[i]=false;
        }
        this.times.get(parkingId).clear();
        this.occupiedPlaces.put(parkingId, 0);
    }


    public boolean isEmpty(int numberOfParking)
    {
        if(this.occupiedPlaces.get(numberOfParking) == 0)
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

    protected boolean isReservation(int parkingId, int numberOfPlace)
    {
        if (!times.get(parkingId).isEmpty() && times.get(parkingId).containsKey(numberOfPlace)) // That Place hasn't been reserved yet
        {
            return true;
        }

        else
            return false;
    }

    protected boolean isAvaliable(int parkingId, int numberOfPlace, Time newTime)
    {
        boolean answer = true;

        if(!times.get(parkingId).isEmpty() && times.get(parkingId).containsKey(numberOfPlace))
        {
            for (int i = 0; i < times.get(parkingId).get(numberOfPlace).size(); i++)
            {
                if (!isPlacePossibleToReserve(times.get(parkingId).get(numberOfPlace).get(i), newTime))
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


    protected int release(int parkingId, int numberOfPlace, Time timeToRelease)
    {
        if(this.parkings.get(parkingId).inRange(numberOfPlace))
        {

            if (times.get(parkingId).isEmpty() || !times.get(parkingId).containsKey(numberOfPlace))
            {
                return 2; // Parking Place hasn't been reserved yet
            }
            else {
                if(times.get(parkingId).get(numberOfPlace).contains(timeToRelease)) // Exists that time in that Place
                {
                    times.get(parkingId).get(numberOfPlace).remove(timeToRelease);
                    if(times.get(parkingId).get(numberOfPlace).isEmpty()) // if now that place is empty
                    {
                        times.get(parkingId).remove(numberOfPlace);
                        this.places.get(parkingId)[numberOfPlace] = false;
                    }
                }
                return 1;
            }
        }
        else

            return 0; //It not in range
    }
}