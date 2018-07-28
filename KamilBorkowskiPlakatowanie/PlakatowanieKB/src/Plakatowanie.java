import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Plakatowanie {
    
    private int iloscPlakatow=0;
    private int iloscBlokow;
    private int [] stos;
    private int plakatyNaStosie=0;

    private final String nazwaPliku = "pla10b.in";
    
    public static void main(String[] arqs){ 
        long start = System.currentTimeMillis();
        Plakatowanie p = new Plakatowanie();
        p.zapisz(p.doRoboty());
        long stop = System.currentTimeMillis();
        System.out.println("Czas działa w milisekundach: " + (stop-start));
    }
    
    private int doRoboty(){
        ClassLoader classLoader = getClass().getClassLoader();
        File plik = new File(classLoader.getResource(nazwaPliku).getFile());
        
        try {
            Scanner scan = new Scanner(plik); //pobranie wartości z pliku pla.in
            String linia = scan.nextLine();
            StringTokenizer st = new StringTokenizer(linia);
            iloscBlokow = Integer.parseInt(st.nextToken());
            stos = new int[iloscBlokow];
            
            for(int i=0;i<iloscBlokow;i++){
                linia = scan.nextLine();
                st = new StringTokenizer(linia);
                st.nextToken(); //pominięcie długości budynku, ważna tylko wysokość
                int wysokosc = Integer.parseInt(st.nextToken());
                while (plakatyNaStosie != 0 && stos[plakatyNaStosie-1] > wysokosc) {//czy poprzednie budynki były wyższe? 
                    iloscPlakatow++; //jeśli tak to nakładamy na ich górną część plakat
                    plakatyNaStosie--;
                }
                if (plakatyNaStosie == 0 || stos[plakatyNaStosie-1] < wysokosc){ //analizowany budynek jest nieredundantny
                    stos[plakatyNaStosie] = wysokosc; //dokładamy nowy plakat na stos
                    plakatyNaStosie++;
                    }
            }
            System.out.println(plakatyNaStosie + iloscPlakatow);
            return plakatyNaStosie+iloscPlakatow;
        } catch (FileNotFoundException e){return 0;}
    }
    private void zapisz(int wynik){      
        try {//zapisany plik znajduje się w folderze projektu
            FileWriter fw = new FileWriter("pla.out");
            PrintWriter pw = new PrintWriter(fw);
            pw.println(wynik);
            pw.close();
        }catch(IOException e){}
    }
}