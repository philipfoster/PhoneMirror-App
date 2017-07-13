
package com.github.phonemirror.phonemirrorclient.util;

import android.support.annotation.Nullable;

import com.github.phonemirror.phonemirrorclient.BuildConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Obtains configuration settings. These may come from multiple sources, including
 * defaults (included in {@link BuildConfig}) and user settings.
 */
public class Configuration {

    @Nullable
    public InetAddress getMulticastGroup() {
        try {
            return InetAddress.getByName(BuildConfig.multicastAddress);
        } catch (UnknownHostException e) {
            return null;
        }
    }

    public int getPort() {
        return BuildConfig.port;
    }

    public int getTimeout() {
        return BuildConfig.timeout;
    }

    public long getBeaconFrequency() {
        return BuildConfig.beaconFrequency;
    }
}
