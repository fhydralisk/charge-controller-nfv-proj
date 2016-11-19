/**
*    Copyright 2011, Big Switch Networks, Inc. 
*    Originally created by David Erickson, Stanford University
* 
*    Licensed under the Apache License, Version 2.0 (the "License"); you may
*    not use this file except in compliance with the License. You may obtain
*    a copy of the License at
*
*         http://www.apache.org/licenses/LICENSE-2.0
*
*    Unless required by applicable law or agreed to in writing, software
*    distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
*    WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
*    License for the specific language governing permissions and limitations
*    under the License.
**/

package net.floodlightcontroller.recountfee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.IOFSwitchListener;
import net.floodlightcontroller.core.PortChangeType;
import net.floodlightcontroller.core.annotations.LogMessageCategory;
import net.floodlightcontroller.core.internal.IOFSwitchService;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.packet.TCP;
import net.floodlightcontroller.packet.UDP;
import net.floodlightcontroller.restserver.IRestApiService;

import org.projectfloodlight.openflow.protocol.OFFlowAdd;
import org.projectfloodlight.openflow.protocol.OFFlowModFlags;
import org.projectfloodlight.openflow.protocol.OFFlowRemoved;
import org.projectfloodlight.openflow.protocol.OFFlowStatsReply;
import org.projectfloodlight.openflow.protocol.OFFlowStatsRequest;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFPacketIn;
import org.projectfloodlight.openflow.protocol.OFPacketOut;
import org.projectfloodlight.openflow.protocol.OFPortDesc;
import org.projectfloodlight.openflow.protocol.OFType;
import org.projectfloodlight.openflow.protocol.action.OFAction;
import org.projectfloodlight.openflow.protocol.action.OFActionOutput;
import org.projectfloodlight.openflow.protocol.match.Match;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.protocol.match.MatchFields;
import org.projectfloodlight.openflow.types.DatapathId;
import org.projectfloodlight.openflow.types.EthType;
import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.IpProtocol;
import org.projectfloodlight.openflow.types.MacAddress;
import org.projectfloodlight.openflow.types.OFBufferId;
import org.projectfloodlight.openflow.types.OFPort;
import org.projectfloodlight.openflow.types.OFVlanVidMatch;
import org.projectfloodlight.openflow.types.TableId;
import org.projectfloodlight.openflow.types.VlanVid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Hydralisk
 */

@LogMessageCategory("Recounter")
public class RecountFee implements IFloodlightModule, IOFMessageListener, IOFSwitchListener, IRecountFlowGetter {
	
    private IFloodlightProviderService floodlightProvider;
    protected static Logger log =
			LoggerFactory.getLogger(RecountFee.class);
    
    protected static int TIMEOUT_IDLE = 10;
    protected static int TIMEOUT_HARD = 30;
    protected static int PRIORITY_DEFAULT = 100;
    
    private IRestApiService restApi;
    private IOFSwitchService switchService;
    private FeeContext fee = new FeeContext();
    
    private enum SubscriberField {
    	SUB_SRC,
    	SUB_DST,
    	SUB_NONE
    }

    /**
     * @param floodlightProvider the floodlightProvider to set
     */
    public void setFloodlightProvider(IFloodlightProviderService floodlightProvider) {
        this.floodlightProvider = floodlightProvider;
    }

    @Override
    public String getName() {
        return RecountFee.class.getPackage().getName();
    }

