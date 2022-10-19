package pl.tfij.checktfijstyle.sample.lines;

public class NoConstructorIssues {

    public NoConstructorIssues() {}

    NoConstructorIssues(int p1) {}

    NoConstructorIssues(int p1,
        int p2) {}

    NoConstructorIssues(
            long p1,
            long p2
    ) {}

    NoConstructorIssues(int p1,
                        int p2,
                        int p3) {}

    NoConstructorIssues(int p1,
         int p2,

             int p3,
         int p4

    ) {}

    NoConstructorIssues(int p1, int p2, boolean p3, long p4, Object p5, short p6, Integer p7, int p8, int p9, int p10, int p11, int p12, int p13, int p14, Object... p15) {}
}
