package parkingcg.parkingcg.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import parkingcg.parkingcg.DAO.ConfiguracaoFirebase;
import parkingcg.parkingcg.Entidades.Usuarios;
import parkingcg.parkingcg.Helper.Base64Custom;
import parkingcg.parkingcg.Helper.PreferenciasAndroid;
import parkingcg.parkingcg.R;
//Classe Java de Cadastro do Usuario
public class CadastroActivity extends AppCompatActivity {

    //Declaração de atributos
    private EditText edtCadEmail;
    private EditText edtCadNome;
    private EditText edtCadSobrenome;
    private EditText edtCadSenha;
    private EditText edtCadConfirmarSenha;
    private EditText edtCadAniversario;
    private RadioButton rbMasculino;
    private RadioButton rbFeminino;
    private Button btnGravar;
    private Usuarios usuarios;
    private FirebaseAuth autenticacao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_cadastro );
        //Referencia ao Visual(FrontEnd)
        edtCadEmail = (EditText)findViewById( R.id.edtCadEmail );
        edtCadNome = (EditText)findViewById( R.id.edtCadNome );
        edtCadSenha = (EditText)findViewById( R.id.edtCadSenha );
        edtCadConfirmarSenha = (EditText)findViewById( R.id.edtCadConfirmarSenha );
        rbFeminino = (RadioButton)findViewById( R.id.rbFeminino );
        rbMasculino = (RadioButton)findViewById( R.id.rbMasculino );
        btnGravar = (Button)findViewById( R.id.btnGravar );

        //Acao de Click e criar novo Usuario
        btnGravar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtCadSenha.getText().toString().equals( edtCadConfirmarSenha.getText().toString() )){

                    usuarios = new Usuarios();
                    usuarios.setNome( edtCadNome.getText().toString() );
                    usuarios.setEmail(edtCadEmail.getText().toString());
                    usuarios.setSenha( edtCadSenha.getText().toString() );

                    if (rbFeminino.isChecked()){
                        usuarios.setSexo( "Feminino" );
                    }else{
                        usuarios.setSexo( "Masculino" );
                    }

                    cadastrarUsuario();

                }else{
                    Toast.makeText( CadastroActivity.this, "As senhas não são correspondentes", Toast.LENGTH_LONG).show();
                }
            }
        } );
    }
    //Cadastro do Usuario no FireBase
    private void cadastrarUsuario(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuarios.getEmail(),
                usuarios.getSenha()
        ).addOnCompleteListener( CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    Toast.makeText( CadastroActivity.this, "Usuario cadastrado com sucesso", Toast.LENGTH_LONG ).show();

                    String identificadorUsuario = Base64Custom.codificarBase64( usuarios.getEmail() );
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuarios.salvar();

                    PreferenciasAndroid preferenciasAndroid = new PreferenciasAndroid( CadastroActivity.this );
                    preferenciasAndroid.salvarUsuarioPreferencias( identificadorUsuario, usuarios.getNome() );

                    abrirLoginUsuario();

                }else {//Teste de Try-Catch: Senha/Email e Erro de cadastro
                    String erroExecao = "";
                    try{
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        erroExecao = " Digite uma senha mais forte, contendo no minimo 8 caracteres de letras e números";
                    }catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExecao = "O e-mail digitado é invalido , digite um novo e-mail";
                    }catch (FirebaseAuthUserCollisionException e){
                        erroExecao = "Esse e-mail já está cadastrado no sistema";
                    }catch (Exception e){
                        erroExecao = "Erro ao efetuar o cadastro!";
                        e.printStackTrace();
                    }
                    Toast.makeText( CadastroActivity.this, "Erro " + erroExecao, Toast.LENGTH_LONG ).show();
                }
            }
        } );
    }
    //Chamada do LoginActivit xml
    public void abrirLoginUsuario(){
        Intent intent = new Intent(CadastroActivity.this, LoginActivity.class);
        startActivity( intent );
        finish();
    }
}
