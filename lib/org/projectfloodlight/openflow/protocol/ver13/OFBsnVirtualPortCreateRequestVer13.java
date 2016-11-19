// Copyright (c) 2008 The Board of Trustees of The Leland Stanford Junior University
// Copyright (c) 2011, 2012 Open Networking Foundation
// Copyright (c) 2012, 2013 Big Switch Networks, Inc.
// This library was generated by the LoxiGen Compiler.
// See the file LICENSE.txt which should have been included in the source distribution

// Automatically generated by LOXI from template of_class.java
// Do not modify

package org.projectfloodlight.openflow.protocol.ver13;

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
import java.util.Set;
import org.jboss.netty.buffer.ChannelBuffer;
import com.google.common.hash.PrimitiveSink;
import com.google.common.hash.Funnel;

class OFBsnVirtualPortCreateRequestVer13 implements OFBsnVirtualPortCreateRequest {
    private static final Logger logger = LoggerFactory.getLogger(OFBsnVirtualPortCreateRequestVer13.class);
    // version: 1.3
    final static byte WIRE_VERSION = 4;
    final static int MINIMUM_LENGTH = 20;

        private final static long DEFAULT_XID = 0x0L;

    // OF message fields
    private final long xid;
    private final OFBsnVport vport;
//

    // package private constructor - used by readers, builders, and factory
    OFBsnVirtualPortCreateRequestVer13(long xid, OFBsnVport vport) {
        if(vport == null) {
            throw new NullPointerException("OFBsnVirtualPortCreateRequestVer13: property vport cannot be null");
        }
        this.xid = xid;
        this.vport = vport;
    }

    // Accessors for OF message fields
    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_13;
    }

    @Override
    public OFType getType() {
        return OFType.EXPERIMENTER;
    }

    @Override
    public long getXid() {
        return xid;
    }

    @Override
    public long getExperimenter() {
        return 0x5c16c7L;
    }

    @Override
    public long getSubtype() {
        return 0xfL;
    }

    @Override
    public OFBsnVport getVport() {
        return vport;
    }



    public OFBsnVirtualPortCreateRequest.Builder createBuilder() {
        return new BuilderWithParent(this);
    }

    static class BuilderWithParent implements OFBsnVirtualPortCreateRequest.Builder {
        final OFBsnVirtualPortCreateRequestVer13 parentMessage;

        // OF message fields
        private boolean xidSet;
        private long xid;
        private boolean vportSet;
        private OFBsnVport vport;

        BuilderWithParent(OFBsnVirtualPortCreateRequestVer13 parentMessage) {
            this.parentMessage = parentMessage;
        }

    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_13;
    }

    @Override
    public OFType getType() {
        return OFType.EXPERIMENTER;
    }

    @Override
    public long getXid() {
        return xid;
    }

    @Override
    public OFBsnVirtualPortCreateRequest.Builder setXid(long xid) {
        this.xid = xid;
        this.xidSet = true;
        return this;
    }
    @Override
    public long getExperimenter() {
        return 0x5c16c7L;
    }

    @Override
    public long getSubtype() {
        return 0xfL;
    }

    @Override
    public OFBsnVport getVport() {
        return vport;
    }

    @Override
    public OFBsnVirtualPortCreateRequest.Builder setVport(OFBsnVport vport) {
        this.vport = vport;
        this.vportSet = true;
        return this;
    }


        @Override
        public OFBsnVirtualPortCreateRequest build() {
                long xid = this.xidSet ? this.xid : parentMessage.xid;
                OFBsnVport vport = this.vportSet ? this.vport : parentMessage.vport;
                if(vport == null)
                    throw new NullPointerException("Property vport must not be null");

                //
                return new OFBsnVirtualPortCreateRequestVer13(
                    xid,
                    vport
                );
        }

    }

    static class Builder implements OFBsnVirtualPortCreateRequest.Builder {
        // OF message fields
        private boolean xidSet;
        private long xid;
        private boolean vportSet;
        private OFBsnVport vport;

    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_13;
    }

    @Override
    public OFType getType() {
        return OFType.EXPERIMENTER;
    }

    @Override
    public long getXid() {
        return xid;
    }

    @Override
    public OFBsnVirtualPortCreateRequest.Builder setXid(long xid) {
        this.xid = xid;
        this.xidSet = true;
        return this;
    }
    @Override
    public long getExperimenter() {
        return 0x5c16c7L;
    }

    @Override
    public long getSubtype() {
        return 0xfL;
    }

    @Override
    public OFBsnVport getVport() {
        return vport;
    }

    @Override
    public OFBsnVirtualPortCreateRequest.Builder setVport(OFBsnVport vport) {
        this.vport = vport;
        this.vportSet = true;
        return this;
    }
