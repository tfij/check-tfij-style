@AllArgsConstructor
private static class SampleTestClass {

    private final int a1;
    private final int a2;
    private final int a3;

    @AllArgsConstructor
    static class SubClass {
        private final static String f1 = "v1";
        private final static String f2;
        private final String f3 = "v3";
        private final String f4;
        private String f5 = "aa";
        private String f6;
        private String f7;
        private String f8;
        private String f9;
        private String f10;
        private String f11;
        private String f12;
        private String f13;

        private SampleTestClass(String f1, String f2, String f4) {
            this.f1 = f1;
            this.f2 = f2;
            this.f4 = f4;
        }
    }
}