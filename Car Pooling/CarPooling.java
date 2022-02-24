/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carpooling_package;

import java.util.*;

/**
 * Car Pooling System project.
 * @see <a href = "https://www.oracle.com/technical-resources/articles/java/javadoc-tool.html" > How to Write Doc Comments for the Java doc Tool Reference </a>
 */
public class CarPooling {

    /**
     * @param args the command line arguments
     * @throws ArrayIndexOutOfBoundsException​ if number of cars or passengers exceeded the limit
     * @throws InvalidDataException if invalid entry occur.
     * @see Car
     * @see Passenger
     * @see Input
     * @see hardCoded
     * @see Passengers
     * @see <a href = "https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/ArrayIndexOutOfBoundsException.html" > Array Index Out Of BoundsException Reference </a>
     */
    public static void main(String[] args) throws ArrayIndexOutOfBoundsException​, InvalidDataException
    {
        try
        {
            Input input = new Input(); //Object from Class Input
        
            Car [] car = new Car[10];

            Passenger [] passenger = new Passenger[15];

            hardCoded.add(car, passenger); //Add hardcoded data to system

            int choice,routeNo;
            Passengers passengerFunc =  new PassengersImpl(); 

            OUTER_2:
            while (true)
            {
                choice = input.mainMenu(); // Mainmenu
                switch (choice) 
                {
                    case 1:
                    case 2:
                        if (Passenger.getCounter() > 14) //Case number of passengers allowed is full
                        {
                            throw new ArrayIndexOutOfBoundsException​("The system is full of passengers!"
                                    + "\nNo more passengers are allowed!");
                        }
                        if (choice == 1 ) //Case add Guest.
                        {
                            passenger[Passenger.getCounter()] = input.addGuest();
                        }
                        else if (choice == 2 ) //Case add frequent passenger.
                        {
                            passenger[Passenger.getCounter()] = input.freqPassenger();
                        }   
                        
                        //Creates reference p points to the object of this passenger
                        Passenger p = passenger[Passenger.getCounter() - 1 ]; 
                        OUTER_1:
                        while (true) 
                        {
                            choice = input.passengerFunctionalitiesMenu(p); //Passenger Functionalities Menu
                            switch (choice) {
                                case 1:
                                    while(true)
                                    {
                                        //Tests passenger subscription and whether there is number of trips reserved left
                                        if(Test.testSubscription(p) && (((Subscribers)p).getNumberOfTripsToBeReserved()==0)) //Polymorphism 
                                        {
                                            System.out.println("You have finished the number of trips reserved!"
                                                    + "\nThe Subscription Ended & You will continue as a guest.");
                                            passengerFunc = new freqPassengerImpl(); //initializing 
                                            p = ( (freqPassenger) passengerFunc ).unsubscribe(p); //unsubscribe //Polymorphism
                                        }
                                        boolean b = true; //Used in checking whether a ticket is reserved or not
                                        
                                        routeNo = searchRoute.Search(car); //search route and return route number
                                        b = passengerFunc.reserveTicket(car[routeNo-1], p); //reserves a ticket and returns true if reserved
                                        
                                        if (b == false) //ticket was not reserved
                                        {
                                            System.out.println("\tTicket was not reserved!");
                                            System.out.println("\tSelect another Car route!");
                                        }
                                        else //ticket was reserved
                                        {
                                            p.getT().display();
                                            break;
                                        }
                                    }
                                    break;

                                case 2: // Case decide to subscribe or unsubscribe
                                    if(p.getT()!=null) //No Subscribe or Un-Subscribe unless a ride was booked once.
                                    {
                                        if(Test.testSubscription(p))  //Case Subscriber and want to unSubscribe
                                        {
                                            passengerFunc = new freqPassengerImpl();
                                            p = ( (freqPassenger) passengerFunc ).unsubscribe(p); //Polymorphism
                                        }
                                        else //Case nonSubscriber and want to Subscribe
                                        {
                                            passengerFunc = new guestImpl();
                                            p = ( (guest) passengerFunc).subscribeFreq(p); //Polymorphism
                                        }
                                        passenger[Passenger.getCounter() - 1 ] = p; //refrenceing the array index to the p
                                    }
                                    else //Case No previous bookings 
                                    {
                                        System.out.println("You should book at least once before changing type!");
                                    }
                                    break;
                                case 3: //Case want to write a Report Complain/Review 
                                    if(p.getT()!=null) // assign report written on last ride to the passenger 
                                    {
                                        passenger[Passenger.getCounter() - 1 ].setReport(passengerFunc.report());
                                    } 
                                    else //Case No previous bookings 
                                    {
                                        System.out.println("You should book at least once before reporting complain or Review!");
                                    }
                                    break;
                                default: //Exit from Passenger functionality Menu without booking
                                    if(p.getT()==null)
                                    {
                                        Passenger.setCounter(Passenger.getCounter()-1);
                                        p = passenger[Passenger.getCounter() - 1 ];
                                         System.out.println("\tNo Reservation, So Login Cancelled!");
                                    }
                                    break OUTER_1;
                            }
                        }   
                        break;
                    case 3:
                        Display.passengersDisplay(passenger); //Display Passenger's Data
                        Display.carDisplay(car); //Display Car's Data
                        break;
                    case 4:
                        if (Car.getCounter() > 9) //Case System full of Cars
                        {
                            throw new ArrayIndexOutOfBoundsException​("The system is full of Cars!"
                                    + "\nNo more Cars are allowed!");
                        }
                        car[Car.getCounter()] = input.addCar(); //Adding new Car to the system
                        break;
                    case 5:
                        System.out.println("Thank you for using Car Pooling System");
                        break OUTER_2;
                    default:
                        break;
                }
            }
        } 
        catch(ArrayIndexOutOfBoundsException​ a) //Java defined exception handling Case Passengers or Cars is full.
        {
            System.out.println(a.getMessage());
        }
    }
}
/**A Class that takes input from the user.*/
class Input
{
    /** Scanner input to input data from user. */
    private final Scanner input; // A final data member
    
    /**
     * Class constructor that initializes the scanner.
     */
    Input() 
    {
        this.input = new Scanner(System.in);
    }
    
