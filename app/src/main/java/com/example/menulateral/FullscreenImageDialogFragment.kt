package com.example.menulateral

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide

class FullscreenImageDialogFragment(private val imageUrl: Int) : DialogFragment() {

    private lateinit var imageView: ImageView
    private lateinit var closeButton: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fullscreen_image_dialog, container, false)
        imageView = view.findViewById(R.id.image_view)
        closeButton = view.findViewById(R.id.close_button)

        Glide.with(this)
            .load(imageUrl)
            .into(imageView)

        closeButton.setOnClickListener {
            dismiss()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    }

}