    public Command receive(IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
    	if (msg.getType().equals(OFType.PACKET_IN)) {
    		//OFPacketIn pi = (OFPacketIn) msg;
    		System.out.println("Packet IN Recv");
    		createPassFlowMod(sw, msg, cntx);
    		
    		
    	} else if (msg.getType().equals(OFType.FLOW_REMOVED)) {
    		System.out.println("FlowRemoved Recv");
    		recount(sw, msg, cntx);
    		
    	}
    	
    	/*
    	HubType ht = HubType.USE_PACKET_OUT;
    	switch (ht) {
    	case USE_FLOW_MOD:
            outMessage = createHubFlowMod(sw, msg);
            break;
        default:
    	case USE_PACKET_OUT:
            outMessage = createHubPacketOut(sw, msg);
            break;
    	}
    	*/
        //sw.write(outMessage);
        
        return Command.CONTINUE;
    }
    
    
	protected Match createMatchFromPacket(IOFSwitch sw, OFPort inPort, FloodlightContext cntx) {
		// The packet in match will only contain the port number.
		// We need to add in specifics for the hosts we're routing between.
		FeeMatchFields mfsDefault = fee.defaultMatchFields;
		FeeMatchFields mfs = mfsDefault;
		
		Ethernet eth = IFloodlightProviderService.bcStore.get(cntx, IFloodlightProviderService.CONTEXT_PI_PAYLOAD);
		VlanVid vlan = VlanVid.ofVlan(eth.getVlanID());
		MacAddress srcMac = eth.getSourceMACAddress();
		MacAddress dstMac = eth.getDestinationMACAddress();
		

		Match.Builder mb = sw.getOFFactory().buildMatch();
		mb.setExact(MatchField.IN_PORT, inPort);
		
		SubscriberField sf = SubscriberField.SUB_NONE;

		// TODO Detect switch type and match to create hardware-implemented flow
		// TODO Allow for IPv6 matches
		if (eth.getEtherType() == EthType.IPv4) { /* shallow check for equality is okay for EthType */
			IPv4 ip = (IPv4) eth.getPayload();
			IPv4Address srcIp = ip.getSourceAddress();
			IPv4Address dstIp = ip.getDestinationAddress();
			IPv4Address subscriberIp = null;
			String feeType = fee.feeswitches.get(sw.getId()).feeType;
			
			if (feeType != null) {
				subscriberIp = fee.selectIPOfSubscriber(srcIp, dstIp);

				if (subscriberIp != null) {
					
					if (srcIp.equals(subscriberIp)) {
						sf = SubscriberField.SUB_SRC;
					} else {
						sf = SubscriberField.SUB_DST;
					}
					
					Subscriber ss = fee.subscribers.get(subscriberIp);
					try {
						IFeeType ft = ss.getFeeObject(feeType);
						mfs = ft.getMatchFields();
					} catch (InstantiationException | IllegalAccessException
							| ClassNotFoundException e) {
						// TODO Auto-generated catch block
						mfs = mfsDefault;
						e.printStackTrace();
					}
				} 
				
			}
			
			
			if (mfs.matchTransportPort != FeeMatchFields.MatchMethod.MATCH_NONE &&
					mfs.matchIP != FeeMatchFields.MatchMethod.MATCH_NONE) {
				
				
				mb.setExact(MatchField.ETH_TYPE, EthType.IPv4);
				
				IPv4Address srcIpFinal = null;
				IPv4Address dstIpFinal = null;
				
				switch (mfs.matchIP){
				case MATCH_ALL:
					srcIpFinal = srcIp;
					dstIpFinal = dstIp;
					break;
				case MATCH_DST:
					dstIpFinal = dstIp;
					break;
				case MATCH_SRC:
					srcIpFinal = srcIp;
					break;
				case MATCH_SUBSCRIBER:
					switch (sf) {
					case SUB_DST:
						dstIpFinal = dstIp;
						break;
					case SUB_NONE:
						// ??
						System.out.println("cannot match subscriber...");
						srcIpFinal = srcIp;
						dstIpFinal = dstIp;
						break;
					case SUB_SRC:
						srcIpFinal = srcIp;
						break;
					}
					break;
					
				case MATCH_SERVER:
					// TODO what if default is this? error maybe.
					switch (sf) {
					case SUB_DST:
						srcIpFinal = srcIp;
						break;
					case SUB_NONE:
						// ??
						System.out.println("cannot match subscriber...");
						srcIpFinal = srcIp;
						dstIpFinal = dstIp;
						break;
					case SUB_SRC:
						dstIpFinal = dstIp;
						break;
					}
					break;

				case MATCH_NONE:
					System.out.println("This must be a huge bug!");
					break;
				
				}
				
				if (srcIpFinal != null)
					mb.setExact(MatchField.IPV4_SRC, srcIpFinal);
				if (dstIpFinal != null)
					mb.setExact(MatchField.IPV4_DST, dstIpFinal);
			
				/*
				 * Take care of the ethertype if not included earlier,
				 * since it's a prerequisite for transport ports.
				 */
				
				if (mfs.matchTransportPort != FeeMatchFields.MatchMethod.MATCH_NONE) {
					Integer srcPort = null, dstPort = null;
					
					Integer srcPortFinal = null;
					Integer dstPortFinal = null;
					
					if (ip.getProtocol().equals(IpProtocol.TCP)) {
						TCP tcp = (TCP) ip.getPayload();
						srcPort = tcp.getSourcePort().getPort();
						dstPort = tcp.getDestinationPort().getPort();
						
					} else if (ip.getProtocol().equals(IpProtocol.UDP)) {
						UDP udp = (UDP) ip.getPayload();
						srcPort = udp.getSourcePort().getPort();
						dstPort = udp.getDestinationPort().getPort();
					}

					switch (mfs.matchTransportPort) {
					case MATCH_ALL:
						srcPortFinal = srcPort;
						dstPortFinal = dstPort;
						break;
					case MATCH_SRC:
						srcPortFinal = srcPort;
						break;
					case MATCH_DST:
						dstPortFinal = dstPort;
						break;
					case MATCH_SUBSCRIBER:
						switch (sf) {
						case SUB_DST:
							dstPortFinal = dstPort;
							break;
						case SUB_NONE:
							// ??
							System.out.println("cannot match subscriber...");
							dstPortFinal = dstPort;
							srcPortFinal = srcPort;
							break;
						case SUB_SRC:
							srcPortFinal = srcPort;
							break;
						}

						break;
					case MATCH_SERVER:
						switch (sf) {
						case SUB_DST:
							srcPortFinal = srcPort;
							break;
						case SUB_NONE:
							// ??
							System.out.println("cannot match subscriber...");
							dstPortFinal = dstPort;
							srcPortFinal = srcPort;
							break;
						case SUB_SRC:
							dstPortFinal = dstPort;
							break;
						}

						break;
					case MATCH_NONE:
						System.out.println("This must be a huge bug!");
						break;
					
					}
					
					
					
					if (ip.getProtocol().equals(IpProtocol.TCP)) {
						TCP tcp = (TCP) ip.getPayload();
						mb.setExact(MatchField.IP_PROTO, IpProtocol.TCP);
						if (srcPortFinal != null)
							mb.setExact(MatchField.TCP_SRC, tcp.getSourcePort());
						if (dstPortFinal != null)
							mb.setExact(MatchField.TCP_DST, tcp.getDestinationPort());
					} else if (ip.getProtocol().equals(IpProtocol.UDP)) {
						UDP udp = (UDP) ip.getPayload();
						mb.setExact(MatchField.IP_PROTO, IpProtocol.UDP);
						if (srcPortFinal != null)
							mb.setExact(MatchField.UDP_SRC, udp.getSourcePort());
						if(dstPortFinal != null)
							mb.setExact(MatchField.UDP_DST, udp.getDestinationPort());
					}
				}
			}

		} else if (eth.getEtherType() == EthType.ARP) { /* shallow check for equality is okay for EthType */
			mb.setExact(MatchField.ETH_TYPE, EthType.ARP);
		}
		
		if (mfs.matchEther != FeeMatchFields.MatchMethod.MATCH_NONE) {
			MacAddress srcMacFinal = null;
			MacAddress dstMacFinal = null;
			
			switch (mfs.matchIP){
			case MATCH_ALL:
				srcMacFinal = srcMac;
				dstMacFinal = dstMac;
				break;
			case MATCH_DST:
				dstMacFinal = dstMac;
				break;
			case MATCH_SRC:
				srcMacFinal = srcMac;
				break;
			case MATCH_SUBSCRIBER:
				switch (sf) {
				case SUB_DST:
					dstMacFinal = dstMac;
					break;
				case SUB_NONE:
					// ??
					System.out.println("cannot match subscriber...");
					srcMacFinal = srcMac;
					dstMacFinal = dstMac;
					break;
				case SUB_SRC:
					srcMacFinal = srcMac;
					break;
				}
				break;
				
			case MATCH_SERVER:
				// TODO what if default is this? error maybe.
				switch (sf) {
				case SUB_DST:
					srcMacFinal = srcMac;
					break;
				case SUB_NONE:
					// ??
					System.out.println("cannot match subscriber...");
					srcMacFinal = srcMac;
					dstMacFinal = dstMac;
					break;
				case SUB_SRC:
					dstMacFinal = dstMac;
					break;
				}
				break;

			case MATCH_NONE:
				System.out.println("This must be a huge bug!");
				break;
			
			}

			if (srcMacFinal != null)
				mb.setExact(MatchField.ETH_SRC, srcMac);
			if (dstMacFinal != null)
				mb.setExact(MatchField.ETH_DST, dstMac);
		}

		if (true) {
			if (!vlan.equals(VlanVid.ZERO)) {
				mb.setExact(MatchField.VLAN_VID, OFVlanVidMatch.ofVlanVid(vlan));
			}
		}

		return mb.build();
	}
	
	
	private void createPassFlowMod(IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
    	OFPacketIn pi = (OFPacketIn) msg;
        OFFlowAdd.Builder fmb = sw.getOFFactory().buildFlowAdd();
        Set<OFFlowModFlags> rmFlagContainer = new HashSet<OFFlowModFlags>();
        // send flow removed message back.
        rmFlagContainer.add(OFFlowModFlags.SEND_FLOW_REM);
        OFPort inPort = pi.getMatch().get(MatchField.IN_PORT);
        
        fmb.setBufferId(pi.getBufferId())
        .setXid(pi.getXid())
        .setMatch(createMatchFromPacket(sw, inPort, cntx))
        .setIdleTimeout(TIMEOUT_IDLE)
        .setHardTimeout(TIMEOUT_HARD)
        .setPriority(PRIORITY_DEFAULT)
        .setFlags(rmFlagContainer);
        
        

        // set actions
        
        OFActionOutput.Builder actionBuilder = sw.getOFFactory().actions().buildOutput();
        actionBuilder.setPort(OFPort.IN_PORT);
        fmb.setActions(Collections.singletonList((OFAction) actionBuilder.build()));
        sw.write(fmb.build());
        
        if (pi.getBufferId().equals(OFBufferId.NO_BUFFER)) {
        	System.out.println("First packet, sending back.");
            OFPacketOut.Builder pob = sw.getOFFactory().buildPacketOut();
            pob.setBufferId(pi.getBufferId()).setXid(pi.getXid());
            
            // set actions
    		List<OFAction> actions = new ArrayList<OFAction>();
    		actions.add(sw.getOFFactory().actions().output(inPort, Integer.MAX_VALUE));
    		pob.setActions(actions);

            // set data if it is included in the packetin
            byte[] packetData = pi.getData();
            pob.setData(packetData);
            
            sw.write(pob.build());
        }
        
    }
	
