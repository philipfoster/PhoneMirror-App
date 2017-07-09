/*
 * PhoneMirror-client
 * Copyright (C) 2017  Philip Foster
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.phonemirror.phonemirrorclient.data;

import android.util.Log;

import java.net.InetAddress;

/**
 * This class contains information about a compatible device on the network
 */
@SuppressWarnings("WeakerAccess")
public class Device {

    private String name;
    private String serialNo;
    private Type deviceType = Type.PHONE;
    private transient InetAddress ipAddress;
    private transient boolean connected;
    private Device(InetAddress ipAddress, String name, String serialNo, boolean connected, Type deviceType) {
        this.ipAddress = ipAddress;
        this.name = name;
        this.serialNo = serialNo;
        this.connected = connected;
        if (deviceType != null) {
            this.deviceType = deviceType;
        }
    }

    public void setIpAddr(InetAddress ip) {
        this.ipAddress = ip;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public String getName() {
        return name;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public boolean isConnected() {
        return connected;
    }

    public Type getDeviceType() {
        return deviceType;
    }

    @Override
    public String toString() {
        return "Device{" +
                "name='" + name + '\'' +
                ", serialNo='" + serialNo + '\'' +
                ", deviceType=" + deviceType +
                ", ipAddress=" + ipAddress +
                ", connected=" + connected +
                '}';
    }

    @SuppressWarnings("SimplifiableIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Device)) return false;

        Device device = (Device) o;

        if (isConnected() != device.isConnected()) return false;
        if (getName() != null ? !getName().equals(device.getName()) : device.getName() != null)
            return false;
        if (getSerialNo() != null ? !getSerialNo().equals(device.getSerialNo()) : device.getSerialNo() != null)
            return false;
        if (getDeviceType() != device.getDeviceType()) return false;
        return getIpAddress() != null ? getIpAddress().equals(device.getIpAddress()) : device.getIpAddress() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getSerialNo() != null ? getSerialNo().hashCode() : 0);
        result = 31 * result + (getDeviceType() != null ? getDeviceType().hashCode() : 0);
        result = 31 * result + (getIpAddress() != null ? getIpAddress().hashCode() : 0);
        result = 31 * result + (isConnected() ? 1 : 0);
        return result;
    }

    @SuppressWarnings("unused")
    public enum Type {PHONE, PC}

    @SuppressWarnings("unused")
    public static class Builder {

        private static final String TAG = "Device.Builder";

        private InetAddress ipAddress;
        private String name;
        private String serialNo;
        private boolean connected = false;
        private Device.Type type = null;

        public Builder setIpAddress(InetAddress ipAddress) {
            this.ipAddress = ipAddress;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSerialNo(String serialNo) {
            this.serialNo = serialNo;
            return this;
        }

        public Device build() {
            return new Device(ipAddress, name, serialNo, connected, type);
        }

        public Builder setDeviceType(Device.Type type) {
            this.type = type;
            return this;
        }

        /**
         * Copies all fields from a provided {@link Device}. Will overwrite previously set fields.
         * @param device the device to copy
         * @return a reference to this {@link Builder} for chaining.
         */
        public Builder copyFrom(Device device) {
            if (device == null) {
                Log.w(TAG, "copyFrom: Provided device was null");
                return this;
            }

            ipAddress = device.getIpAddress();
            name = device.getName();
            serialNo = device.getSerialNo();
            connected = device.isConnected();
            type = device.getDeviceType();

            return this;
        }
    }
}
