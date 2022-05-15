import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
public class GenetikAlgorithms {
    int populasyon_sayisi;
    int HedefFVDegeri ;
    int elitizm_sayisi;
    char[] password = new char[HedefFVDegeri];   //şifremizi array olarak oluşturduk. 
    ArrayList<char [][]> butunnesiller = new ArrayList<char[][]>();    //butun nesilleri tutacagimiz arraylist
    public GenetikAlgorithms(char[] password, int populasyon_sayisi, int HedefFVDegeri, int elitisim_sayisi){
        this.HedefFVDegeri = HedefFVDegeri;
        this.password = password;
        this.populasyon_sayisi = populasyon_sayisi;
        this.elitizm_sayisi = elitisim_sayisi;   
    }
    public int FVHesapla(char[] kromozom){         // sifreye ne kadar uzaklikta oldugumuzu tutacagimiz metot
        int FV = 0;                                // default olarak 0 atadim
        if (kromozom.length != password.length){   // parametre olarak girilen tahmin ile sifre ayni uzunlukta degilse hata veriyor
            System.out.print("Tahmininiz ve sifre ayni uzunlukta degil"); // ama aynı uzunlukta olmama ihtimali yok koddan çıkartabilirim
            return -1;                   
        }
        for (int i = 0 ; i < password.length; i++ ){      // sifrenin uzunlugu kadar donecek bir dongu
            if (password[i] == kromozom[i]){              // eger ayni konumdaki harfler tutuyorsa fv 1 artiyor
                FV += 1;
            }
            else{
                FV += 0 ;
            }           
        }
        return FV;                                          // son olarak fv degerimizi donduruyoruz.
    }
    
    public char GenAta(){
        char gen = 0;
        String alphabet = "0123456789AaBbCcDdEeFfGgHhIiJjKkLlMmNnOoPpRrSsTtUuVvYyZzWwQq ";              
        Random r = new Random();
        gen = alphabet.charAt(r.nextInt(alphabet.length()));        
        return gen;
    }
    
    public void nesilOlustur(){
        char[][] nesil = new char[populasyon_sayisi][] ;
        for (int i = 0 ; i<populasyon_sayisi; i++){
            char[] kromozom = new char[HedefFVDegeri];
            for (int j = 0 ; j<HedefFVDegeri; j++){
                char xd = GenAta();
                kromozom[j] = xd;
            }
            nesil[i] = kromozom;
        }
        butunnesiller.add(nesil);
        SonNesilSirala();
    }
    public char[] MutasyonYap(char [] kromozom){
        float cark = MutasyonOlasiligi();
        if (cark < 0.1){        
            char mutasyon = GenAta();
            Random r = new Random();
            int sayi = r.nextInt(HedefFVDegeri);
            kromozom[sayi] = mutasyon;
        }
        return kromozom;
    }
    public int randomKesmebitis(){
        Random r = new Random();
        int bitis = r.nextInt(HedefFVDegeri);
        return bitis;
    }
    public int randomKesmebaslangic(){
        Random r = new Random();
        int baslangic = r.nextInt(HedefFVDegeri);
        return baslangic;
    }
    
