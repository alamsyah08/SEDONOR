package com.example.sedonor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.SurfaceView
import android.view.View
import android.widget.TextView
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.common.util.concurrent.ListenableFuture
import com.google.firebase.firestore.FirebaseFirestore
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors
import com.google.firebase.firestore.DocumentSnapshot

class CheckIn1 : AppCompatActivity() {

    private lateinit var cameraSelector: CameraSelector
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private lateinit var processCameraProvider: ProcessCameraProvider
    private lateinit var cameraPreview: Preview
    private lateinit var previewView: PreviewView

    private lateinit var imageAnalysis: ImageAnalysis

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_in1)

//        val db = FirebaseFirestore.getInstance()
//        val scannedValue = intent.getStringExtra("scannedValue")

        cameraSelector = CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build()
        cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        previewView = findViewById<PreviewView>(R.id.previewView)
        cameraProviderFuture.addListener(
            {
                processCameraProvider = cameraProviderFuture.get()
                bindCameraPreview(previewView)
                bindInputAnalyser()
            }, ContextCompat.getMainExecutor(this)
        )
    }

    private fun bindCameraPreview(view: View){

        cameraPreview = Preview.Builder()
            .setTargetRotation(previewView.display.rotation)
            .build()

        cameraPreview.setSurfaceProvider(previewView.surfaceProvider)
        processCameraProvider.bindToLifecycle(this, cameraSelector, cameraPreview)
    }

    private fun bindInputAnalyser(){
        val barcodeScanner: BarcodeScanner = BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build()
        )

        imageAnalysis = ImageAnalysis.Builder()
            .setTargetRotation(previewView.display.rotation)
            .build()

        val cameraExecutor = Executors.newSingleThreadExecutor()

        imageAnalysis.setAnalyzer(cameraExecutor){ imageProxy ->
            processImageProxy(barcodeScanner, imageProxy)
        }

        processCameraProvider.bindToLifecycle(this, cameraSelector, imageAnalysis)
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun processImageProxy(
        barcodeScanner: BarcodeScanner,
        imageProxy: ImageProxy
    ){
        val inputImage = InputImage.fromMediaImage(imageProxy.image!!, imageProxy.imageInfo.rotationDegrees)
        barcodeScanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                if(barcodes.isNotEmpty()){
                    val scannedValue = barcodes[0].displayValue

                    Handler().postDelayed({
                        if (onScan != null) {
                            onScan?.invoke(barcodes)
                            onScan = null
//                            finish()

    //                        val intent = Intent(this, CheckinDetail::class.java)
    //                        intent.putExtra("scannedValue", scannedValue)
                            getDataQr(scannedValue)
//                            startActivity(intent)
                        }
                    }, 200)
                }
            }.addOnFailureListener{
                it.printStackTrace()
            }.addOnCompleteListener{
                imageProxy.close()
            }
    }

    companion object {
        private var onScan: ((barcodes: List<Barcode>) -> Unit)? = null
        fun startScanner(context: Context, onScan: (barcodes: List<Barcode>)-> Unit){
            this.onScan = onScan
            Intent(context, CheckIn1::class.java).also {
                context.startActivity(it)
            }
        }
    }

    private fun getDataQr(scannedValue: String?){
        db.collection("tempat").document(scannedValue.toString())
            .get()
            .addOnCompleteListener { task ->
                val tempat = convertDocumentSnapshotToLokasiDonor(task.result)

                val intent = Intent(this, CheckinDetail::class.java)
                intent.putExtra("NAMADONOR", tempat?.nama)
                intent.putExtra("ALAMATLOKASI", tempat?.lokasi)
                intent.putExtra("IMAGELOKASI", tempat?.foto)

                finish()
                startActivity(intent)
            }
    }

    private fun convertDocumentSnapshotToLokasiDonor(documentSnapshot: DocumentSnapshot): LokasiDonor? {
        return documentSnapshot.toObject(LokasiDonor::class.java)
    }

}