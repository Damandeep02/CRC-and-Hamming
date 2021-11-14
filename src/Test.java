import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Test {
    private static void crc() {
        Scanner sn = new Scanner(System.in);

        System.out.print("Simulating CRC(CRC8) error Detection\nEnter the number of hops(1 or 2).\n");
        int hops = sn.nextInt();
        System.out.println("Enter the Dataword");
        String binaryData = sn.next();
        int i;
        for (i = 0; i + 16 <= binaryData.length(); i = i + 16) {
            String dataword = binaryData.substring(i, i + 16);
            System.out.println("\nThe 16 bits are " + dataword);
            System.out.println("For Hop-1:-\n");
            String dataword1 = dataword + "00000000";
            String remainder = binaryXorDivision(dataword1, "100000111");
            System.out.println("Remainder = " + remainder);
            dataword1 = dataword + remainder;
            System.out.println("The sent dataword is: " + dataword1);
            System.out.println("Enter Prob for Bit flip");
            double prob = sn.nextDouble();
            dataword1 = Flipping(dataword1, prob);
            System.out.println("Recieved Dataword is:- "+dataword1);
            String syndrome = binaryXorDivision(dataword1, "100000111");
            if (Objects.equals(syndrome, "00000000")) {
                System.out.println("No Error Found");
            } else {
                System.out.println("Error Found.Discard!");
            }
            if (hops == 2) {
                System.out.println("For Hop 2:- \n");
                dataword1 = dataword1.substring(0, 16);
                String dataword2 = dataword1 + "00000000";
                remainder = binaryXorDivision(dataword1, "100000111");
                System.out.println("Remainder = " + remainder);
                dataword2 = dataword1 + remainder;
                System.out.println("The sent codeword is: " + dataword2);
                System.out.println("Enter Prob for Bit flip");
                prob = sn.nextDouble();
                dataword2 = Flipping(dataword2, prob);
                System.out.println("Recieved Codeword is:- "+dataword2);
                syndrome = binaryXorDivision(dataword2, "100000111");
                if (Objects.equals(syndrome, "00000000")) {
                    System.out.println("No Error Found");
                } else {
                    System.out.println("Error Found.Discard!");
                }
            }
        }
        if (binaryData.length() % 16 != 0) {
            String dataword = binaryData.substring(i, binaryData.length());
            for (int j = dataword.length(); j < 16; j++) {
                dataword = dataword + "0";
            }
            System.out.println("\nThe 16 bits are " + dataword);
            System.out.println("For Hop-1:-\n");
            String dataword1 = dataword + "00000000";
            String remainder = binaryXorDivision(dataword1, "100000111");
            System.out.println("Remainder = " + remainder);
            dataword1 = dataword + remainder;
            System.out.println("The sent dataword is: " + dataword1);
            System.out.println("Enter Prob for Bit flip");
            double prob = sn.nextDouble();
            dataword1 = Flipping(dataword1, prob);
            System.out.println("Recieved Dataword is:- "+dataword1);
            String syndrome = binaryXorDivision(dataword1, "100000111");
            if (Objects.equals(syndrome, "00000000")) {
                System.out.println("No Error Found");
            } else {
                System.out.println("Error Found.Discard!");
            }
            if (hops == 2) {
                System.out.println("For Hop 2:- \n");
                dataword1 = dataword1.substring(0, 16);
                String dataword2 = dataword1 + "00000000";
                remainder = binaryXorDivision(dataword1, "100000111");
                System.out.println("Remainder = " + remainder);
                dataword2 = dataword1 + remainder;
                System.out.println("The sent codeword is: " + dataword2);
                System.out.println("Enter Prob for Bit flip");
                prob = sn.nextDouble();
                dataword2 = Flipping(dataword2, prob);
                System.out.println("Recieved Codeword is:- "+dataword2);
                syndrome = binaryXorDivision(dataword2, "100000111");
                if (Objects.equals(syndrome, "00000000")) {
                    System.out.println("No Error Found");
                } else {
                    System.out.println("Error Found.Discard!");
                }
            }


        }



    }


    public static String Flipping(String str, double prob) {
        char[] characters = new char[str.length()];
        str.getChars(0, str.length(), characters, 0);
        Random rand = new Random(System.currentTimeMillis());
        double mul = rand.nextFloat();
        System.out.println("Given Prob=>" + prob + "\nRandom Prob=>" + mul);
        prob = prob * mul;
        System.out.println("Final Prob=> Given Prob X Random Prob =>" + prob);
        if (prob < 0.5 && prob > 0.2) {
            for (int i = 0; i < characters.length; i++) {
                if (characters[i] == '1') {
                    characters[i] = '0';
                    break;
                }

            }
        } else if (prob > 0.5 && prob < 0.7) {
            int flag = 0;
            for (int i = 0; i < characters.length; i++) {
                if (characters[i] == '1') {
                    characters[i] = '0';
                    flag++;
                    if (flag == 2) {
                        break;
                    }
                }
            }
        } else if (prob < 1 && prob > 0.7) {
            int flag = 0;
            for (int i = 0; i < characters.length; i++) {
                if (characters[i] == '1') {
                    characters[i] = '0';
                    flag++;
                    if (flag == 4) {
                        break;
                    }
                } else if (characters[i] == '0') {
                    characters[i] = '1';
                    flag++;
                    if (flag == 4) {
                        break;
                    }
                }
            }
        }
        StringBuilder ret = new StringBuilder();
        for (char c : characters) {
            ret.append(c);
        }
        System.out.println("Sent CodeWord: " + str);
        return ret.toString();
    }



    public static String binaryXorDivision(String dividend, String divisor) {
        String temp = dividend.substring(0, divisor.length() - 1);
        for (int i = divisor.length() - 1; i < dividend.length(); i++) {
            temp = temp + dividend.charAt(i);
            if (temp.charAt(0) == '1') {
                temp = xor(temp, divisor);
            } else {
                temp = xor(temp, "000000000");
            }
            temp = temp.substring(1);
        }
        return temp;
    }

    public static String xor(String str1, String str2) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str1.length(); i++) {
            if (str1.charAt(i) == str2.charAt(i)) {
                result.append("0");
            } else {
                result.append("1");
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        crc();
    }
}

