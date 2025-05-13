package com.hugo.memo

import android.content.Context

class SingletonMemo private constructor()//On mets le constructor privé car on ne veut pas créer un constructeur à l'extérieur de la classe
{
    companion object{
        private var instance : SingletonMemo? = null
        var liste:MutableList<Memo> = mutableListOf();

        fun getInstance(context: Context) : SingletonMemo{
           synchronized(this){ // garanti qu'un seul thread à la fois va exécuter
               return instance ?:SingletonMemo().also {
                   instance = it
               }
           }
        }

        fun ajouterMemo(memo : Memo){
            liste.add(memo)
        }


    }


}