    /**
     * The Main menu of the Car pooling system. 
     * @return the choice chosen by the user.
     * @throws InputMismatchException if the input was not an integer.
     * @throws InvalidDataException if the user entered a number not in 
     * the Choices Range[1 to 5] or Entered a negative number.
     * @see <a href = "https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/InputMismatchException.html" > Input Mismatch Exception Reference </a>
     */
    final int mainMenu() throws InvalidDataException, InputMismatchException // A final method
    {
        while(true)
        {
            try
            {
                System.out.println("Welcome to Car Pooling System");
                System.out.println("Press [1] to book as a guest\n"
                        + "Press [2] to subscribe a frequent passenger\n"
                        + "Press [3] to display all data\n"
                        + "Press [4] to add a new Car\n"
                        + "Press [5] to exit");
                int choice = input.nextInt();
                
                if(choice < 0)
                {
                    throw new InvalidDataException("Your choice can not be negative!");
                }
                else if (choice < 1 || choice > 5)
                {
                    throw new InvalidDataException("Enter a valid choice!");
                }
                
                return choice;
            }
            catch(InvalidDataException in) //User defined exception handling
            {
                System.out.println(in);
            }
            catch(InputMismatchException in) //Java defined exception handling
            {
                System.out.println(in + ":\n\tPlease Re-Enter your choice correctly!");
                input.nextLine();
            }
        }
    }
    
    /**
     * The Menu of the functionalities the Passenger can do. 
     * @param p the passenger who chooses from the menu
     * @return the choice chosen by the user.
     * @throws InputMismatchException if the input was not an integer.
     * @throws InvalidDataException if the user entered a number not in 
     * the Choices Range [1 to 4] or Entered a negative number.
     * @see <a href = "https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/InputMismatchException.html" > Input Mismatch Exception Reference </a>
     */
    final int passengerFunctionalitiesMenu(Passenger p) throws InvalidDataException, InputMismatchException // A final method
    {
        while(true)
        {
            try
            {
                if(Test.testSubscription(p)) 
                {
                    System.out.println("Press [1] to search for routes and reserve a ticket in a car if available\n"
                            + "Press [2] to unsubscribe frequent passenger\n"
                            + "Press [3] to Report Complaint/Review\n"
                            + "Press [4] to exit");
                }
                else 
                {
                    System.out.println("Press [1] to search for routes and reserve a ticket in a car if available\n"
                            + "Press [2] to subscribe frequent passenger\n"
                            + "Press [3] to Report Complaint/Review\n"
                            + "Press [4] to exit");
                }
                int choice = input.nextInt();
                
                if(choice < 0)
                {
                    throw new InvalidDataException("Your choice can not be negative!");
                }
                else if (choice < 1 || choice > 4)
                {
                    throw new InvalidDataException("Enter a valid choice!");
                }
                return choice;
            }
            catch(InvalidDataException in) //User defined exception handling
            {
                System.out.println(in);
            }
            catch(InputMismatchException in) //Java defined exception handling
            {
                System.out.println(in + ":\n\tPlease Re-Enter your choice correctly!");
                input.nextLine();
            }
        }
    }
    
    /**
     * Inputs the information of the car from the user and
     * creates a car object.
     * @return an Object of the created car.
     * @throws InvalidDataException if invalid entry
     * when number of trips per day not in Range [1 to 20] 
     * and when maximum capacity in car per trip not in Range [1 to 15] 
     * and when price is over 2000
     * and when Enter a negative number.
     * @throws InputMismatchException if the entered input mismatched the required input data type. 
     * @see Car#Car(java.lang.String, int, int, java.lang.String, java.lang.String, java.lang.String, double) 
     * @see <a href = "https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/InputMismatchException.html" > Input Mismatch Exception Reference </a>
     */
    Car addCar() throws InvalidDataException, InputMismatchException
    {
        while(true)
        {
            try
            {
                String ID = generateID.createID();
                System.out.print("Enter the driver: ");
                String driverName = input.next();
                input.nextLine();
                
                System.out.print("Enter number of trips per day: ");
                int numberOfTripsPerDay = input.nextInt();
                
                if (numberOfTripsPerDay < 0)
                {
                    throw new InvalidDataException("\n\tThe number of trips can not be negative!"
                            + "\n\tPlease Re-Enter your Data & Enter a valid number of trips.");
                }
                else if (numberOfTripsPerDay < 1 || numberOfTripsPerDay >= 20 )
                {
                    throw new InvalidDataException("\n\tThe number of trips is out of Range allowed per day!"
                            + "\n\tPlease Re-Enter your Data & Enter number of trips between 1 and 20.");
                }
                
                System.out.print("Enter maximum capacity per trip: ");
                int maximumCapacityPerTrip = input.nextInt();
                
                if (maximumCapacityPerTrip < 0)
                {
                    throw new InvalidDataException("\n\tThe maximum capacity per trip can not be negative!"
                            + "\n\tPlease Re-Enter your Data & Enter a valid maximum capacity per trip.");
                }
                else if (maximumCapacityPerTrip < 1 || maximumCapacityPerTrip >= 15 )
                {
                    throw new InvalidDataException("\n\tThe maximum capacity per "
                            + "trip is out of Range allowed on our system!"
                            + "\n\tPlease Re-Enter your Data & Enter a maximum capacity between 1 and 15.");
                }
                
                System.out.print("Enter the assembly location: ");
                String assemblyLocation = input.next();
                System.out.print("Enter the destination location: ");
                String destinationLocation = input.next();
                
                System.out.print("Enter the price of this trip: ");
                double price = input.nextDouble();
                
                if (price < 0)
                {
                    throw new InvalidDataException("\n\tThe price can not be negative!"
                            + "\n\tPlease Re-Enter your Data & Enter a valid price.");
                }
                if (price > 2000)
                {
                    throw new InvalidDataException("\n\tThe price can not be over 2000!"
                            + "\n\tPlease Re-Enter your Data & Enter a valid price.");
                }
                
                Car c = new Car(String.valueOf(generateID.getIdCounter()),
                        numberOfTripsPerDay,maximumCapacityPerTrip, driverName, 
                        assemblyLocation, destinationLocation, price);
                
                return c;
            }
            catch(InvalidDataException in) //User defined exception handling
            {
                System.out.println(in);
            }
            catch(InputMismatchException in) //Java defined exception handling
            {
                System.out.println(in + ":\n\tPlease Re-Enter your data correctly!");
                input.nextLine();
            }
        }
    }
    
    /**
     * Inputs the name of the guest passenger and
     * creates an object of the guest passenger.
     * Called when creating a new guest passenger.
     * @return an object of the guest passenger.
     * @see nonSubscribers#nonSubscribers(java.lang.String) 
     */
    Passenger addGuest() // An Overloaded method
    {
        System.out.print("Enter your name: ");
        String name = input.next();
        input.nextLine();
        Passenger p = new nonSubscribers(name);
        return p;
    }
    
