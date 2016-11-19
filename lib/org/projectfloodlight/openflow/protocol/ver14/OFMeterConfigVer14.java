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

class OFMeterConfigVer14 implements OFMeterConfig {
    private static final Logger logger = LoggerFactory.getLogger(OFMeterConfigVer14.class);
    // version: 1.4
    final static byte WIRE_VERSION = 5;
    final static int MINIMUM_LENGTH = 8;

        private final static int DEFAULT_FLAGS = 0x0;
        private final static long DEFAULT_METER_ID = 0x0L;
        private final static List<OFMeterBand> DEFAULT_ENTRIES = ImmutableList.<OFMeterBand>of();

    // OF message fields
    private final int flags;
    private final long meterId;
    private final List<OFMeterBand> entries;
//
    // Immutable default instance
    final static OFMeterConfigVer14 DEFAULT = new OFMeterConfigVer14(
        DEFAULT_FLAGS, DEFAULT_METER_ID, DEFAULT_ENTRIES
    );

    // package private constructor - used by readers, builders, and factory
    OFMeterConfigVer14(int flags, long meterId, List<OFMeterBand> entries) {
        if(entries == null) {
            throw new NullPointerException("OFMeterConfigVer14: property entries cannot be null");
        }
        this.flags = flags;
        this.meterId = meterId;
        this.entries = entries;
    }

    // Accessors for OF message fields
    @Override
    public int getFlags() {
        return flags;
    }

    @Override
    public long getMeterId() {
        return meterId;
    }

    @Override
    public List<OFMeterBand> getEntries() {
        return entries;
    }

    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_14;
    }



    public OFMeterConfig.Builder createBuilder() {
        return new BuilderWithParent(this);
    }

    static class BuilderWithParent implements OFMeterConfig.Builder {
        final OFMeterConfigVer14 parentMessage;

        // OF message fields
        private boolean flagsSet;
        private int flags;
        private boolean meterIdSet;
        private long meterId;
        private boolean entriesSet;
        private List<OFMeterBand> entries;

        BuilderWithParent(OFMeterConfigVer14 parentMessage) {
            this.parentMessage = parentMessage;
        }

    @Override
    public int getFlags() {
        return flags;
    }

    @Override
    public OFMeterConfig.Builder setFlags(int flags) {
        this.flags = flags;
        this.flagsSet = true;
        return this;
    }
    @Override
    public long getMeterId() {
        return meterId;
    }

    @Override
    public OFMeterConfig.Builder setMeterId(long meterId) {
        this.meterId = meterId;
        this.meterIdSet = true;
        return this;
    }
    @Override
    public List<OFMeterBand> getEntries() {
        return entries;
    }

    @Override
    public OFMeterConfig.Builder setEntries(List<OFMeterBand> entries) {
        this.entries = entries;
        this.entriesSet = true;
        return this;
    }
    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_14;
    }



        @Override
        public OFMeterConfig build() {
                int flags = this.flagsSet ? this.flags : parentMessage.flags;
                long meterId = this.meterIdSet ? this.meterId : parentMessage.meterId;
                List<OFMeterBand> entries = this.entriesSet ? this.entries : parentMessage.entries;
                if(entries == null)
                    throw new NullPointerException("Property entries must not be null");

                //
                return new OFMeterConfigVer14(
                    flags,
                    meterId,
                    entries
                );
        }

    }

    static class Builder implements OFMeterConfig.Builder {
        // OF message fields
        private boolean flagsSet;
        private int flags;
        private boolean meterIdSet;
        private long meterId;
        private boolean entriesSet;
        private List<OFMeterBand> entries;

    @Override
    public int getFlags() {
        return flags;
    }

    @Override
    public OFMeterConfig.Builder setFlags(int flags) {
        this.flags = flags;
        this.flagsSet = true;
        return this;
    }
    @Override
    public long getMeterId() {
        return meterId;
    }

    @Override
    public OFMeterConfig.Builder setMeterId(long meterId) {
        this.meterId = meterId;
        this.meterIdSet = true;
        return this;
    }
    @Override
    public List<OFMeterBand> getEntries() {
        return entries;
    }

    @Override
    public OFMeterConfig.Builder setEntries(List<OFMeterBand> entries) {
        this.entries = entries;
        this.entriesSet = true;
        return this;
    }
    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_14;
    }

//
        @Override
        public OFMeterConfig build() {
            int flags = this.flagsSet ? this.flags : DEFAULT_FLAGS;
            long meterId = this.meterIdSet ? this.meterId : DEFAULT_METER_ID;
            List<OFMeterBand> entries = this.entriesSet ? this.entries : DEFAULT_ENTRIES;
            if(entries == null)
                throw new NullPointerException("Property entries must not be null");


            return new OFMeterConfigVer14(
                    flags,
                    meterId,
                    entries
                );
        }

    }


    final static Reader READER = new Reader();
    static class Reader implements OFMessageReader<OFMeterConfig> {
        @Override
        public OFMeterConfig readFrom(ChannelBuffer bb) throws OFParseError {
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
            int flags = U16.f(bb.readShort());
            long meterId = U32.f(bb.readInt());
            List<OFMeterBand> entries = ChannelUtils.readList(bb, length - (bb.readerIndex() - start), OFMeterBandVer14.READER);

            OFMeterConfigVer14 meterConfigVer14 = new OFMeterConfigVer14(
                    flags,
                      meterId,
                      entries
                    );
            if(logger.isTraceEnabled())
                logger.trace("readFrom - read={}", meterConfigVer14);
            return meterConfigVer14;
        }
    }

    public void putTo(PrimitiveSink sink) {
        FUNNEL.funnel(this, sink);
    }

    final static OFMeterConfigVer14Funnel FUNNEL = new OFMeterConfigVer14Funnel();
    static class OFMeterConfigVer14Funnel implements Funnel<OFMeterConfigVer14> {
        private static final long serialVersionUID = 1L;
        @Override
        public void funnel(OFMeterConfigVer14 message, PrimitiveSink sink) {
            // FIXME: skip funnel of length
            sink.putInt(message.flags);
            sink.putLong(message.meterId);
            FunnelUtils.putList(message.entries, sink);
        }
    }


    public void writeTo(ChannelBuffer bb) {
        WRITER.write(bb, this);
    }

    final static Writer WRITER = new Writer();
    static class Writer implements OFMessageWriter<OFMeterConfigVer14> {
        @Override
        public void write(ChannelBuffer bb, OFMeterConfigVer14 message) {
            int startIndex = bb.writerIndex();
            // length is length of variable message, will be updated at the end
            int lengthIndex = bb.writerIndex();
            bb.writeShort(U16.t(0));

            bb.writeShort(U16.t(message.flags));
            bb.writeInt(U32.t(message.meterId));
            ChannelUtils.writeList(bb, message.entries);

            // update length field
            int length = bb.writerIndex() - startIndex;
            bb.setShort(lengthIndex, length);

        }
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("OFMeterConfigVer14(");
        b.append("flags=").append(flags);
        b.append(", ");
        b.append("meterId=").append(meterId);
        b.append(", ");
        b.append("entries=").append(entries);
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
        OFMeterConfigVer14 other = (OFMeterConfigVer14) obj;

        if( flags != other.flags)
            return false;
        if( meterId != other.meterId)
            return false;
        if (entries == null) {
            if (other.entries != null)
                return false;
        } else if (!entries.equals(other.entries))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime * result + flags;
        result = prime *  (int) (meterId ^ (meterId >>> 32));
        result = prime * result + ((entries == null) ? 0 : entries.hashCode());
        return result;
    }

}
