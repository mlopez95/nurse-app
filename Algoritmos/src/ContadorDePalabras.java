import java.io.*;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ContadorDePalabras {

    public static void main(String[] args) {
        System.out.println("Algoritmo de contador de palabras...");

        //Leemos el archivo que esta en la carpeta resources
        File file = new File("resources/archivo.txt");
        Map<String, Integer> palabras = new HashMap<>();

        try {
            Reader targetReader = new FileReader(file);
            BufferedReader fileInputStream = new BufferedReader(targetReader);
            String linea;

            //Iteramos linea por linea
            while ((linea= fileInputStream.readLine())!=null){

                //hacemos un split por los espacios
                String[] arrayPalabras = linea.split(" ");

                //iteramos sobre las palabras que obtuvimos
                for (String palabra: arrayPalabras) {

                    //Reemplazamos los caracteres especiales de cada palabra (Dejamos solo las letras y numeros)
                    palabra= palabra.replaceAll("[^a-zA-Z0-9 -]", "").toLowerCase();

                    //Validamos que la palabra tenga por lo menos una letra
                    if(palabra.length() > 1) {
                        if (palabras.containsKey(palabra)) {
                            palabras.put(palabra, palabras.get(palabra) + 1);
                        } else {
                            palabras.put(palabra, 1);
                        }
                    }
                }

            }
            if(palabras.isEmpty())
                System.out.println("No se encontraron palabras en el archivo");
            else
                obtenerValorMasRepetido(palabras);

        } catch (FileNotFoundException e) {
            System.out.println("Ocurrio un error al leer el archivo");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {

        }
    }

    private static void obtenerValorMasRepetido(Map<String, Integer> mapaSinOrdenar){
        Map<String, Integer> result = mapaSinOrdenar.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        Map.Entry<String,Integer> entry = result.entrySet().iterator().next();
        Integer valueMax = entry.getValue();

        System.out.println("La(s) palabra(s) mas repetida(s) : ");
        result.forEach((k,v)->{
            if(valueMax.equals(v)) {
                System.out.println(k+" con "+v+" apariciones");
            }
        });
    }
}
