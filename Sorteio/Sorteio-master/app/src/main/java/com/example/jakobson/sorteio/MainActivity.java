//pacotes
package com.example.jakobson.sorteio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //Acao de sortiar Numero
    public void sortearNumero(View view){

        TextView textoResultado = findViewById(R.id.textoresultado);
        //Gerar um numeiro inteiro
        int numero = new Random().nextInt(11);
        //Ao cliclar no botao mudara o texto informando o numero.
        textoResultado.setText("O número selecionado é: " + numero);

    }
}
