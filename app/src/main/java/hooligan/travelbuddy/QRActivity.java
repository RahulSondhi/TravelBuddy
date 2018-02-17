package hooligan.travelbuddy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

public class QRActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

//        BarcodeDetector detector =
//                new BarcodeDetector.Builder(getApplicationContext())
//                        .setBarcodeFormats(Barcode.DATA_MATRIX | Barcode.QR_CODE)
//                        .build();
//
//        Bitmap myBitmap = BitmapFactory.decodeResource(
//                getApplicationContext().getResources(),
//                R.drawable.puppy);
//
//        Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
//        SparseArray<Barcode> barcodes = detector.detect(frame);
//
//        CameraSource cameraSource = new CameraSource.Builder(this, detector) .setRequestedPreviewSize(640, 480) .build();
//
//        View cameraView = (SurfaceView)findViewById(R.id.camera_view);
//        View barcodeInfo = (TextView)findViewById(R.id.code_info);
//
//        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() { @Override public void surfaceCreated(SurfaceHolder holder) { }
//
//            @Override
//            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
//            }
//
//            @Override
//            public void surfaceDestroyed(SurfaceHolder holder) {
//            } });
    }
}
