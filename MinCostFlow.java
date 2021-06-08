import java.util.*;

public class MinCostFlow
{
    private int N, E, S, T;
    private int[] head, par;
    private ArrayList<Integer> to, link;
    private ArrayList<Long> cap, used, cost;
    private long[] dist;
    final long LLINF = (1L << 61);
    private Pair<Long, Long> flow;
    private ArrayList<Pair<Pair<Integer, Integer>, Long> > edges; //tells you edges used in final flow

    MinCostFlow(int n)
    {
        N = n;
        E = 2;
        S = N - 2;
        T = N - 1;
        head = new int[N];
        par = new int[N];
        to = new ArrayList<Integer>(); to.add(0); to.add(0);
        link = new ArrayList<Integer>(); link.add(0); link.add(0);
        cap = new ArrayList<Long>(); cap.add(0L); cap.add(0L);
        used = new ArrayList<Long>(); used.add(0L); used.add(0L);
        cost = new ArrayList<Long>(); cost.add(0L); cost.add(0L);
        dist = new long[N];
        flow = new Pair<Long, Long>(0L, 0L);
        edges = new ArrayList<Pair<Pair<Integer, Integer>, Long> >();
    }
    public void addEdge(int u, int v, long ca, long co)
    {
        System.out.println(u + " " + v + " " + ca + "," + co);
        link.add(head[u]);
        to.add(v);
        cap.add(ca);
        used.add(0L);
        cost.add(co);
        head[u] = E;
        E++;
        link.add(head[v]);
        to.add(u);
        cap.add(0L);
        used.add(0L);
        cost.add(-co);
        head[v] = E;
        E++;
    }
    private boolean spfa()
    {
        for (int i = 0; i < N; i++)
        {
            dist[i] = LLINF;
            par[i] = -1;
        }
        dist[S] = 0;
        Queue<Pair<Long, Integer> > q = new LinkedList<Pair<Long, Integer> >();
        q.add(new Pair<Long, Integer> (0L, S));
        while(!q.isEmpty())
        {
            long d = q.peek().getFirst();
            int u = q.peek().getSecond();
            q.remove();
            if (dist[u] != d) continue;
            for (int e = head[u]; e > 0; e = link.get(e))
            {
                if (cap.get(e) == 0) continue;
                int v = to.get(e);
                if (dist[v] <= d + cost.get(e)) continue;
                dist[v] = d + cost.get(e);
                par[v] = e;
                q.add(new Pair<Long, Integer> (dist[v], v));
            }
        }
        return (dist[T] < LLINF);
        //careful here: sometimes you wanna send as much flow as you can with negative cost, in this case, exit condition is (dist[T] < 0)
    }
    private Pair<Long, Long> aug()
    {
        long gain = LLINF, gaincost = 0L;
        for (int u = T; u != S; u = to.get(par[u] ^ 1))
        {
            gain = Math.min(gain, cap.get(par[u]));
        }
        for (int u = T; u != S; u = to.get(par[u] ^ 1))
        {
            cap.set(par[u], cap.get(par[u]) - gain);
            cap.set(par[u] ^ 1, cap.get(par[u] ^ 1) + gain);
            used.set(par[u], used.get(par[u]) + gain);
            used.set(par[u] ^ 1, used.get(par[u] ^ 1) - gain);
            gaincost += gain * cost.get(par[u]);
        }
        return new Pair<Long, Long> (gain, gaincost);
    }
    public void calculate()
    {
        // System.out.println("E = " + E + " N = " + N);
        while(spfa())
        {
            Pair<Long, Long> gained = aug();
            flow.setFirst(flow.getFirst() + gained.getFirst());
            flow.setSecond(flow.getSecond() + gained.getSecond());
        }
        for (int u = 0; u < N; u++)
        {
            for (int e = head[u]; e > 0; e = link.get(e))
            {
                long c = used.get(e);
                if (c <= 0) continue;
                int v = to.get(e);
                Pair<Integer, Integer> edge = new Pair<Integer, Integer>(u, v);
                edges.add(new Pair<Pair<Integer, Integer>, Long> (edge, c));
            }
        }
        return;
    }
    public Pair<Long, Long> getFlow()
    {
        return flow;
    }
    public ArrayList<Pair<Pair<Integer, Integer>, Long> > getEdges()
    {
        return edges;
    }
}
