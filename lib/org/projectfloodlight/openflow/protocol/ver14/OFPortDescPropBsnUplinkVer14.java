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
import org.jboss.netty.buffer.ChannelBuffer;
import com.google.common.hash.PrimitiveSink;
import com.google.common.hash.Funnel;

class OFPortDescPropBsnUplinkVer14 implements OFPortDescPropBsnUplink {
    private static final Logger logger = LoggerFactory.getLogger(OFPortDescPropBsnUplinkVer14.class);
    // version: 1.4
    final static byte WIRE_VERSION = 5;
    final static int LENGTH = 12;


    // OF message fields
//
    // Immutable default instance
    final static OFPortDescPropBsnUplinkVer14 DEFAULT = new OFPortDescPropBsnUplinkVer14(

    );

    final static OFPortDescPropBsnUplinkVer14 INSTANCE = new OFPortDescPropBsnUplinkVer14();
    // private empty constructor - use shared instance!
    private OFPortDescPropBsnUplinkVer14() {
    }

    // Accessors for OF message fields
    @Override
    public int getType() {
        return 0xffff;
    }

    @Override
    public long getExperimenter() {
        return 0x5c16c7L;
    }

    @Override
    public long getExpType() {
        return 0x0L;
    }

    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_14;
    }



    // no data members - do not support builder
    public OFPortDescPropBsnUplink.Builder createBuilder() {
        throw new UnsupportedOperationException("OFPortDescPropBsnUplinkVer14 has no mutable properties -- builder unneeded");
    }


    final static Reader READER = new Reader();
    static class Reader implements OFMessageReader<OFPortDescPropBsnUplink> {
        @Override
        public OFPortDescPropBsnUplink readFrom(ChannelBuffer bb) throws OFParseError {
            int start = bb.readerIndex();
            // fixed value property type == 0xffff
            short type = bb.readShort();
            if(type != (short) 0xffff)
                throw new OFParseError("Wrong type: Expected=0xffff(0xffff), got="+type);
            int length = U16.f(bb.readShort());
            if(length != 12)
                throw new OFParseError("Wrong length: Expected=12(12), got="+length);
            if(bb.readableBytes() + (bb.readerIndex() - start) < length) {
                // Buffer does not have all data yet
                bb.readerIndex(start);
                return null;
            }
            if(logger.isTraceEnabled())
                logger.trace("readFrom - length={}", length);
            // fixed value property experimenter == 0x5c16c7L
            int experimenter = bb.readInt();
            if(experimenter != 0x5c16c7)
                throw new OFParseError("Wrong experimenter: Expected=0x5c16c7L(0x5c16c7L), got="+experimenter);
            // fixed value property expType == 0x0L
            int expType = bb.readInt();
            if(expType != 0x0)
                throw new OFParseError("Wrong expType: Expected=0x0L(0x0L), got="+expType);

            if(logger.isTraceEnabled())
                logger.trace("readFrom - returning shared instance={}", INSTANCE);
            return INSTANCE;
        }
    }

    public void putTo(PrimitiveSink sink) {
        FUNNEL.funnel(this, sink);
    }

    final static OFPortDescPropBsnUplinkVer14Funnel FUNNEL = new OFPortDescPropBsnUplinkVer14Funnel();
    static class OFPortDescPropBsnUplinkVer14Funnel implements Funnel<OFPortDescPropBsnUplinkVer14> {
        private static final long serialVersionUID = 1L;
        @Override
        public void funnel(OFPortDescPropBsnUplinkVer14 message, PrimitiveSink sink) {
            // fixed value property type = 0xffff
            sink.putShort((short) 0xffff);
            // fixed value property length = 12
            sink.putShort((short) 0xc);
            // fixed value property experimenter = 0x5c16c7L
            sink.putInt(0x5c16c7);
            // fixed value property expType = 0x0L
            sink.putInt(0x0);
        }
    }


    public void writeTo(ChannelBuffer bb) {
        WRITER.write(bb, this);
    }

    final static Writer WRITER = new Writer();
    static class Writer implements OFMessageWriter<OFPortDescPropBsnUplinkVer14> {
        @Override
        public void write(ChannelBuffer bb, OFPortDescPropBsnUplinkVer14 message) {
            // fixed value property type = 0xffff
            bb.writeShort((short) 0xffff);
            // fixed value property length = 12
            bb.writeShort((short) 0xc);
            // fixed value property experimenter = 0x5c16c7L
            bb.writeInt(0x5c16c7);
            // fixed value property expType = 0x0L
            bb.writeInt(0x0);


        }
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("OFPortDescPropBsnUplinkVer14(");
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

        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;

        return result;
    }

}
