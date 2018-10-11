import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;



public class App {

    static void lineTower() {
        String line = "###";
        int n = 6;
        for (int i = 0; i <= n; i++) {
            System.out.println(line);
        }
    }
    static void tower2(int a) {
        String line1 = "##";
        for (int i = 0; i < a; i++) {
            line1 = line1 + "#";
            System.out.println(line1);
        }
    }
//        recurtion with IF
//        static void towerIf ( int c){
//            if (c < 1) {
//                return;
//            }
//            String line2 = "##";
//            System.out.println(line2);
//            towerIf(c - 1);
//
//        }




    public static void main(String[] args) {

    }
}
