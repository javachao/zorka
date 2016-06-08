/**
 * Copyright 2012-2016 Rafal Lewczuk <rafal.lewczuk@jitlogic.com>
 * <p/>
 * This is free software. You can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * <p/>
 * This software is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * <p/>
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, see <http://www.gnu.org/licenses/>.
 */

package com.jitlogic.zorka.core.spy;

import static com.jitlogic.zorka.common.util.ZorkaUnsafe.BYTE_ARRAY_OFFS;
import static com.jitlogic.zorka.common.util.ZorkaUnsafe.UNSAFE;

/**
 * Constants and functions describing raw trace format (as generated by agent).
 */
public class TraceFormat {

    // Single-byte encodedd tags (potentially often used).
    public static final int TAG_STRING_REF     = 0x06; /** String reference is a tagged number. */
    public static final int TAG_SYMBOL_REF     = 0x07; /** Symbol reference is a tagged number. */
    public static final int TAG_KEYWORD_REF    = 0x08; /** Keyword reference is a tagged number. */
    public static final int TAG_TRACEID_REF    = 0x09; /** TraceID reference is a tagged number. */
    public static final int TAG_TRACE_START;
    public static final int TAG_TRACE_START_BE = 0x0a;
    public static final int TAG_TRACE_START_LE = 0x0b;
    public static final int TAG_TRACE_ATTR     = 0x0c;

    // Two-byte encoded tags (less often used ones)
    public static final int TAG_TRACE_BEGIN    = 0x21;
    public static final int TAG_EXCEPTION      = 0x22;
    public static final int TAG_EXCEPTION_REF  = 0x23;

    //
    public static final int TRACE_DROP_TOKEN   = 0xe0; /* TRACE DROP is encoded as simple value. */

    /** This is pre-computed 4-byte trace record header. */
    public static final int TREC_HEADER;

    public static final int TREC_HEADER_BE = 0xd80a9f48;
    public static final int TREC_HEADER_LE = 0x489f0bd8;

    static {
        /* Determine byte order here. */
        byte[] b = new byte[2];
        UNSAFE.putShort(b, BYTE_ARRAY_OFFS, (short)0x0102);
        if (b[0] == 0x01) {
            // Big Endian
            TAG_TRACE_START = TAG_TRACE_START_BE;
            TREC_HEADER = TREC_HEADER_BE;
        } else {
            // Little endian
            TAG_TRACE_START = TAG_TRACE_START_LE;
            TREC_HEADER = TREC_HEADER_LE;
        }
    }

}
