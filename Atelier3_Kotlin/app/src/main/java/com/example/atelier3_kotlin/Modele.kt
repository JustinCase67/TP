package com.example.atelier3_kotlin

import android.os.Handler
import android.os.Looper

class Modele : Sujet {

    private var valeur: Int = 0
    private var obs: ObservateurChangement? = null

    init {
        genererChangementValeur()
    }

    /**
     * Génère un changement de valeur après un délai de 5000 ms.
     */
    private fun genererChangementValeur() {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            setValeur(78)
        }, 5000)
    }

    fun getValeur(): Int = valeur

    private fun setValeur(v: Int) {
        valeur = v
        avertirObservateurs()
    }

    // --- Méthodes de Sujet ---
    override fun ajouterObservateur(obs: Class<ObservateurChangement>) {
        this.obs = obs
    }

    override fun enleverObservateur(obs: ObservateurChangement) {
        if (this.obs == obs) {
            this.obs = null
        }
    }

    override fun avertirObservateurs() {
        obs?.changement(valeur)
    }
}