    public int RandomKromozom(){
        Random r = new Random();
        int kromozom = r.nextInt(populasyon_sayisi/2);
        return kromozom;
    }
    public float MutasyonOlasiligi(){
        Random r = new Random();
        float mutasyonOlasiligi = r.nextFloat();
        return mutasyonOlasiligi;
    }
    public int kacNesilde(){
        return butunnesiller.size();
    }
    public void herNesilEnYakinCozum(){
        for(int i = 0 ; i < butunnesiller.size();i++ ){
            int max = 0 ;
            for (int j = 0 ; j < butunnesiller.get(i).length; j++ ){
                int xd =FVHesapla(butunnesiller.get(i)[j]);
                if (xd > max){
                    max = xd;
                }
            }
            System.out.print(max + " ");
        }
        System.out.println();
    }
    public void SonNesilSirala(){
        int nesil_sayisi = butunnesiller.size();                    //nesil sayısı
        char [][] son_nesil = butunnesiller.get(nesil_sayisi -1 );  // son nesil
        for (int i = 1; i < son_nesil.length;i++  ){                // son nesilin bütün elemanlarının fvsini hesaplayacağız
            int value = FVHesapla(son_nesil[i]);                    // değer = i.indexteki elemanın fv değeri
            char[] eleman = son_nesil[i];                           // eleman nesilin i.indexteki kromozom
            int j = i-1;                                            // j i'nin 1 eksiği
            while (j >= 0 && FVHesapla(son_nesil[j]) < value){      // j 0 olana kadar ve son_nesil[j]nin değeri i.değerinkinden yüksek ise
                son_nesil[j+1] = son_nesil[j];                      // 
                j = j-1;
            }
            son_nesil[j+1] = eleman;            
        }
        butunnesiller.remove(nesil_sayisi - 1);
        butunnesiller.add(son_nesil);
    }
    public void Caprazlama(){
        char[][] yeni_nesil = new char[populasyon_sayisi][] ;                            // 50 kromozomdan oluşacak bir yeni nesil oluşturucaz
        int nesil_sayisi = butunnesiller.size();                          // kaç nesil olduğunu aliyoruz.
        char[][] son_nesil = butunnesiller.get(nesil_sayisi -1 );        // son nesli alıyoruz
        for (int k = 0 ; k<elitizm_sayisi;k++){                                       // ilk 26 tanesini elit olarak yeni nesile alıyoruz.
            yeni_nesil[k]=son_nesil[k]; 
        }
        for(int d = elitizm_sayisi; d < populasyon_sayisi; d++ ){                                // kalanları çaprazlayarak doldurucaz 
            int baslangic = randomKesmebaslangic();                       // kesmenin yapılacağı başlangıç ve bitiş değerlerini alıyoruz
            int bitis = randomKesmebitis();
            bitis =Math.max(baslangic,bitis);
            baslangic = Math.min(baslangic,bitis);
            int ilk_kromozom_konum =  RandomKromozom();                   // kromozomların konumunu alıyoruz
            int ikinci_kromozom_konum =  RandomKromozom(); 
        
            char[] ilk_kromozom = son_nesil[ilk_kromozom_konum];         // atamaları yapıyoruz
            char[] ikinci_kromozom = son_nesil[ikinci_kromozom_konum];
            
            char[] ilk_cocuk = ilk_kromozom.clone() ;                    // ilk kromozomu ilk cocuga cloneluyoruz
            for (int i = baslangic; i<bitis+1;i++){                    // baslangıc ve bitis araligindaki kisima ie ikinci kromozomu yerlestiriyoruz
                ilk_cocuk[i]=ikinci_kromozom[i];                
            }
            char[] ikinci_cocuk = ikinci_kromozom.clone();               // ikinci cocuk icin ise tam tersini yapıyoruz
            for (int i = baslangic; i<bitis+1;i++){
                ikinci_cocuk[i]=ilk_kromozom[i];
            }
            int ilk_cocuk_fv = FVHesapla(ilk_cocuk);
            int ikinci_cocuk_fv = FVHesapla(ikinci_cocuk);
            
            if (ilk_cocuk_fv > ikinci_cocuk_fv){
                ilk_cocuk = MutasyonYap(ilk_cocuk);
                yeni_nesil[d] = ilk_cocuk;
            }
            else{
                ikinci_cocuk = MutasyonYap(ikinci_cocuk);
                yeni_nesil[d] = ikinci_cocuk;
            }
        }       
        butunnesiller.add(yeni_nesil);                                   // yeni olusan nesili yeni nesillere ekliyoruz
        SonNesilSirala();                                                // ve yeni olusturulan nesili siraliyoruz.
        
    }
}
