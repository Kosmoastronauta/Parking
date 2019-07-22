package Traffic;

import java.util.ArrayList;
import java.util.*;

public class Reservation
{
    private int from;
    private int to;
    protected Hashtable<Integer, Integer> occupiedPlaces; // <id of parking, occupiedPlaces>
    protected Hashtable<Integer, boolean[]> places; // <id of parking, places status>
    Hashtable<Integer, Parking> parkings; // <id of parking, parking>
    Hashtable<Integer, Hashtable<Integer, ArrayList<Time>>> times; // each parking has it's own list of times for
    // each place
    // in it

    public Reservation() {}

    public Reservation(Parking parking)
    {
        places = new Hashtable<>();
        places.put(parking.getId(), new boolean[parking.getNumberOfPlaces()]);
        parkings = new Hashtable<>();
        this.occupiedPlaces = new Hashtable<>();
        times = new Hashtable<>();
        addParking(parking);
        Arrays.fill(this.places.get(parking.getId()), false);
    }

    public int getFrom()
    {
        return from;
    }

    public int getTo()
    {
        return to;
    }

    public int reservePlace(int parkingId, int numberOfPlace, Time newTime)
    {
        if(this.parkings.get(parkingId).inRange(numberOfPlace))  // That Place exist
        {
            if(!isParkingInReseservation(parkingId))
                createPlaceForNotExistingParkingOnPlaceWithTime(parkingId, numberOfPlace, newTime);

            if(!isReservation(parkingId, numberOfPlace)) // That Place hasn't been reserved yet
            {
                createPlaceForExistingParkingOnPlaceWithTime(parkingId, numberOfPlace, newTime);
                occupiedPlaces.put(parkingId, occupiedPlaces.get(parkingId) + 1);
                this.places.get(parkingId)[numberOfPlace] = true;

                return 1;
            }
            else
            {
                if(isAvaliablePlaceInTime(parkingId, numberOfPlace, newTime))
                    return 1; // Is avaliable can be reserved

                else
                    return 2; // is not avaliable Time Conflict
            }
        }
        else
            return 0; // That Place doesn't exist in parking
    }

    public boolean isFreePlace(int parkingId, int numberOfPlace)
    {
        if(this.times.get(parkingId).isEmpty())
            return true;

        else if(!this.times.get(parkingId).containsKey(numberOfPlace))
            return true;

        else
            return false;
    }

    public void resetParking(int parkingId)
    {
        for(int i = 0; i < this.parkings.get(parkingId).numberOfPlaces; i++)
        {
            this.places.get(parkingId)[i] = false;
        }
        this.times.get(parkingId).clear();
        this.occupiedPlaces.put(parkingId, 0);
    }

    protected boolean isPlacePossibleToReserve(Time oldTime, Time newTime)
    {
        if(newTime.valueTo() <= oldTime.valueFrom() || newTime.valueFrom() >= oldTime.valueTo())
            return true;
        else
            return false;
    }

    protected boolean isReservation(int parkingId, int numberOfPlace)
    {
        if(!times.get(parkingId).isEmpty() && times.get(parkingId).containsKey(numberOfPlace)) // That Place hasn't been reserved yet
            return true;
        else
            return false;
    }

    protected boolean isAvaliablePlaceInTime(int parkingId, int numberOfPlace, Time newTime)
    {
        boolean answer = true;

        if(!times.get(parkingId).isEmpty() && times.get(parkingId).containsKey(numberOfPlace))
        {
            for(int i = 0; i < times.get(parkingId).get(numberOfPlace).size(); i++)
            {
                if(!isPlacePossibleToReserve(times.get(parkingId).get(numberOfPlace).get(i), newTime))
                    answer = false; // checking if there is time conflict
            }

            if(answer)
                return true; // Avaliable and has other reservations
            else
                return false; // Occupied in that time
        }
        else
            return true;
    }

    protected int release(int parkingId, int numberOfPlace, Time timeToRelease)
    {
        if(!isParkingInReseservation(parkingId))
        {
            return 2; // That parking does not exist int reservation
        }
        if(this.parkings.get(parkingId).inRange(numberOfPlace))
        {
            if(times.get(parkingId).isEmpty() || !times.get(parkingId).containsKey(numberOfPlace))
            {
                return 2; // Parking Place hasn't been reserved yet
            }
            else
            {
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

    public int addParking(Parking parking)
    {
        if(this.isParkingInReseservation(parking.getId()))
            return 0;

        else
        {
            this.places.put(parking.getId(), new boolean[parking.getNumberOfPlaces()]);
            parkings.put(parking.getId(),parking);
            this.occupiedPlaces.put(parking.getId(), 0);
            ArrayList<Time> tempList = new ArrayList<>();
            Hashtable<Integer, ArrayList<Time>> tempHashTable = new Hashtable<>();
            Time tempTime = new Time(0, 0, 0, 1);
            tempHashTable.put(0, new ArrayList<>());
            tempHashTable.remove(0);

            times.put(parking.getId(), tempHashTable); // fill times proper initialized data structures

            return 1;
        }
    }

    public boolean isParkingInReseservation(int parkingId)
    {
        if(this.parkings.containsKey(parkingId)) return true;
        else return false;
    }

    private void createPlaceForExistingParkingOnPlaceWithTime(int parkingId, int numberOfPlace,Time time)
    {
        ArrayList<Time> tempList = new ArrayList<>(); // list of times for specyfic place on specyfic parking
        tempList.add(time); // Adding new time to this list of times
        Hashtable<Integer, ArrayList<Time>> tempHashTable;
        tempHashTable = times.get(parkingId); //getting hashtable <Place, times> for specyfic parking
        tempHashTable.put(numberOfPlace, tempList); // Updating that hashtable with new list of times
        times.put(parkingId, tempHashTable); // updating info for specyfic parking
    }

    private void createPlaceForNotExistingParkingOnPlaceWithTime(int parkingId, int numberOfPlace, Time time)
    {
        ArrayList<Time> tempList = new ArrayList<>();
        tempList.add(time);
        Hashtable<Integer, ArrayList<Time>> tempHashtable = new Hashtable<>();
        tempHashtable.put(numberOfPlace, tempList);
        this.times.put(parkingId, tempHashtable);
    }
}