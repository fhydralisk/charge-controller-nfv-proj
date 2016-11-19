// Copyright (c) 2008 The Board of Trustees of The Leland Stanford Junior University
// Copyright (c) 2011, 2012 Open Networking Foundation
// Copyright (c) 2012, 2013 Big Switch Networks, Inc.
// This library was generated by the LoxiGen Compiler.
// See the file LICENSE.txt which should have been included in the source distribution

// Automatically generated by LOXI from template of_class.java
// Do not modify

package org.projectfloodlight.openflow.protocol.ver14;

import org.projectfloodlight.openflow.protocol.*;
import org.projectfloodlight.openflow.protocol.action.*;
import org.projectfloodlight.openflow.protocol.actionid.*;
import org.projectfloodlight.openflow.protocol.bsntlv.*;
import org.projectfloodlight.openflow.protocol.errormsg.*;
import org.projectfloodlight.openflow.protocol.meterband.*;
import org.projectfloodlight.openflow.protocol.instruction.*;
import org.projectfloodlight.openflow.protocol.instructionid.*;
import org.projectfloodlight.openflow.protocol.match.*;
import org.projectfloodlight.openflow.protocol.oxm.*;
import org.projectfloodlight.openflow.protocol.queueprop.*;
import org.projectfloodlight.openflow.types.*;
import org.projectfloodlight.openflow.util.*;
import org.projectfloodlight.openflow.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import com.google.common.collect.ImmutableList;
import java.util.Set;
import org.jboss.netty.buffer.ChannelBuffer;
import com.google.common.hash.PrimitiveSink;
import com.google.common.hash.Funnel;

class OFBsnVrfCounterStatsEntryVer14 implements OFBsnVrfCounterStatsEntry {
    private static final Logger logger = LoggerFactory.getLogger(OFBsnVrfCounterStatsEntryVer14.class);
    // version: 1.4
    final static byte WIRE_VERSION = 5;
    final static int MINIMUM_LENGTH = 8;

        private final static long DEFAULT_VRF = 0x0L;
        private final static List<U64> DEFAULT_VALUES = ImmutableList.<U64>of();

    // OF message fields
    private final long vrf;
    private final List<U64> values;
//
    // Immutable default instance
    final static OFBsnVrfCounterStatsEntryVer14 DEFAULT = new OFBsnVrfCounterStatsEntryVer14(
        DEFAULT_VRF, DEFAULT_VALUES
    );

    // package private constructor - used by readers, builders, and factory
    OFBsnVrfCounterStatsEntryVer14(long vrf, List<U64> values) {
        if(values == null) {
            throw new NullPointerException("OFBsnVrfCounterStatsEntryVer14: property values cannot be null");
        }
        this.vrf = vrf;
        this.values = values;
    }

    // Accessors for OF message fields
    @Override
    public long getVrf() {
        return vrf;
    }

    @Override
    public List<U64> getValues() {
        return values;
    }

    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_14;
    }



    public OFBsnVrfCounterStatsEntry.Builder createBuilder() {
        return new BuilderWithParent(this);
    }

    static class BuilderWithParent implements OFBsnVrfCounterStatsEntry.Builder {
        final OFBsnVrfCounterStatsEntryVer14 parentMessage;

        // OF message fields
        private boolean vrfSet;
        private long vrf;
        private boolean valuesSet;
        private List<U64> values;

        BuilderWithParent(OFBsnVrfCounterStatsEntryVer14 parentMessage) {
            this.parentMessage = parentMessage;
        }

    @Override
    public long getVrf() {
        return vrf;
    }

    @Override
    public OFBsnVrfCounterStatsEntry.Builder setVrf(long vrf) {
        this.vrf = vrf;
        this.vrfSet = true;
        return this;
    }
    @Override
    public List<U64> getValues() {
        return values;
    }

    @Override
    public OFBsnVrfCounterStatsEntry.Builder setValues(List<U64> values) {
        this.values = values;
        this.valuesSet = true;
        return this;
    }
    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_14;
    }



        @Override
        public OFBsnVrfCounterStatsEntry build() {
                long vrf = this.vrfSet ? this.vrf : parentMessage.vrf;
                List<U64> values = this.valuesSet ? this.values : parentMessage.values;
                if(values == null)
                    throw new NullPointerException("Property values must not be null");

                //
                return new OFBsnVrfCounterStatsEntryVer14(
                    vrf,
                    values
                );
        }

    }

    static class Builder implements OFBsnVrfCounterStatsEntry.Builder {
        // OF message fields
        private boolean vrfSet;
        private long vrf;
        private boolean valuesSet;
        private List<U64> values;

    @Override
    public long getVrf() {
        return vrf;
    }

    @Override
    public OFBsnVrfCounterStatsEntry.Builder setVrf(long vrf) {
        this.vrf = vrf;
        this.vrfSet = true;
        return this;
    }
    @Override
    public List<U64> getValues() {
        return values;
    }

    @Override
    public OFBsnVrfCounterStatsEntry.Builder setValues(List<U64> values) {
        this.values = values;
        this.valuesSet = true;
        return this;
    }
    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_14;
    }

