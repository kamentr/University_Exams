using System;
using System.Collections.Generic;
using System.Text;

namespace MyAutoPark
{
    public abstract class Vehicle
    {
        private int power;

        private int maxPassengers;

        private double maxCargoWeight;

        public Vehicle(int power, int maxPassengers, double maxCargoWeight)
        {
            Power = power;
            MaxPassengers = maxPassengers;
            MaxCargoWeight = maxCargoWeight;
        }

        public double MaxCargoWeight
        {
            get { return maxCargoWeight; }
            private set { maxCargoWeight = value; }
        }


        public int MaxPassengers
        {
            get { return maxPassengers; }
            private set { maxPassengers = value; }
        }

        public int Power
        {
            get { return power; }
            private set { power = value; }
        }


    }
}
