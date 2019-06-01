using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace MyList
{
    class IEnumerator<T> : IEnumerator
    {
        public object Current => list[positon];
        int positon = -1;
        public T[] list;

        public IEnumerator(T[] list)
        {
            this.list = list;
        }

        public bool MoveNext()
        {
            positon++;
            return (positon < list.Length/2+1);
        }

        public void Reset()
        {
            positon = -1;
        }
    }
}
