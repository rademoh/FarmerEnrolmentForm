package com.example.rabiu.farmerenrolmentliberia;

/**
 * Created by Rabiu on o1/28/15.
 */

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Windows on 19-01-2015.
 */
public class SyncService extends Service {
    // Storage for an instance of the sync adapter
    private static FarmersSyncAdapter sSyncAdapter = null;
    // Object to use as a thread-safe lock
    private static final Object sSyncAdapterLock = new Object();
    /*
     * Instantiate the sync adapter object.
     */
    @Override
    public void onCreate() {
        /*
         * Create the sync adapter as a singleton.
         * Set the sync adapter as syncable
         * Disallow parallel syncs
         */
        synchronized (sSyncAdapterLock) {
            if (sSyncAdapter == null) {
                sSyncAdapter = new FarmersSyncAdapter(getApplicationContext(), true);
            }
        }

    }
    /**
     * Return an object that allows the system to invoke
     * the sync adapter.
     *
     */
    @Override
    public IBinder onBind(Intent intent) {
        /*
         * Get the object that allows external processes
         * to call onPerformSync(). The object is created
         * in the base class code when the SyncAdapter
         * constructors call super()
         */
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