	private void recount(IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
		FeeDevice feeDevice = fee.feeswitches.get(sw.getId());
		if (feeDevice != null && feeDevice.status != FeeDevice.Status.OFFLINE && feeDevice.feeType != null) {
			OFFlowRemoved fr = (OFFlowRemoved) msg;
			IPv4Address ipsrc = fr.getMatch().get(MatchField.IPV4_SRC);
			IPv4Address ipdst = fr.getMatch().get(MatchField.IPV4_DST);
			Subscriber ss = fee.selectSubscriber(ipsrc, ipdst);
			/*
			if (fee.subscribers.containsKey(ipsrc)) {
				ss = fee.subscribers.get(ipsrc);
			} else if (fee.subscribers.containsKey(ipdst)) {
				ss = fee.subscribers.get(ipdst);
			} else {
				System.out.println("subscriber not found");
				return;
			}*/
			
			if (ss == null) {
				return;
			}
			
			try {
				IFeeType fee = ss.getFeeObject(feeDevice.feeType);
				if (fee != null) {
					fee.recount(FeeUnit.fromFlowRemoved(ss, fr));
				}
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
				
		}
	}
    /*
    private OFMessage createHubFlowMod(IOFSwitch sw, OFMessage msg) {
    	OFPacketIn pi = (OFPacketIn) msg;
        OFFlowAdd.Builder fmb = sw.getOFFactory().buildFlowAdd();
        fmb.setBufferId(pi.getBufferId())
        .setXid(pi.getXid());

        // set actions
        OFActionOutput.Builder actionBuilder = sw.getOFFactory().actions().buildOutput();
        actionBuilder.setPort(OFPort.FLOOD);
        fmb.setActions(Collections.singletonList((OFAction) actionBuilder.build()));

        return fmb.build();
    }
    
    private OFMessage createHubPacketOut(IOFSwitch sw, OFMessage msg) {
    	OFPacketIn pi = (OFPacketIn) msg;
        OFPacketOut.Builder pob = sw.getOFFactory().buildPacketOut();
        pob.setBufferId(pi.getBufferId()).setXid(pi.getXid()).setInPort((pi.getVersion().compareTo(OFVersion.OF_12) < 0 ? pi.getInPort() : pi.getMatch().get(MatchField.IN_PORT)));
        
        // set actions
        OFActionOutput.Builder actionBuilder = sw.getOFFactory().actions().buildOutput();
        actionBuilder.setPort(OFPort.FLOOD);
        pob.setActions(Collections.singletonList((OFAction) actionBuilder.build()));

        // set data if it is included in the packetin
        if (pi.getBufferId() == OFBufferId.NO_BUFFER) {
            byte[] packetData = pi.getData();
            pob.setData(packetData);
        }
        return pob.build();  
    }
    */
	
    /**
     * @param sw
     *            the switch object that we wish to get flows from
     * @param outPort
     *            the output action port we wish to find flows with
     * @return a list of OFFlowStatisticsReply objects or essentially flows
     */
	
    public List<OFFlowStatsReply> getFlows(DatapathId dpid) {

    	ArrayList<OFFlowStatsReply> statsReply = new ArrayList<OFFlowStatsReply>();
        List<OFFlowStatsReply> values = null;
        Future<List<OFFlowStatsReply>> future;

        // Statistics request object for getting flows
        IOFSwitch sw = switchService.getSwitch(dpid);
        
        
        OFFlowStatsRequest req = sw.getOFFactory().buildFlowStatsRequest()
        		.setMatch(sw.getOFFactory().buildMatch().build())
        		.setTableId(TableId.ALL)
        		.build();

        try {
            // System.out.println(sw.getStatistics(req));
            future = sw.writeStatsRequest(req);
            values = future.get(10, TimeUnit.SECONDS);
            if (values != null) {
                for (OFFlowStatsReply stat : values) {
                    statsReply.add(stat);
                }
            }
        } catch (Exception e) {
            log.error("Failure retrieving statistics from switch " + sw, e);
        }        
        return statsReply;
    }


    @Override
    public boolean isCallbackOrderingPrereq(OFType type, String name) {
        return false;
    }

    @Override
    public boolean isCallbackOrderingPostreq(OFType type, String name) {
        return false;
    }

    // IFloodlightModule
    
    @Override
    public Collection<Class<? extends IFloodlightService>> getModuleServices() {
        // We don't provide any services, return null
    	Collection<Class<? extends IFloodlightService>> l =
				new ArrayList<Class<? extends IFloodlightService>>();
		l.add(IRecountFlowGetter.class);
		return l;
    }

    @Override
    public Map<Class<? extends IFloodlightService>, IFloodlightService>
            getServiceImpls() {
        // We don't provide any services, return null
    	Map <Class<? extends IFloodlightService>, IFloodlightService> l = 
    			new HashMap<Class<? extends IFloodlightService>, IFloodlightService>();
    			
    	l.put(IRecountFlowGetter.class, this);
  
        return l;
    }

    @Override
    public Collection<Class<? extends IFloodlightService>>
            getModuleDependencies() {
        Collection<Class<? extends IFloodlightService>> l = 
                new ArrayList<Class<? extends IFloodlightService>>();
        l.add(IFloodlightProviderService.class);
        l.add(IRestApiService.class);
        l.add(IOFSwitchService.class);
        
        return l;
    }

    @Override
    public void init(FloodlightModuleContext context)
            throws FloodlightModuleException {
        floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
        restApi =  context.getServiceImpl(IRestApiService.class);
        switchService = context.getServiceImpl(IOFSwitchService.class);
    }

    @Override
    public void startUp(FloodlightModuleContext context) {
        floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);
        floodlightProvider.addOFMessageListener(OFType.FLOW_REMOVED, this);
        
        switchService.addOFSwitchListener(this);
        restApi.addRestletRoutable(new RecountFeeRoutable(fee));
    }
    
    
    /* ******************* *
     *  IOFSwitchListener  *
     * ******************* */

