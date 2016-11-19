package net.floodlightcontroller.recountfee;

import java.util.List;

import net.floodlightcontroller.core.module.IFloodlightService;

import org.projectfloodlight.openflow.protocol.OFFlowStatsReply;
import org.projectfloodlight.openflow.types.DatapathId;

public interface IRecountFlowGetter  extends IFloodlightService {
	public List<OFFlowStatsReply> getFlows(DatapathId dpid);
}
