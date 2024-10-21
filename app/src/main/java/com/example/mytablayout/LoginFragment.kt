package com.example.mytablayout

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.HideReturnsTransformationMethod
import android.text.method.LinkMovementMethod
import android.text.method.PasswordTransformationMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.mytablayout.databinding.FragmentLoginBinding

class LoginFragment : Fragment(R.layout.fragment_login) {

    private var isPasswordVisible: Boolean = false

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        with(binding) {
            setupRegisterText()
            setupPasswordToggle()

            btnLogin.setOnClickListener {
                val username = lusername.text.toString()
                val password = lpassword.text.toString()
                val isChecked = box.isChecked


                when {
                    username.isEmpty() -> {
                        Toast.makeText(requireContext(), "Username is required", Toast.LENGTH_SHORT)
                            .show()
                    }

                    password.isEmpty() -> {
                        Toast.makeText(requireContext(), "Password is required", Toast.LENGTH_SHORT)
                            .show()
                    }

                    !isChecked -> {
                        Toast.makeText(
                            requireContext(),
                            "You must accept the terms",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    else -> {

                        val prefs = requireActivity().getSharedPreferences(
                            "UserData",
                            AppCompatActivity.MODE_PRIVATE
                        )
                        val savedUsername = prefs.getString("username", "")
                        val savedPassword = prefs.getString("password", "")

                        if (username == savedUsername && password == savedPassword) {
                            // Navigasi ke halaman HomePage
                            val intent = Intent(requireContext(), HomePage::class.java).apply {
                                putExtra("username", savedUsername)

                            }
                            startActivity(intent)
                            requireActivity().finish()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Username or Password is incorrect",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }


    private fun setupPasswordToggle() {
        binding.lpassword.setOnTouchListener { _, event ->
            val drawableEnd = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (binding.lpassword.right - binding.lpassword.compoundDrawables[drawableEnd].bounds.width())) {
                    togglePasswordVisibility()
                    return@setOnTouchListener true
                }
            }
            false
        }
    }

    private fun togglePasswordVisibility() {
        if (isPasswordVisible) {
            // Sembunyikan password
            binding.lpassword.transformationMethod = PasswordTransformationMethod.getInstance()
            binding.lpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.lock, 0, R.drawable.eye_icon_close, 0
            )
        } else {
            // Tampilkan password
            binding.lpassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            binding.lpassword.setCompoundDrawablesRelativeWithIntrinsicBounds(
                R.drawable.lock, 0, R.drawable.eye, 0
            )
        }
        binding.lpassword.setSelection(binding.lpassword.text.length)
        isPasswordVisible = !isPasswordVisible
    }

    private fun setupRegisterText() {
        val text = "New Member? Register"
        val spannableString = SpannableString(text)


        spannableString.setSpan(
            ForegroundColorSpan(Color.BLACK),
            text.indexOf("New Member"),
            text.indexOf("New Member") + "New Member?".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        val registerSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Navigasi ke halaman registrasi
                val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)
                viewPager?.setCurrentItem(0, true)
            }
        }


        spannableString.setSpan(
            ForegroundColorSpan(Color.BLUE),  // Warna biru untuk "Register"
            text.indexOf("Register"),
            text.indexOf("Register") + "Register".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Menambahkan ClickableSpan ke "Register"
        spannableString.setSpan(
            registerSpan,
            text.indexOf("Register"),
            text.indexOf("Register") + "Register".length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        // Atur text ke TextView
        binding.tvRegister.text = spannableString
        binding.tvRegister.movementMethod =
            LinkMovementMethod.getInstance()
    }
}
