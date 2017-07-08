package com.github.phonemirror.phonemirrorclient.repo;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.UUID;

/**
 * This class manages a persistent serial ID. This id allows a specific installation to be
 * identified so the correct cryptographic key can be obtained when communicating with other
 * devices.
 */
public class SerialRepository {

    private static final String REPO_KEY = "SerialId";
    private static final String TAG = "SerialRepository";

    private String serial;

    /**
     * Create a new {@link SerialRepository} instance.
     * @implNote This class will not store the provided {@link Context}, so callers do not need to
     * worry about leaking it.
     * @param ctx the current context
     */
    public SerialRepository(Context ctx) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ctx);

        serial = prefs.getString(REPO_KEY, null);
        if (serial == null) {
            Log.d(TAG, "SerialRepository: Serial ID does not exist. Generating a new one.");
            serial = generateSerialId(prefs);
        } else {
            Log.d(TAG, "SerialRepository: Successfully loaded serial id from SharedPrefs");
        }

    }

    /**
     * Generate a new ID and save it to {@code SharedPreferences}
     * @param prefs the {@link SharedPreferences} to save to.
     * @return the generated ID
     */
    private String generateSerialId(SharedPreferences prefs) {

        String id = UUID.randomUUID().toString();
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REPO_KEY, id);

        if (!editor.commit()) {
            // this should only happen if two instances of SerialRepository try to write a new
            // value at the same time.
            Log.w(TAG, "SerialRepository: writing serial id failed.");
            id = prefs.getString(REPO_KEY, null);
            if (id == null) {
                throw new IllegalStateException("Could not obtain a serial id.");
            }
        }

        return id;
    }


    /**
     * Obtain the application installation's unique identifier.
     * @return the ID.
     */
    public String getSerialId() {
        return serial;
    }


}
