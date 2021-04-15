package com.example.cachorros

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var valor1:Cachorro
    lateinit var valor2:Cachorro

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }



    fun comprar(view: View) {

        val apiCachorros = ConexaoApiCachorros.criar()

        val et_id1:EditText = findViewById(R.id.et_cachorro1)
        val et_id2:EditText = findViewById(R.id.et_cachorro2)

        val id1 = et_id1.text.toString().toInt()
        val id2 = et_id2.text.toString().toInt()

        apiCachorros.get(id1).enqueue(object : Callback<Cachorro> {
            override fun onResponse(call: Call<Cachorro>, response: Response<Cachorro>) {
                val cachorro1 = response.body()

                if (cachorro1 != null) {
                    valor1 = cachorro1
                }
            }
            override fun onFailure(call: Call<Cachorro>, t: Throwable) {
                Toast.makeText(baseContext, "Erro: ${t.message!!}", Toast.LENGTH_SHORT).show()
            }
        })

        apiCachorros.get(id2).enqueue(object : Callback<Cachorro> {
            override fun onResponse(call: Call<Cachorro>, response: Response<Cachorro>) {
                val cachorro2 = response.body()

                if (cachorro2 != null) {
                    valor2 = cachorro2
                }
            }
            override fun onFailure(call: Call<Cachorro>, t: Throwable) {
                Toast.makeText(baseContext, "Erro: ${t.message!!}", Toast.LENGTH_SHORT).show()
            }
        })


        val tela2 = Intent(this,Tela2::class.java)
        val tela3 = Intent(this,Tela3::class.java)



        if(valor1.id == null && valor2.id == null){
            val tvNegativa:TextView = findViewById(R.id.tv_mensagem_negativa)
            tvNegativa.text = "Deu ruim... Nenhum cachorro encontrado com os ids $id1 e $id2"

            startActivity(tela2)
        }else{
            val tvResposta:TextView = findViewById(R.id.tv_resposta)

            val total = valor1.precoMedio + valor2.precoMedio

            tvResposta.text = "Cachorro 1: ${valor1.raca}" +
                    "Cachorro 2: ${valor2.raca}" +
                    "Preço total de compra: R$${total}"

            startActivity(tela3)
        }
    }
}