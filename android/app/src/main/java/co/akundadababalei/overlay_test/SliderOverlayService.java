package co.akundadababalei.overlay_test;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.IdRes;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.chinalwb.slidetoconfirmlib.ISlideListener;
import com.chinalwb.slidetoconfirmlib.SlideToConfirm;


public class SliderOverlayService extends Service {
    private WindowManager windowManager;
    private View switchView;

    @Override
    public IBinder onBind(android.content.Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();
        switchView = LayoutInflater.from(this).inflate(R.layout.slider_layout, null);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
        );

        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.x = 428;
        params.y = 1058;

        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        windowManager.addView(switchView, params);
        SlideToConfirm floating_switch = switchView.findViewById(R.id.slide_to_confirm);
        floating_switch.setEngageText("Arrive");
//        floating_switch.setOnTouchListener(
//                new View.OnTouchListener() {
//                    private int initialX;
//                    private int initialY;
//                    private float touchX;
//                    private float touchY;
//                    private int lastAction;
//
//                    @Override
//                    public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
////                            initialX = params.x;
////                            initialY = params.y;
////
////                            touchX = motionEvent.getRawX();
////                            touchY = motionEvent.getRawY();
////
////                            Log.e("initialX", String.valueOf(initialX));
////                            Log.e("initialY", String.valueOf(initialY));
////
////                            Log.e("touchX", String.valueOf(touchX));
////                            Log.e("touchY", String.valueOf(touchY));
//
////                            lastAction = motionEvent.getAction();
//                            return true;
//                        }
//
//                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                            view.performClick();
//                            return true;
//                        }
//
//                        if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
//                            params.x = initialX + (int) (motionEvent.getRawX() - touchX);
//                            params.y = initialY + (int) (motionEvent.getRawY() - touchY);
//
//                            windowManager.updateViewLayout(switchView, params);
//                            Log.e("params.x", String.valueOf(params.x));
//                            Log.e("params.y", String.valueOf(params.y));
//                            lastAction = motionEvent.getAction();
//                            return true;
//                        }
//                        return false;
//                    }
//
//                }
//        );
        floating_switch.setSlideListener(new ISlideListener() {
            @Override
            public void onSlideStart() {

            }

            @Override
            public void onSlideMove(float v) {
            }

            @Override
            public void onSlideCancel() {
            }

            @Override
            public void onSlideDone() {
                Log.e("Status", "Done");
                openApp();
                stopSelf();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (switchView != null) {
            windowManager.removeView(switchView);
        }
    }

    public void openApp() {
        Uri uri = Uri.parse("http://www.overlay-test.com/paul");
        Intent shared = new Intent(Intent.ACTION_VIEW);
        shared.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        shared.setData(uri);

        Intent chooser = Intent.createChooser(shared, "Open With");
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(chooser);
    }

}