    /**
     * An object of the guest passenger is created.
     * Called when a frequent passenger wants to become a guest.
     * @param p the passenger that will be a guest.
     * @return an object of the guest passenger.
     * @see nonSubscribers#nonSubscribers(java.lang.String, carpooling_package.Car, carpooling_package.ticket) 
     */
    Passenger addGuest(Passenger p) //An Overloaded method
    {
        return new nonSubscribers(p.getName(),p.getC(),p.getT());
    }
    
    /**
     * Inputs the name, age and number of trips to be reserved of the frequent passenger and
     * creates an object of the frequent passenger
     * if age and number of trips are suitable while
     * makes the passenger continue in the system as a guest if not.
     * Called when creating a new frequent passenger.
     * @return an object of the frequent passenger if data was suitable to subscription while
     * an object of guest passenger if not.
     * @throws InvalidDataException if age is out of range which is [10 to 90] allowed on system
     * or number of trips is over 180 or enter a negative number.
     * @throws InputMismatchException if data entered was not not in the proper format.
     * @see nonSubscribers#nonSubscribers(java.lang.String) 
     * @see Subscribers#Subscribers(java.lang.String, int, int) 
     * @see <a href = "https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/InputMismatchException.html" > Input Mismatch Exception Reference </a>
     */
    Passenger freqPassenger() throws InvalidDataException, InputMismatchException // An Overloaded method
    {
        while(true)
        {
            try
            {
                System.out.print("Enter your name: ");
                String name = input.next();
                input.nextLine();
                
                System.out.print("Enter your age: ");
                int age = input.nextInt();
                
                if (age < 0)
                {
                    throw new InvalidDataException("\n\tThe age can not be negative!"
                            + "\n\tPlease Re-Enter your Data & Enter a valid age.");
                }
                if (age < 10 || age >= 90)
                {
                    throw new InvalidDataException("\n\tYour age is out of Range allowed on our system!"
                            + "\n\tPlease Re-Enter your Data & Enter a valid age between 10 and 90.");
                }

                System.out.print("Enter your number of trips to be reserved: ");
                int numberoftripstobereserved = input.nextInt();
                
                if (numberoftripstobereserved < 0)
                {
                    throw new InvalidDataException("\n\tThe number of trips to be reserved can not be negative!"
                            + "\n\tPlease Re-Enter your Data & Enter a valid number of trips.");
                }
                if (numberoftripstobereserved >= 180)
                {
                    throw new InvalidDataException("\n\tThe number of trips to be reserved is out of Range allowed on our system!"
                            + "\n\tPlease Re-Enter your Data & Enter a valid number of trips that is less than 180 ");
                }

                Passenger p;
                
                if (age < 20 && numberoftripstobereserved < 10)
                {
                    System.out.println("You can not subscribe as your age is below 20 "
                            + "& number of trips to be reserved is below 10!"
                            + "\nYou will continue as a guest!");
                    p = new nonSubscribers(name);
                }
                else if (age < 20 && numberoftripstobereserved >= 10)
                {
                    System.out.println("Your age is below 20 so you can not subscribe!\nYou will continue as a guest!");
                    p = new nonSubscribers(name);
                }
                else if (age >= 20 && numberoftripstobereserved < 10)
                {
                    System.out.println("The number of trips to be reserved is below 10 "
                            + "so you can not subscribe!\n"
                            + "You will continue as a guest!");
                    p = new nonSubscribers(name);
                }
                else
                {
                    p = new Subscribers(name, age, numberoftripstobereserved);
                } 
                
                return p;
            }
            catch(InvalidDataException in) //User defined exception handling
            {
                System.out.println(in);
            }
            catch(InputMismatchException in) //Java defined exception handling
            {
                System.out.println(in + ":\n\tPlease Re-Enter your data correctly!");
                input.nextLine();
            }
        }
    }
    
    /**
     * Inputs the age and number of trips to be reserved of the frequent passenger and
     * creates an object of the frequent passenger
     * if age and number of trips are suitable while
     * makes the passenger continue in the system as a guest if not.
     * Called when a guest wants to become frequent passenger.
     * @param p The passenger that wants to become a frequent passenger.
     * @return an object of the frequent passenger if data was suitable to subscription while
     * an object of guest passenger if not.
     * @throws InvalidDataException if age is out of range which is [10 to 90] allowed on system
     * or number of trips is over 180 or enter a negative number.
     * @throws InputMismatchException if data entered was not not in the proper format.
     * @see Subscribers#Subscribers(java.lang.String, int, int, carpooling_package.Car, carpooling_package.ticket) 
     * @see <a href = "https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/InputMismatchException.html" > Input Mismatch Exception Reference </a>
     */
    Passenger freqPassenger(Passenger p) throws InputMismatchException// An Overloaded method
    {
        while(true)
        {
            try
            {
                System.out.print("Enter your age: ");
                int age = input.nextInt();
                
                if (age < 0)
                {
                    throw new InvalidDataException("\n\tThe age can not be negative!"
                            + "\n\tPlease Re-Enter your Data & Enter a valid age.");
                }
                if (age < 10 || age >= 90)
                {
                    throw new InvalidDataException("\n\tYour age is out of Range allowed on our system!"
                            + "\n\tPlease Re-Enter your Data & Enter a valid age between 10 and 90.");
                }

                System.out.print("Enter your number of trips to be reserved: ");
                int numberoftripstobereserved = input.nextInt();
                
                if (numberoftripstobereserved < 0)
                {
                    throw new InvalidDataException("\n\tThe number of trips to be reserved can not be negative!"
                            + "\n\tPlease Re-Enter your Data & Enter a valid number of trips.");
                }
                if (numberoftripstobereserved >= 180)
                {
                    throw new InvalidDataException("\n\tThe number of trips to be reserved is out of Range allowed on our system!"
                            + "\n\tPlease Re-Enter your Data & Enter a valid number of trips that is less than 180 ");
                }
                
                if (age < 20 && numberoftripstobereserved < 10)
                {
                    System.out.println("You can not subscribe as your age is below 20 "
                            + "& number of trips to be reserved is below 10!"
                            + "\nYou will continue as a guest!");
                    return p;
                }
                else if (age < 20 && numberoftripstobereserved >= 10)
                {
                    System.out.println("Your age is below 20 so you can not subscribe!\nYou will continue as a guest!");
                    return p;
                }
                else if (age >= 20 && numberoftripstobereserved < 10)
                {
                    System.out.println("The number of trips to be reserved is below 10 "
                            + "so you can not subscribe!\n"
                            + "You will continue as a guest!");
                    return p;
                }
                else 
                {
                    return new Subscribers(p.getName(), age, numberoftripstobereserved,p.getC(),p.getT());
                }
                
            }
            catch(InvalidDataException in) //User defined exception handling
            {
                System.out.println(in);
            }
            catch(InputMismatchException in) //Java defined exception handling
            {
                System.out.println(in + ":\n\tPlease Re-Enter your data correctly!");
                input.nextLine();
            }
        }
    }
}

