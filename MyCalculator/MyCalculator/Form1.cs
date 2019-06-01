using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace MyCalculator
{
    public partial class Form1 : Form
    {
        Calculator calculator;
        public Form1()
        {
            InitializeComponent();
            calculator = new Calculator();
        }

        private void btnNumbersEvent(object sender, EventArgs e)
        {
            calculator.NumbersPressed(sender, result);
        }

        private void btnOperatorClick(object sender, EventArgs e)
        {
            calculator.OperatorsPressed(sender, result, helper);
        }

        private void ClearTextBox(object sender, EventArgs e)
        {
            calculator.ClearTextBox(sender, result, helper);
        }

        private void btnEqualsClick(object sender, EventArgs e)
        {
            calculator.EqualsPressed(result, helper);
        }

        private void btnPercent(object sender, EventArgs e)
        {
            calculator.PercentPressed(result);
        }

        private void btnMemoryClick(object sender, EventArgs e)
        {
            calculator.MemoryPressed(sender, result);
        }
    }
}
