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

    private val dao = AlunoDAO
    private var adapter: ArrayAdapter<Aluno>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)

        dao.salva(Aluno("Marcelo", "1122223333", "marcelo@gmail.com"))
        dao.salva(Aluno("Flavia", "1144445555", "flavia@gmail.com "))

        configuraNovoAluno()
    }

    override fun onResume() {
        super.onResume()
        configuraLista()
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

    private fun configuraLista() {
        val listaDeAlunos = findViewById<ListView>(R.id.activity_lista_alunos_listview)
        val alunos = dao.todos()
        configuraAdapter(listaDeAlunos, alunos)
        configuraItemClickListener(listaDeAlunos, alunos)
        configuraItemLongClickListener(listaDeAlunos, alunos)
    }

    private fun configuraItemLongClickListener(listaDeAlunos: ListView, alunos: ArrayList<Aluno>) {
        listaDeAlunos.onItemLongClickListener = AdapterView.OnItemLongClickListener { parent: AdapterView<*>?, view: View?, position: Int, id: Long ->
            val alunoEscolhido = alunos.get(position)
            dao.remove(alunoEscolhido)
            adapter?.remove(alunoEscolhido)
            this.configuraLista()
            true
        }
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