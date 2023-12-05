package com.ndiman.classmath.ui.home.materi

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.github.barteksc.pdfviewer.PDFView
import com.ndiman.classmath.BuildConfig
import com.ndiman.classmath.data.pref.FilePdf
import com.ndiman.classmath.databinding.ActivityPdfViewerBinding
import com.ndiman.classmath.ui.ViewModelFactory
import com.ndiman.classmath.ui.home.materi.viewmodel.PdfViewerViewModel
import java.io.InputStream

class PdfViewerActivity : AppCompatActivity() {

    private lateinit var pdfView: PDFView

    private val viewModel by viewModels<PdfViewerViewModel> {
        ViewModelFactory.getInstance(this)
    }

    private var binding: ActivityPdfViewerBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfViewerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        pdfView = binding!!.pdfViewer

        val tutorial = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_ID_PDF, FilePdf::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_ID_PDF)
        }

        if (tutorial != null){
            showLoading(true)
            viewModel.loadPdf(uri+ tutorial.tutorialFile)
        }

        viewModel.pdfStream.observe(this) { inputStream ->
            showPdf(inputStream)
        }

        viewModel.pdfError.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            Log.e("PDFView", errorMessage)
        }

        setUpAction()

    }

    private fun setUpAction(){
        binding?.topAppBar?.setNavigationOnClickListener {
            @Suppress("DEPRECATION")
            onBackPressed()
        }
    }


    private fun showPdf(inputStream: InputStream?){
        inputStream?.let {
            pdfView.fromStream(it)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(false)
                .password(null)
                .enableAntialiasing(true)
                .spacing(0)
                .onLoad { numberOfPages ->
                    Log.d("PDFView", "Jumlah Halaman: $numberOfPages")
                    showLoading(false)
                }
                .onError { t ->
                    Log.e("PDFView", "Kesalahan PDF: ${t.message}")
                    showLoading(false)
                }
                .load()
        }
    }

    private fun showLoading(state: Boolean) {
        binding?.progressBar?.visibility = state.toVisibility()
    }

    private fun Boolean.toVisibility() = if (this) View.VISIBLE else View.GONE

    companion object {
        private const val uri = BuildConfig.BASE_URI_PHOTO
        const val EXTRA_ID_PDF = "extra_id_pdf"
    }
    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}