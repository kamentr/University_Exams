using System;
using System.Collections.Generic;
using System.Text;

namespace MyAutoPark
{
    class PublicTransportationAutopark : AutoPark
    {
        private int maximumPassengers;

        public int MaximumPassengers
        {
            get { return maximumPassengers; }
            private set { maximumPassengers = value; }
        }

        public PublicTransportationAutopark(List<Vehicle> vehicles, int maximumPassengers) 
            : base(vehicles)
        {
            MaximumPassengers = maximumPassengers;
        }


    }
}
