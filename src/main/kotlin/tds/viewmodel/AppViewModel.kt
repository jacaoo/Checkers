package tds.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import tds.storage.MongoDriver
import tds.storage.*
import tds.model.*
import tds.view.InputName

class AppViewModel (driver: MongoDriver, val scope: CoroutineScope) {

    private val storage =
        //TextFileStorage<Name, Game>("games", GameSerializer) , isto usa-se quando não queremos gastar as cenas diárias do mongo
        MongoStorage<Name, Game>("games", driver, GameSerializer)  // Para guardar as coisas na base de dados do Mongo

    var clash by mutableStateOf(Clash(storage))  // Clash itself
    var inputName by mutableStateOf<InputName?>(null) //StartOrJoinDialog
        private set
    var errorMessage by mutableStateOf<String?>(null) //ErrorDialog state
        private set
    var waitingJob by mutableStateOf<Job?>(null) // É utilizado para os tempos de espera entre jogadas de cada player
    val isWaiting: Boolean get() = waitingJob != null // Complemento da linha de cima

    val newAvailable: Boolean get() = clash.canNewBoard()

    private val turnAvailable: Boolean
        get() = (board as? BoardRun)?.turn == sidePlayer || newAvailable


    val board : Board get() = (clash as ClashRun).game.board
    val hasClash: Boolean get() = clash is ClashRun // Isto vai servir para definir o estado do jogo se o player atual começou um clash
    val sidePlayer: Player? get() = (clash as? ClashRun)?.sideplayer
    val name: Name get() = (clash as ClashRun).id

    fun hideError() {
        errorMessage = null
    }

    private fun exec(fx: Clash.() -> Clash): Unit = // Serve para Usar funções do Clash e detetar erros das mesmas
        try {
            //throw Exception("My blow up")
            clash = clash.fx()
        } catch (e: Exception) {        // Report exceptions in ErrorDialog
            errorMessage = e.message
        }

    fun play(from : Square, to : Square) {
        exec { play(from, to) }
        //waitForOtherSide()
    }

    private fun waitForOtherSide() {
        if (turnAvailable) return
        waitingJob = scope.launch(Dispatchers.IO) {
            do {
                delay(3000)
                try { clash = clash.refresh() }
                catch (e: NoChangesException) { /* Ignore */ }
                catch (e: Exception) {
                    errorMessage = e.message
                    if (e is GameDeletedException) clash = Clash(storage)
                }
            } while (!turnAvailable)
            waitingJob = null
        }
    }

    fun refresh() = exec(Clash::refresh)

    //fun newBoard(): Unit = exec(Clash::newBoard)

    fun start(name: Name) {
        closeStartOrJoinDialog()
        exec {
            try {
                joinClash(name) // Tenta dar join no jogo com o nome especificado
            } catch (e: Exception) {
                start(name) // Se o jogo não existir, cria um novo
            }
        }
        waitForOtherSide()
    }
    fun closeStartOrJoinDialog() { inputName = null }

}