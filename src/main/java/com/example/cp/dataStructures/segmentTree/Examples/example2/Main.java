package com.example.cp.dataStructures.segmentTree.Examples.example2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    final static long MOD = 1000000007;
    public static void main(String args[]) {
        FastScanner fs = new FastScanner();
        PrintWriter out = new PrintWriter(System.out);

        int n = fs.nextInt();

        long[] a = fs.nextLongArray(n);

        SegmentTree st = new SegmentTree(a);

        int q = fs.nextInt();

        int two = fs.nextInt();

        for(int i = 0; i < q; i++) {
            int l = fs.nextInt();
            int r = fs.nextInt();

            int sum[] = st.rsq(l,r);

            long total = 0;
            int range = (r-l+1);
            for(int k = 0; k < sum.length; k++) {
                int ones = sum[k];
                int zeros = range - ones;
                if(sum[k] != 0 && sum[k] != range) {
                    long times =  ((range * (range-1) * (range - 2)) - (ones * (ones-1) * (ones-2)) - (zeros * (zeros-1) * (zeros-2))) / 6;
                    total += times * modpow(2, k, MOD);
                }
            }
        }

        out.flush();
        out.close();
    }

    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner() {
            try {
                br = new BufferedReader(new InputStreamReader(System.in));
                //                br = new BufferedReader(new FileReader("testdata.out"));
                st = new StringTokenizer("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public String next() {
            if (st.hasMoreTokens())
                return st.nextToken();
            try {
                st = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return st.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public long nextLong() {
            return Long.parseLong(next());
        }

        public double nextDouble() {
            return Double.parseDouble(next());
        }

        public String nextLine() {
            String line = "";
            try {
                line = br.readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return line;
        }

        public char nextChar() {
            return next().charAt(0);
        }

        public Integer[] nextIntegerArray(int n) {
            Integer[] a = new Integer[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public int[] nextIntArray(int n) {
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }

        public long[] nextLongArray(int n) {
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }

        public char[] nextCharArray() {
            return nextLine().toCharArray();
        }
    }

    public static  long modpow(long num, long pow, long mod) {
        BigInteger bnum = BigInteger.valueOf(num);
        BigInteger bpow = BigInteger.valueOf(pow);
        BigInteger bmod = BigInteger.valueOf(mod);
        return bnum.modPow(bpow, bmod).longValue();
    }
}
class SegmentTree {

    static int countBits(long number) {
        return (int)(Math.log(number) / Math.log(2) + 1);
    }

    public static boolean isKthBitSet(long n, int k) {
        return  ((n & (1 << (k - 1))) >= 1);
    }

    private Node[] heap;
    private long[] array;
    private int size;

    public SegmentTree(long[] array) {
        this.array = Arrays.copyOf(array, array.length);
        size = (int) (2 * Math.pow(2.0, Math.floor((Math.log((double) array.length) / Math.log(2.0)) + 1)));

        heap = new Node[size];
        build(1, 0, array.length);
    }

    private void build(int v, int from, int size) {
        heap[v] = new Node();
        heap[v].from = from;
        heap[v].to = from + size - 1;

        if(size == 1) {
            for(int i = 0; i < heap[v].set.length; i++) {
                heap[v].set[i] = isKthBitSet(array[from], i) ? 1 : 0;
            }
        } else {
            build(2 * v, from, size / 2);
            build(2 * v + 1, from + size / 2, size - size / 2);
            for(int i = 0; i < heap[v].set.length; i++) {
                heap[v].set[i] = heap[2*v].set[i] + heap[2*v+1].set[i];
            }
        }
    }

    private boolean contains(int from1, int to1, int from2, int to2) {
        return from2 >= from1 && to2 <= to1;
    }

    //check inclusive intersection, test if range1[from1, to1] intersects range2[from2, to2]
    private boolean intersects(int from1, int to1, int from2, int to2) {
        return from1 <= from2 && to1 >= from2   //  (.[..)..] or (.[...]..)
                || from1 >= from2 && from1 <= to2; // [.(..]..) or [..(..)..
    }



    public int[] rsq(int from, int to) {
        return rsq(1, from, to);
    }

    private int[] rsq(int v, int from, int to) {
        Node n = heap[v];

        if (contains(from, to, n.from, n.to)) {
            return heap[v].set;
        }

        if (intersects(from, to, n.from, n.to)) {
            int[] leftSum = rsq(2 * v, from, to);
            int[] rightSum = rsq(2 * v + 1, from, to);

            int sum[] = new int[leftSum.length];
            for (int i = 0; i < leftSum.length; i++) sum[i] = leftSum[i] + rightSum[i];
            return sum;
        }
        return new int[countBits((int)Math.pow(10, 12)+10)];
    }

    static class Node {
        int set[];

        int from;
        int to;

        Node(){
            set = new int[countBits((int)Math.pow(10,12)+10)];
        }
        int size() {
            return to - from + 1;
        }
    }
}