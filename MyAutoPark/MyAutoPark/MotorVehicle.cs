using System;
using System.Collections.Generic;
using System.Text;

namespace MyAutoPark
{
    class MotorVehicle : Vehicle
    {
        public MotorVehicle(int power, int maxPassengers, double maxCargoWeight) 
            : base(power, maxPassengers, maxCargoWeight)
        {

        }
    }
}
