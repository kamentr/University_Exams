using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace MyCalculator
{
    class Calculator
    {
        private bool isCalculating { get; set; }
        private double oldValue { get; set; }
        private string Operator { get; set; }
        private double memoryValue = 0;

        internal void NumbersPressed(Object sender, TextBox result)
        {
            Button btnClicked = (Button)sender;
            if (result.Text == "0" || isCalculating)
            {
                result.Clear();
            }
            if (btnClicked.Text == "." && result.Text.Contains("."))
            {

            }
            else
            {
                result.Text += btnClicked.Text;
            }
            isCalculating = false;
        }

        internal void OperatorsPressed(Object sender, TextBox result, TextBox helper)
        {
            if (!isCalculating) EqualsPressed(result, helper);
            Button btnClicked = (Button)sender;
            oldValue = double.Parse(result.Text);
            Operator = btnClicked.Text;
            isCalculating = true;

            helper.Text += result.Text + Operator;
        }

        internal void MemoryPressed(Object sender, TextBox result)
        {
            Button btnClicked = (Button)sender;
            string operation = btnClicked.Text;
            switch (operation)
            {
                case "M+":
                    memoryValue += double.Parse(result.Text);
                    break;
                case "M-":
                    memoryValue -= double.Parse(result.Text);
                    break;
                case "MR":
                    result.Text = memoryValue.ToString();
                    break;
                case "MC":
                    memoryValue = 0;
                    break;
                default:
                    break;
            }
        }

        internal void PercentPressed(TextBox result)
        {
            result.Text = (double.Parse(result.Text) / 100.0).ToString();
        }

        internal void EqualsPressed(TextBox result, TextBox helper)
        {
            double newValue = double.Parse(result.Text);
            helper.Clear();

            switch (Operator)
            {
                case "+":
                    result.Text = (oldValue + newValue).ToString();
                    break;
                case "-":
                    result.Text = (oldValue - newValue).ToString();
                    break;
                case "x":
                    result.Text = (oldValue * newValue).ToString();
                    break;
                case "/":
                    result.Text = (oldValue / newValue).ToString();
                    break;
                default:
                    break;
            }
            isCalculating = true;
        }

        internal void ClearTextBox(Object sender, TextBox result, TextBox helper)
        {
            Button btnClicked = (Button)sender;

            if (btnClicked.Text == "C")
            {
                oldValue = 0;
                helper.Clear();
                result.Text = "0";
            }
            else
            {
                result.Text = "0";
            }
        }
    }
}
