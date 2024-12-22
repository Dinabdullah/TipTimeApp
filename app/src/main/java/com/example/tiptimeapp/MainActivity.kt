package com.example.tiptimeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tiptimeapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlin.math.floor

class MainActivity : AppCompatActivity() {
    private var total = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        //enableEdgeToEdge()
        setContentView(binding.root)

        total = savedInstanceState?.getDouble("total") ?: 0.0
        binding.resultTv.text = total.toString()

        binding.calcBtn.setOnClickListener {
            val costInput = binding.serviceEt.text.toString()
            if (costInput.isEmpty()) {
                binding.serviceEt.error = "Required"
                return@setOnClickListener
            }

            val cost = binding.serviceEt.text.toString().toDouble()
            val checkedRB = binding.group.checkedRadioButtonId
            val tip = when (checkedRB) {
                R.id.amazing_btn -> 0.2
                R.id.good_btn -> 0.18
                else -> 0.15
            }
            total = cost * tip

            if (binding.tipSwitch.isChecked)
                total = floor(total)

            binding.resultTv.text = "$$total"

            Snackbar.make(
                binding.root,
                "Reset Everything",
                BaseTransientBottomBar.LENGTH_INDEFINITE
            )
                .setAction("Proceed") {
                    binding.serviceEt.text?.clear()
                    binding.group.check(R.id.amazing_btn)
                    binding.tipSwitch.isChecked = true
                    binding.resultTv.setText("Tip Amount")
                }
                .setBackgroundTint(getColor(android.R.color.background_dark))
                .show()
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("total", total)
    }
}