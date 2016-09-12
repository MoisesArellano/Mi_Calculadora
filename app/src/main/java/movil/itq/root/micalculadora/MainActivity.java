package movil.itq.root.micalculadora;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private TextView _screen;
    private String pantalla = "";
    private String operadorActual = "";
    private String resultado = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _screen = (TextView)findViewById(R.id.textView);
        _screen.setText(pantalla);
    }
    private void updateScreen(){
        _screen.setText(pantalla);
    }

    public void onClickNumber(View v){
        if(resultado != ""){
            clear();
            updateScreen();
        }
        Button b = (Button) v;
        pantalla += b.getText();
        updateScreen();
    }

    private boolean isOperator(char op){
        switch (op){
            case '+':
            case '-':
            case '*':
            case '/':return true;
            default: return false;
        }
    }

    public void onClickOperator(View v){
        if(pantalla == "") return;

        Button b = (Button)v;

        if(resultado != ""){
            String _pantalla = resultado;
            clear();
            pantalla = _pantalla;
        }

        if(operadorActual != ""){
            Log.d("CalcX", ""+pantalla.charAt(pantalla.length()-1));
            if(isOperator(pantalla.charAt(pantalla.length()-1))){
                pantalla = pantalla.replace(pantalla.charAt(pantalla.length()-1), b.getText().charAt(0));
                updateScreen();
                return;
            }else{
                getresultado();
                pantalla = resultado;
                resultado = "";
            }
            operadorActual = b.getText().toString();
        }
        pantalla += b.getText();
        operadorActual = b.getText().toString();
        updateScreen();
    }

    private void clear(){
        pantalla = "";
        operadorActual = "";
        resultado = "";
    }

    public void onClickClear(View v){
        clear();
        updateScreen();
    }

    private double operate(String a, String b, String op){
        switch (op){
            case "+": return Double.valueOf(a) + Double.valueOf(b);
            case "-": return Double.valueOf(a) - Double.valueOf(b);
            case "*": return Double.valueOf(a) * Double.valueOf(b);
            case "/": try{
                return Double.valueOf(a) / Double.valueOf(b);
            }catch (Exception e){
                Log.d("Calc", e.getMessage());
            }
            default: return -1;
        }
    }

    private boolean getresultado(){
        if(operadorActual == "") return false;
        String[] operation = pantalla.split(Pattern.quote(operadorActual));
        if(operation.length < 2) return false;
        resultado = String.valueOf(operate(operation[0], operation[1], operadorActual));
        return true;
    }

    public void onClickEqual(View v){
        if(pantalla == "") return;
        if(!getresultado()) return;
        _screen.setText(pantalla + "\n" + String.valueOf(resultado));
    }
}