/**
 * An interface of the Functionalities that the passengers can do.
 */
interface Passengers //An interface
{
    /**
     * Allows user to search different routes.
     * @param c An array of the cars to choose the route.
     * @return Route number of the desired route.
     */
    int searchRoutes(Car[] c);
    
    /**
     * Reserves a ticket within the Car chosen.
     * @param c the car of the route chosen.
     * @param p the passenger who reserved this ticket.
     * @return boolean true if ticket successfully reserved while false if not.
     */
    boolean reserveTicket(Car c, Passenger p);
    
    /**
     * Report Complain or Review by the passenger on the trip.
     * @return a String of the report written.
     */
    String report();
    
}

/**
 * An interface that has the functionality allowed to frequent passengers only 
 * and extends the common functionalities of all passengers
 * to apply Interface segregation principle.
 */
interface freqPassenger extends Passengers // An inherited interface from Passengers interface to allow apply interface segregation principle
{
    /**
     * Allows Subscribers passengers to un-subscribe and become guest passengers.
     * @param p The passenger who wants to un-subscribe.
     * @return passenger that un-subscribed and became a guest passenger.
     */
    Passenger unsubscribe(Passenger p);
}

/**
 * An interface that has the functionality allowed to guest passengers only 
 * and extends the common functionalities of all passengers
 * to apply Interface segregation principle.
 */
interface guest extends Passengers //An inherited interface from Passenger interface to allow apply interface segregation principle
{
    /**
     * Allows Guest passengers to subscribe and become Subscribers passengers.
     * @param p The passenger who wants to subscribe.
     * @return passenger that subscribed and became a Subscriber passenger.
     */
    Passenger subscribeFreq(Passenger p);
}

/**
 * A Class that implements the Passengers functionalities interface 
 * to apply Single responsibility principle
 * @see Passengers
 */
class PassengersImpl implements Passengers // A calss implements Passengers interface to allow apply interface segregation principle and SRP
{
    /**
     * Allows user to search different routes.
     * @param car An array of the cars to choose the route.
     * @return Route number of the desired route.
     * @see searchRoute#Search(carpooling_package.Car[]) 
     * {@inheritDoc }
     */
    @Override
    public int searchRoutes(Car[] car)  //An overridden method
    {
        return searchRoute.Search(car);
    }
    
    /**
     * Reserves a ticket within the Car chosen.
     * @param c the car of the route chosen.
     * @param p the passenger who reserved this ticket.
     * @return boolean true if ticket successfully reserved while false if not.
     * @throws Exception if no place available in this trip.
     * @see Calculatable#calculateTripPrice(double) 
     * @see Test
     * @see ticket
     * {@inheritDoc }
     */
    @Override
    public boolean reserveTicket(Car c, Passenger p)  //An overridden method
    {
        Calculatable cal = new CalculatableImpl(); 
        while(true)
        {
            try
            {
                if (Test.testTripAvailability(c))
                {
                    UpdateData.update(c, p);
                    ticket t = ticket.getMytick();
                    t.setCarCode(c.getUniqueCode());
                    
                    if (Test.testSubscription(p))
                    {
                        t.setTripPrice(cal.calculateTripPrice(c.getUniqueRoute().getTripPrice()));
                    }
                    else 
                    {
                        t.setTripPrice(c.getUniqueRoute().getTripPrice());
                    }
                    
                    p.setT(t);
                    return true;
                }
                else 
                {
                    throw new Exception("There is no place available in this trip!");    
                }
            }
            catch (Exception e) //Java defined exception handling
            {
                System.out.println("\n"+ e +"\n");
                return false;
            }
        }
      
    }
    
    /**
     * Report Complain or Review by the passenger on the trip.
     * @return a String of the report written.
     * @see <a href = "https://stackoverflow.com/questions/39589285/java-how-to-ignore-any-input-after-white-space" > Ignore input after whitespace Reference </a>
     * {@inheritDoc}
     */
    @Override
    public String report() //Overridden method
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Type your Complaint or Review (Press enter to submit)");
        String s = input.nextLine();
        
        return s;
    }
}

/**
 * A Class that implements the Frequent Passengers functionalities interface only
 * to apply interface segregation and Single responsibility principle to passengers
 * and extends the Common Functionalities of passengers.
 * @see freqPassenger
 */
class freqPassengerImpl extends PassengersImpl implements freqPassenger //An inherited class from PassengersImpl
{
    /** Object of class Input to allow to call addGuest method. */
    private final Input input = new Input(); //final data member
    
    /**
     * Allows Subscribers passengers to un-subscribe and become guest passengers.
     * @param p The passenger who wants to un-subscribe.
     * @return passenger that un-subscribed and became a guest passenger.
     * @see Input#addGuest(carpooling_package.Passenger)
     */
    @Override
    public Passenger unsubscribe(Passenger p) //Overridden method
    {
        return input.addGuest(p);
    }
    
}

/**
 * A Class that implements the Guest Passengers functionalities interface only
 * to apply interface segregation and Single responsibility principle to passengers
 * and extends the Common Functionalities of passengers.
 * @see guest
 */
class guestImpl extends PassengersImpl implements guest //An inherited class from PassengersImpl and implements guest interface to allow apply interface segregation principle
{
    /** Object of class Input to allow to call addGuest method. */
    private final Input input = new Input(); // final data member
    
    /**
     * Allows non-Subscribers passengers to subscribe and become Subscribers.
     * @param p The passenger who wants to subscribe.
     * @return passenger that subscribed and became a Subscriber.
     * @throws InvalidDataException if invalid entry occured.
     * @see Input#freqPassenger(carpooling_package.Passenger) 
     * {@inheritDoc }
     */
    @Override
    public Passenger subscribeFreq(Passenger p)  //An overridden method
    {
        return input.freqPassenger(p);
    }
}

