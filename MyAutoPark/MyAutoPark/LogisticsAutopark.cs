using System;
using System.Collections.Generic;
using System.Text;

namespace MyAutoPark
{
    class LogisticsAutopark : AutoPark
    {
        private double maximumCargoWeight;

        public double MaximumCargoWeight
        {
            get { return maximumCargoWeight; }
            private set { maximumCargoWeight = value; }
        }

        public LogisticsAutopark(List<Vehicle> vehicles, double maximumCargoWeight ) : base(vehicles)
        {
            MaximumCargoWeight = maximumCargoWeight;
        }


    }
}
