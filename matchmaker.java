import java.util.*;

class MatchMaker
{
    public static void main(String[] args) throws Exception
    {
        int L, R, K;
        Matching M;
        Scanner sc = new Scanner(System.in);
        DisjointSetUnion D;
        L = sc.nextInt(); //numer of people
        R = sc.nextInt(); //number of houses
        M = new Matching(L, R);
        D = new DisjointSetUnion(L);
        int[][] grid = new int[L][R];
        for (int i = 0; i < R; i++)
        {
            int c;
            c = sc.nextInt(); //capacity of each house
            M.setCapacity(i, c);
        }
        K = sc.nextInt(); //staple groups
        for (int i = 0; i < K; i++)
        {
            int k, head = -1;
            k = sc.nextInt();
            for (int j = 0; j < k; j++)
            {
                int u = sc.nextInt();
                if (head != -1)
                {
                    D.merge(u, head);
                }
                else
                {
                    head = u;
                }
            }
        }
        E = sc.nextInt(); //preferences
        for (int i = 0; i < L; i++)
        {
            for (int j = 0; j < R; j++)
            {
                int pref = sc.nextInt();
                grid[D.get(i)][j] += pref;
            }
        }
        int[] res = M.calculate();
        for (int i = 0; i < L; i++)
        {
            System.out.println(i + " " + res[i]);
        }
    }
}
