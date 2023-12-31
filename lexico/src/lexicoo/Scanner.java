/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lexicoo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


class Scanner {
    private final String source;

    private final List<Token> tokens = new ArrayList<>();

    private int linea = 1;
    private char current;
    int start = current;
    private String lexema;
    private TipoToken estado = TipoToken.INICIO;
    private static final Map<String, TipoToken> palabrasReservadas;
    static {
        palabrasReservadas = new HashMap<>();
        palabrasReservadas.put("y", TipoToken.Y);
        palabrasReservadas.put("class", TipoToken.CLASS);
        palabrasReservadas.put("else", TipoToken.ELSE);
        palabrasReservadas.put("false", TipoToken.FALSE);
        palabrasReservadas.put("for", TipoToken.FOR);
        palabrasReservadas.put("funcion", TipoToken.FUN);
        palabrasReservadas.put("if", TipoToken.IF);
        palabrasReservadas.put("null", TipoToken.NULL);
        palabrasReservadas.put("o", TipoToken.O);
        palabrasReservadas.put("println", TipoToken.PRINTLN);
        palabrasReservadas.put("return", TipoToken.RETURN);
        palabrasReservadas.put("super", TipoToken.SUPER);
        palabrasReservadas.put("this", TipoToken.THIS);
        palabrasReservadas.put("true", TipoToken.TRUE);
        palabrasReservadas.put("var", TipoToken.VAR);
        palabrasReservadas.put("while", TipoToken.WHILE);
        palabrasReservadas.put("int", TipoToken.INT);
        palabrasReservadas.put("main", TipoToken.MAIN);
        
    }

    Scanner(String source){
        this.source = source;
        
    }

    List<Token> scanTokens(){
        
        //Aquí va el corazón del scanner.
         while (!isAtEnd()) { 
            char c = advance(); 
            switch (estado) {
                case INICIO:
                    if (Character.isLetter(c)) {
                        estado = estado.IDENTIFICADOR;
                        start = current - 1;
                    }else if(c == '"') {
                        estado = estado.CADENA;
                        } else if (Character.isDigit(c)) {
                            estado = estado.NUMERO;
                            start = current - 1;
                     } else {
                        Resto_Token(c);
                    }
                    break;
                case IDENTIFICADOR:
                    if (!Numerico(c)) {
                        estado = estado.INICIO;
                        String palabra = source.substring(start, current - 1);
                        TipoToken tipo = palabrasReservadas.getOrDefault(palabra, TipoToken.ERROR);
                        tokens.add(new Token(tipo, palabra, tipo.PALABRA_RESERVADA, linea));
                        current--;
                    }
                    break;
                case NUMERO:
                    if (!Character.isDigit(c)) {
                        estado = estado.INICIO;
                        String numero = source.substring(start, current -1 );
                        tokens.add(new Token(TipoToken.NUMERO, numero, Integer.parseInt(numero), linea));
                        current--;
                    }
                    break;
                case CADENA:
                    if (c == '"') {
                        estado = estado.INICIO;
                        String value = source.substring(start , current - 1);
                        tokens.add(new Token(TipoToken.CADENA, value, value, linea));
                    }
                    break;
            }
        }
           
        /*
        Analizar el texto de entrada para extraer todos los tokens
        y al final agregar el token de fin de archivo
         */
        tokens.add(new Token(TipoToken.EOF, "", "fin", linea));

        return tokens;
    }
private char advance() {
    return source.charAt(current++);
}
private boolean isAtEnd() {
    return current >= source.length();
}
private boolean Numerico(char c) {
    return Character.isLetterOrDigit(c) || c == '_';
}
public void addToken(TipoToken tipo, char c) {
   tokens.add(new Token(tipo,lexema,c,linea));
}
private void Resto_Token(char c) {
    switch (c) {
        case '(':
            addToken(TipoToken.PARENTESIS_IZQUIERDO,c);
            break;
        case ')':
            addToken(TipoToken.PARENTESIS_DERECHO,c);
            break;
        case '{':
            addToken(TipoToken.LLAVE_IZQUIERDA,c);
            break;
        case '}':
            addToken(TipoToken.LLAVE_DERECHA,c);
            break;
            case ',':
            addToken(TipoToken.COMA,c);
            break;
        case '.':
            addToken(TipoToken.PUNTO,c);
            break;
        case ';':
            addToken(TipoToken.PUNTO_Y_COMA,c);
            break;
        case '-':
            addToken(TipoToken.RESTA,c);
            break;
        case '+':
            addToken(TipoToken.SUMA,c);
            break;
        case '*':
            addToken(TipoToken.MULTIPLICACION,c);
            break;
        case '/':
            addToken(TipoToken.DIVISION,c);
            break;
        case '&':
            addToken(TipoToken.Y,c);
            break;
        case '|':
            addToken(TipoToken.O,c);
            break;
        case '[':
            addToken(TipoToken.CORCHETE_IZQUIERDO,c);
            break;
        case ']':
            addToken(TipoToken.CORCHETE_DERECHO,c);
            break;
        case '!':
            addToken(TipoToken.NEGACION,c);
            break;
        case '=':
            addToken(TipoToken.ASIGNAR,c);
            break;
    }
}
/*
Signos o símbolos del lenguaje:
(
)
{
}
,
.
;
-
+
*
/
!
!=
=
==
<
<=
>
>=
// -> comentarios (no se genera token)
/* ... * / -> comentarios (no se genera token)
Identificador,
Cadena
Numero
Cada palabra reservada tiene su nombre de token

 */
}