	@Override
	public void switchAdded(DatapathId switchId) {
		// TODO Auto-generated method stub
		
		IOFSwitch sw = switchService.getSwitch(switchId);
		
        OFFlowAdd.Builder fmb = sw.getOFFactory().buildFlowAdd();
        
        Match.Builder mb = sw.getOFFactory().buildMatch();
        
        mb.setExact(MatchField.ETH_DST, MacAddress.of("ff:ff:ff:ff:ff:ff"));
        mb.setExact(MatchField.IN_PORT, OFPort.of(1));
        fmb.setMatch(mb.build())
        .setPriority(PRIORITY_DEFAULT + 10);
        

        // set actions
        
        OFActionOutput.Builder actionBuilder = sw.getOFFactory().actions().buildOutput();
        actionBuilder.setPort(OFPort.LOCAL);
        fmb.setActions(Collections.singletonList((OFAction) actionBuilder.build()));
        sw.write(fmb.build());

		if (fee.feeswitches.containsKey(switchId)) {
			fee.feeswitches.get(switchId).status = FeeDevice.Status.ONLINE;
			
		} else {
			//TODO remove type here
			fee.addSwitch(switchId, null, FeeDevice.Status.ONLINE);
		}
		
	}

	@Override
	public void switchRemoved(DatapathId switchId) {
		// TODO Auto-generated method stub
		if (fee.feeswitches.containsKey(switchId)) {
			fee.feeswitches.get(switchId).status = FeeDevice.Status.OFFLINE;
		} else {
			System.out.println("a invalid switch disconnected..?");
		}
	}

	@Override
	public void switchActivated(DatapathId switchId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void switchPortChanged(DatapathId switchId, OFPortDesc port,
			PortChangeType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void switchChanged(DatapathId switchId) {
		// TODO Auto-generated method stub
		
	}
}
