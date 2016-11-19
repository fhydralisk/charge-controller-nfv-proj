package net.floodlightcontroller.netlayerdrop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFType;

import net.floodlightcontroller.core.FloodlightContext;
import net.floodlightcontroller.core.IFloodlightProviderService;
import net.floodlightcontroller.core.IOFMessageListener;
import net.floodlightcontroller.core.IOFSwitch;
import net.floodlightcontroller.core.internal.IOFSwitchService;
import net.floodlightcontroller.core.module.FloodlightModuleContext;
import net.floodlightcontroller.core.module.FloodlightModuleException;
import net.floodlightcontroller.core.module.IFloodlightModule;
import net.floodlightcontroller.core.module.IFloodlightService;
import net.floodlightcontroller.debugcounter.IDebugCounterService;
import net.floodlightcontroller.devicemanager.IDevice;
import net.floodlightcontroller.devicemanager.IDeviceService;
import net.floodlightcontroller.devicemanager.internal.Device;
import net.floodlightcontroller.firewall.Firewall;
import net.floodlightcontroller.packet.Ethernet;
import net.floodlightcontroller.packet.IPv4;
import net.floodlightcontroller.routing.IRoutingDecision;
import net.floodlightcontroller.routing.IRoutingService;
import net.floodlightcontroller.routing.RoutingDecision;
import net.floodlightcontroller.topology.ITopologyService;

import org.projectfloodlight.openflow.protocol.OFFlowAdd;
import org.projectfloodlight.openflow.protocol.OFMessage;
import org.projectfloodlight.openflow.protocol.OFPacketIn;
import org.projectfloodlight.openflow.protocol.OFPacketOut;
import org.projectfloodlight.openflow.protocol.OFVersion;
import org.projectfloodlight.openflow.protocol.match.MatchField;
import org.projectfloodlight.openflow.types.IPv4Address;
import org.projectfloodlight.openflow.types.MacAddress;
import org.projectfloodlight.openflow.types.OFPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NetLayerDrop implements IFloodlightModule, IOFMessageListener {
    private IFloodlightProviderService floodlightProvider;
	protected IOFSwitchService switchService;
	protected IDeviceService deviceManagerService;
	protected IRoutingService routingEngineService;
	protected ITopologyService topologyService;
	protected IDebugCounterService debugCounterService;
	protected static Logger logger;


    /**
     * @param floodlightProvider the floodlightProvider to set
     */

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "netlayerdrop";
	}

	@Override
	public boolean isCallbackOrderingPrereq(OFType type, String name) {
		return (type.equals(OFType.PACKET_IN) && (name.equals("topology") || name.equals("devicemanager")));
	}

	@Override
	public boolean isCallbackOrderingPostreq(OFType type, String name) {
		// TODO Auto-generated method stub
		return (type.equals(OFType.PACKET_IN) && (name.equals("forwarding")));	
	}

	@Override
	public Command receive(
			IOFSwitch sw, OFMessage msg, FloodlightContext cntx) {
		// hack
		OFPacketIn pi=(OFPacketIn)msg;
		OFPort inPort = (pi.getVersion().compareTo(OFVersion.OF_12) < 0 ? pi.getInPort() : pi.getMatch().get(MatchField.IN_PORT));

		logger.info("nld rec");
		Ethernet eth=IFloodlightProviderService.bcStore.get(cntx, IFloodlightProviderService.CONTEXT_PI_PAYLOAD);
		if (eth.getEtherType().getValue()!=Ethernet.TYPE_IPv4){
			//logger.info("nld:not ipv4");
			return Command.CONTINUE;
		}
		
		IPv4 ipv4=(IPv4) eth.getPayload();
		IPv4Address ipv4dst;
		ipv4dst=ipv4.getDestinationAddress();
		//ipv4src=ipv4.getSourceAddress();
		IPv4Address snm=IPv4Address.of("255.255.0.0");
		IPv4Address ndst,nsrc;
		ndst=ipv4dst.applyMask(snm);
		nsrc=IPv4Address.of("10.0.0.0");
		
		if (ndst.compareTo(nsrc)==0){
			return Command.CONTINUE;
		}
		
		// should install a flow and do drop?
		if (sw.getId().getLong()<10) {
			// drop decisions
			IRoutingDecision decision=RoutingDecision.rtStore.get(cntx, IRoutingDecision.CONTEXT_DECISION);
			if (decision==null) {
				decision=new RoutingDecision(sw.getId(), inPort,
						IDeviceService.fcStore.get(cntx, IDeviceService.CONTEXT_SRC_DEVICE),
						IRoutingDecision.RoutingAction.DROP);
				decision.addToContext(cntx);
			} else {
				decision.setRoutingAction(IRoutingDecision.RoutingAction.DROP);
			}
			logger.info("packet enter router, drop");
		} else {
			// setup dst to router
			/*IDevice dstDevice;
			dstDevice=IDeviceService.fcStore.get(cntx, IDeviceService.CONTEXT_DST_DEVICE);
			if (dstDevice != null){
				IDeviceService.fcStore.remove(cntx, IDeviceService.CONTEXT_DST_DEVICE);
			}
			
			Device fakeDstDevice=new Device();*/
			
			
			logger.info("packet enter switch, set dest");
		}
		
		return Command.CONTINUE;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleServices() {
		return null;
	}

	@Override
	public Map<Class<? extends IFloodlightService>, IFloodlightService> getServiceImpls() {
		return null;
	}

	@Override
	public Collection<Class<? extends IFloodlightService>> getModuleDependencies() {
		Collection<Class<? extends IFloodlightService>> l =
				new ArrayList<Class<? extends IFloodlightService>>();
		l.add(IFloodlightProviderService.class);
		l.add(IDeviceService.class);
		l.add(IRoutingService.class);
		l.add(ITopologyService.class);
		l.add(IDebugCounterService.class);
		return l;
	}

	@Override
	public void init(FloodlightModuleContext context)
			throws FloodlightModuleException {
		floodlightProvider = context.getServiceImpl(IFloodlightProviderService.class);
		deviceManagerService = context.getServiceImpl(IDeviceService.class);
		routingEngineService = context.getServiceImpl(IRoutingService.class);
		topologyService = context.getServiceImpl(ITopologyService.class);
		debugCounterService = context.getServiceImpl(IDebugCounterService.class);
		switchService = context.getServiceImpl(IOFSwitchService.class);
		logger = LoggerFactory.getLogger(Firewall.class);
	}

	@Override
	public void startUp(FloodlightModuleContext context)
			throws FloodlightModuleException {
		floodlightProvider.addOFMessageListener(OFType.PACKET_IN, this);
		
	}

}
