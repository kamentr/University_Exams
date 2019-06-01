using System;
using System.Collections.Generic;
using System.Text;

namespace MyAutoPark
{
    abstract class AutoPark
    {
        private List<Vehicle> vehicles;
        public int VehiclesCount => Vehicles.Count;

        public AutoPark()
        {

        }

        public List<Vehicle> Vehicles
        {
            get { return vehicles; }
            private set { vehicles = value; }
        }

        public AutoPark(List<Vehicle> vehicles)
        {
            Vehicles = vehicles;
        }

    }
}
