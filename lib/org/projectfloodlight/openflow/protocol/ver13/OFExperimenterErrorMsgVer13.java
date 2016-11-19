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

class OFExperimenterErrorMsgVer13 implements OFExperimenterErrorMsg {
    private static final Logger logger = LoggerFactory.getLogger(OFExperimenterErrorMsgVer13.class);
    // version: 1.3
    final static byte WIRE_VERSION = 4;
    final static int MINIMUM_LENGTH = 16;

        private final static long DEFAULT_XID = 0x0L;
        private final static int DEFAULT_SUBTYPE = 0x0;
        private final static long DEFAULT_EXPERIMENTER = 0x0L;
        private final static OFErrorCauseData DEFAULT_DATA = OFErrorCauseData.NONE;

    // OF message fields
    private final long xid;
    private final int subtype;
    private final long experimenter;
    private final OFErrorCauseData data;
//
    // Immutable default instance
    final static OFExperimenterErrorMsgVer13 DEFAULT = new OFExperimenterErrorMsgVer13(
        DEFAULT_XID, DEFAULT_SUBTYPE, DEFAULT_EXPERIMENTER, DEFAULT_DATA
    );

    // package private constructor - used by readers, builders, and factory
    OFExperimenterErrorMsgVer13(long xid, int subtype, long experimenter, OFErrorCauseData data) {
        if(data == null) {
            throw new NullPointerException("OFExperimenterErrorMsgVer13: property data cannot be null");
        }
        this.xid = xid;
        this.subtype = subtype;
        this.experimenter = experimenter;
        this.data = data;
    }

