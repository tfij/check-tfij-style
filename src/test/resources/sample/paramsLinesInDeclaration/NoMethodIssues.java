package pl.tfij.checktfijstyle.sample.lines;

public class NoMethodIssues {

    public static void f1() {}

    static void f2(int p1) {}

    public void f3(int p1,
        int p2) {}

    void f4(
            int p1,
            int p2
    ) {}

    private void f5(int p1,
                    int p2,
                    int p3) {}

    public Object f6(int p1,
         int p2,

             int p3,
         int p4

    ) {
        return null;
    }

    public String f7(int p1, int p2, boolean p3, long p4, Object p5, short p6, Integer p7, int p8, int p9, int p10, int p11, int p12, int p13, int p14, Object... p15) {
        return "OK";
    }

    public void f8(
            int p1, int p2, boolean p3, long p4, Object p5, short p6, Integer p7, int p8, int p9, int p10, int p11, int p12, int p13, int p14, Object... p15
    ) {}
}
