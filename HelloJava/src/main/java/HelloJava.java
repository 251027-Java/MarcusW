public class HelloJava {

    //Fields or properties
    private String userName;

    //Methods or behaviors
    public HelloJava(String name) {
        this.userName = name;
    }

    //Application will start from the "entrypoint", which will most often be 'main'
    public static void main(String[] args) {
        System.out.println("Hello Java!");
        IO.println("Hello Again!");

        //Numerical Types
        int it;         //integer values
        double db;      //decimal values
        float ft;       //floating point decimals
        short st;       //small integer values
        long lg;        //Big integer values
        byte bt;        //Very small integer values

        //Non-Numerical Types
        char ch;        //characters (single letters, punctuation, etc.)
        String sg;      //an array of characters (words, sentences, etc.)

        boolean bn;     //true/false values

        // void;        //mostly a return type, represents a nothing

        // null;        //not a value, absence of a value


        // Control flow covers all of the keywords and functionality that allow an application  to make a decision, and act on it without us providing additional input
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String name) {
        this.userName = name;
    }
}
