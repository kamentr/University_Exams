using System;

namespace MyBank
{
    class Program
    {
        static void Main(string[] args)
        {

            Loan loan = new Loan();

            try
            {

                loan.CreateLoan();

            }
            catch (ArgumentException e)
            {

                Console.WriteLine(e.Message);

            }

                Console.WriteLine(loan);
                loan.MonthlyPaymentPlanner();

        }
    }
}
