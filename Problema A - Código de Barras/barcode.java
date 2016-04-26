import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Collection;
import java.lang.Math;

class BarCode {
    public static void main(String[] args) {
        String entrada;
        String saida = "";
        String binario;
        Integer dig;
        boolean par;
        int verificadorLido;
        int primeiroDigito;
        int somaImpares = 0;
        int somaPares = 0;
        int terceiroValor;
        int digitoVerificador;
        
        // MAPA PARA OS CODIGOS BINARIOS ASSOCIADOS AOS DIGITOS DE PARIDADE IMPAR
        // DADOS DA TABELA 2 -> LADO ESQUERDO -> PARIDADE IMPAR
        HashMap<String, Integer> mapEsquerdaImpares = new HashMap<String, Integer>();
        mapEsquerdaImpares.put("0001101", 0);
        mapEsquerdaImpares.put("0011001", 1);
        mapEsquerdaImpares.put("0010011", 2);
        mapEsquerdaImpares.put("0111101", 3);
        mapEsquerdaImpares.put("0100011", 4);
        mapEsquerdaImpares.put("0110001", 5);
        mapEsquerdaImpares.put("0101111", 6);
        mapEsquerdaImpares.put("0111011", 7);
        mapEsquerdaImpares.put("0110111", 8);
        mapEsquerdaImpares.put("0001011", 9);
        
        // MAPA PARA OS CODIGOS BINARIOS ASSOCIADOS AOS DIGITOS DE PARIDADE PAR
        // DADOS DA TABELA 2 -> LADO ESQUERDO -> PARIDADE PAR
        HashMap<String, Integer> mapEsquerdaPares = new HashMap<String, Integer>();
        mapEsquerdaPares.put("0100111", 0);
        mapEsquerdaPares.put("0110011", 1);
        mapEsquerdaPares.put("0011011", 2);
        mapEsquerdaPares.put("0100001", 3);
        mapEsquerdaPares.put("0011101", 4);
        mapEsquerdaPares.put("0111001", 5);
        mapEsquerdaPares.put("0000101", 6);
        mapEsquerdaPares.put("0010001", 7);
        mapEsquerdaPares.put("0001001", 8);
        mapEsquerdaPares.put("0010111", 9);
        
        // MAPA PARA OS CODIGOS BINARIOS ASSOCIADOS AOS DIGITOS DO LADO DIREITO
        // DADOS DA TABELA 2 -> LADO DIREITO
        HashMap<String, Integer> mapDireita = new HashMap<String, Integer>();
        mapDireita.put("1110010", 0);
        mapDireita.put("1100110", 1);
        mapDireita.put("1101100", 2);
        mapDireita.put("1000010", 3);
        mapDireita.put("1011100", 4);
        mapDireita.put("1001110", 5);
        mapDireita.put("1010000", 6);
        mapDireita.put("1000100", 7);
        mapDireita.put("1001000", 8);
        mapDireita.put("1110100", 9);
        
        // MAPA PARA ASSOCIAR AS SEQUÊNCIAS DE PARIDADE COM O PRIMEIRO DIGITO DO CODIGO DE BARRAS
        // PAR = TRUE || IMPAR = FALSE
        // DADOS DA TABELA 1 
        HashMap<Collection<Boolean>, Integer> mapPrimeiroDigito = new HashMap<Collection<Boolean>, Integer>();
        mapPrimeiroDigito.put(new ArrayList<Boolean>(Arrays.asList(false, false, false, false, false, false)), 0);
        mapPrimeiroDigito.put(new ArrayList<Boolean>(Arrays.asList(false, false, true, false, true, true)), 1);
        mapPrimeiroDigito.put(new ArrayList<Boolean>(Arrays.asList(false, false, true, true, false, true)), 2);
        mapPrimeiroDigito.put(new ArrayList<Boolean>(Arrays.asList(false, false, true, true, true, false)), 3);
        mapPrimeiroDigito.put(new ArrayList<Boolean>(Arrays.asList(false, true, false, false, true, true)), 4);
        mapPrimeiroDigito.put(new ArrayList<Boolean>(Arrays.asList(false, true, true, false, false, true)), 5);
        mapPrimeiroDigito.put(new ArrayList<Boolean>(Arrays.asList(false, true, true, true, false, false)), 6);
        mapPrimeiroDigito.put(new ArrayList<Boolean>(Arrays.asList(false, true, false, true, false, true)), 7);
        mapPrimeiroDigito.put(new ArrayList<Boolean>(Arrays.asList(false, true, false, true, true, false)), 8);
        mapPrimeiroDigito.put(new ArrayList<Boolean>(Arrays.asList(false, true, true, false, true, false)), 9);
        
        Scanner scan = new Scanner(System.in);
        
        // ENQUANTO HOUVER LINHA A SER LIDA...
        while (scan.hasNext()) {
            
            // FAZ A LEITURA DA LINHA INTEIRA (CODIGO BINARIO COM 95 CARACTERES)
            entrada = scan.nextLine();
            
            // VAI GUARDAR OS DIGITOS DO LADO ESQUERDO
            ArrayList<Integer> digitosEsquerda = new ArrayList<Integer>();
            // VAI GUARDAR OS DIGITOS DO LADO DIREITO
            ArrayList<Integer> digitosDireita = new ArrayList<Integer>();
            // VAI GUARDAR A PARIDADE DOS DIGITOS DA ESQUERDA (TRUE PARA PAR, FALSE PARA IMPAR)
            ArrayList<Boolean> paridade = new ArrayList<Boolean>();
            
            // COMO OS 3 PRIMEIROS DIGITOS SAO DO LEFT GUARD
            // INICIA A LEITURA A PARTIR DO QUARTO DIGITO
            // SEPARANDO-OS DE 7 EM 7
            // E ATE CHEGAR NO CENTRAL GUARD (INDICE 45)
            for (int i = 3; i < 45; i+=7) {
                // RETORNA UMA SUBSTRING COM OS 7 BITS REFERENTE AO DIGITO
                binario = entrada.substring(i, i+7);
                // TENTA BUSCAR O DIGITO CORRESPONDENTE AO BINARIO LA NO MAPA DOS DIGITOS DA ESQUERDA DE PARIDADE PAR
                // CASO NAO ENCONTRE NO MAPA DOS PARES, SIGNIFICA QUE E IMPAR E RETORNA NULL
                dig = mapEsquerdaPares.get(binario);
                // SE NAO RETORNAR NULL, ENTAO PAR = TRUE
                par = dig != null;
                
                // SE NAO FOR PAR, VAI TER QUE BUSCAR NO MAPA DOS IMPARES
                if (!par) dig = mapEsquerdaImpares.get(binario);
                
                // ADICIONA O DIGITO ENCONTRADO NA LISTA DOS DIGITOS DA ESQUERDA
                digitosEsquerda.add(dig);
                // E SUA RESPECTIVA PARIDADE TAMBEM E GUARDADA NESSA LISTA
                paridade.add(par);
            }
            
            // AGORA COM OS DIGITOS DA DIREITA (BEM MAIS SIMPLES)
            // CONTINUA A LEITURA INICIANDO NO PROXIMO DIGITO QUE SUCEDE O CENTRAL GUARD (INDICE 50)
            // ATE CHEGAR NO CODIGO DO DIGITO VERIFICADOR (INDICE 85)
            for (int i = 50; i < 85; i+=7) {
                binario = entrada.substring(i, i+7); // MESMO ESQUEMA DA SUBSTRING
                dig = mapDireita.get(binario); // PROCURA O BINARIO NO MAPA DA DIREITA E RETORNA SEU VALOR
                digitosDireita.add(dig); // ADICIONA O DIGITA NA LISTA DOS DIGITOS DA DIREITA
            }
            
            // SEPARADAMENTE, GUARDA-SE O SUPOSTO CODIGO VERIFICADOR QUE FOI LIDO
            verificadorLido = mapDireita.get(entrada.substring(85, 92));
            
            // COMO JA SE TEM A SEQUÊNCIA DA PARIDADE, PODE-SE PROCURAR ESSA SEQUÊNCIA NO MAPA DA PARIDADE
            // PARA RETORNAR O PRIMEIRO DIGITO
            primeiroDigito = mapPrimeiroDigito.get(paridade);
            
            // ENCONTRADO O PRIMEIRO DIGITO, ADICIONA-O NO COMECO DA LISTA DOS DIGITOS DA ESQUERDA
            digitosEsquerda.add(0, primeiroDigito);
            
            // CONCATENA OS DIGITOS DE AMBOS OS LADOS EM UMA UNICA LISTA
            ArrayList<Integer> codigoCompleto = new ArrayList<Integer>();
            codigoCompleto.addAll(digitosEsquerda);
            codigoCompleto.addAll(digitosDireita);
            
            // A REGRA PRA SOMAR E A SEGUINTE
            // A PRIMEIRA SOMA E COM OS NUMEROS EM POSICAO IMPAR (1º, 3º, 5º, 7º, ETC)
            // A SEGUNDA E COM O NUMEROS EM POSICAO PAR (2º, 4º, 6º, ETC)
            
            // RESETA OS VALORES DAS SOMAS PARA AS PROXIMAS ITERACÕES
            somaPares = 0;
            somaImpares = 0;
            
            int i = 1;
            for (Integer digito : codigoCompleto) {
                if (i % 2 == 0)
                    somaPares += digito;
                else
                    somaImpares += digito;
                i++;
            }

            // MULTIPLICA-SE A SOMA DAS POSICÕES PARES POR 3 E SOMA-SE AOS IMPARES
            terceiroValor = somaImpares + 3 * somaPares;

            // CASO SEJA MULTIPLO DE 10, O DIGITO VERIFICADOR SERA ZERO
            // MAS CASO NAO SEJA, DEVE-SE CALCULAR FAZENDO
            // "modulo da diferenCa entre esse nUmero e o primeiro mUltiplo de 10 maior ou igual a esse nUmero"
            if (terceiroValor % 10 == 0)
                digitoVerificador = 0;
            else
                digitoVerificador = Math.abs(terceiroValor - (terceiroValor - (terceiroValor % 10) + 10));
            
            // ADICIONA AO CODIGO COMPLETO, O DIGITO VERIFICADOR ENCONTRADO
            codigoCompleto.add(digitoVerificador);
            
            saida = ""; // RESETA A SAIDA
            // SE O DIGITO QUE ENCONTROU E IGUAL AO LIDO
            // SIGNIFICA QUE ESTAVA CORRETO E FAZ A SAIDA DO CODIGO NO FORMATO XX-XXXXX-XXXXX-X
            if (digitoVerificador == verificadorLido) {
                i = 0;
                for (Integer digit : codigoCompleto) {
                    if (i == 2 || i == 7 || i == 12) {
                        saida += "-";
                    }
                    
                    saida += digit;
                    i++;
                }
            } else // SE NAO FOREM IGUAIS, O DIGITO VERIFICADOR LIDO ESTAVA INCORRETO E A SAIDA E FEITA NESSE FORMATO
                saida = String.format("barcode incorreto: lido = %d esperado = %d", verificadorLido, digitoVerificador);
            
            System.out.println(saida);
        }   
    }
}