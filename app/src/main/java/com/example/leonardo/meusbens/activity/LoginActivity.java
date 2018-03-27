package com.example.leonardo.meusbens.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.leonardo.meusbens.R;
import com.example.leonardo.meusbens.config.ConfiguracaoFirebase;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView irparacadastro ;
    private GoogleSignInClient  mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth autenticacao;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.leonardo.meusbens",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        verificarUsuarioLogado();

        // Initialize Facebook Login button
        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
    //    loginButton.setReadPermissions("email", "public_profile");
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login Cancelado!",Toast.LENGTH_SHORT).show();            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(LoginActivity.this, "Erro ao realizar login com  Facebook"+ error.getMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        });

        //LOGIN GOOGLE
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn. getClient ( this , gso );


        irparacadastro = (TextView) findViewById(R.id.idtextviewcadastro);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        irparacadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //ir para tela de login
                Intent intent = new Intent(LoginActivity.this,CadastroActivity.class);
                startActivity(intent);
                finish();

            }
        });

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.sign_in_button){
                    signIn();

                }

            }
        });

    }


    @Override
    public void onStart() {
        super.onStart();
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {


                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {


            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        autenticacao.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(LoginActivity.this, "Login Realizado com Sucesso!",Toast.LENGTH_SHORT).show();
                            abrirTelaPrincipal();
                        } else {

                            Toast.makeText(LoginActivity.this, "Erro ao realizar login com  Google."+task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, DashboradActivity.class);
        startActivity( intent );
        finish();
    }

    private void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        if( autenticacao.getCurrentUser() != null ){
            abrirTelaPrincipal();
        }
    }

    //pegar as credenciais
    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        autenticacao.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            abrirTelaPrincipal();
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            Log.w("teste", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Erro  na Autenticação! ."+task.getException(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}