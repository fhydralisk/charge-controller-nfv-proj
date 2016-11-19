package net.floodlightcontroller.recountfee;

import java.util.ArrayList;
import java.util.List;

import org.projectfloodlight.openflow.protocol.OFFlowStatsEntry;
import org.projectfloodlight.openflow.protocol.OFFlowStatsReply;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.DatapathId;
import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.IpProtocol;
import org.projectfloodlight.openflow.types.TransportPort;
import org.restlet.resource.Get;

public class RecountFeeDevice extends RecountFeeRestletRequestBase {

	@Get("json")
	public FeeResponse<FeeFlowEntries> getDevice() {
		FeeResponse<FeeFlowEntries> fr = new FeeResponse<FeeFlowEntries>();
		
		
		IRecountFlowGetter fg =
                (IRecountFlowGetter)getContext().getAttributes().
                    get(IRecountFlowGetter.class.getCanonicalName());
		
		String dpids = (String) getRequestAttributes().get("dpid");
		DatapathId dpid;
		try {
			dpid = DatapathId.of(dpids);
		} catch (NumberFormatException e){
			fr.error = new ErrorCode(404);
			return fr;
		}
		List<OFFlowStatsReply> replies = fg.getFlows(dpid);
		//System.out.println(replies.toString());
		fr.error = new ErrorCode(200);
		
		FeeFlowEntries ffes = new FeeFlowEntries();
		FeeDevice feeDevice = context.feeswitches.get(dpid);
		ffes.type = feeDevice.feeType;
		ffes.description = feeDevice.description;
		for (OFFlowStatsReply reply : replies) {
			for (OFFlowStatsEntry entry : reply.getEntries()) {
				IpProtocol ipproto = entry.getMatch().get(MatchField.IP_PROTO);
				if (ipproto != null) {
					IPv4Address ipsrc, ipdst;
					TransportPort portsrc, portdst;
					String strProtocol, direction;
					
					if (ipproto.equals(IpProtocol.TCP)) {
						portsrc = entry.getMatch().get(MatchField.TCP_SRC);
						portdst = entry.getMatch().get(MatchField.TCP_DST);
						strProtocol = "TCP";
					} else if (ipproto.equals(IpProtocol.UDP)) {
						portsrc = entry.getMatch().get(MatchField.UDP_SRC);
						portdst = entry.getMatch().get(MatchField.UDP_DST);
						strProtocol = "UDP";
					} else {
						continue;
					}
					
					ipsrc = entry.getMatch().get(MatchField.IPV4_SRC);
					ipdst = entry.getMatch().get(MatchField.IPV4_DST);
					
					if (ipsrc == null || ipdst == null)
						continue;
					Long subid;
					NetAddress subaddr;
					NetAddress remoteaddr;
					
					if (context.subscribers.containsKey(ipsrc)) {
						subaddr = new NetAddress(ipsrc, portsrc);
						remoteaddr = new NetAddress(ipdst, portdst);
						subid = Long.valueOf(context.subscribers.get(ipsrc).id);
						direction = "=>";
					} else if (context.subscribers.containsKey(ipdst)) {
						subaddr = new NetAddress(ipdst, portdst);
						remoteaddr = new NetAddress(ipsrc, portsrc);
						subid = Long.valueOf(context.subscribers.get(ipdst).id);	
						direction = "<=";
					} else
						// Unknown flow
						continue;
					
					FeeFlowEntry ffe = new FeeFlowEntry();
					ffe.subscriber = subaddr;
					ffe.subscriberId = subid;
					ffe.remote = remoteaddr;
					ffe.ip_protocol = strProtocol;
					ffe.flow = entry.getByteCount().getValue();
					ffe.duration = entry.getDurationSec();
					ffe.direction = direction;
					
					ffes.flows.add(ffe);
				}
			}
		}
		fr.payload = ffes;
		return fr;
	}
}

class FeeFlowEntries {
	public String type;
	public String description;
	public List<FeeFlowEntry> flows = new ArrayList<FeeFlowEntry>();
}

class FeeFlowEntry {
	public NetAddress subscriber;
	public Long subscriberId;
	public NetAddress remote;
	public String ip_protocol;
	public Long flow;
	public Long duration;
	public String direction;
}

class NetAddress {
	public String ip;
	public Integer port;
	public NetAddress() {
		
	}
	
	public NetAddress(IPv4Address ip, TransportPort port) {
		this.ip = ip.toString();
		this.port = port.getPort();
	}
}