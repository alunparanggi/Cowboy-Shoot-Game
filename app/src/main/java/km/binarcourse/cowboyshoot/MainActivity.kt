package km.binarcourse.cowboyshoot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import km.binarcourse.cowboyshoot.databinding.ActivityMainBinding

enum class GameState{
    PLAY,
    STOP
}

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var firstPlayerPosition = MIDDLE
    private var secondPlayerPosition = MIDDLE
    private lateinit var gameState: GameState

    companion object{
        const val TOP = 0
        const val MIDDLE = 1
        const val BOTTOM = 2

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        gameState = GameState.PLAY

        binding.ivArrowUp.setOnClickListener { onUpButtonClicked() }
        binding.ivArrowDown.setOnClickListener { onDownButtonClicked() }
        binding.tvStatusGame.setOnClickListener { onStatusGameClicked() }
    }

    private fun onUpButtonClicked(){
        when(gameState){
            GameState.PLAY -> {
                when {
                    binding.ivFirstPlayer0.visibility == View.VISIBLE -> {
                        binding.ivFirstPlayer0.visibility = View.INVISIBLE
                        binding.ivFirstPlayer1.visibility = View.INVISIBLE
                        binding.ivFirstPlayer2.visibility = View.VISIBLE
                        firstPlayerPosition = BOTTOM
                    }
                    binding.ivFirstPlayer1.visibility == View.VISIBLE -> {
                        binding.ivFirstPlayer0.visibility = View.VISIBLE
                        binding.ivFirstPlayer1.visibility = View.INVISIBLE
                        binding.ivFirstPlayer2.visibility = View.INVISIBLE
                        firstPlayerPosition = TOP
                    }
                    binding.ivFirstPlayer2.visibility == View.VISIBLE -> {
                        binding.ivFirstPlayer0.visibility = View.INVISIBLE
                        binding.ivFirstPlayer1.visibility = View.VISIBLE
                        binding.ivFirstPlayer2.visibility = View.INVISIBLE
                        firstPlayerPosition = MIDDLE
                    }
                }
            }
            GameState.STOP -> {
                showToast()
            }
        }
    }

    private fun onDownButtonClicked(){
        when(gameState){
            GameState.PLAY -> {
                when {
                    binding.ivFirstPlayer0.visibility == View.VISIBLE -> {
                        binding.ivFirstPlayer0.visibility = View.INVISIBLE
                        binding.ivFirstPlayer1.visibility = View.VISIBLE
                        binding.ivFirstPlayer2.visibility = View.INVISIBLE
                        firstPlayerPosition = MIDDLE
                    }
                    binding.ivFirstPlayer1.visibility == View.VISIBLE -> {
                        binding.ivFirstPlayer0.visibility = View.INVISIBLE
                        binding.ivFirstPlayer1.visibility = View.INVISIBLE
                        binding.ivFirstPlayer2.visibility = View.VISIBLE
                        firstPlayerPosition = BOTTOM
                    }
                    binding.ivFirstPlayer2.visibility == View.VISIBLE -> {
                        binding.ivFirstPlayer0.visibility = View.VISIBLE
                        binding.ivFirstPlayer1.visibility = View.INVISIBLE
                        binding.ivFirstPlayer2.visibility = View.INVISIBLE
                        firstPlayerPosition = TOP
                    }
                }
            }
            GameState.STOP -> {
                showToast()
            }
        }

    }

    private fun showToast(){
        Toast.makeText(this, "Restart the game!", Toast.LENGTH_SHORT).show()
    }

    private fun moveSecondPlayerRandom(){
        when((0..2).random()){
            0 -> {
                binding.ivSecondPlayer0.visibility = View.VISIBLE
                binding.ivSecondPlayer1.visibility = View.INVISIBLE
                binding.ivSecondPlayer2.visibility = View.INVISIBLE
                secondPlayerPosition = TOP
            }
            1 -> {
                binding.ivSecondPlayer0.visibility = View.INVISIBLE
                binding.ivSecondPlayer1.visibility = View.VISIBLE
                binding.ivSecondPlayer2.visibility = View.INVISIBLE
                secondPlayerPosition = MIDDLE
            }
            2 -> {
                binding.ivSecondPlayer0.visibility = View.INVISIBLE
                binding.ivSecondPlayer1.visibility = View.INVISIBLE
                binding.ivSecondPlayer2.visibility = View.VISIBLE
                secondPlayerPosition = BOTTOM
            }
        }
    }

    private fun changeCharacterImageWin(){
        binding.ivFirstPlayer0.setImageResource(R.drawable.ic_cowboy_left_shoot_true)
        binding.ivFirstPlayer1.setImageResource(R.drawable.ic_cowboy_left_shoot_true)
        binding.ivFirstPlayer2.setImageResource(R.drawable.ic_cowboy_left_shoot_true)

        binding.ivSecondPlayer0.setImageResource(R.drawable.ic_cowboy_right_dead)
        binding.ivSecondPlayer1.setImageResource(R.drawable.ic_cowboy_right_dead)
        binding.ivSecondPlayer2.setImageResource(R.drawable.ic_cowboy_right_dead)
    }

    private fun changeCharacterImageLose(){
        binding.ivFirstPlayer0.setImageResource(R.drawable.ic_cowboy_left_dead)
        binding.ivFirstPlayer1.setImageResource(R.drawable.ic_cowboy_left_dead)
        binding.ivFirstPlayer2.setImageResource(R.drawable.ic_cowboy_left_dead)

        binding.ivSecondPlayer0.setImageResource(R.drawable.ic_cowboy_right_shoot_true)
        binding.ivSecondPlayer1.setImageResource(R.drawable.ic_cowboy_right_shoot_true)
        binding.ivSecondPlayer2.setImageResource(R.drawable.ic_cowboy_right_shoot_true)
    }

    private fun showGameResult(){
        if(firstPlayerPosition == secondPlayerPosition) {
            changeCharacterImageLose()
            binding.tvGameResult.text = getString(R.string.text_you_lose)
        } else {
            changeCharacterImageWin()
            binding.tvGameResult.text = getString(R.string.text_you_win)
        }
    }

    private fun onRestartCharacterPlayerPosition(){
        //first player
        binding.ivFirstPlayer0.visibility = View.INVISIBLE
        binding.ivFirstPlayer1.visibility = View.VISIBLE
        binding.ivFirstPlayer2.visibility = View.INVISIBLE
        firstPlayerPosition = MIDDLE

        //second player
        binding.ivSecondPlayer0.visibility = View.INVISIBLE
        binding.ivSecondPlayer1.visibility = View.VISIBLE
        binding.ivSecondPlayer2.visibility = View.INVISIBLE
        secondPlayerPosition = MIDDLE
    }

    private fun onRestartCharacterImage(){
        binding.ivFirstPlayer0.setImageResource(R.drawable.ic_cowboy_left_shoot_false)
        binding.ivFirstPlayer1.setImageResource(R.drawable.ic_cowboy_left_shoot_false)
        binding.ivFirstPlayer2.setImageResource(R.drawable.ic_cowboy_left_shoot_false)

        binding.ivSecondPlayer0.setImageResource(R.drawable.ic_cowboy_right_shoot_false)
        binding.ivSecondPlayer1.setImageResource(R.drawable.ic_cowboy_right_shoot_false)
        binding.ivSecondPlayer2.setImageResource(R.drawable.ic_cowboy_right_shoot_false)
    }

    private fun onRestartGame(){
        onRestartCharacterPlayerPosition()
        onRestartCharacterImage()
        binding.tvGameResult.text = null
        binding.tvStatusGame.text = getString(R.string.text_fire)
        gameState = GameState.PLAY
    }



    private fun onStatusGameClicked(){
        when(gameState){
            GameState.PLAY -> {
                moveSecondPlayerRandom()
                showGameResult()
                binding.tvStatusGame.text = getString(R.string.text_restart)
                gameState = GameState.STOP
            }
            GameState.STOP -> {
                onRestartGame()
            }
        }
    }
}