package com.example.agenda.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.agenda.R
import com.example.agenda.model.Aluno

public class ListaAlunosAdapter(private val context: Context) : BaseAdapter() {

    private val alunos: MutableList<Aluno> = arrayListOf()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val viewCriada = LayoutInflater
                .from(context)
                .inflate(R.layout.item_aluno, parent, false)

        val alunoDevolvido = alunos.get(position)
        vincula(viewCriada, alunoDevolvido)
        return viewCriada
    }



    override fun getItem(position: Int): Aluno {
        return alunos.get(position)
    }

    override fun getItemId(position: Int): Long {
        return alunos.get(position).id.toLong()
    }

    override fun getCount(): Int {
        return alunos.size
    }

    private fun vincula(viewCriada: View, alunoDevolvido: Aluno) {
        val nome = viewCriada.findViewById<TextView>(R.id.item_aluno_nome)
        nome.setText(alunoDevolvido.nome)
        val telefone = viewCriada.findViewById<TextView>(R.id.item_aluno_telefone)
        telefone.setText(alunoDevolvido.telefone)
    }

    fun remove(alunoEscolhido: Aluno) {
        alunos.remove(alunoEscolhido)
        notifyDataSetChanged()
    }

    public fun atualiza(alunos : List<Aluno>){
            this.alunos.clear()
            this.alunos.addAll(alunos)
        notifyDataSetChanged()

    }




}