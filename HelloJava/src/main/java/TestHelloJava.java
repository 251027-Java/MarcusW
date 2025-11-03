public class TestHelloJava {

    public static void main(String[] args) {

        HelloJava test = new HelloJava("Marcus");
        String currName = test.getUserName();
        System.out.println(String.format("Name is %s", currName));
        test.setUserName("Billy");
        String nowName = test.getUserName();
        System.out.println(String.format("Name is now %s", nowName));
    }
}
