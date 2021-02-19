package com.example.agenda.model

import java.io.Serializable


class Aluno(internal var nome: String,
        internal var telefone: String,
        internal var email: String) : Serializable{

    constructor() : this(nome = "", telefone = "", email = "")

    internal var id: Int = 0


    override fun toString(): String {
        return nome + " - " + telefone
     }

    fun temIdValido(): Boolean {
        return id > 0
    }

    companion object {
        fun setId(aluno: Aluno, id: Int){
          aluno.id = id
        }
    }


}


