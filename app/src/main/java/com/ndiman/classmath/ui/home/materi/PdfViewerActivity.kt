package com.ndiman.classmath.ui.home.materi

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.github.barteksc.pdfviewer.PDFView
import com.google.android.material.appbar.MaterialToolbar
import com.ndiman.classmath.BuildConfig
import com.ndiman.classmath.R
import com.ndiman.classmath.data.local.entity.FavoritMateri
import com.ndiman.classmath.data.local.entity.HistoriMateri
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

    private lateinit var toolbar: MaterialToolbar

    private val dataEntity = FavoritMateri()

    private val dataEntityHistory = HistoriMateri()

    private var binding: ActivityPdfViewerBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfViewerBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        pdfView = binding!!.pdfViewer
        toolbar= binding!!.topAppBar

        val tutorial = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra(EXTRA_ID_PDF, FilePdf::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra(EXTRA_ID_PDF)
        }

        if (tutorial != null){
            showLoading(true)
            dataEntity.idTutorial = tutorial.idTutorial
            dataEntity.grade = tutorial.grade
            dataEntity.title = tutorial.title
            dataEntity.tutorialImage = tutorial.tutorialImage
            dataEntity.tutorialFile = tutorial.tutorialFile

            dataEntityHistory.idTutorial = tutorial.idTutorial
            dataEntityHistory.grade = tutorial.grade
            dataEntityHistory.title = tutorial.title
            dataEntityHistory.tutorialImage = tutorial.tutorialImage
            dataEntityHistory.tutorialFile = tutorial.tutorialFile

            viewModel.loadPdf(uri+ tutorial.tutorialFile)
        }

        viewModel.pdfStream.observe(this) { inputStream ->
            showPdf(inputStream)
        }

        viewModel.pdfError.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            Log.e("PDFView", errorMessage)
        }

        setFavoritIcon()
        setUpAction()
        setFavoritMateri()
        setAddHistoryMateri()

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

    private fun setFavoritIcon() {
        viewModel.getIsFavoriteMateri(dataEntity.idTutorial).observeOnce(this) { isExist ->
            val iconRes = if (isExist) R.drawable.baseline_bookmark_24 else R.drawable.outline_bookmark_border_24
            toolbar.menu.findItem(R.id.fav_button)?.setIcon(iconRes)
        }
    }

    private fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: Observer<T>) {
        observe(owner, object : Observer<T> {
            override fun onChanged(t: T) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

    private fun setFavoritMateri(){
        toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.fav_button -> {
                    viewModel.getIsFavoriteMateri(dataEntity.idTutorial).observeOnce(this){isExist->
                        if (isExist){
                            item.setIcon(R.drawable.outline_bookmark_border_24)
                            viewModel.deleteFavMateri(dataEntity)
                            Toast.makeText(this, "Materi dihapus dari daftar favorit", Toast.LENGTH_SHORT).show()
                        } else{
                            item.setIcon(R.drawable.baseline_bookmark_24)
                            viewModel.insertFavMateri(dataEntity)
                            Toast.makeText(this, "Materi ditambahkan ke daftar favorit", Toast.LENGTH_SHORT).show()
                        }
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun setAddHistoryMateri(){
        viewModel.insertHistoryMateri(dataEntityHistory)
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