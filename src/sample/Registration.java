
package sample;

import java.util.Scanner;

public class Registration {

    public static void main(String[] args) {
        System.out.println("Registration of iHealth\nPlease fill in the required fields");
        Scanner sc = new Scanner(System.in);
        
        //User name input
        System.out.print("Full name: ");
        String user_name = sc.nextLine();
        
        boolean valid = false;
        
        while(!valid){
            System.out.print("IC Number: ");
            String ic_number = sc.nextLine();
            //Check the validity of IC Number input
            String[] icPart = ic_number.split("-");
            if(icPart[0].length()!=6 || icPart[1].length()!=2 || icPart[2].length()!=4){
                System.out.println("Invalid IC Number");
            }else{
                valid = true;
            }
        }
        //Phone number input
        System.out.print("Phone Number: ");
        String phone_number = sc.nextLine();
        
        //Email address input & validity check
        valid = false;
        while(!valid){
            System.out.print("E-mail Address: ");
            String user_email = sc.nextLine();
            for(int i=0;i<user_email.length();i++){
                if(user_email.charAt(i)=='@'){
                    for(int j=i;j<user_email.length();j++){
                        if(user_email.charAt(j)=='.'){
                            valid = true;
                            break;
                        }
                    }
                }
            }
            if(!valid){
                System.out.println("Invalid e-mail address");
            }
        }
        String password;
        valid = false;
        while(!valid){
            System.out.println("Password: ");
            password = sc.nextLine();
            if(password.length()<8){
                System.out.println("Password is too short");
                valid = true;
            }
            
        }
        System.out.println("Thank you for registering!");
    }
    
}
