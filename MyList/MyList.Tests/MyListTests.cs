using System;
using NUnit.Framework;

namespace MyList.Tests
{
    public class MyListTests
    {
        private MyList<int> nums;

        [SetUp]
        public void Setup()
        {
            nums = new MyList<int>();
        }


        [Test]
        public void Add_TestIfAddWorks_One()
        {
            this.nums.Add(9);
            Assert.That(this.nums.Count, Is.EqualTo(1));
        }

        [Test]
        public void Add_TestIfAddWorks_Thousand()

        {
            for (int i = 0; i < 1000; i++)
            {
                nums.Add(i);
            }
        }

        [Test]
        public void Contains_CheckForExistingItem_TrueFalse()
        {
            nums.Add(1);
            nums.Add(2);
            nums.Add(4);
            nums.Add(10);

            Assert.That(this.nums.Contains(4), Is.True);

            nums.Remove(4);

            Assert.That(this.nums.Contains(4), Is.False);
        }

        [Test]
        public void IndexOf_GetsIndexOfElement_MinusOne()
        {
            nums.Add(1);
            nums.Add(2);
            nums.Add(4);
            nums.Add(10);

            Assert.That(nums.IndexOf(100), Is.EqualTo(-1));
        }

        [Test]
        public void IndexOf_GetsIndexOfElement_446()
        {
            for (int i = 0; i < 1000; i++)
            {
                nums.Add(i);
            }

            Assert.That(nums.IndexOf(446), Is.EqualTo(446));
        }

        [Test]
        public void Remove_RemovesGivenElement_True_Zero()
        {
            for (int i = 0; i < 1000; i++)
            {
                nums.Add(i);
            }
            for (int i = 0; i < 1000; i++)
            {
                Assert.That(nums.Remove(i), Is.EqualTo(true));

            }
            Assert.That(nums.Count, Is.EqualTo(0));

        }

        [Test]
        public void Remove_RemovesGivenElement_False()
        {
            for (int i = 0; i < 1000; i+=2)
            {
                nums.Add(i);
            }
            for (int i = 1; i < 1000; i+=2)
            {
                Assert.That(nums.Remove(i), Is.EqualTo(false));
            }
        }

        [Test]
        public void Insert_InsertsGivenElement_1000()
        {
            for (int i = 0; i < 1000; i++)
            {
                nums.Add(i);
            }

            int count = nums.Count;
            for (int i = 0; i < 1000; i++)
            {
                nums.Insert(i, i);
            }
            Assert.That(nums.Count, Is.EqualTo(count + 1000));
        }
    }
}
