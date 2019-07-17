package Traffic;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import java.util.*;


public class Reservation {

    private int from;
    private int to;
    protected Parking parking;
    protected int occupied_spaces;
    protected boolean[] spaces;
    Hashtable<Integer,ArrayList<Time>> times; // in ith index is list of reservation for ith space

    public Reservation(){}

    public Reservation(Parking parking)
    {
        this.parking = parking;
        this.occupied_spaces = 0;
        this.spaces = new boolean[parking.number_of_spaces];
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


    public int reservSpace(int number_of_space, Time newTime)
    {
        if(this.parking.inRange(number_of_space))  // That space exist
        {
            if (!wereReservations(number_of_space)) // That space hasn't been reserved yet
            {
                times.put(number_of_space, new ArrayList<Time>());
                times.get(number_of_space).add(newTime);
                this.occupied_spaces++;

                return 1;
            }
            else
                {
                    if(avaliable(number_of_space, newTime))
                        return 1;

                    else
                        return 2;
                }
        }

        else
        {
            return 0; // That space doesn't exist in parking
        }
    }



    public boolean isFree(int number_of_space)
    {
        if(!this.times.isEmpty())
        {
            return true;
        }
        else if(!this.times.containsKey(number_of_space))
        {
            return true;
        }

        else
            return false;
    }

    public void reset()
    {
        for(int i=0; i<this.parking.number_of_spaces; i++)
        {
            this.spaces[i] = false;
        }
        this.occupied_spaces = 0;
    }

    public boolean empty()
    {
        if(this.occupied_spaces == 0)
            return true;
        else
            return false;
    }

    protected boolean possibleToReserve(Time oldTime, Time newTime)
    {
        if(newTime.valueTo() < oldTime.valueFrom() || newTime.valueFrom() > oldTime.valueTo())
        {
            return true;
        }

        else
            return false;

    }

    protected boolean wereReservations(int number_of_space)
    {
        if (!times.isEmpty() && times.containsKey(number_of_space)) // That space hasn't been reserved yet
        {
            return true;
        }

        else
            return false;
    }

    protected boolean avaliable(int number_of_space, Time newTime)
    {
        boolean answer = true;

        if(!times.isEmpty() && times.containsKey(number_of_space))
        {
            for (int i = 0; i < times.get(number_of_space).size(); i++)
            {
                if (!possibleToReserve(times.get(number_of_space).get(i), newTime))
                {
                    answer = false; // checking if there is time conflict
                }
            }

            if (answer) {
               // times.get(number_of_space).add(newTime);
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


    protected int release(int number_of_space, Time timeToRelease)
    {
        if(this.parking.inRange(number_of_space))
        {
            if (times.isEmpty() || !times.containsKey(number_of_space))
            {
                return 2; // Parking space hasn't been reserved yet
            }
            else {
                if(times.get(number_of_space).contains(timeToRelease)) // Exists that time in that space
                {
                    times.get(number_of_space).remove(timeToRelease);
                    if(times.get(number_of_space).isEmpty())
                    {
                        times.remove(number_of_space);
                    }
                }
                return 1;
            }
        }
        else
            return 0;
    }







}
