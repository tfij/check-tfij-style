@AllArgsConstructor
static class SampleTestClass {

    SampleTestClass(String f1, String f2) {

        // sample comment
        this.f1 = f1;
        this.f2 = f2;
    }

    SampleTestClass(String f1, String f2, String f3) {
        this.f1 = f1;
        this.f2 = f2;
        /**
         * sample
         *
         * comment
         */

        this.f3 = f3;

    }

    void metthod1() {

        System.out.pringline("1");
        System.out.pringline("2");
        System
                .out
                .pringline("3");
        System.out.pringline("4");

        System.out.pringline("5");
    }

    void metthod2() {

    }

    void metthod3() {


    }

    static class InnerClass {

        void metthod4() {
            System.out.pringline("1");
            System.out.pringline("2");
            System
                    .out
                    .pringline("3");
            System.out.pringline("4");
            System.out.pringline("5");

        }

    }

    record RecordClass(String a, String b) {
        constructor() {
            System.out.pringline("1");
            System.out.pringline("2");

            System
                    .out
                    .pringline("3");
            System.out.pringline("4");
            System.out.pringline("5");
        }

        void metthod5() {

            System.out.pringline("1");
            System.out.pringline("2");
            System
                    .out
                    .pringline("3");
            System.out.pringline("4");

            System.out.pringline("5");


        }
    }
}