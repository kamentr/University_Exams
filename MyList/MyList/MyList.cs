using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyList
{
    public class MyList<T> : IList<T>
    {
        public T this[int index] { get => Getter(index); set => array[index] = value; }

        private T Getter(int index)
        {
            if (index < Count)
            {
                return array[index];
            }
            else
            {
                throw new IndexOutOfRangeException();
            }
        }

        public int Count => size;

        public bool IsReadOnly => array.IsReadOnly;

        private T[] array;

        private int size = 0;

        public MyList()
        {
            array = new T[4];
        }

        public MyList(int size)
        {
            array = new T[size];
        }

        public void Add(T item)
        {
            if (size + 1 >= array.Length)
            {
                DoubleTheSize();
            }
            array[size++] = item;
        }

        private void DoubleTheSize()
        {
            T[] newArray = new T[array.Length * 2];
            Array.Copy(array, newArray, array.Length);
            array = newArray;
        }

        public void Clear()
        {
            array = new T[4];
            size = 0;
        }

        public bool Contains(T item)
        {
            return array.Contains(item);
        }

        public void CopyTo(T[] array, int arrayIndex)
        {
            Array.Copy(this.array, arrayIndex, array, 0, this.array.Length - arrayIndex - 1);
        }

        public int IndexOf(T item)
        {
            if (array.Contains(item))
            {
                for (int i = 0; i < Count; i++)
                {
                    if (array[i].Equals(item))
                    {
                        return i;
                    }
                }
            }
            return -1;
        }

        public void Insert(int index, T item)
        {
            if (Count + 1 > array.Length)
            {
                DoubleTheSize(); 
            }

            T[] newArray = new T[array.Length + 1];
            Array.Copy(array, 0, newArray, 0, index);
            Array.Copy(array, index, newArray, index+1, array.Length - index - 1);
            newArray[index] = item;
            array = newArray;
            size++;
        }

        public bool Remove(T item)
        {

            if (array.Contains(item))
            {
                int index = IndexOf(item);
                T[] newArray = new T[size - 1];
                Array.Copy(array, 0, newArray, 0, index);
                Array.Copy(array, index + 1, newArray, index, size - index - 1);
                array = newArray;
                size--;
                return true;
            }
            
            return false;
        }

        public void RemoveAt(int index)
        {
            T item = array[index];
            Remove(item);
        }

        public IEnumerator<T> GetEnumerator()
        {
            for (int i = 0; i < Count; i++)
            {
                yield return array[i];
            }
        }

        IEnumerator IEnumerable.GetEnumerator()
        {
            return GetEnumerator();
        }
    }
}
