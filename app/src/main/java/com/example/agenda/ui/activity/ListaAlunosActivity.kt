package com.example.agenda.ui.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.agenda.R
import com.example.agenda.dao.AlunoDAO
import com.example.agenda.model.Aluno
import com.example.agenda.ui.adapter.ListaAlunosAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ListaAlunosActivity : AppCompatActivity() {

    private var listaDeAlunos: ListView? = null
    private val dao = AlunoDAO
    private val alunosAdapter: ListaAlunosAdapter by lazy { ListaAlunosAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_alunos)
        listaDeAlunos = findViewById<ListView>(R.id.activity_lista_alunos_listview)
        configuraLista()
        configuraNovoAluno()
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.activity_lista_alunos_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId
        if (itemId == R.id.activity_lista_alunos_menu_remover) {
            confirmaRemocao(item)
        }

        return super.onContextItemSelected(item)
    }

    private fun confirmaRemocao(item: MenuItem) {
        AlertDialog.Builder(this)
                .setTitle("Removendo aluno")
                .setMessage("Tem certeza que deseja remover o aluno?")
                .setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, which ->
                    val menuInfo = item.menuInfo as AdapterContextMenuInfo
                    val alunoEscolhido = alunosAdapter.getItem(menuInfo.position)
                    alunoEscolhido.let {
                        dao.remove(it)
                        alunosAdapter.remove(alunoEscolhido)
                    }
                })
                .setNegativeButton("Não", null)
                .show()
    }

    override fun onResume() {
        super.onResume()

        alunosAdapter.run {
            val alunos: ArrayList<Aluno> = dao.todos()
            alunosAdapter.atualiza(dao.todos())
            configuraItemClickListener(alunos)
        }

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
        val alunos = dao.todos()
        configuraAdapter()
        configuraItemClickListener(alunos)
        registerForContextMenu(listaDeAlunos)
    }

    private fun configuraItemClickListener(alunos: ArrayList<Aluno>) {
        listaDeAlunos?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l ->
            val alunoEscolhido = alunos.get(i)
            abreFormularioModoEditaAluno(alunoEscolhido)
        }
    }

    private fun abreFormularioModoEditaAluno(alunoEscolhido: Aluno) {
        startActivity(Intent(this, FormularioAlunoActivity::class.java).putExtra(ConstantesActivities.CHAVE_ALUNO, alunoEscolhido))
    }

    private fun configuraAdapter() {
        listaDeAlunos?.adapter = alunosAdapter
    }
}
