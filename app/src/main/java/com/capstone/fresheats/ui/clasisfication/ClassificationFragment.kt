package com.capstone.fresheats.ui.clasisfication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.capstone.fresheats.R
import com.capstone.fresheats.databinding.FragmentClassificationBinding
import com.capstone.fresheats.ml.Capstone
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ClassificationFragment : Fragment() {

    private var _binding: FragmentClassificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var toolbar: Toolbar
    private var camera: Button? = null
    private var gallery: Button? = null
    private var imageView: ImageView? = null
    private var result: TextView? = null
    private var imageSize = 224

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClassificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        camera = view.findViewById(R.id.btncamera)
        gallery = view.findViewById(R.id.btngallery)
        result = view.findViewById(R.id.tvresult)
        imageView = view.findViewById(R.id.imgclassification)

        camera?.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 3)
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    100
                )
            }
        }

        gallery?.setOnClickListener {
            val galleryIntent = Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            )
            startActivityForResult(galleryIntent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == AppCompatActivity.RESULT_OK) {
            when (requestCode) {
                3 -> {
                    val image = data?.extras?.get("data") as Bitmap
                    imageView?.let {
                        Glide.with(this)
                            .load(image)
                            .into(it)
                    }
                    classifyImage(image)
                    hideDescription()
                }
                1 -> {
                    val selectedImage: Uri? = data?.data
                    selectedImage?.let {
                        imageView?.let { it1 ->
                            Glide.with(this)
                                .load(it)
                                .into(it1)
                        }
                        val imageBitmap =
                            MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, it)
                        classifyImage(imageBitmap)
                        hideDescription()
                    }
                }
            }
        }
    }

    private fun hideDescription() {
        binding.tvdesc.visibility = View.GONE
    }

    private fun classifyImage(image: Bitmap) {
        try {
            if (_binding != null) {
                val model = Capstone.newInstance(requireContext())

                val resizedImage = Bitmap.createScaledBitmap(image, imageSize, imageSize, true)

                val inputFeature0 =
                    TensorBuffer.createFixedSize(intArrayOf(1, imageSize, imageSize, 3), DataType.FLOAT32)
                val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
                byteBuffer.order(ByteOrder.nativeOrder())

                val intValues = IntArray(imageSize * imageSize)
                resizedImage.getPixels(
                    intValues,
                    0,
                    resizedImage.width,
                    0,
                    0,
                    resizedImage.width,
                    resizedImage.height
                )
                var pixel = 0

                for (i in 0 until imageSize) {
                    for (j in 0 until imageSize) {
                        val value = intValues[pixel++]
                        byteBuffer.putFloat(((value shr 16) and 0xFF) * (1f / 255))
                        byteBuffer.putFloat(((value shr 8) and 0xFF) * (1f / 255))
                        byteBuffer.putFloat((value and 0xFF) * (1f / 255))
                    }
                }

                inputFeature0.loadBuffer(byteBuffer)

                val outputs = model.process(inputFeature0)
                val outputFeature0 = outputs.outputFeature0AsTensorBuffer
                val confidences = outputFeature0.floatArray

                if (confidences != null && confidences.isNotEmpty()) {
                    var maxPos = 0
                    var maxConfidence = confidences[0]

                    for (i in 1 until confidences.size) {
                        if (confidences[i] > maxConfidence) {
                            maxConfidence = confidences[i]
                            maxPos = i
                        }
                    }

                    val classes = arrayOf(
                        "Fresh Apple", "Fresh Banana", "Fresh Orange", "Fresh Potato", "Fresh Tomato",
                        "Fresh Carrot", "Fresh Meat", "Stale Apple", "Stale Banana", "Stale Orange",
                        "Stale Potato", "Stale Tomato", "Stale Carrot", "Stale Meat"
                    )

                    val resultText = String.format(
                        "Hasil Prediksi : %s\nPresentase : %d%%",
                        classes[maxPos],
                        (maxConfidence * 100).toInt()
                    )

                    result?.text = resultText
                }

                model.close()
            }
        } catch (e: IOException) {
            // TODO Handle the exception
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}