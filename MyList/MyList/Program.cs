using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyList
{
    class Program
    {
        static void Main(string[] args)
        {
            MyList<int> nums = new MyList<int>();
            nums.Add(3);
            nums.Add(4);
            nums.Add(6);
            nums.Add(7);
            nums.Add(8);
            nums.RemoveAt(5);
            nums.Insert(2, 5);
            foreach (var num in nums)
            {
                Console.WriteLine(num);
            }
            //Console.WriteLine(nums.Count);
            Console.ReadKey();
        }
    }
}
