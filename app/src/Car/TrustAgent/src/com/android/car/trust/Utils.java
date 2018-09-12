/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */
package com.android.car.trust;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;

import java.nio.ByteBuffer;
import java.util.UUID;

public class Utils {

    public static byte[] getBytes(long l) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.SIZE / Byte.SIZE);
        buffer.putLong(0, l);
        return buffer.array();
    }

    public static long getLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.SIZE / Byte.SIZE);
        buffer.put(bytes);
        buffer.flip();
        return buffer.getLong();
    }

    public static BluetoothGattCharacteristic getCharacteristic(int uuidRes,
            BluetoothGattService service, Context context) {
        return service.getCharacteristic(UUID.fromString(context.getString(uuidRes)));
    }
}
