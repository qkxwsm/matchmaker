import java.util.*;

public class Matching
{
    private int L, R, N, S, T, E;
    private MinCostFlow M;
    private ArrayList<Pair<Long, Integer> >[] edges;
    private int[] match;

    final long LLINF = (1L << 61);

    Matching(int l, int r)
    {
        L = l;
        R = r;
        N = L + R + 3;
        E = N - 3;
        S = N - 2;
        T = N - 1;
        M = new MinCostFlow(N);
        edges = (ArrayList<Pair<Long, Integer> >[]) new ArrayList[L];
        for (int i = 0; i < L; i++)
        {
            edges[i] = new ArrayList<Pair<Long, Integer> >();
        }
        match = new int[L];
    }
    /*
        adds edge from a (on left) to b (on right) with utility c.
    */
    public void addEdge(int a, int b, long c)
    {
        // System.out.println("Try " + a + " " + b + " " + c);
        edges[a].add(new Pair<Long, Integer>(c, b + L));
    }
    /*
        sets the capacity of item b to c.
    */
    public void setCapacity(int b, long c)
    {
        M.addEdge(b + L, T, c, 0);
    }
    // public ArrayList<Pair<Long, Integer> > clean(ArrayList<Pair<Long, Integer> > a)
    // {
    //     Collections.sort(a);
    //     long val = 1;
    //     for (int i = 0; i < a.size(); i++)
    //     {
    //         Pair<Long, Integer> cur = a.get(i);
    //         if (i != 0 && cur.getFirst() != a.get(i - 1).getFirst())
    //         {
    //             val++;
    //         }
    //         a.set(i, new Pair<Long, Integer>(val * val, cur.getSecond()));
    //     }
    //     return a;
    // }
    public int[] calculate() throws Exception
    {
        /*
            ok: is it ok for something on the left to get assigned to nothing?
        */
        // if (ok)
        // {
        //     M.addEdge(E, T, L, 0);
        //     for (int i = 0; i < L; i++)
        //     {
        //         edges[i].add(new Pair<Integer, Integer>(E, LLINF));
        //     }
        // }
        // for (int i = 0; i < L; i++)
        // {
        //     edges[i] = clean(edges[i]);
        // }
        for (int i = 0; i < L; i++)
        {
            M.addEdge(S, i, 1, 0);
        }
        for (int u = 0; u < L; u++)
        {
            for (Pair<Long, Integer> e : edges[u])
            {
                M.addEdge(u, e.getSecond(), 1, e.getFirst());
            }
        }
        M.calculate();
        Pair<Long, Long> f = M.getFlow();
        System.out.println("Flow " + f.getFirst() + " cost " + f.getSecond());
        System.out.println("L = " + L);
        if (f.getFirst() != L)
        {
            throw new Exception("No Matching Exists");
        }
        ArrayList<Pair<Pair<Integer, Integer>, Long> > edges = M.getEdges();
        for (Pair<Pair<Integer, Integer>, Long> e : edges)
        {
            int u = e.getFirst().getFirst();
            int v = e.getFirst().getSecond();
            if (u < L)
            {
                match[u] = v - L;
            }
        }
        return match;
    }
}
