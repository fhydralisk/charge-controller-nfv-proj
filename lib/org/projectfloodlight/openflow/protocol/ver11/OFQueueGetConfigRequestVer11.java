// Copyright (c) 2008 The Board of Trustees of The Leland Stanford Junior University
// Copyright (c) 2011, 2012 Open Networking Foundation
// Copyright (c) 2012, 2013 Big Switch Networks, Inc.
// This library was generated by the LoxiGen Compiler.
// See the file LICENSE.txt which should have been included in the source distribution

// Automatically generated by LOXI from template of_class.java
// Do not modify

package org.projectfloodlight.openflow.protocol.ver11;

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

class OFQueueGetConfigRequestVer11 implements OFQueueGetConfigRequest {
    private static final Logger logger = LoggerFactory.getLogger(OFQueueGetConfigRequestVer11.class);
    // version: 1.1
    final static byte WIRE_VERSION = 2;
    final static int LENGTH = 16;

        private final static long DEFAULT_XID = 0x0L;
        private final static OFPort DEFAULT_PORT = OFPort.ANY;

    // OF message fields
    private final long xid;
    private final OFPort port;
//
    // Immutable default instance
    final static OFQueueGetConfigRequestVer11 DEFAULT = new OFQueueGetConfigRequestVer11(
        DEFAULT_XID, DEFAULT_PORT
    );

    // package private constructor - used by readers, builders, and factory
    OFQueueGetConfigRequestVer11(long xid, OFPort port) {
        if(port == null) {
            throw new NullPointerException("OFQueueGetConfigRequestVer11: property port cannot be null");
        }
        this.xid = xid;
        this.port = port;
    }

    // Accessors for OF message fields
    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_11;
    }

    @Override
    public OFType getType() {
        return OFType.QUEUE_GET_CONFIG_REQUEST;
    }

    @Override
    public long getXid() {
        return xid;
    }

    @Override
    public OFPort getPort() {
        return port;
    }



    public OFQueueGetConfigRequest.Builder createBuilder() {
        return new BuilderWithParent(this);
    }

    static class BuilderWithParent implements OFQueueGetConfigRequest.Builder {
        final OFQueueGetConfigRequestVer11 parentMessage;

        // OF message fields
        private boolean xidSet;
        private long xid;
        private boolean portSet;
        private OFPort port;

        BuilderWithParent(OFQueueGetConfigRequestVer11 parentMessage) {
            this.parentMessage = parentMessage;
        }

    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_11;
    }

    @Override
    public OFType getType() {
        return OFType.QUEUE_GET_CONFIG_REQUEST;
    }

    @Override
    public long getXid() {
        return xid;
    }

    @Override
    public OFQueueGetConfigRequest.Builder setXid(long xid) {
        this.xid = xid;
        this.xidSet = true;
        return this;
    }
    @Override
    public OFPort getPort() {
        return port;
    }

    @Override
    public OFQueueGetConfigRequest.Builder setPort(OFPort port) {
        this.port = port;
        this.portSet = true;
        return this;
    }


        @Override
        public OFQueueGetConfigRequest build() {
                long xid = this.xidSet ? this.xid : parentMessage.xid;
                OFPort port = this.portSet ? this.port : parentMessage.port;
                if(port == null)
                    throw new NullPointerException("Property port must not be null");

                //
                return new OFQueueGetConfigRequestVer11(
                    xid,
                    port
                );
        }

    }

    static class Builder implements OFQueueGetConfigRequest.Builder {
        // OF message fields
        private boolean xidSet;
        private long xid;
        private boolean portSet;
        private OFPort port;

    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_11;
    }

    @Override
    public OFType getType() {
        return OFType.QUEUE_GET_CONFIG_REQUEST;
    }

    @Override
    public long getXid() {
        return xid;
    }

    @Override
    public OFQueueGetConfigRequest.Builder setXid(long xid) {
        this.xid = xid;
        this.xidSet = true;
        return this;
    }
    @Override
    public OFPort getPort() {
        return port;
    }

    @Override
    public OFQueueGetConfigRequest.Builder setPort(OFPort port) {
        this.port = port;
        this.portSet = true;
        return this;
    }