    // Accessors for OF message fields
    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_13;
    }

    @Override
    public OFType getType() {
        return OFType.ERROR;
    }

    @Override
    public long getXid() {
        return xid;
    }

    @Override
    public OFErrorType getErrType() {
        return OFErrorType.EXPERIMENTER;
    }

    @Override
    public int getSubtype() {
        return subtype;
    }

    @Override
    public long getExperimenter() {
        return experimenter;
    }

    @Override
    public OFErrorCauseData getData() {
        return data;
    }



    public OFExperimenterErrorMsg.Builder createBuilder() {
        return new BuilderWithParent(this);
    }

    static class BuilderWithParent implements OFExperimenterErrorMsg.Builder {
        final OFExperimenterErrorMsgVer13 parentMessage;

        // OF message fields
        private boolean xidSet;
        private long xid;
        private boolean subtypeSet;
        private int subtype;
        private boolean experimenterSet;
        private long experimenter;
        private boolean dataSet;
        private OFErrorCauseData data;

        BuilderWithParent(OFExperimenterErrorMsgVer13 parentMessage) {
            this.parentMessage = parentMessage;
        }

    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_13;
    }

    @Override
    public OFType getType() {
        return OFType.ERROR;
    }

    @Override
    public long getXid() {
        return xid;
    }

    @Override
    public OFExperimenterErrorMsg.Builder setXid(long xid) {
        this.xid = xid;
        this.xidSet = true;
        return this;
    }
    @Override
    public OFErrorType getErrType() {
        return OFErrorType.EXPERIMENTER;
    }

    @Override
    public int getSubtype() {
        return subtype;
    }

    @Override
    public OFExperimenterErrorMsg.Builder setSubtype(int subtype) {
        this.subtype = subtype;
        this.subtypeSet = true;
        return this;
    }
    @Override
    public long getExperimenter() {
        return experimenter;
    }

    @Override
    public OFExperimenterErrorMsg.Builder setExperimenter(long experimenter) {
        this.experimenter = experimenter;
        this.experimenterSet = true;
        return this;
    }
    @Override
    public OFErrorCauseData getData() {
        return data;
    }

    @Override
    public OFExperimenterErrorMsg.Builder setData(OFErrorCauseData data) {
        this.data = data;
        this.dataSet = true;
        return this;
    }


        @Override
        public OFExperimenterErrorMsg build() {
                long xid = this.xidSet ? this.xid : parentMessage.xid;
                int subtype = this.subtypeSet ? this.subtype : parentMessage.subtype;
                long experimenter = this.experimenterSet ? this.experimenter : parentMessage.experimenter;
                OFErrorCauseData data = this.dataSet ? this.data : parentMessage.data;
                if(data == null)
                    throw new NullPointerException("Property data must not be null");

                //
                return new OFExperimenterErrorMsgVer13(
                    xid,
                    subtype,
                    experimenter,
                    data
                );
        }

    }

    static class Builder implements OFExperimenterErrorMsg.Builder {
        // OF message fields
        private boolean xidSet;
        private long xid;
        private boolean subtypeSet;
        private int subtype;
        private boolean experimenterSet;
        private long experimenter;
        private boolean dataSet;
        private OFErrorCauseData data;

    @Override
    public OFVersion getVersion() {
        return OFVersion.OF_13;
    }

    @Override
    public OFType getType() {
        return OFType.ERROR;
    }

    @Override
    public long getXid() {
        return xid;
    }

    @Override
    public OFExperimenterErrorMsg.Builder setXid(long xid) {
        this.xid = xid;
        this.xidSet = true;
        return this;
    }
    @Override
    public OFErrorType getErrType() {
        return OFErrorType.EXPERIMENTER;
    }

    @Override
    public int getSubtype() {
        return subtype;
    }

    @Override
    public OFExperimenterErrorMsg.Builder setSubtype(int subtype) {
        this.subtype = subtype;
        this.subtypeSet = true;
        return this;
    }
    @Override
    public long getExperimenter() {
        return experimenter;
    }

    @Override
    public OFExperimenterErrorMsg.Builder setExperimenter(long experimenter) {
        this.experimenter = experimenter;
        this.experimenterSet = true;
        return this;
    }
    @Override
    public OFErrorCauseData getData() {
        return data;
    }

    @Override
    public OFExperimenterErrorMsg.Builder setData(OFErrorCauseData data) {
        this.data = data;
        this.dataSet = true;
        return this;
    }
//
        @Override
        public OFExperimenterErrorMsg build() {
            long xid = this.xidSet ? this.xid : DEFAULT_XID;
            int subtype = this.subtypeSet ? this.subtype : DEFAULT_SUBTYPE;
            long experimenter = this.experimenterSet ? this.experimenter : DEFAULT_EXPERIMENTER;
            OFErrorCauseData data = this.dataSet ? this.data : DEFAULT_DATA;
            if(data == null)
                throw new NullPointerException("Property data must not be null");


            return new OFExperimenterErrorMsgVer13(
                    xid,
                    subtype,
                    experimenter,
                    data
                );
        }

    }


    final static Reader READER = new Reader();
    static class Reader implements OFMessageReader<OFExperimenterErrorMsg> {
        @Override
        public OFExperimenterErrorMsg readFrom(ChannelBuffer bb) throws OFParseError {
            int start = bb.readerIndex();
            // fixed value property version == 4
            byte version = bb.readByte();
            if(version != (byte) 0x4)
                throw new OFParseError("Wrong version: Expected=OFVersion.OF_13(4), got="+version);
            // fixed value property type == 1
            byte type = bb.readByte();
            if(type != (byte) 0x1)
                throw new OFParseError("Wrong type: Expected=OFType.ERROR(1), got="+type);
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
            // fixed value property errType == 65535
            short errType = bb.readShort();
            if(errType != (short) 0xffff)
                throw new OFParseError("Wrong errType: Expected=OFErrorType.EXPERIMENTER(65535), got="+errType);
            int subtype = U16.f(bb.readShort());
            long experimenter = U32.f(bb.readInt());
            OFErrorCauseData data = OFErrorCauseData.read(bb, length - (bb.readerIndex() - start), OFVersion.OF_13);

            OFExperimenterErrorMsgVer13 experimenterErrorMsgVer13 = new OFExperimenterErrorMsgVer13(
                    xid,
                      subtype,
                      experimenter,
                      data
                    );
            if(logger.isTraceEnabled())
                logger.trace("readFrom - read={}", experimenterErrorMsgVer13);
            return experimenterErrorMsgVer13;
        }
    }

    public void putTo(PrimitiveSink sink) {
        FUNNEL.funnel(this, sink);
    }

    final static OFExperimenterErrorMsgVer13Funnel FUNNEL = new OFExperimenterErrorMsgVer13Funnel();
    static class OFExperimenterErrorMsgVer13Funnel implements Funnel<OFExperimenterErrorMsgVer13> {
        private static final long serialVersionUID = 1L;
        @Override
        public void funnel(OFExperimenterErrorMsgVer13 message, PrimitiveSink sink) {
            // fixed value property version = 4
            sink.putByte((byte) 0x4);
            // fixed value property type = 1
            sink.putByte((byte) 0x1);
            // FIXME: skip funnel of length
            sink.putLong(message.xid);
            // fixed value property errType = 65535
            sink.putShort((short) 0xffff);
            sink.putInt(message.subtype);
            sink.putLong(message.experimenter);
            message.data.putTo(sink);
        }
    }


    public void writeTo(ChannelBuffer bb) {
        WRITER.write(bb, this);
    }

    final static Writer WRITER = new Writer();
    static class Writer implements OFMessageWriter<OFExperimenterErrorMsgVer13> {
        @Override
        public void write(ChannelBuffer bb, OFExperimenterErrorMsgVer13 message) {
            int startIndex = bb.writerIndex();
            // fixed value property version = 4
            bb.writeByte((byte) 0x4);
            // fixed value property type = 1
            bb.writeByte((byte) 0x1);
            // length is length of variable message, will be updated at the end
            int lengthIndex = bb.writerIndex();
            bb.writeShort(U16.t(0));

            bb.writeInt(U32.t(message.xid));
            // fixed value property errType = 65535
            bb.writeShort((short) 0xffff);
            bb.writeShort(U16.t(message.subtype));
            bb.writeInt(U32.t(message.experimenter));
            message.data.writeTo(bb);

            // update length field
            int length = bb.writerIndex() - startIndex;
            bb.setShort(lengthIndex, length);

        }
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder("OFExperimenterErrorMsgVer13(");
        b.append("xid=").append(xid);
        b.append(", ");
        b.append("subtype=").append(subtype);
        b.append(", ");
        b.append("experimenter=").append(experimenter);
        b.append(", ");
        b.append("data=").append(data);
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
        OFExperimenterErrorMsgVer13 other = (OFExperimenterErrorMsgVer13) obj;

        if( xid != other.xid)
            return false;
        if( subtype != other.subtype)
            return false;
        if( experimenter != other.experimenter)
            return false;
        if (data == null) {
            if (other.data != null)
                return false;
        } else if (!data.equals(other.data))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;

        result = prime *  (int) (xid ^ (xid >>> 32));
        result = prime * result + subtype;
        result = prime *  (int) (experimenter ^ (experimenter >>> 32));
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        return result;
    }

}