/**
 * An interface of the methods that are used in calculations.
 */
interface Calculatable // An interface
{
    /**
     * Calculates the trip price discount.
     * @param price price that the trip discount will apply on.
     * @return the price after applying discount.
     */
    double calculateTripPrice(double price); //Catculating method
}

/**
 * A class that implements the Calculatable interface methods.
 * @see Calculatable
 */
class CalculatableImpl implements Calculatable //A class implelments Calculatable to apply Single responsability principle
{
    /**
     * Calculates the trip price discount.
     * @param price price that the trip discount will apply on.
     * @return the price after applying discount.
     * {@inheritDoc }
     */
    @Override
    public double calculateTripPrice(double price) //Calculate method
    {
        return price * 0.5;
    }  
}

/**
 * An interface of the Display method.
 */
interface Displayable // An interface
{
    /**
     * A method used to display data.
     */
    void display();
}

/**
 * A class representing Route Data only to apply Single Responsibility Principle.
 * @see Displayable
 */
class Route implements Displayable 
{
    /**
     * The assembly Location of the Car route.
     */
    private final String assemblyLocation; //final data member
    
    /**
     * The Destination Location of the Car route.
     */
    private final String destinationLocation; //final data member
    
    /**
     * The trip price of the route.
     */
    private final double tripPrice; //final data member
    
    /**
     * Gets the assembly Location of the Car route.
     * @return Assembly Location
     */
    public String getAssemblyLocation() 
    {
        return assemblyLocation;
    }
    
    /**
     * Gets the Destination Location of the Car route.
     * @return Destination Location
     */
    public String getDestinationLocation() 
    {
        return destinationLocation;
    }
    
    /**
     * Gets the trip price of the route.
     * @return Trip price
     */
    public double getTripPrice() 
    {
        return tripPrice;
    }
    
    /**
     * Class constructor initializes the data members with the arguments.
     * @param assemblyLocation Assembly Location of the Car route.
     * @param destinationLocation Destination Location of the Car route.
     * @param tripPrice Trip price of the route.
     */
    Route(String assemblyLocation, String destinationLocation, double tripPrice)
    {
        this.assemblyLocation = assemblyLocation;
        this.destinationLocation = destinationLocation;
        this.tripPrice = tripPrice;
    }
    
    /**
     * Displays the Route Assembly Location and Destination Location.
     * {@inheritDoc }
     */
    @Override
    public void display() //An overridden method
    {
       System.out.println(assemblyLocation + "  \t\t\t  " + destinationLocation);  
    }
    
}
/**
 * A class representing Car's Data only to apply Single Responsibility Principle.
 * @see Displayable
 */
class Car implements Displayable
{
    /** Car's unique code. */
    private final String uniqueCode; //final data member
    
    /** Car's number of trips per day. */
    private final int numberOfTripsPerDay; //final data member
    
    /** Car's unique Route. */
    private final Route uniqueRoute; //final data member
    
    /** The maximum capacity of car per trip. */
    private final int maximumCapacityPerTrip; //final data member
    
    /** Car's Driver name. */
    private final String driverName; //final data member
    
    /** Number of Cars counter. */
    private static int counter = 0; //Static data member
    
    /** Car's current capacity. */
    private int currentCapacity;
    
    /**
     * Gets Car's current capacity.
     * @return current capacity of car.
     */
    public int getCurrentCapacity() 
    {
        return currentCapacity;
    }
    
    /**
     * Sets Current capacity with the argument
     * @param currentCapacity the new current capacity value.
     */
    public void setCurrentCapacity(int currentCapacity) 
    {
        this.currentCapacity = currentCapacity;
    }
    
    /**
     * Gets Car's unique route.
     * @return unique route of car.
     */
    public Route getUniqueRoute() 
    {
        return uniqueRoute;
    }
    
    /**
     * Gets Car's unique code.
     * @return unique code of car.
     */
    public String getUniqueCode() 
    {
        return uniqueCode;
    }
    
    /**
     * Gets number of trips per day the car do.
     * @return number of trips per day.
     */
    public int getNumberOfTripsPerDay() 
    {
        return numberOfTripsPerDay;
    }
    
    /**
     * Gets the maximum capacity of seats in the car per trip.
     * @return maximum capacity per trip.
     */
    public int getMaximumCapacityPerTrip() 
    {
        return maximumCapacityPerTrip;
    }
    /**
     * Gets the number of Cars in the system.
     * @return counter of the number of cars.
     */
    public static int getCounter() 
    {
        return counter;
    }
    
    /**
     * Constructor that sets the Data members with the arguments and increments the number of cars by 1
     * @param uniqueCode The car's unique code.
     * @param numberOfTripsPerDay The car's number of trips per day.
     * @param maximumCapacityPerTrip The maximum capacity of car per trip.
     * @param driverName Driver's name.
     * @param assembly Route Assembly Location.
     * @param destination Route Destination Location.
     * @param Price Price of the trip.
     */
    Car(String uniqueCode, int numberOfTripsPerDay, int maximumCapacityPerTrip,
            String driverName, String assembly, String destination, double Price)
    {
        this.currentCapacity = 0;
        this.uniqueCode = uniqueCode;
        this.numberOfTripsPerDay = numberOfTripsPerDay;
        this.maximumCapacityPerTrip = maximumCapacityPerTrip;
        this.driverName = driverName;
        uniqueRoute = new Route(assembly,destination,Price);
        counter++;
    }
    
    /**
     * Displays Car Data.
     * {@inheritDoc}
     */
    @Override
    public void display() //An overridden method
    {
        System.out.println("Driver name: " + driverName);
        System.out.println("Car code: " + uniqueCode);
        System.out.println("Number of trips per day: " + numberOfTripsPerDay);
        System.out.println("Maximum capacity per trip: " + maximumCapacityPerTrip);
        System.out.println("Assembly Location: " + uniqueRoute.getAssemblyLocation());
        System.out.println("Destination Location: " + uniqueRoute.getDestinationLocation());
        System.out.println("Trip price: " + uniqueRoute.getTripPrice());
    }

}

/**
 * An abstract class representing two types of passengers Data only to apply Single Responsibility Principle.
 * @see Displayable
 */
abstract class Passenger implements Displayable
{
    /** The Passenger's name. */
    private final String name; //final data member
    
    /** The number of passengers. */
    private static int counter = 0; //static data member
    
    /** The Passenger's reservation ticket. */
    private ticket t;
    
    /** The car rode by the passenger. */
    private Car c;
    
