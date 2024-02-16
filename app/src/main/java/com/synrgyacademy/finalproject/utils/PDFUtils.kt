package com.synrgyacademy.finalproject.utils

import android.content.Context
import androidx.core.content.FileProvider
import com.rajat.pdfviewer.PdfViewerActivity
import com.rajat.pdfviewer.util.saveTo
import com.synrgyacademy.finalproject.R
import java.io.File
import java.io.InputStream

object PDFUtils {
    fun openPDFContent(context: Context, inputStream: InputStream, fileName: String) {
        val filePath = context.externalCacheDir?.absolutePath ?: context.cacheDir.absolutePath
        val fileNameExtension =
            fileName.ifEmpty { context.getString(R.string.app_name) + ".pdf" }
        val file = inputStream.saveToFile(filePath, fileNameExtension)

        val uri = FileProvider.getUriForFile(
            context,
            "com.synrgyacademy.finalproject" + ".provider", file
        )

        // Start the PdfViewerActivity with the file path
        context.startActivity(PdfViewerActivity.launchPdfFromPath(
            context = context,
            path = uri.toString(),
            pdfTitle = fileName,
            saveTo = saveTo.ASK_EVERYTIME
        ))
    }

    private fun InputStream.saveToFile(filePath: String, fileName: String): File = use { input ->
        val file = File(filePath, fileName)
        file.outputStream().use { output ->
            input.copyTo(output)
        }
        input.close()
        file
    }
}