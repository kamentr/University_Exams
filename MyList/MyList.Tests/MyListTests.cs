using System;
using Microsoft.VisualStudio.TestTools.UnitTesting;
using NUnit.Framework;

namespace MyList.Tests
{
    [TestClass]
    public class MyListTests
    {
        private MyList<int> nums;

        [SetUp]
        public void Setup()
        {
            nums = new MyList<int>();
        }


        [Test]
        public void TestMethod1()
        {
            this.nums.Add(9);
            NUnit.Framework.Assert.That(this.nums.Count, Is.EqualTo(1));
        }

        [Test]
        public void TestMethod2()
        {
            this.nums.Add(1);
            this.nums.Add(2);
            this.nums.Add(3);
            this.nums.Add(4);
            this.nums.Remove(2);
            NUnit.Framework.Assert.That(this.nums.Contains(2), Is.False);
        }

        [Test]
        public void Contains_CheckForExistingItem_True()
        {
            Random rand = new Random();
            for (int i = 0; i < 10000; i++)
            {
                nums.Add(rand.Next(1, 1000));
                NUnit.Framework.Assert.That(this.nums.Count, Is.EqualTo(i+1));S
            }

        }
    }
}
