import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TrilhaDeDinheiro {
    public static void main(String[] args) {
        String filePath = "casoC2000.txt"; // Substitua pelo caminho real do arquivo
        char[][] matriz = carregarMatrizDeArquivo(filePath);
        int dinheiroRecuperado = percorrerTrilha(matriz);
        System.out.println("Dinheiro recuperado: " + dinheiroRecuperado);
    }

    public static char[][] carregarMatrizDeArquivo(String filePath) {
        List<String> linhas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ignorar a primeira linha que contém as dimensões da matriz
        linhas.remove(0);

        char[][] matriz = new char[linhas.size()][];
        for (int i = 0; i < linhas.size(); i++) {
            matriz[i] = linhas.get(i).toCharArray();
        }
        return matriz;
    }

    public static int percorrerTrilha(char[][] matriz) {
        int x = 0, y = 0, dinheiroTotal = 0;
        String direcao = "direita"; // Inicia movendo para a direita

        // Encontra a posição inicial (lado esquerdo da matriz)
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i][0] == '-') {
                x = i;
                break;
            }
        }

        while (true) {
            if (y < matriz[0].length && Character.isDigit(matriz[x][y])) {
                // Lê o número completo
                StringBuilder sb = new StringBuilder();
                while (y < matriz[0].length && Character.isDigit(matriz[x][y])) {
                    sb.append(matriz[x][y]);
                    if (direcao.equals("direita") || direcao.equals("esquerda")) {
                        y += direcao.equals("direita") ? 1 : -1;
                    } else {
                        x += direcao.equals("baixo") ? 1 : -1;
                    }
                }
                dinheiroTotal += Integer.parseInt(sb.toString());
                // Ajusta a posição para não pular o próximo caractere não numérico
                if (direcao.equals("direita") || direcao.equals("esquerda")) {
                    y -= direcao.equals("direita") ? 1 : -1;
                } else {
                    x -= direcao.equals("baixo") ? 1 : -1;
                }
            }

            // Movimento e mudança de direção
            if (direcao.equals("direita")) {
                y++;
            } else if (direcao.equals("esquerda")) {
                y--;
            } else if (direcao.equals("cima")) {
                x--;
            } else if (direcao.equals("baixo")) {
                x++;
            }

            if (x < 0 || y < 0 || x >= matriz.length || y >= matriz[0].length) {
                break; // Encerra se chegar ao fim da matriz
            }

            char proximoCaracter = y < matriz[0].length ? matriz[x][y] : ' ';

            // Checa o caracter da próxima posição e ajusta a direção conforme necessário
            if (proximoCaracter == '/') {
                if (direcao.equals("direita")) {
                    direcao = "cima";
                } else if (direcao.equals("esquerda")) {
                    direcao = "baixo";
                } else if (direcao.equals("cima")) {
                    direcao = "direita";
                } else { // "baixo"
                    direcao = "esquerda";
                }
            } else if (proximoCaracter == '\\') {
                if (direcao.equals("direita")) {
                    direcao = "baixo";
                } else if (direcao.equals("esquerda")) {
                    direcao = "cima";
                } else if (direcao.equals("cima")) {
                    direcao = "esquerda";
                } else { // "baixo"
                    direcao = "direita";
                }
            } else if (proximoCaracter == '#') {
                break; // Chegou ao final da trilha
            }
        }

        return dinheiroTotal;
    }
}
