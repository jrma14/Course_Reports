//package com.example.coursereports
//
//import android.app.Activity
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.SurfaceHolder
//import android.view.View
//import android.view.ViewGroup
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContentProviderCompat.requireContext
//import androidx.core.content.ContextCompat
//import androidx.core.util.isNotEmpty
//import com.google.android.gms.common.ConnectionResult
//import com.google.android.gms.common.GoogleApiAvailability
//import com.google.android.gms.vision.CameraSource
//import com.google.android.gms.vision.Detector
//import com.google.android.gms.vision.barcode.Barcode
//import com.google.android.gms.vision.barcode.Barcode.QR_CODE
//import com.google.android.gms.vision.barcode.BarcodeDetector
//
//private const val TAG = "QR Reader"
//
//class QRCodeReader: AppCompatActivity() {
//    private val processor = object : Detector.Processor<Barcode> {
//
//        override fun receiveDetections(detections: Detector.Detections<Barcode>?) {
//            detections?.apply {
//                if (detectedItems.isNotEmpty()) {
//                    val qr = detectedItems.valueAt(0)
//                    // Parses the WiFi format for you and gives the field values directly
//                    // Similarly you can do qr.sms for SMS QR code etc.
//                    qr.wifi?.let {
//                        Log.d(TAG, "SSID: ${it.ssid}, Password: ${it.password}")
//                    }
//                }
//            }
//        }
//
//        override fun release() {}
//    }
//
//    private fun setupCameraView() {
//        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//            BarcodeDetector.Builder(requireContext()).setBarcodeFormats(QR_CODE).build().apply {
//                setProcessor(processor)
//                if (!isOperational) {
//                    Log.d(TAG, "Native QR detector dependencies not available!")
//                    return
//                }
//                cameraSource = CameraSource.Builder(requireContext(), this).setAutoFocusEnabled(true)
//                    .setFacing(CameraSource.CAMERA_FACING_BACK).build()
//            }
//        } else {
//            // Request camers permission from user
//            // Add <uses-permission android:name="android.permission.CAMERA" /> to AndroidManifest.xml
//        }
//    }
//
//    private val callback = object : SurfaceHolder.Callback {
//
//        override fun surfaceCreated(holder: SurfaceHolder) {
//            // Ideally, you should check the condition somewhere
//            // before inflating the layout which contains the SurfaceView
//            if (isPlayServicesAvailable(requireActivity()))
//                cameraSource?.start(holder)
//        }
//
//        override fun surfaceDestroyed(holder: SurfaceHolder) {
//            cameraSource?.stop()
//        }
//
//        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) { }
//    }
//
//
//    // Helper method to check if Google Play Services are up to-date on the phone
//    fun isPlayServicesAvailable(activity: Activity): Boolean {
//        val code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(applicationContext)
//        if (code != ConnectionResult.SUCCESS) {
//            GoogleApiAvailability.getInstance().getErrorDialog(activity, code, code).show()
//            return false
//        }
//        return true
//    }
//
//    // Create camera source and attach surface view callback to surface holder
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_camera_sheet, container, false).also {
//            setupCamera()
//            it.surfaceView.holder.addCallback(callback)
//        }
//    }
//
//    // Free up camera source resources
//    override fun onDestroy() {
//        super.onDestroy()
//        cameraSource?.release()
//    }
//}