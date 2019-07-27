package parkingcg.parkingcg.Entidades;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import parkingcg.parkingcg.DAO.ConfiguracaoFirebase;
//Classe Usuario
public class Usuarios {
//Atributos do Usuario
    private String id;
    private String email;
    private String senha;
    private String nome;
    private String sobrenome;
    private String aniverversario;
    private String sexo;

    //Construtor Vazio
    public Usuarios() {
    }

    //Referencia a classe java da ConfiguracaoFirebase
    public void salvar() {
        DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
        referenciaFirebase.child( "usuarios" ).child( String.valueOf( getId())).setValue( this );
    }
    @Exclude
    //Criando Map e atribuindo o ID de cada atributo pelo metodo get
    public Map<String, Object> toMap(){
        HashMap<String, Object> HashMapUsuario = new HashMap<>(  );

        HashMapUsuario.put( "id",getId() );
        HashMapUsuario.put( "email",getEmail() );
        HashMapUsuario.put("senha",getSenha());
        HashMapUsuario.put("nome",getNome());
        HashMapUsuario.put( "sobenome",getSobrenome() );
        HashMapUsuario.put( "aniversario",getAniverversario());
        HashMapUsuario.put("sexo",getSexo());

        return HashMapUsuario;
    }
//Getter e Setter da classe  Usuario
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getAniverversario() {
        return aniverversario;
    }

    public void setAniverversario(String aniverversario) {
        this.aniverversario = aniverversario;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getEmail(){
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