    /** The Report given by the passenger on the car he rode. */
    private String report;
      
    /** 
     * Class constructor that sets name of passenger to the argument
     * and increment passengers counter by 1.
     * @param name Name of the passenger.
     */
    Passenger(String name) // An Overloaded constructor
    {
        this.name = name;
        counter++; 
    }
    
    /** 
     * Class constructor that sets name of passenger
     * and its Car
     * and his reserve ticket to the arguments.
     * @param name Name of the passenger.
     * @param c The Car the passenger rode.
     * @param t The ticket of his reservation.
     */
    Passenger(String name, Car c, ticket t) // An Overloaded constructor
    {
        this.name = name;
        this.c = c;
        this.t = t;
    }
    
    /** 
     * Gets the passenger's ticket.
     * @return ticket of the passenger.
     */
    public ticket getT() 
    {
        return t;
    }
    
    /** 
     * Gets the car rode by the passenger.
     * @return Car rode by the passenger.
     */
    public Car getC() 
    {
        return c;
    }
    
    /** 
     * Gets the passenger's name.
     * @return name of passenger.
     */
    public String getName() 
    {
        return name;
    }
    
    /** 
     * Gets the number of passengers.
     * @return number of passengers.
     */
    public static int getCounter() //static method
    {
        return counter;
    }
    
    /** 
     * Sets number of passengers to the argument.
     * @param counter The number that the number of passengers that will be set to.
     */
    public static void setCounter(int counter) 
    {
        Passenger.counter = counter;
    }
    
    /** 
     * Sets passenger's ticket to the argument.
     * @param t Ticket that passenger's ticket will be set to.
     */
    public void setT(ticket t) 
    {
        this.t = t;
    }
    
    /** 
     * Sets the car rode by the passenger to the arguments.
     * @param c Car rode by the passenger.
     */
    public void setC(Car c) 
    {
        this.c = c;
    }
    
    /** 
     * Gets the report the passenger wrote.
     * @return a string of the report the passenger wrote.
     */
    public String getReport() 
    {
        return report;
    }
    
    /** 
     * Sets the report the passenger wrote to the arguments.
     * @param report Report written by the passenger.
     */
    public void setReport(String report) 
    {
        this.report = report;
    }
    
    /** 
     * Displays the Passenger's Data.
     * @see Car#display() 
     * {@inheritDoc} 
     */
  @Override
    public void display() //An overridden method
    {
        System.out.println("Name: " + name);
        System.out.println("The Details of the Car this Passenger rode: ");
        c.display();
        if(report != null)
        {
            System.out.println("Report written on this trip:" + report);
        }
        System.out.println();
    }
  
}

/**
 * A class representing nonSubscribers passengers Data only to apply Single Responsibility Principle.
 */
class nonSubscribers extends Passenger //inherited class from Passenger
{
    /** 
     * Class constructor that calls the super Constructor.
     * It is called when a new non subscriber is initialized.
     * @see Passenger#Passenger(java.lang.String) 
     * @param name Name of the passenger.
     */
    nonSubscribers(String name) // An Overloaded constructor
    {
        super(name);
    }
    
    /** 
     * Class constructor that calls the super Constructor.
     * It is called when a Subscriber decides to un-subscribe and be a non subscriber.
     * @param name Name of the passenger.
     * @param c The Car the passenger rode.
     * @param t The ticket of his reservation.
     * @see Passenger#Passenger(java.lang.String, carpooling_package.Car, carpooling_package.ticket) 
     */
    nonSubscribers(String name, Car c,ticket t) // An Overloaded constructor
    {
        super(name,c,t);
    }

}

/**
 * A class representing Subscribers passengers Data only to apply Single Responsibility Principle.
 */
class Subscribers extends Passenger //inherited class from Passenger
{
    /** Age of the passenger. */
    private final int age; //final data member
    
    /** The number of trips to be reserved by the passenger. */
    private final int numberOfTripsToBeReserved; //final data member
    
    /**T he number of trips left to the passenger. */
    private int numberOfTripsLeft; //Calculated data member
    
    /** 
     * Class constructor that calls the super Constructor setting its name from arguments
     * and sets age and number of trips to be reserved with the arguments.
     * It is called when a new subscriber is initialized.
     * @param name Name of the passenger.
     * @param age The passenger's age.
     * @param numberOfTripsToBeReserved The number of trips to be reserved by the passenger.
     * @see Passenger#Passenger(java.lang.String)  
     */
    Subscribers(String name, int age, int numberOfTripsToBeReserved) // An Overloaded constructor
    {
        super(name);
        this.age = age;
        this.numberOfTripsToBeReserved = numberOfTripsToBeReserved;
    }
    
    /** 
     * Class constructor that calls the super Constructor setting its name, car, ticket from arguments
     * and sets age and number of trips to be reserved with the arguments.
     * It is called when a nonSubscriber decides to subscribe and be a subscriber.
     * @param name Name of the passenger.
     * @param age The passenger's age.
     * @param numberOfTripsToBeReserved The number of trips to be reserved by the passenger.
     * @param c Car rode by passenger
     * @param t ticket reserved for passenger
     * @see Passenger#Passenger(java.lang.String, carpooling_package.Car, carpooling_package.ticket) 
     */
    Subscribers(String name, int age, int numberOfTripsToBeReserved,Car c,ticket t) // An Overloaded constructor
    {
        super(name,c,t);
        this.age = age;
        this.numberOfTripsToBeReserved = numberOfTripsToBeReserved;
    }
    
    /** 
     * Gets the number of trips to be reserved by the passenger.
     * @return number of trips to be reserved by the passenger.
     */
    public int getNumberOfTripsToBeReserved() 
    {
        return numberOfTripsToBeReserved;
    }
    
    /** 
     * Sets the number of trips to be left for the passenger with the arguments.
     * @param numberOfTripsleft number of trips to be left for the passenger.
     */
    public void setNumberOfTripsLeft(int numberOfTripsleft) 
    {
        this.numberOfTripsLeft = numberOfTripsleft;
    }
    
    /** 
     * @see Passenger#display() 
     * {@inheritDoc} 
     */
    @Override
    public void display() //Overridden method
    {
        super.display();
        System.out.println("Price paid: "+ super.getT().getTripPrice());
        System.out.println("Age of passenger: "+  age);
        System.out.println("Number of trips reserved: " + numberOfTripsToBeReserved);
        System.out.println("Number of trips Left: " + numberOfTripsLeft);
    }
    
}

/**
 * A class used for testing.
 */
