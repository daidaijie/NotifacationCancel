package top.daidaijie.notifacationcancel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

public class CancelBroadCast extends BroadcastReceiver {

    private OnCancelListener onCancelListener;

    public OnCancelListener getOnCancelListener() {
        return onCancelListener;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (onCancelListener != null) {
            onCancelListener.onCancel();
        }
    }

    private static String ACTION_CANCEL_NOTIFICATION = "com.cancel.notification";

    public static Intent newIntent() {
        Intent intent = new Intent(ACTION_CANCEL_NOTIFICATION);
        return intent;
    }

    public static IntentFilter newIntentFilter() {
        IntentFilter filter = new IntentFilter(ACTION_CANCEL_NOTIFICATION);
        return filter;
    }

    interface OnCancelListener {
        void onCancel();
    }
}
