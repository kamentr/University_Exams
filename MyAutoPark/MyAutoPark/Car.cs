using System;
using System.Collections.Generic;
using System.Text;

namespace MyAutoPark
{
    class Car : Vehicle
    {
        public Car(int power, int maxPassengers, double maxCargoWeight) 
            : base(power, maxPassengers, maxCargoWeight)
        {

        }
    }
}