class Test
{
    /**
     * Tests subscription of the passenger.
     * @param p Passenger to be tested if subscriber or not.
     * @return boolean indicates subscription, returns true if subscriber and false if not.
     */
    static final boolean testSubscription(Passenger p) //static final method
    {
        return ( p instanceof Subscribers );
    }
    
    /**
     * Tests availability in car.
     * @param car Car to be tested if a free seat exists or not.
     * @return boolean indicates presence of seats in car, returns true if a free seat exists and false if not.
     */
    static final boolean testTripAvailability(Car car) //static final method
    {
        return (car.getCurrentCapacity() < car.getMaximumCapacityPerTrip());
    }
    
}

/**
 * A class used for updating Car's and Passenger's data.
 */
class UpdateData
{
    /**
     * Updates the Car's and Passenger's data.
     * @param car car to be updated.
     * @param passenger passenger to be updated.
     * @see Test#testSubscription(carpooling_package.Passenger) 
     * @see Car#setCurrentCapacity(int) 
     * @see Car#getCurrentCapacity() 
     * @see Subscribers#getNumberOfTripsToBeReserved() 
     * @see Subscribers#setNumberOfTripsLeft(int) 
     * @see Passenger#setC(carpooling_package.Car) 
     */
    static final void update(Car car, Passenger passenger) //static final method
    {
        car.setCurrentCapacity(car.getCurrentCapacity()+1);
        passenger.setC(car);
        if(Test.testSubscription(passenger)) 
        {
            //polymorphism
            ((Subscribers)passenger).setNumberOfTripsLeft(((Subscribers)passenger).getNumberOfTripsToBeReserved()-1);
        }
    }
    
}

/**
 * A Class that allows user to search for available routes.
 */
class searchRoute
{
    /** Scanner input to input data from user. */
    private static final Scanner input = new Scanner(System.in);
    
    /**
     * Searches whether by Route Number or writing route searching for.
     * @param car Array of cars to search for the routes available.
     * @return Route Number of route wanted.
     * @throws InputMismatchException if string is entered instead of integer.
     * @throws InvalidDataException if entered an invalid choice which is not [1] or [2].
     * @see searchRoute#searchByRouteName(carpooling_package.Car[]) 
     * @see searchRoute#searchByRouteNumber(carpooling_package.Car[]) 
     * @see <a href = "https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/InputMismatchException.html" > Input Mismatch Exception Reference </a>
     */
    static int Search(Car[] car) // static method
    {
        while(true)
        {
            try
            {
                System.out.println("Press [1] to write the Route you are searching for."
                        + "\nPress [2] to Display Routes available and choose the Route number you are searching for.");
                int choice = input.nextInt();
                int routeNo = 0;
                
                if (choice != 1 && choice != 2)
                {
                    throw new InvalidDataException("\n\tPlease Enter [1] or [2].");
                }
                
                if (choice == 1)
                {
                    routeNo = searchByRouteName(car);
                }
                else if (choice == 2)
                {
                    routeNo = searchByRouteNumber(car);
                }
                
                return routeNo;
            }
            catch(InvalidDataException in) //User defined Exception handling
            {
                System.out.println(in);
            }
            catch(InputMismatchException in) //Java defined Exception handling
            {
                System.out.println(in + ":\n\tPlease Re-Enter your data correctly!");
                input.nextLine();
            }
        }
    }
    
    /**
     * Searches for a Route by route name and if not found call search by Route number method.
     * @param car array of cars to search for routes.
     * @return route number of the desired route.
     * @throws InvalidDataException if invalid entry occurred.
     * @see searchRoute#searchByRouteNumber(carpooling_package.Car[]) 
     * @see Car
     */
    private static int searchByRouteName(Car[] car) throws InvalidDataException //private static method
    {
        System.out.println("Enter the Assembly Location and Destination Location in this format [Assembly-Destination]");
        String Loc = input.next();
        input.nextLine();
        
        int routeNo = 0;
        boolean found = false;
        for(int i = 0 ;i < Car.getCounter();i++)
        {
            routeNo++;
            if(Loc.startsWith(car[i].getUniqueRoute().getAssemblyLocation()) && Loc.endsWith(car[i].getUniqueRoute().getDestinationLocation()))
            {
                found = true;
                break;
            }
        }
        if (found)
        {
            return routeNo;
        }
        else
        {
            System.out.println("The Route you searched for can not be found! "
                    + "\nRoutes available will be displayed, "
                    + "choose the number of route you are searching for!");
            input.nextLine();
            return searchByRouteNumber(car);
        }
    }
    
    /**
     * Displays all Routes available and Searches for a Route by route number.
     * @param car array of cars to search for routes.
     * @return route number of the desired route.
     * @throws InputMismatchException if string is entered instead of integer.
     * @throws NullPointerException if try to access a route number out of range available.
     * @throws InvalidDataException if entered a negative route number or number 0.
     * @see Car
     * @see <a href = "https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/InputMismatchException.html" > Input Mismatch Exception Reference </a>
     * @see <a href = "https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/NullPointerException.html" > Null Pointer Exception Reference </a>
     */
    private static int searchByRouteNumber(Car[] car) throws InvalidDataException, InputMismatchException, NullPointerException //private static method
    {
        while(true)
        {
            try
            {
                
                System.out.println("\n---------------------------------------------------------------------\n");
                System.out.println("Routes Available:\n");
                System.out.println("Route number\t\tAssembly Location\t\tDestination Location");
                System.out.println("------------\t\t-----------------\t\t--------------------");

                for(int i = 0 ;i < Car.getCounter();i++)
                {
                    System.out.print("     " + (i+1) + "   \t\t  ");
                    car[i].getUniqueRoute().display();
                }
                System.out.println("\n---------------------------------------------------------------------\n");

                System.out.println("Enter the number of Route you are searching for: ");
                int routeNo = input.nextInt();
                if (routeNo < 0)
                {
                    throw new InvalidDataException("\n\tThe route number can not be negative!"
                            + "\n\tPlease Re-Enter your Data & Enter a valid route number.");
                }
                if (routeNo == 0)
                {
                    throw new InvalidDataException("\n\tThe Route number you entered does not exist!"
                        + "\n\tPlease enter a valid Route number within the range.");
                }
                System.out.println ("You have chosen Route no. : " + routeNo
                        +  "\nAssembly Location: "+ car[routeNo-1].getUniqueRoute().getAssemblyLocation()
                        + "\nDestination Location: " + car[routeNo-1].getUniqueRoute().getDestinationLocation());

                return routeNo;
            } 
            catch(InputMismatchException in) //Java defined Exception handling
            {
                System.out.println(in + ":\n\tPlease Re-Enter your data correctly!");
                input.nextLine();
            }
            catch (NullPointerException in) //Java defined Exception handling
            {
                System.out.println(in + ":\n\tThe Route number you entered does not exist!"
                        + "\n\tPlease enter a valid Route number within the range.");
                input.nextLine();
            }
            catch(InvalidDataException in) //User defined Exception handling
            {
                System.out.println(in);
            }
        }
    }
    
}

