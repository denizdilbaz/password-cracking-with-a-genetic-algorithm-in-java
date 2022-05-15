public class GACozdur {
    int populasyon_sayisi;
    int HedefFVDegeri ;
    int elitizm_sayisi;
    char[] password = new char[HedefFVDegeri];   //şifremizi array olarak oluşturduk. 
    public GACozdur(char[] password, int populasyon_sayisi, int HedefFVDegeri, int elitizm_sayisi){
        this.HedefFVDegeri = HedefFVDegeri;
        this.password = password;
        this.populasyon_sayisi = populasyon_sayisi;
        this.elitizm_sayisi = elitizm_sayisi;   
    }    
    public int Sonuc(){
        long startTime = System.currentTimeMillis();
        GenetikAlgorithms main = new GenetikAlgorithms(password,populasyon_sayisi,password.length,elitizm_sayisi);
        main.butunnesiller.clear();
        main.nesilOlustur();
        int nesil_sayisi = main.butunnesiller.size();
        char[] ilk_kromozom = main.butunnesiller.get(nesil_sayisi -1 )[0];
        int ilk_kromozom_fv = main.FVHesapla(ilk_kromozom);
        while(ilk_kromozom_fv != password.length ){
            main.Caprazlama();
            nesil_sayisi = main.butunnesiller.size();
            ilk_kromozom = main.butunnesiller.get(nesil_sayisi-1)[0];
            ilk_kromozom_fv = main.FVHesapla(ilk_kromozom);        
        }
        System.out.print("Sifre Bulundu: ");
        for (int i = 0 ; i < ilk_kromozom.length;i++){
            System.out.print(ilk_kromozom[i]);        
        }
        System.out.println();
        System.out.println("Bulunan Nesil: " + main.kacNesilde());
        System.out.print("Nesillerin en yakin fv degerleri: ");        
        main.herNesilEnYakinCozum();
        long endTime = System.currentTimeMillis();
        long estimatedTime = endTime - startTime;
        System.out.println("Gecen sure " + estimatedTime + " milisaniye");
        return main.kacNesilde();
        
        
    }
    
}
