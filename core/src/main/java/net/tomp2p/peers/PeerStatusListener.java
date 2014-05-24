/*
 * Copyright 2012 Thomas Bocek
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package net.tomp2p.peers;

/**
 * All classes that are interested if a new peer was discovered or a peer died (that means all classes that store peer
 * addresses) should implement this interface and add itself as a listener.
 * 
 * @author Thomas Bocek
 * 
 */
public interface PeerStatusListener {

    /**
     * The reasons why a peer can fail. The first is not a failure yet, just a notification that the peer is not
     * reacting. Once its confirmed multiple times, its set to probably offline. If a peer shutsdown friendly, then
     * currently, the peer is marked as such and cannot rejoin within 20 seconds. The same happens for an exception.
     * 
     * @author Thomas Bocek
     * 
     */
    public enum FailReason {
        Timeout, ProbablyOffline, Shutdown, Exception
    }

    /**
     * Called if the peer does not send answer in time. The peer may be busy, so there is a chance of seeing this peer
     * again.
     * 
     * @param remotePeer
     *            The address of the peer that failed
     * @param force
     *            The reason, why the peer failed. This is important to understand if we can reenable the peer.
     * @return False if nothing happened, true if there was a change
     */
    boolean peerFailed(final PeerAddress remotePeer, final FailReason reason);

    /**
     * Called if the peer is online and who reported it. This method may get called many times, for each successful
     * request.
     * 
     * @param remotePeer
     *            The address of the peer that is online.
     * @param referrer
     *            the peer that reported the availability of peerAddress
     * @return False if nothing happened, true if there was a change
     */
    boolean peerFound(final PeerAddress remotePeer, final PeerAddress referrer);
}