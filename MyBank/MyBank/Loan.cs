using System;

namespace MyBank
{
    class Loan
    {
        private double principal;
        private double interestRate;
        private double termInMonths;
        private double Interest;
        private double Balance;

        public Loan()
        {

        }
        public Loan(double principalm, double interestRate, double termInMonths)
        {
            Principal = principal;
            InterestRate = interestRate;
            TermInMonths = termInMonths;
        }
        public Loan CreateLoan()
        {
            Console.Write("Principal amount: ");
            principal = double.Parse(Console.ReadLine());

            Console.Write("Annual interest rate: ");
            interestRate = double.Parse(Console.ReadLine());

            Console.Write("Term in months: ");
            termInMonths = double.Parse(Console.ReadLine());

            Loan loan = new Loan(principal, interestRate, termInMonths);

            return loan;
        }

        public double TermInMonths
        {
            get { return termInMonths; }
            private set
            {
                if (String.IsNullOrWhiteSpace(value.ToString()) || value < 0 || value.ToString().Length == 0)
                {
                    throw new ArgumentException("Invalid fomrat. Principal cannot be empty or negative");
                }

                termInMonths = value;
            }
        }


        public double InterestRate
        {
            get { return interestRate; }
            private set
            {
                if (String.IsNullOrWhiteSpace(value.ToString()) || value < 0 || value.ToString().Length == 0)
                {
                    throw new ArgumentException("Invalid fomrat. Interest rate cannot be empty or negative");
                }

                interestRate = value;
            }
        }


        public double Principal
        {
            get { return principal; }
            private set
            {
                if (String.IsNullOrWhiteSpace(value.ToString()) || value < 0 || value.ToString().Length == 0)
                {
                    throw new ArgumentException("Invalid fomrat. Principal cannot be empty or negative");
                }

                principal = value;
            }
        }

        public void MonthlyPaymentPlanner()
        {
            Balance = Principal;
            DateTime currentDate = DateTime.Now;
            Console.WriteLine($"\nSchedule\n----------------------------------------------------------------");


            for (int i = 0; i < termInMonths; i++)
            {
                Interest = (InterestRate / 1200) * Balance;
                Balance -= CalculateMonthlyPayment() - Interest;
                if (Interest > 10)
                {
                    Console.WriteLine($"{currentDate.ToString("MMM-yyyy")} -> Interest: ${Interest:f2}  Principal: ${(CalculateMonthlyPayment() - Interest):f2}  Balance: ${Balance:f2}");
                }
                else
                {
                    Console.WriteLine($"{currentDate.ToString("MMM-yyyy")} -> Interest: ${Interest:f2}   Principal: ${(CalculateMonthlyPayment() - Interest):f2}  Balance: ${Balance:f2}");
                }
                currentDate = currentDate.AddMonths(1);
            }


        }

        public double CalculateMonthlyPayment()
        {
            double MonthlyRate = interestRate / 1200;

            return (MonthlyRate + (MonthlyRate / (Math.Pow((1 + MonthlyRate), TermInMonths) - 1))) * Principal;
        }

        public double CalculateTotalLoanCost()
        {
            double MonthlyLoanRate = InterestRate / 1200;

            return (MonthlyLoanRate * Principal * TermInMonths) / (1 - Math.Pow((1 + MonthlyLoanRate), -TermInMonths));
        }

        public override string ToString()
        {
            return $"\nYou will have to pay ${CalculateMonthlyPayment():f2} every month for {TermInMonths} months.\n" +
                   $"The total loan cost will be ${CalculateTotalLoanCost():f2}.";
        }

    }
}