/**
 * Ticket Class that has ticket data only to apply Single Responsibility Principle.
 * @see Displayable
 */
class ticket implements Displayable
{
    /** The Car code of the reserved car. */
    private String carCode;
    
    /** The tripPrice for the reserved car. */
    private double tripPrice; //Calculated data member
    
    /** Private Constructor to implement a Singleton Pattern. */
    private static final ticket mytick = new ticket(); //final static data member
    
    /**
     * Gets the ticket object created from the private constructor.
     * @return the ticket object created from the private constructor.
     */
    public final static ticket getMytick() //final static method 
    {
        return mytick;
    }
    
    /**
     * Gets the price of the trip.
     * @return the Trip Price.
     */
    public double getTripPrice() 
    {
        return tripPrice;
    }
    
    /**
     * Sets the Car Code in the ticket with the arguments.
     * @param carCode the Code of the reserved car.
     */
    public void setCarCode(String carCode) 
    {
        this.carCode = carCode;
    }
    
    /**
     * Sets the Car Price in the ticket with the arguments.
     * @param tripPrice the Price of the reserved car.
     */
    public void setTripPrice(double tripPrice) 
    {
        this.tripPrice = tripPrice;
    }
    
    /**
     * Displays ticket Data.
     * {@inheritDoc}
     */
    @Override
    public void display() //Overridden method
    {
        System.out.println("\n-----------------------------------------------\n");
        System.out.println("Ticket successfully reserved!\n");
        System.out.println("Reserved ticket:");
        System.out.println("Car code: " + carCode + "\nPrice of trip: " + tripPrice);
        System.out.println("\n-----------------------------------------------\n");
    }

}

/**
 * Class that Displays All data in the system (Cars and Passengers).
 */
class Display 
{
    /**
     * Displays All Cars in the CarPooling System.
     * @param car array of cars to be displayed.
     * @see Car
     */
   static void carDisplay(Car[] car) //A static method
    {
       System.out.println("\n--------------------------------------------------------\n");
       System.out.println("\t\t\tCars");
        for(int i = 0; i < Car.getCounter(); i++) 
        {
            System.out.printf("\nCar %d :\n",i+1);
            car[i].display();
        }
        System.out.println("\n--------------------------------------------------------\n");
    }
    
    /**
     * Displays All Passengers in the CarPooling System.
     * @param passenger array of passengers to be displayed.
     * @see Passenger
     * @see Test#testSubscription(carpooling_package.Passenger) 
     */
    static void passengersDisplay(Passenger[] passenger) //A static method
    {
        System.out.println("\n--------------------------------------------------------\n");
        System.out.println("\t\t\tPassengers");
       
        for (int i = 0; i < Passenger.getCounter();i++)
        {
            System.out.printf("\nPassenger %d :\n",i+1);
            
            if(Test.testSubscription(passenger[i])) 
            {
                System.out.println("This Passenger is currently a Subscriber [Frequent Passenger]");
            } 
            else 
            {
                System.out.println("This Passenger is currently a nonSubscriber [Guest]");
            }
            
            passenger[i].display();      
        }
        
        System.out.println("\n--------------------------------------------------------\n");
    }
    
}

/**
 * A class that inputs Cars and Passengers hard-coded.
 */
class hardCoded
{
    /**
     * An object from class PassengersImpl to access the functionalities of the passenger.
     * @see PassengersImpl
     */
    private final static Passengers p = new PassengersImpl(); //A final static data member
    
    /**
     * Adds Cars and passengers HardCoded.
     * @param car array of cars to add to it new car.
     * @param passenger array of passengers to add new passenger to it.
     * @see PassengersImpl
     * @see Car
     * @see Passenger
     */
    static final void add(Car[] car, Passenger[] passenger) // A final static method
    {
        car[Car.getCounter()] = new Car(generateID.createID(),10,4,"Ahmed","Helioplis","NasrCity",10);
        passenger[Passenger.getCounter()] = new nonSubscribers("Hossam");
        p.reserveTicket(car[Car.getCounter()-1],passenger[Passenger.getCounter()-1]);
        
        car[Car.getCounter()] = new Car(generateID.createID(),12,6,"Ali","NasrCity","Helioplis",10);
        
        car[Car.getCounter()] = new Car(generateID.createID(),5,15,"Mohamed","Rehab","Madinaty",15);
        passenger[Passenger.getCounter()] = new Subscribers("Mona", 40, 20);
        p.reserveTicket(car[Car.getCounter()-1],passenger[Passenger.getCounter()-1]);
        
        car[Car.getCounter()] = new Car(generateID.createID(),5,20,"Hazem","Helioplis","Madinaty",30);
        car[Car.getCounter()] = new Car(generateID.createID(),5,20,"Adel","Madinaty","Helioplis",30);
        car[Car.getCounter()] = new Car(generateID.createID(),6,25,"Shady","Helioplis","Rehab",30);
        car[Car.getCounter()] = new Car(generateID.createID(),6,25,"Ali","Rehab","Helioplis",30);
        
    }
}

/**
 * An abstract class to generate a unique code for each Car.
 */
abstract class generateID // An abstract class
{
    /** The counter used to generate the unique code. */
    private static int idCounter = 100; //A static private variable
    
    /**
     * Gets the counter thar refers to the unique code.
     * @return The Counter which refers to the car unique code. 
     */
    public final static int getIdCounter() //A static final method
    {
        return idCounter;
    }
    
    /**
     * Increments the counter which refers to the unique car code.
     * @return The Counter which refers to the car unique code in string format. 
     */
    public final static String createID() //A static final method
    {
        idCounter++;
        return String.valueOf(idCounter);
    }
    
}

/**
 * User-Defined Exception for invalid Data Entry Out Of Range required.
 */
@SuppressWarnings("serial")
class InvalidDataException extends Exception // An inherited class from Exception
{
    InvalidDataException(String Message)
    {
        super(Message);
    }
    
}



