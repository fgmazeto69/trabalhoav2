package fag;

import java.util.ArrayList;
import java.util.List;

public class tre {
   public static void main(String[] args) {
	   List<String> frutas = new ArrayList<>();
	     frutas.add("Maça");
	     frutas.add("Banana");
	     frutas.add("Laranja");
	     frutas.add("Manga");
	     frutas.add("Pêra");
	     
	     System.out.println("Listas de Frutas:");
	     for (String fruta : frutas) {
	    	 System.out.println(fruta);
     }
   }
}
