package com.curriculosuper10app.curriculosuper10app;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    private TextView T1;
    private TextView T2;
   // private RecyclerView recyclerView_ID;

    private Button excluir;
    private Button gravar;

    private EditText nome;
    private EditText email;

    private ArrayList<String> RecebeNome;
    private ArrayList<String> RecebeEmail;



    private Retrofit retrofit;


    private ListView listView_ID;
    //Para montar na tela a tabela com os dados
    private ArrayAdapter<String> itensAdaptador;
    private ArrayList<String> itens;
    private ArrayList<Integer> IDS;
    //variavel de controle do id clicado
    public int IDClick =0;
    private TextView idtesteclick;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        T1 =      (TextView) findViewById(R.id.T1);
        T2 =      (TextView) findViewById(R.id.T2);

        nome =      (EditText) findViewById(R.id.nome);
        email =      (EditText) findViewById(R.id.email);

        excluir =      (Button) findViewById(R.id.excluir);
        gravar =      (Button) findViewById(R.id.gravar);
        listView_ID = (ListView) findViewById(R.id.listView_ID);


        lista();
        //adicionando função no clique no listview
        listView_ID.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {




                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    OkHttpClient client = new OkHttpClient();

                    HttpUrl.Builder urlBuilder = HttpUrl.parse("http:www.cc10.site/API_Lista.php").newBuilder();


                    String url = urlBuilder.build().toString();

                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        //txtInfo.setText(response.body().string());

                                        try {
                                            String data = response.body().string();

                                            JSONArray jsonArray = new JSONArray(data);
                                            JSONObject jsonObject;

                                            //pega o ID
                                            jsonObject = jsonArray.getJSONObject(position);
                                            T1.setText(jsonObject.getString("usuario"));




                                        } catch (JSONException e) {
                                            T2.setText(e.getMessage());
                                        }


                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                        }

                        ;
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //armazena o shared preferences para passaro id para a outra atividade

                //Abre nova atividade
              //  startActivity( new Intent(Movimentacao.this,Nova_Movimentacao.class));

            }//Fim da classe do onclivk
        });  //Fim da classe do onclick





        excluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               lista();
            }
        });




        gravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    OkHttpClient client = new OkHttpClient();

                    HttpUrl.Builder urlBuilder = HttpUrl.parse("http:www.cc10.site/API_Insert.php").newBuilder();
                    urlBuilder.addQueryParameter("mome", nome.getText().toString());
                    urlBuilder.addQueryParameter("email", email.getText().toString());

                    String url = urlBuilder.build().toString();

                    Request request = new Request.Builder()
                            .url(url)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {

                        }

                        @Override
                        public void onResponse(Call call, final Response response) throws IOException {

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    try {
                                        T1.setText(response.body().string());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });
                        }

                        ;
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });





    }//fim do oncreate


public void lista() {



    try {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        OkHttpClient client = new OkHttpClient();

        HttpUrl.Builder urlBuilder = HttpUrl.parse("http:www.cc10.site/API_Lista.php").newBuilder();


        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            //txtInfo.setText(response.body().string());

                            try {
                                String data = response.body().string();

                                JSONArray jsonArray = new JSONArray(data);
                                JSONObject jsonObject;



                               // jsonObject = jsonArray.getJSONObject(0);
                                //Criar adaptador
                                itens = new ArrayList<String>();
                                IDS = new ArrayList<Integer>();

                                itensAdaptador = new ArrayAdapter<String>(getApplicationContext(),
                                        android.R.layout.simple_list_item_2,
                                        android.R.id.text2, itens);
                                listView_ID.setAdapter(itensAdaptador);

                             /*   itens.add("Nome: "+"Teste "+" |  Email:"+"Teste email");
                                itens.add("Nome: "+"Teste "+" |  Email:"+"Teste email");
                                itens.add("Nome: "+"Teste "+" |  Email:"+"Teste email");
                                itens.add("Nome: "+"Teste "+" |  Email:"+"Teste email");
                                itens.add("Nome: "+"Teste "+" |  Email:"+"Teste email");
                                itens.add("Nome: "+"Teste "+" |  Email:"+"Teste email");
                            */

                                for (int i = 0; i < jsonArray.length(); i++) {
                                    jsonObject = jsonArray.getJSONObject(i);
                                    itens.add("Nome: "+jsonObject.getString("usuario")+" |  Email:"+jsonObject.getString("email"));


                                    //   T1.setText(jsonObject.getString("usuario"));

                                }


                            } catch (JSONException e) {
                                T2.setText(e.getMessage());
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                });
            }

            ;
        });
    } catch (Exception e) {
        e.printStackTrace();
    }

}

}//fim da classe java
