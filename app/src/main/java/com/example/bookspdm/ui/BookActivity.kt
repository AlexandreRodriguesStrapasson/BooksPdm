package com.example.bookspdm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookspdm.databinding.ActivityBookBinding

class BookActivity : AppCompatActivity() {
    private val acb: ActivityBookBinding by lazy {
        ActivityBookBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(acb.root)
    }
}