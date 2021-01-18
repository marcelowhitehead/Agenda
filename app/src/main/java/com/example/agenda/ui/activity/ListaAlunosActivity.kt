package com.example.agenda.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.agenda.R
import com.example.agenda.dao.AlunoDAO
import com.example.agenda.model.Aluno
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaAlunosActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)
        val dao = AlunoDAO
        dao.salva(Aluno("Marcelo", "1122223333", "marcelo@gmail.com"))
        dao.salva(Aluno("Flavia", "1144445555", "flavia@gmail.com "))

        configuraNovoAluno()
    }

    private fun configuraNovoAluno() {
        val botaoAdicionar = findViewById<FloatingActionButton>(R.id.lista_alunos_fab_novo_aluno)
        abreFormularioModoInsereAluno(botaoAdicionar)
    }

    private fun abreFormularioModoInsereAluno(botaoAdicionar: FloatingActionButton) {
        botaoAdicionar.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, FormularioAlunoActivity::class.java))
        })
    }

    override fun onResume() {
        super.onResume()

        val dao = AlunoDAO
        configuraLista(dao)
    }

    private fun configuraLista(dao: AlunoDAO) {
        val listaDeAlunos = findViewById<ListView>(R.id.activity_lista_alunos_listview)
        val alunos = dao.todos()
        configuraAdapter(listaDeAlunos, alunos)
        configuraItemClickListener(listaDeAlunos, alunos)
    }

    private fun configuraItemClickListener(listaDeAlunos: ListView, alunos: ArrayList<Aluno>) {
        listaDeAlunos.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val alunoEscolhido = alunos.get(i)
            abreFormularioModoEditaAluno(alunoEscolhido)
        }
    }

    private fun abreFormularioModoEditaAluno(alunoEscolhido: Aluno) {
        startActivity(Intent(this, FormularioAlunoActivity::class.java).putExtra(ConstantesActivities.CHAVE_ALUNO, alunoEscolhido))
    }


    private fun configuraAdapter(listaDeAlunos: ListView, alunos: ArrayList<Aluno>) {
        listaDeAlunos.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alunos.map { it.nome })
    }
}