//
        @Override
        public OFBsnVirtualPortCreateRequest build() {
            long xid = this.xidSet ? this.xid : DEFAULT_XID;
            if(!this.vportSet)
                throw new IllegalStateException("Property vport doesn't have default value -- must be set");
            if(vport == null)
                throw new NullPointerException("Property vport must not be null");


            return new OFBsnVirtualPortCreateRequestVer13(
                    xid,
                    vport
                );
        }

    }


    final static Reader READER = new Reader();
    static class Reader implements OFMessageReader<OFBsnVirtualPortCreateRequest> {
        @Override
        public OFBsnVirtualPortCreateRequest readFrom(ChannelBuffer bb) throws OFParseError {
            int start = bb.readerIndex();
            // fixed value property version == 4
            byte version = bb.readByte();
            if(version != (byte) 0x4)
                throw new OFParseError("Wrong version: Expected=OFVersion.OF_13(4), got="+version);
            // fixed value property type == 4
            byte type = bb.readByte();
            if(type != (byte) 0x4)
                throw new OFParseError("Wrong type: Expected=OFType.EXPERIMENTER(4), got="+type);
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
            long xid = U32.f(bb.readInt());
            // fixed value property experimenter == 0x5c16c7L
            int experimenter = bb.readInt();
            if(experimenter != 0x5c16c7)
                throw new OFParseError("Wrong experimenter: Expected=0x5c16c7L(0x5c16c7L), got="+experimenter);
            // fixed value property subtype == 0xfL
            int subtype = bb.readInt();
            if(subtype != 0xf)
                throw new OFParseError("Wrong subtype: Expected=0xfL(0xfL), got="+subtype);
            OFBsnVport vport = OFBsnVportVer13.READER.readFrom(bb);

            OFBsnVirtualPortCreateRequestVer13 bsnVirtualPortCreateRequestVer13 = new OFBsnVirtualPortCreateRequestVer13(
                    xid,
                      vport
                    );
            if(logger.isTraceEnabled())
                logger.trace("readFrom - read={}", bsnVirtualPortCreateRequestVer13);
            return bsnVirtualPortCreateRequestVer13;
        }
    }

    public void putTo(PrimitiveSink sink) {
        FUNNEL.funnel(this, sink);
    }

    final static OFBsnVirtualPortCreateRequestVer13Funnel FUNNEL = new OFBsnVirtualPortCreateRequestVer13Funnel();
    static class OFBsnVirtualPortCreateRequestVer13Funnel implements Funnel<OFBsnVirtualPortCreateRequestVer13> {
        private static final long serialVersionUID = 1L;
        @Override
        public void funnel(OFBsnVirtualPortCreateRequestVer13 message, PrimitiveSink sink) {
            // fixed value property version = 4
            sink.putByte((byte) 0x4);
            // fixed value property type = 4
            sink.putByte((byte) 0x4);
            // FIXME: skip funnel of length
            sink.putLong(message.xid);
            // fixed value property experimenter = 0x5c16c7L
            sink.putInt(0x5c16c7);
            // fixed value property subtype = 0xfL
            sink.putInt(0xf);
            message.vport.putTo(sink);
        }
    }


    public void writeTo(ChannelBuffer bb) {
        WRITER.write(bb, this);
    }

    final static Writer WRITER = new Writer();
    static class Writer implements OFMessageWriter<OFBsnVirtualPortCreateRequestVer13> {
        @Override
        public void write(ChannelBuffer bb, OFBsnVirtualPortCreateRequestVer13 message) {
            int startIndex = bb.writerIndex();
            // fixed value property version = 4
            bb.writeByte((byte) 0x4);
            // fixed value property type = 4
            bb.writeByte((byte) 0x4);
            // length is length of variable message, will be updated at the end
            int lengthIndex = bb.writerIndex();
            bb.writeShort(U16.t(0));

            bb.writeInt(U32.t(message.xid));
            // fixed value property experimenter = 0x5c16c7L
            bb.writeInt(0x5c16c7);
            // fixed value property subtype = 0xfL
            bb.writeInt(0xf);
            message.vport.writeTo(bb);

            // update length field
            int length = bb.writerIndex() - startIndex;
            bb.setShort(lengthIndex, length);

        }
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("OFBsnVirtualPortCreateRequestVer13(");
        b.append("xid=").append(xid);
        b.append(", ");
        b.append("vport=").append(vport);
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
        OFBsnVirtualPortCreateRequestVer13 other = (OFBsnVirtualPortCreateRequestVer13) obj;

        if( xid != other.xid)
            return false;
        if (vport == null) {
            if (other.vport != null)
                return false;
        } else if (!vport.equals(other.vport))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime *  (int) (xid ^ (xid >>> 32));
        result = prime * result + ((vport == null) ? 0 : vport.hashCode());
        return result;
    }

}
