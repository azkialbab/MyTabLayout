package com.example.mytablayout

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.mytablayout.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var isPasswordVisible: Boolean = false
    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)

        with(binding) {
            setupPasswordToggle()

            btnRegister.setOnClickListener {
                val username = rusername.text.toString()
                val email = remail.text.toString()
                val phone = rphone.text.toString()
                val password = rpassword.text.toString()

                // Validasi input
                if (username.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                    Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT)
                        .show()
                } else if (!termsCheckbox.isChecked) {
                    Toast.makeText(
                        requireContext(),
                        "You must agree to the terms",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                    val prefs = requireActivity().getSharedPreferences(
                        "UserData",
                        AppCompatActivity.MODE_PRIVATE
                    )
                    val editor = prefs.edit()
                    editor.putString("username", username)
                    editor.putString("email", email)
                    editor.putString("phone", phone)
                    editor.putString("password", password)
                    editor.apply()


                    val intent = Intent(requireContext(), HomePage::class.java).apply {
                        putExtra("username", username)
                    }
                    startActivity(intent)
                    requireActivity().finish()
                }
            }

            setupLoginText()
        }
    }

    private fun setupPasswordToggle() {
        binding.rpassword.setOnTouchListener { _, event ->
            val drawableEnd = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.rpassword.right - binding.rpassword.compoundDrawables[drawableEnd].bounds.width())) {
                    togglePasswordVisibility()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            binding.rpassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.rpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.lock, 0, R.drawable.eye_icon_close, 0
            )
        } else {
            binding.rpassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.rpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.lock, 0, R.drawable.eye, 0
            )
        }
        binding.rpassword.setSelection(binding.rpassword.text.length)
        isPasswordVisible = !isPasswordVisible
    }

    private fun setupLoginText() {
        val text = "Already have an account? Log in"
        val spannableString = SpannableString(text)

        // Span untuk membuat "Log in" bisa diklik
        val loginSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Pindah ke tab login (posisi 0)
                val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
                viewPager?.setCurrentItem(1, true)
            }
        }

        spannableString.setSpan(
            loginSpan,
            text.indexOf("Log in"),
            text.indexOf("Log in") + "Log in".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Atur teks ke TextView
        binding.tvlogin.text = spannableString
        binding.tvlogin.movementMethod = LinkMovementMethod.getInstance()
    }
}
