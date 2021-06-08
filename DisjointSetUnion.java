import java.util.*;

class DisjointSetUnion
{
    private int N;
    private int[] dsu;
    private int[] siz;
    DisjointSetUnion(int n)
    {
        N = n;
        dsu = new int[N];
        siz = new int[N];
        for (int i = 0; i < N; i++)
        {
            dsu[i] = i;
            siz[i] = 1;
        }
    }
    public int get(int u)
    {
        if (dsu[u] != u)
        {
            dsu[u] = get(dsu[u]);
        }
        return dsu[u];
    }
    public boolean merge(int u, int v)
    {
        u = get(u);
        v = get(v);
        if (u == v)
        {
            return false;
        }
        if (siz[u] > siz[v])
        {
            dsu[v] = u;
            siz[u] += siz[v];
            siz[v] = 0;
        }
        else
        {
            dsu[u] = v;
            siz[v] += siz[u];
            siz[u] = 0;
        }
        return true;
    }
}
