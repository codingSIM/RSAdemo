import com.company.Password;

import java.lang.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Menus {
    private static int p,q,n,phi,e,d;
    private static BigInteger m,c,c1;
    private static int p1,q1,n1,phi1,e1,d1;
    private static int min,max;
    //private static BigInteger testMersenne, test;

    //private static int x, y, x1, y1;


    static Random rand;

    public static ArrayList<Integer> primesList = new ArrayList<Integer>();
    public static ArrayList<Integer> eList=new ArrayList<Integer>();


    private static final String TITLE = "\n2910326 Computer Security Coursework\n" +
            "by Sorana_MARIN_33603291\n\n" +
            "***************************************\n" +
            "Declaration: This demonstration and assignment has been inspired by some articles on the internet\n" +
            "***************************************\n";



    public static void main(String[] args) {
        System.out.println(TITLE);

//      Can be changed to other min, max values
        generatePrimes(150,1000);

        // MERSENNE testing -----------------------------------------------------------------------
//        test = BigInteger.valueOf(2);
//        testMersenne = (test.pow(chooseRL(primesList)).subtract(BigInteger.valueOf(1)));
//        System.out.println(testMersenne);
//        //Hardware and algorithm limitations - Not good
//        System.out.println(checkIsPrime(testMersenne));

        //OTHER tests --------------------------------------------------------------------------
//        System.out.println(checkIsCoPrime(35,20));
//        System.out.println("TESTTTTTT: " + checkIsCoPrime(3,192));
//        System.out.println(checkIsCoPrime(192,3));

//        System.out.println(chooseRL(primesList));
//        System.out.println(chooseRL(primesList));

//        for (int i = 0; i < primesList.size(); i++) {
//            System.out.print(primesList.get(i) + " , ");
//        }
//        System.out.println(primesList);

//        System.out.println(chooseE(40));
//        System.out.println(calculateD(17, 60*52)); //From video



        //MENU ----------------------------------------------------------------------------------
        Scanner UInput = new Scanner(System.in);
        System.out.println("Welcome to Sorana's RSA demonstration, with guests Alice, Bob and Charlie");
        System.out.println("!!! This is a demonstration, the primes and numbers used are scaled down. Usually RSA keys are much much bigger (>1024 bits) !!!");

        //Menu System
        mainMenu: while (true) {
            System.out.println("\nMain-menu:");
            System.out.println("(Enter options: 1,2,3 or q):");
            System.out.println("1. Auto-generate key-pair (step-by-step).");
            System.out.println("2. Communication scenario.");
            System.out.println("3. Test your key.");
            System.out.println("q. Quit.");

            //switch case for the menu
            switch (UInput.next()) {
                default -> System.out.println("Invalid, please enter 1,2,3 or q.\n");
                case "1" -> { //if user entered 1
                    generateKeyPair1();
                    System.out.println("\n--------------------------------------------------------------\nRSA - How it's done\n--------------------------------------------------------------");
                    System.out.println("1. P and Q are randomly chosen from a list. p = " + p + " & q = " + q);
                    System.out.println("2. Next n and phi get calculated.\n\tn = p * q = " + n + " & phi = (p-1)*(q-1) = " + phi);
                    System.out.println("3. e gets chosen such that phi and e don't share any factors, using the GCD algorithm. e = " + e);
                    System.out.println("4. Calculate exponent d. This is calculated using the modular inverse algorithm. d = " + d);
                    System.out.println("\nPublic key: (e: " + e + ", n: " + n + ")");
                    System.out.println("Private key: (d: " + d + ")");
                    //System.out.println("Checking d: " + (e*d%phi==1));
                    System.out.println("--------------------------------------------------------------\n");
                }
                case "2" -> { //if user entered 2
                    System.out.println("\n-Generating keys-----------------------------------------------");
                    generateKeyPair1();
                    generateKeyPair2();
                    System.out.println("Alice's keys: public (e: "+e1+", n: "+n1+"), private (d: "+d1+")");
                    System.out.println("Bob's keys: public (e: "+e+", n: "+n+"), private (d: "+d+")");
                    System.out.println("-----------------------------------------------------------------");

                    System.out.println("Please enter a message to be encrypted: (m should be a positive integer < 2147483647)");

                    if (UInput.hasNextInt()) { //checking to see the user entered INT
                        m= BigInteger.valueOf(UInput.nextInt());
                        if (m.compareTo(BigInteger.valueOf(0))==-1) {
                            System.out.println("You entered an invalid m, m should be positive.");
                        }else{
                            System.out.println("\n\n>> Alice wants to send (" + m + ") as a message to Bob, so she looks up his public key (e: " + e + ", n: " + n + ")");
                            System.out.println("|--------------------------------------------------------------------------------------------------|");
                            System.out.println("               Alice - encrypts                 >>>>                 Bob - decrypts             ");
                            System.out.println("|--------------------------------------------------------------------------------------------------|");
                            c = encrypt(m, e, n);
                            System.out.println("  " + m + " -> encrypt: (" + m + " ^ " + e + " mod " + n + ") -> " + c + " -> decrypt: (" + c + " ^ " + d + " mod " + n + ") -> " + decrypt(c, d, n));
                            System.out.println("\n\n>> Bob wants to send back (" + m + ") as a message to Alice, so he looks up her public key (e: " + e1 + ", n: " + n1 + ")");
                            System.out.println("|--------------------------------------------------------------------------------------------------|");
                            System.out.println("                Bob - encrypts                  >>>>                Alice - decrypts            ");
                            System.out.println("|--------------------------------------------------------------------------------------------------|");
                            c1 = encrypt(m, e1, n1);
                            System.out.println("  " + m + " -> encrypt: (" + m + " ^ " + e1 + " mod " + n1 + ") -> " + c1 + " -> decrypt: (" + c1 + " ^ " + d1 + " mod " + n1 + ") -> " + decrypt(c1, d1, n1));
                            //System.out.println(m+"-> encrypt("+m+") -> "+c1+" -> decrypt("+c1+") ->"+decrypt(c1,d1,n1));
                        }
                    }
                    else {
                        System.out.println("Please input an integer");
                        UInput.next();
                    }
                }
                case "3" -> { //if user entered q
                    //Beginning statement
                    if (p>1) {
                        System.out.println("Current key is: public (e: "+e+", n: "+n+") & private (d: "+d+")");
                    }


                    optionalMenu: while(true) {
                        System.out.println("\nTesting keys: (enter 1,2 or c)");
                        System.out.println("1. Test p, q, e - coprime phi, e?");
                        System.out.println("2. Find d.");
                        System.out.println("3. Test d - correct mod_inv?");
                        System.out.println("c. Close submenu.");
                        switch (UInput.next()) {
                            case "1" -> {
                                System.out.println("Enter p q e, integers, separated by spaces: ");
                                System.out.println("Hint: Try entering 5 3 4");
                                if (UInput.hasNextInt()) { //checking to see the user entered INT
                                    p = UInput.nextInt();
                                    q = UInput.nextInt();
                                    e = UInput.nextInt();
                                    n = calcN(p,q);
                                    phi = calcPhi(p,q);
                                    System.out.println("\n--------------------------------------------------------------------");
                                    System.out.println("Charlie: Let's see here, I'll help you out and test this for you.");
                                    System.out.println("First test: p(prime) = "+checkIsPrime(p)+" & q(prime) = "+checkIsPrime(q));
                                    System.out.println("Second test: are phi and e, coprime? " + checkIsCoPrime(e,phi));
                                    if (p<10) {
                                        System.out.println("Charlie: Oh no, your prime numbers are really small, wouldn't be a match for anyone.");
                                        System.out.println("Charlie: Please try prime numbers higher than 10. My test has detected you entered smaller numbers.");
                                    }
                                    if (checkIsPrime(p) && checkIsPrime(q) && checkIsCoPrime(e,phi)) {
                                        System.out.println("Charlie: Your numbers are prime and your e and phi are coprime. \nYou may proceed in calculating d.");
                                    }
                                    System.out.println("--------------------------------------------------------------------\n");
                                }
                                else {
                                    System.out.println("Please input p q e, integers");
                                    UInput.next();
                                }
                            }
                            case "2" -> {
                                System.out.println("Enter p q e, separated by spaces");
                                System.out.println("Hint: Try entering 5 3 4");
                                if (UInput.hasNextInt()) { //checking to see the user entered INT
                                    p = UInput.nextInt();
                                    q = UInput.nextInt();
                                    e = UInput.nextInt();
                                    n = calcN(p,q);
                                    phi = calcPhi(p,q);
                                    System.out.println("\n--------------------------------------------------------------------");
                                    System.out.println("Charlie: Let's see here, I'll help you out and test this for you.");
                                    System.out.println("First test: p(prime) = "+checkIsPrime(p)+" & q(prime) = "+checkIsPrime(q));
                                    System.out.println("Second test: are phi and e, coprime? " + checkIsCoPrime(e,phi));
                                    if (checkIsPrime(p) && checkIsPrime(q) && checkIsCoPrime(e,phi)) {
                                        System.out.println("Charlie: Seems all my tests return true.");
                                        System.out.println("The value you're looking for is: d = "+modInverse(e,phi));
                                    }
                                    else {
                                        System.out.println("Some of my tests failed, please make sure you entered prime p, prime q and coprime e");
                                        System.out.println("A valid choice for e is: " + chooseE(phi));
                                    }
                                    System.out.println("--------------------------------------------------------------------\n");
                                }
                                else {
                                    System.out.println("Please input p q e, integers");
                                    UInput.next();
                                }
                            }

                            case "3" -> {
                                System.out.println("Enter p q e d, separated by spaces");
                                if (UInput.hasNextInt()) { //checking to see the user entered INT
                                    p = UInput.nextInt();
                                    q = UInput.nextInt();
                                    e = UInput.nextInt();
                                    d = UInput.nextInt();
                                    n = calcN(p,q);
                                    phi = calcPhi(p,q);
                                    System.out.println("\n--------------------------------------------------------------------");
                                    System.out.println("Charlie: Let's see here, testing d.");
                                    if (checkIsPrime(p) && checkIsPrime(q) && checkIsCoPrime(e,phi)) {
                                        System.out.println("Charlie: Seems all my tests return true, so far. We continue.");
                                        System.out.println("Your value is: d = " + d);
                                        if (d!=modInverse(e,phi)) {
                                            System.out.println("Seems like we got different values for d.");
                                            System.out.println("Let's see if they work:");
                                            System.out.println("My value is: "+modInverse(e,phi)+" & it is: "+checkIsD(e,modInverse(e,phi),phi));
                                            System.out.println("Your value: "+d + " & it is:" +checkIsD(e,d,phi));
                                        }
                                        else {
                                            System.out.println("Seems like we both got the same value. Nicely done!");
                                            System.out.println("Let's do a little test, we can both send a message to eachother encrypted with this key.\n(e: "+e+", n: "+n+")");
                                            System.out.println("Encrypted message: " + encrypt(BigInteger.valueOf(42), e, n));
                                            System.out.println("Send message (int) back? ");
                                            if (UInput.hasNextInt()) {
                                                m = BigInteger.valueOf(UInput.nextInt());
                                                System.out.println("You sent an encrypted message: "+encrypt(m,e,n));
                                            }
                                            else {
                                                System.out.println("You didn't enter an int.");
                                            }
                                        }
                                    }
                                    else {
                                        System.out.println("Some of my tests failed, please make sure you entered prime p, prime q and coprime e");
                                    }
                                    System.out.println("--------------------------------------------------------------------\n");
                                }
                                else {
                                    System.out.println("Please input p q e, integers");
                                    UInput.next();
                                }
                            }
                            case "c" -> {
                                System.out.println("Charlie: Bye!");
                                break optionalMenu;
                            }
                            default -> {
                                System.out.println("Please enter 1,2,3 or c.");
                            }
                        }
                    }

                    //Checking input
                    if (UInput.hasNextInt()) { //checking to see the user entered INT
                        System.out.println("Chosen key: " + Password.generator(UInput.nextInt(), UInput.nextInt(), UInput.nextInt()));
                    }
                    else if ((UInput.nextLine().equals("") || UInput.nextLine().equals(" ")) && p>1) {
                        System.out.println("Old key: ");
                    }
                    else {
                        System.out.println("Please enter p q e separated by spaces");
                        UInput.next();
                    }
                }
                case "q" -> { //if user entered q
                    System.out.println("Goodbye. Have a nice day!");
                    break mainMenu;
                }
            }
        }


    }

    public static void generateKeyPair1() {
        p = chooseRL(primesList);
        q = chooseRL(primesList);
        while (p==q) {q = chooseRL(primesList);}
        n = calcN(p,q);
        phi = calcPhi(p,q);
        e = chooseE(phi);
        d = modInverse(e, phi);
    }
    public static void generateKeyPair2() {
        p1 = chooseRL(primesList);
        q1 = chooseRL(primesList);
        while (p1==q1) {q1 = chooseRL(primesList);}
        n1 = calcN(p1,q1);
        phi1 = calcPhi(p1,q1);
        e1 = chooseE(phi1);
        d1 = modInverse(e1, phi1);
    }

    ///Helper functions regarding prime generation + checks --------------------------------
    public static void generatePrimes(int min, int max) {
        for (int i = min; i < max; i++) {
            if (checkIsPrime(i)) {
                primesList.add(i);
            }
        }
    }

    public static boolean checkIsPrime(int nr) {
        for (int i = 2; i < Math.sqrt(nr); i++) {
            if (nr % i == 0) {return false;}
        }
        return true;
    }

    public static boolean checkIsD(int e,int d,int phi) {
        return (((e*d)%phi) == 1);
    }

    //MERSENNE NR TESTING AND TRIALS
    //Check is prime for Big Integer - too big time complexity
//    public static boolean checkIsPrime(BigInteger nr) {
//        for (int i = 2; (BigInteger.valueOf(i).compareTo(nr.sqrt())) == -1; i++) {
//            if (nr.mod(BigInteger.valueOf(i)) == BigInteger.valueOf(i)) {return false;}
//        }
//        return true;
//    }

//    //Different D calculation - works?
//    public static int[] egcd( int a, int b) {
//        int[] ret = new int[3];
//        int g,x,y;
//        if (a==0) {
//            ret[0] = b;
//            ret[1] = 0;
//            ret[2] = 1;
//            return ret;
//        }
//        else {
//            int[] set = egcd(b % a, a);
//            g = set[0];
//            y = set[1];
//            x = set[2];
//            ret[0] = g;
//            ret[1] = (x-((int)Math.floor(b/a)*y));
//            ret[2] = y;
//            return ret;
//        }
//    }
//
//    public static int modular_inv(int e, int phi) {
//        int[] ret;
//        int g,x,y;
//        ret = egcd(e, phi);
//        g = ret[0];
//        x = ret[1];
//        y = ret[2];
//
//        if (g!=1) {
//            System.out.println("Modular inverse doesn't exist...");
//            return -1;
//        }
//        else {
//            return x%phi;
//        }
//
//    }

    static int modInverse(int atest, int mtest)
    {
        int[] xtest, ytest;
        xtest = new int[1];
        ytest = new int[1];
        int gtest = gcdExtended(atest, mtest, xtest, ytest);
        if (gtest != 1) {
            System.out.println("! Inverse doesn't exist");
        }
        else
        {

            // m is added to handle negative x
            int restest = (xtest[0] % mtest + mtest) % mtest;
            //System.out.println("Modular multiplicative inverse is " + restest);
            return restest;
        }
        return -1;
    }

    // Function for extended Euclidean Algorithm
    static int gcdExtended(int atest, int btest, int[] xtest, int[] ytest)
    {

        // Base Case
        if (atest == 0)
        {
            xtest[0] = 0;
            ytest[0] = 1;
            return btest;
        }

        // To store results of recursive call
        int[] x1test, y1test;
        x1test = new int[1];
        y1test = new int[1];
        int gcdtest = gcdExtended(btest % atest, atest, x1test, y1test);

        // Update x and y using results of recursive
        // call
        xtest[0] = y1test[0] - (btest / atest) * x1test[0];
        ytest[0] = x1test[0];

        return gcdtest;
    }

//    public static int findD(int e, int phi) {
//        int ans = calculateD(e,phi)%phi;
//        if (ans<0) {ans+=phi;}
//        return ans;
//    }


    //recursive check is coprime, using HCD
    public static boolean checkIsCoPrime(int e, int phi) {
        if (e != 0) {
            //System.out.println("E is: " + e);
            //System.out.println("phi is:" + phi);
            return checkIsCoPrime(phi % e, e);
        } else {
            return (phi==1) ? true : false;
        }
    }

    ///RSA - functions ----------------------------------------------

    //Choose random list item
    public static int chooseRL(ArrayList<Integer> list) {
        rand=new Random();
        return list.get(rand.nextInt(list.size()));
    }
    //Calculate n
    public static int calcN(int p, int q) {return p*q;}
    //Calculate phi
    public static int calcPhi(int p, int q) {return (p-1)*(q-1);}



    //Choose e
    public static int chooseE(int phi) {
        int potE = ((int)(Math.random()*(phi-4))+3);
        while (true) {
            //return 3;
            if (checkIsCoPrime(potE,phi)) {return potE;}
            else {potE=((int)(Math.random()*(phi-4))+3);}
        }
    }

    //Calculate d - Private key - OLD METHOD - used to return -1 sometimes
//    public static int calculateD(int e, int phi) {
//        e = e % phi;
//        for (int i = 0; i < phi; i++) {
//            if (((e%phi)*(i%phi)) % phi == 1) {
//                return i;
//            }
//        }
//        return -1;
//    }

    public static BigInteger encrypt(BigInteger m, int e, int n) {
        return m.pow(e).mod(BigInteger.valueOf(n));
    }

    public static BigInteger decrypt(BigInteger c, int d, int n) {
        return c.pow(d).mod(BigInteger.valueOf(n));
    }
}
