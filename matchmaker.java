import java.util.*;

class MatchMaker
{
    public static void main(String[] args) throws Exception
    {
        int L, R, E;
        Matching M;
        Scanner sc = new Scanner(System.in);
        L = sc.nextInt();
        R = sc.nextInt();
        E = sc.nextInt();
        M = new Matching(L, R);
        for (int i = 0; i < R; i++)
        {
            int c;
            c = sc.nextInt();
            M.setCapacity(i, c);
        }
        for (int i = 0; i < E; i++)
        {
            int u, v, c;
            u = sc.nextInt();
            v = sc.nextInt();
            c = sc.nextInt();
            M.addEdge(u, v, c);
        }
        int[] res = M.calculate();
        for (int i = 0; i < L; i++)
        {
            System.out.println(i + " " + res[i]);
        }
    }
}
