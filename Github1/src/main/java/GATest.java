
import java.util.Scanner;

public class GATest {
    public static void main(String args[]){        
        char[] password1 = {'D','e','e','p',' ','L','e','a','r','n','i','n','g',' ','2','0','2','2'};
        char[] password2 = {'D','e','e','p','L','e','a','r','n','i','n','g'};
        int populasyonSayisi1 = 100;
        int populasyonSayisi2 = 500;
        int elitizmSayisi1 = 20;
        int elitizmSayisi2 = 50;
        int toplamNesil = 0;
        
                
        for (int i = 0 ; i<6; i++){
            System.out.println("--------------------------------------------------------");
            if (i<=2){
                System.out.println(String.format("%d populasyon sayili, %d elitizim sayili Deep Learning 2022 icin %d deneme",populasyonSayisi2,elitizmSayisi2,i+1));
                GACozdur test = new GACozdur(password1,populasyonSayisi2,password1.length,elitizmSayisi2);
                int a = test.Sonuc();
                toplamNesil += a;
                if (i == 2){
                    System.out.println("3 kere cozdurunce ortaya cikan ortalama nesil sayisi: " + toplamNesil / 3);                    
                }                
            }
            if (i>2){
                if (i == 3){
                    toplamNesil = 0 ;
                }
                System.out.println(String.format("%d populasyon sayili, %d elitizim sayili DeepLearning icin %d deneme",populasyonSayisi1,elitizmSayisi1,i-2));                
                GACozdur test = new GACozdur(password2,populasyonSayisi1,password2.length,elitizmSayisi1);                
                int a = test.Sonuc();
                toplamNesil += a;
                if (i == 5 ){
                    System.out.println("3 kere cozdurunce ortaya cikan ortalama nesil sayisi: " + toplamNesil / 3);                    
                } 
            }
        }
        
    }
}
