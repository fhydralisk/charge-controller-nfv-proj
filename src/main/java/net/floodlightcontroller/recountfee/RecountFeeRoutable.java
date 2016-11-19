/**
 *    Copyright 2013, Big Switch Networks, Inc.
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

import org.restlet.Context;
import org.restlet.Restlet;

import org.restlet.routing.Router;

import net.floodlightcontroller.restserver.RestletRoutable;

public class RecountFeeRoutable implements RestletRoutable {
	
	public FeeContext feeContext;
	
	public RecountFeeRoutable(FeeContext ctx) {
		feeContext = ctx;
	}
	
	@Override
	public Restlet getRestlet(Context context) {
        Router router = new Router(context);
        
        RecountFeeSubscriber.context = feeContext;
        RecountFeeSubscribers.context = feeContext;
        RecountFeeDevices.context = feeContext;
        RecountFeeDevice.context = feeContext;
        
        router.attach("subscriber/{id}", RecountFeeSubscriber.class);
        router.attach("subscribers", RecountFeeSubscribers.class);
        router.attach("devices" , RecountFeeDevices.class);
        router.attach("device/{dpid}", RecountFeeDevice.class);
        return router;
	}

	@Override
	public String basePath() {
		return "/charge/";
	}

}
