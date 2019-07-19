package Traffic;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        Scanner anyKey = new Scanner(System.in);
        int numberOfSpace = 0;
        Parking parking = null;
        Scanner input = new Scanner(System.in);
        int choice = 0;
        int spaces = 0;

        while(choice!=5)
        {
            System.out.println("Parking reservation system");
            System.out.println("1. Create Parking");
            System.out.println("2. Add space");
            System.out.println("3. Release space");
            System.out.println("4. Report");
            System.out.println("5. Exit");

            System.out.println("Your choice: ");

            try {
                choice = input.nextInt();

            } catch (IllegalArgumentException e){
                System.out.println("Invalid Data Type !!! Press any key to continue");
                anyKey.next();
                continue;
            }

            switch (choice)
            {
                case 1:
                {
                    if(parking == null) {
                        System.out.println("Numer of spaces: ");
                        spaces = input.nextInt();
                        parking = new Parking(spaces);
                        System.out.println("Parking with has been created :) Press any key to continue");
                    }
                    else
                    {
                        System.out.println("Parking already exists");
                    }
                    break;
                }

                case 2:
                {
                    int temp;
                   if(parking == null)
                   {
                       System.out.println("Parking hasn't been created yet!!! press any key to continue");
                   }
                   else
                   {
                       System.out.println("Number of space to release: ");
                       try {
                           numberOfSpace = input.nextInt();
                       }catch (Exception e)
                       {
                           System.out.println("Sorry something wrong with typed data :(");
                       }
                        temp = parking.reservPlace(numberOfSpace);
                       if(temp == 1)
                       {
                           System.out.println("Your space has been reserved :) press any key to continue");
                        //   anyKey.next();
                       }
                       else if(temp == 2)
                       {
                           System.out.println("This space is already reserved by another person");
                       }
                       else
                       {
                           System.out.println("There is no free spaces in this parking :(");
                       }
                   }

                    break;
                }

                case 3:
                {
                    if(parking!=null) {
                        int temp;
                        try {
                            System.out.println("Number of space to release: ");
                            numberOfSpace = input.nextInt();
                        }catch (Exception e)
                        {
                            System.out.println("Something wrong with typed data");
                        }
                        temp = parking.release(numberOfSpace);

                        if (temp == 1) {
                            System.out.println("Realeasing a space is completed :)");
                        }
                        else if(temp == 2)
                        {
                            System.out.println("That space was free before releasing");
                        }
                            else {
                            System.out.println("Wrong number of space !!! :(");
                        }
                    }

                    else
                    {
                        System.out.println("Parking hasn't been created yet");
                    }
                    break;
                }

                case 4:
                {
                    if (parking!=null)
                    {
                        System.out.println(parking.toString());
                    }
                    else
                    {
                        System.out.println("Parking hasn't been created yet");
                        System.out.println("Press any key to continue");
                    }
                    break;
                }

                case 5:
                {
                    System.out.println("Thank You for using our system, Come back later");
                    break;
                }

                default: {
                    System.out.println("Your number of option is wrong :|");
                    break;
                }

            }
        }

    }
}