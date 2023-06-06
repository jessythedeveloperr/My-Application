package com.example.myapplication
import android.os.Bundle
import andriod.os.CountDownTimer
import andriod.view.View
import andriod.widget.Button
import andriod.widget.TextView
import andriod.graphics.Color
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    // Declare variables for UI components
    private lateinit var cup1Button: Button
    private lateinit var cup2Button: Button
    private lateinit var cup3Button: Button
    private lateinit var messageText: TextView
    private lateint var scoreText: TextView
    private lateint var levelText: TextView
    //Variables to track the current level and the cup with the ball
    private var currentLevel: Int = 1
    private var ballCup: Int = 0
    private var score: Int = 0
    private var level: Int = 1
    private var difficultyFactor: Int = 1
    private var timer: CountDownTimer? = null
    private var timeLeftMillis: Long = 0
    private var timerInterval: Long = 1000
    private var totalTimeMillis: Long = 60000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Intialize UI components variables using findViewById()
        cup1Button = findViewById(R.id.cup1)
        cup2Button = findViewById(R.id.cup2)
        cup3Button = findViewById(R.id.cup3)
        messageText = findViewById(R.id.messageText)
        scoreText = findViewById(R.id.scoreText)
        levelText = findViewById(R.id.levelText)
        restartButton = findViewById(R.id.restartButton)

        //Set click listeners for the cup buttons
        cup1Button.setOnClickListener {
            if (ballCup == 1) {
                messageText.text = "Correct"
                messageText.setTextColor(color.GREEN)
                increaseScore()
                increaseDifficulty()
            } else {
                messageText.text = "Wrong"
                messageText.text.setTextColor(Color.RED)
            }
        }

        cup2Button.setOnClickListener {
            if (ballCup == 1) {
                messageText.text = "Correct"
                messageText.setTextColor(color.GREEN)
                increaseScore()
                increaseDifficulty()
            } else {
                messageText.text = "Wrong"
                messageText.text.setTextColor(Color.RED)
            }

        }

        cup3Button.setOnClickListener {
            if (ballCup == 1) {
                messageText.text = "Correct"
                messageText.setTextColor(color.GREEN)
                increaseScore()
                increaseDifficulty()
            } else {
                messageText.text = "Wrong"
                messageText.text.setTextColor(Color.RED)
            }
        }
            cup1Button.setOnClickListener { checkAnswer(1) }
            cup2Button.setOnClickListener { checkAnswer(2) }
            cup3Button.setOnClickListener { checkAnswer(3) }
            restartButton.setOnClickListener { restartGame() }

            shuffleCups()
            startTimer()

            //Intialize the game
            startNewGame()

        }
         private fun startNewGame() {
             ballCup =(1..3).random()
             score = 0
             level = 1
             difficultyFactor = 1
             updateScore()
             updateLevel()
             resetMessage()
         }
         private fun checkAnswer(selectedCup: Int) {
             if(selectedCup ==ballCup) {
                 messageText.setTextColor(getColor(R.color.correct))
                 messageText.text = getString(R.string.correct)
                 score++
             } else {
                 messageText.setTextColor(getColor(R.color.wrong))
                 messageText.text = getString(R.string.wrong)
             }
             updateScore()
             shuffleCups()

             if(selectedCup ==ballCup) {
              score++
              if (score % 2 ==0) {
                  level++
                  difficultyFactor *= 2
                  updateLevel()
              }
             }
         }

         private fun shuffleCups() {
             val anim = AnimationUtils.loadAnimation(this, R.anim.flip)
             cup1Button.startAnimation(anim)
             cup2Button.startAnimation(anim)
             cup3Button.startAnimation(anim)
             ballCup =(1..3).random()
         }
         private fun updateScore() {
             scoreText.text = getString(R.string.score, score)
         }
         private fun updateLevel() {
             levelText.text = getString(R.string.level, level)
         }
         private fun resetMessage() {
             messageText.setTextColor(getColor(R.color.default_text_color))
             messageText.text = ""
         }
         private fun startTimer() {
             timer?.cancel()

             timer = object :CountDownTimer(totalTimeMillis, timerInterval) {
                 override fun onTick(millisUntilFinished: Long) {
                     timeLeftMillis = millisUntilFinished
                     updateTimeUI()
                 }

                 private fun restartGame() {
                     score = 0
                     level = 1

                     scoreText.text = "Score: $score"
                     messageText.text = ""

                     cup1.isEnabled = true
                     cup2.isEnabled = true
                     cup3.isEnabled = true

                     startRound()
                 }
                 private fun startRound() {
                     shuffleCups()

                     cup1.text = ""
                     cup2.text = ""
                     cup3.text = ""
                     messageText.text = "Find the ball!"

                     cup1.isEnabled = true
                     cup2.isEnabled = true
                     cup3.isEnabled = true
                 }

                 override fun onFinish() {
                     cup1.isEnabled = false
                     cup2.isEnabled = false
                     cup3.isEnabled = false

                     messageText.text = "Game Over! You ran out of time."
                 }
             }.start()

             if (score >= targetScore) {

                 val dialogBuilder = AlertDialog.Builder(this)
                 dialogBuilder.setMessage("Congratulations! You completed the level.")
                     .setPositiveButton("Restart") { _, _ ->
                         restartGame()
                     }
                     .setNegativeButton("Play Again") { _, _ ->
                         startRound()
                     }
                     .setCancelable(false)
                     .show()
             }
         }
}