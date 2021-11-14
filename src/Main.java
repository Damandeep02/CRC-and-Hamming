import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Choose the Error detection and Correction method");
        System.out.println("For Cyclic Redundancy Check (CRC) press '1'.\nFor Hamming Code press '2'.");
        int n = input.nextInt();
        switch (n) {
            case 1: {
                Crc();
                break;
            }
            case 2: {
                HammingCode();
                break;
            }
        }

    }
    private static void Crc() {
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

    static void HammingCode(){
        Scanner sn=new Scanner(System.in);
        System.out.println("Enter your Dataword");
        String binaryData=sn.nextLine();
       int i;
        for(i=0;i+4<=binaryData.length();i=i+4) {
            String dataword=binaryData.substring(i,i+4);
            System.out.println("\nThe four bits are "+dataword);
            senderCode(dataword,sn);
        }
        if(binaryData.length()%4!=0) {
            String bits=binaryData.substring(i,binaryData.length());
            for(int j=bits.length();j<4;j++) {
                bits=bits+"0";
            }
            System.out.println("\nThe four bits are "+bits);
            senderCode(bits,sn);
        }

    }
    private static void senderCode(String dataword, Scanner sn) {


        int codeword[]=new int[8];
        codeword[3]=(int)dataword.charAt(3)-48;
        codeword[5]=(int)dataword.charAt(2)-48;
        codeword[6]=(int)dataword.charAt(1)-48;
        codeword[7]=(int)dataword.charAt(0)-48;
            if((codeword[3]+codeword[5]+codeword[7])%2==0) {
                codeword[1]=0;
            }
            else {
                codeword[1]=1;
            }
            if((codeword[3]+codeword[6]+codeword[7])%2==0) {
                codeword[2]=0;
            }
            else {
                codeword[2]=1;
            }
            if((codeword[5]+codeword[6]+codeword[7])%2==0) {
                codeword[4]=0;
            }
            else {
                codeword[4]=1;
            }


        System.out.print("The sent data is ");
        for(int i=1;i<=7;i++) {
            System.out.print(codeword[i]);
        }System.out.println();
        System.out.println("Enter the probability of an error to occur");
        double p=sn.nextFloat();

        double randProb=Math.random();

        int randBitPos=0;
        while(randBitPos<1 || randBitPos>7) {
            randBitPos=(int)Math.ceil(Math.random()*10);

        }

        if (randProb<=p) {
            if(codeword[randBitPos]==1) {
                codeword[randBitPos]=0;
            }
            else {
                codeword[randBitPos]=1;
            }
        }
        System.out.print("The recieved data is ");
        for(int i=1;i<=7;i++) {
            System.out.print(codeword[i]);
        }System.out.println();

        String parityCode="";
            if((codeword[1]+codeword[3]+codeword[5]+codeword[7])%2==0) {
                parityCode+="0";
            }else {
                parityCode+="1";
            }
            if((codeword[2]+codeword[3]+codeword[6]+codeword[7])%2==0) {
                parityCode="0"+parityCode;
            }else {
                parityCode="1"+parityCode;
            }
            if((codeword[4]+codeword[5]+codeword[6]+codeword[7])%2==0) {
                parityCode="0"+parityCode;
            }else {
                parityCode="1"+parityCode;
            }

        int decimalValue = Integer.parseInt(parityCode, 2);
        if(decimalValue==0) {
            System.out.println("No error found");
        }
        else {
            System.out.println("The error is found at position "+decimalValue);
            if(codeword[decimalValue]==1) {
                codeword[decimalValue]=0;
            }
            else {
                codeword[decimalValue]=1;
            }
            System.out.print("The corrected codeword is ");
            for(int i=1;i<=7;i++) {
                System.out.print(codeword[i]);
            }System.out.println();
        }

    }
}