//
        @Override
        public OFQueueGetConfigRequest build() {
            long xid = this.xidSet ? this.xid : DEFAULT_XID;
            OFPort port = this.portSet ? this.port : DEFAULT_PORT;
            if(port == null)
                throw new NullPointerException("Property port must not be null");


            return new OFQueueGetConfigRequestVer11(
                    xid,
                    port
                );
        }

    }


    final static Reader READER = new Reader();
    static class Reader implements OFMessageReader<OFQueueGetConfigRequest> {
        @Override
        public OFQueueGetConfigRequest readFrom(ChannelBuffer bb) throws OFParseError {
            int start = bb.readerIndex();
            // fixed value property version == 2
            byte version = bb.readByte();
            if(version != (byte) 0x2)
                throw new OFParseError("Wrong version: Expected=OFVersion.OF_11(2), got="+version);
            // fixed value property type == 22
            byte type = bb.readByte();
            if(type != (byte) 0x16)
                throw new OFParseError("Wrong type: Expected=OFType.QUEUE_GET_CONFIG_REQUEST(22), got="+type);
            int length = U16.f(bb.readShort());
            if(length != 16)
                throw new OFParseError("Wrong length: Expected=16(16), got="+length);
            if(bb.readableBytes() + (bb.readerIndex() - start) < length) {
                // Buffer does not have all data yet
                bb.readerIndex(start);
                return null;
            }
            if(logger.isTraceEnabled())
                logger.trace("readFrom - length={}", length);
            long xid = U32.f(bb.readInt());
            OFPort port = OFPort.read4Bytes(bb);
            // pad: 4 bytes
            bb.skipBytes(4);

            OFQueueGetConfigRequestVer11 queueGetConfigRequestVer11 = new OFQueueGetConfigRequestVer11(
                    xid,
                      port
                    );
            if(logger.isTraceEnabled())
                logger.trace("readFrom - read={}", queueGetConfigRequestVer11);
            return queueGetConfigRequestVer11;
        }
    }

    public void putTo(PrimitiveSink sink) {
        FUNNEL.funnel(this, sink);
    }

    final static OFQueueGetConfigRequestVer11Funnel FUNNEL = new OFQueueGetConfigRequestVer11Funnel();
    static class OFQueueGetConfigRequestVer11Funnel implements Funnel<OFQueueGetConfigRequestVer11> {
        private static final long serialVersionUID = 1L;
        @Override
        public void funnel(OFQueueGetConfigRequestVer11 message, PrimitiveSink sink) {
            // fixed value property version = 2
            sink.putByte((byte) 0x2);
            // fixed value property type = 22
            sink.putByte((byte) 0x16);
            // fixed value property length = 16
            sink.putShort((short) 0x10);
            sink.putLong(message.xid);
            message.port.putTo(sink);
            // skip pad (4 bytes)
        }
    }


    public void writeTo(ChannelBuffer bb) {
        WRITER.write(bb, this);
    }

    final static Writer WRITER = new Writer();
    static class Writer implements OFMessageWriter<OFQueueGetConfigRequestVer11> {
        @Override
        public void write(ChannelBuffer bb, OFQueueGetConfigRequestVer11 message) {
            // fixed value property version = 2
            bb.writeByte((byte) 0x2);
            // fixed value property type = 22
            bb.writeByte((byte) 0x16);
            // fixed value property length = 16
            bb.writeShort((short) 0x10);
            bb.writeInt(U32.t(message.xid));
            message.port.write4Bytes(bb);
            // pad: 4 bytes
            bb.writeZero(4);


        }
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("OFQueueGetConfigRequestVer11(");
        b.append("xid=").append(xid);
        b.append(", ");
        b.append("port=").append(port);
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
        OFQueueGetConfigRequestVer11 other = (OFQueueGetConfigRequestVer11) obj;

        if( xid != other.xid)
            return false;
        if (port == null) {
            if (other.port != null)
                return false;
        } else if (!port.equals(other.port))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime *  (int) (xid ^ (xid >>> 32));
        result = prime * result + ((port == null) ? 0 : port.hashCode());
        return result;
    }

}
