using System;
using System.Collections.Generic;

namespace MyAutoPark
{
    class Program
    {
        static void Main(string[] args)
        {
            Bus Bus = new Bus(230, 2, 400);
            Van Van = new Van(380, 2, 400);
            Bus BusTwo = new Bus(130, 4, 400);
            Bus BusThree = new Bus(130, 4, 400);

            Car BMW = new Car(130, 4, 400);
            Car Audi = new Car(130, 4, 400);
            MotorVehicle Toyota = new MotorVehicle(130, 4, 400);

            PublicTransportationAutopark PTautopark = new PublicTransportationAutopark(new List<Vehicle>() { Bus, Van, BusTwo, BusThree }, 50);
            LogisticsAutopark Lautopark = new LogisticsAutopark(new List<Vehicle>() { BMW, Audi, Toyota }, 2500);
            
            List<AutoPark> MyAutoparks = new List<AutoPark>() {PTautopark, Lautopark};

            DisplayInfo(MyAutoparks);
        }

        private static void DisplayInfo(List<AutoPark> myAutoparks)
        {
            int i = 1;
            foreach (var autopark in myAutoparks)
            { 
                Console.WriteLine($"Number of vehicles in {i}-autopark is {autopark.VehiclesCount}");
                i++; 
            }

            foreach (var autopark in myAutoparks)
            {
                if(autopark is PublicTransportationAutopark)
                {
                    PublicTransportationAutopark PTAutopark = (PublicTransportationAutopark)autopark; 
                    Console.WriteLine($"Public Transportation Autopark - Maximum passengers: {PTAutopark.MaximumPassengers}");
                }
                else
                {
                    LogisticsAutopark LAutopark = (LogisticsAutopark)autopark;
                    Console.WriteLine($"Logistics Autopark - Maximum cargo weight: {LAutopark.MaximumCargoWeight}");
                }
            }
        }
    }
}
