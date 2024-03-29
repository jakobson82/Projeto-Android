package parkingcg.parkingcg.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import parkingcg.parkingcg.DAO.ConfiguracaoFirebase;
import parkingcg.parkingcg.Entidades.Usuarios;
import parkingcg.parkingcg.R;

public class LoginActivity extends AppCompatActivity {
    //Atributos da Tela de Login
    private EditText edtEmail;
    private EditText edtSenha;
    private TextView tvAbreCadastro;
    private Button btnLogar;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        //Referencia dos atributos com o Visual(FrontEnd)
        edtEmail = (EditText) findViewById( R.id.editEmail);
        edtSenha = (EditText) findViewById( R.id.editSenha );
        tvAbreCadastro = (TextView) findViewById( R.id.tvAbreCadastro );
        btnLogar = (Button) findViewById( R.id.btnLogar );

        //Ação de Click Digitar email e senha nos campos, com aviso na tela
        btnLogar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtEmail.getText().toString().equals( "" ) && !edtSenha.getText().toString().equals( "" )) {
                    usuarios = new Usuarios();
                    usuarios.setEmail( edtEmail.getText().toString() );
                    usuarios.setSenha( edtSenha.getText().toString() );

                    validarLogin();
                } else {
                    Toast.makeText( LoginActivity.this, "Preencha os campos de e-mail e senha!", Toast.LENGTH_SHORT ).show();
                }
            }
        } );

        tvAbreCadastro.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreCadastroUsuario();
            }
        } );

    }

        //Açao de Validar ou Usuario Invalido na tela de login
        private void validarLogin () {

            autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
            autenticacao.signInWithEmailAndPassword( usuarios.getEmail(), usuarios.getSenha() ).addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {

                        abrirTelaPrincipal();
                        Toast.makeText( LoginActivity.this, "Login efetuado com Sucesso!", Toast.LENGTH_SHORT ).show();
                    }else {
                        Toast.makeText( LoginActivity.this, "Usuario ou senha invalidos", Toast.LENGTH_LONG ).show();
                    }
                }
            } );
        }
        //Acao de usuario ja cadastrado abrir tela de login
        public void abrirTelaPrincipal () {
            Intent intentAbrirTelaPrincipal = new Intent( LoginActivity.this, MapsActivity.class);
            startActivity( intentAbrirTelaPrincipal );
        }
        //Açao de Cadastro de Usuario
        public void abreCadastroUsuario(){
            Intent intent = new Intent (LoginActivity.this, CadastroActivity.class);
            startActivity( intent );
        }
    }
