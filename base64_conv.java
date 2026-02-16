import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner; 
import java.io.FileOutputStream;



public class base64_conv {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner (System.in);
        String myFile = sc.next();
        if (myFile.endsWith(".b64")){
            from_b64(myFile);
        }
        else {
            to_b64(myFile);
        }
    }

    public static void to_b64 (String myFile) {

        char[] encode = {            
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            '0','1','2','3','4','5','6','7','8','9',
            '+','/'
        };

        int currConct;
        int mask = 63;

        
        try (
            FileInputStream fis = new FileInputStream(myFile);
            FileOutputStream fos = new FileOutputStream(myFile+".b64")
        ) {
            
            byte[] curr = new byte[3];
            int bytesRead;

            while (true) {
                bytesRead = fis.read(curr);     //read 3 bytes from the file and store in curr, returns number of bytes read

                if (bytesRead == -1) {
                    break; // EOF
                }

                if (bytesRead == 2) curr[2]=0;
                if (bytesRead == 1) {
                    curr[1]=0;
                    curr[2]=0;
                }

                currConct = 0;
                for(int i = 0;i<3;i+=1){
                    currConct = (currConct << 8) | (curr[i] & 0xff);   //concatinate the 3 bytes read into curr into a integer called currConct
                }

                int i = 3;
                int till = 0;

                if (bytesRead == 2) till = 1;    //limit iteration till the first 3 sets of 6 bits
                if (bytesRead == 1) till = 2;    //limit iteration till the first 2 sets of 6 bits

                while(i>=till){
                    int temp = currConct;
                    temp = temp >> 6*i;         //shift temp left so that the required 6 bits are at the rightmost end
                    int res = temp & mask;      //remove the bits that are to the left of the required 6 bits, res now has the exact 6 bits we need
                    // System.out.print(encode[res]);
                    fos.write(encode[res]);     //write the corresponding character from the b64 encoding to the file
                    i-=1;
                }

                //padding
                if (bytesRead == 2) fos.write('=');   //System.out.print("=");
                if (bytesRead == 1) {fos.write('='); fos.write('='); }  //System.out.print("==");


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void from_b64(String myFile){

        int currConct;
        int mask = 255;


        try (
            FileInputStream fis = new FileInputStream(myFile);
            FileOutputStream fos = new FileOutputStream(myFile.substring(0, myFile.length()-4))
        ) {

            byte[] curr = new byte[4];
            int bytesRead;
            int till;

            while (true) {
                bytesRead = fis.read(curr);

                if (bytesRead == -1) {
                    break; // EOF
                }               

                currConct = 0;
                till = 0;

                for(int i = 0;i<4;i+=1){
                    if (curr[i] >= 97) curr[i] = (byte)(curr[i] - 71);
                    else if (curr[i] >= 65) curr[i] = (byte)(curr[i] - 65);
                    else if (curr[i] == 61) {till += 1; currConct <<= 6; continue;}
                    else if (curr[i] >= 48) curr[i] = (byte)(curr[i] + 4);
                    else if (curr[i] == 47) curr[i] = (byte)(curr[i] + 16);
                    else if (curr[i] == 43) curr[i] = (byte)(curr[i] + 19);

                    currConct = (currConct << 6) | (curr[i]  & 0x3f);   //concatinate the 4 bytes read into curr into a integer called currConct

                }

                for (int i = 2; i>=till; i-=1){
                    int temp = currConct;
                    temp = temp >> 8*i;         //shift temp left so that the required 8 bits are at the rightmost end
                    int res = temp & mask;      //remove the bits that are to the left of the required 8 bits, res now has the exact 8 bits we need
                    // System.out.print(encode[res]);
                    fos.write(res);     //write the corresponding character from the b64 encoding to the file


                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