//
        @Override
        public OFBsnVrfCounterStatsEntry build() {
            long vrf = this.vrfSet ? this.vrf : DEFAULT_VRF;
            List<U64> values = this.valuesSet ? this.values : DEFAULT_VALUES;
            if(values == null)
                throw new NullPointerException("Property values must not be null");


            return new OFBsnVrfCounterStatsEntryVer14(
                    vrf,
                    values
                );
        }

    }


    final static Reader READER = new Reader();
    static class Reader implements OFMessageReader<OFBsnVrfCounterStatsEntry> {
        @Override
        public OFBsnVrfCounterStatsEntry readFrom(ChannelBuffer bb) throws OFParseError {
            int start = bb.readerIndex();
            int length = U16.f(bb.readShort());
            if(length < MINIMUM_LENGTH)
                throw new OFParseError("Wrong length: Expected to be >= " + MINIMUM_LENGTH + ", was: " + length);
            if(bb.readableBytes() + (bb.readerIndex() - start) < length) {
                // Buffer does not have all data yet
                bb.readerIndex(start);
                return null;
            }
            if(logger.isTraceEnabled())
                logger.trace("readFrom - length={}", length);
            // pad: 2 bytes
            bb.skipBytes(2);
            long vrf = U32.f(bb.readInt());
            List<U64> values = ChannelUtils.readList(bb, length - (bb.readerIndex() - start), U64.READER);

            OFBsnVrfCounterStatsEntryVer14 bsnVrfCounterStatsEntryVer14 = new OFBsnVrfCounterStatsEntryVer14(
                    vrf,
                      values
                    );
            if(logger.isTraceEnabled())
                logger.trace("readFrom - read={}", bsnVrfCounterStatsEntryVer14);
            return bsnVrfCounterStatsEntryVer14;
        }
    }

    public void putTo(PrimitiveSink sink) {
        FUNNEL.funnel(this, sink);
    }

    final static OFBsnVrfCounterStatsEntryVer14Funnel FUNNEL = new OFBsnVrfCounterStatsEntryVer14Funnel();
    static class OFBsnVrfCounterStatsEntryVer14Funnel implements Funnel<OFBsnVrfCounterStatsEntryVer14> {
        private static final long serialVersionUID = 1L;
        @Override
        public void funnel(OFBsnVrfCounterStatsEntryVer14 message, PrimitiveSink sink) {
            // FIXME: skip funnel of length
            // skip pad (2 bytes)
            sink.putLong(message.vrf);
            FunnelUtils.putList(message.values, sink);
        }
    }


    public void writeTo(ChannelBuffer bb) {
        WRITER.write(bb, this);
    }

    final static Writer WRITER = new Writer();
    static class Writer implements OFMessageWriter<OFBsnVrfCounterStatsEntryVer14> {
        @Override
        public void write(ChannelBuffer bb, OFBsnVrfCounterStatsEntryVer14 message) {
            int startIndex = bb.writerIndex();
            // length is length of variable message, will be updated at the end
            int lengthIndex = bb.writerIndex();
            bb.writeShort(U16.t(0));

            // pad: 2 bytes
            bb.writeZero(2);
            bb.writeInt(U32.t(message.vrf));
            ChannelUtils.writeList(bb, message.values);

            // update length field
            int length = bb.writerIndex() - startIndex;
            bb.setShort(lengthIndex, length);

        }
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("OFBsnVrfCounterStatsEntryVer14(");
        b.append("vrf=").append(vrf);
        b.append(", ");
        b.append("values=").append(values);
        b.append(")");
        return b.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        OFBsnVrfCounterStatsEntryVer14 other = (OFBsnVrfCounterStatsEntryVer14) obj;

        if( vrf != other.vrf)
            return false;
        if (values == null) {
            if (other.values != null)
                return false;
        } else if (!values.equals(other.values))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime *  (int) (vrf ^ (vrf >>> 32));
        result = prime * result + ((values == null) ? 0 : values.hashCode());
        return result;
    }

}
