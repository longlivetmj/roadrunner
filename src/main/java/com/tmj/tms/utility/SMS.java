package com.tmj.tms.utility;


/**
 * Created by IntelliJ IDEA.
 * User: Hasitha-Ehl
 * Date: 11/3/11
 * Time: 5:24 PM
 */
public class SMS {

    /*public boolean sendMessages(String contactNo, String smsContent, Service service) {
        System.out.println("Sms Sending Start");
        boolean retVal;
        try {
            OutboundNotification outboundNotification = new OutboundNotification();
            service.setOutboundMessageNotification(outboundNotification);
            OutboundMessage msg = new OutboundMessage("+" + contactNo, smsContent);
            service.sendMessage(msg);
            System.out.println(msg);
            System.out.println("Sms Sending End");
            retVal = true;
        } catch (Exception e) {
            e.printStackTrace();
            retVal = false;
        }
        return retVal;
    }

    public List<SmsDetails> readMessages(Service service) throws Exception {
        List<SmsDetails> smsDetailsList = new ArrayList<SmsDetails>();
        List<InboundMessage> msgList;
        try {
//            service.getKeyManager().registerKey("+306948494037", new AESKey(new SecretKeySpec("0011223344556677".getBytes(), "AES")));
            msgList = new ArrayList<InboundMessage>();
            service.readMessages(msgList, InboundMessage.MessageClasses.ALL);
            for (InboundMessage msg : msgList) {
                if (msg.getText().trim().startsWith("TMS") || msg.getText().trim().startsWith("Tms") || msg.getText().trim().startsWith("tms")) {
                    SmsDetails smsDetails = new SmsDetails();
                    smsDetails.setPhoneNo(msg.getOriginator());
                    smsDetails.setSmsContent(msg.getText());
                    smsDetailsList.add(smsDetails);
                }
                service.deleteMessage(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return smsDetailsList;
    }

    public class OutboundNotification implements IOutboundMessageNotification {
        public void process(AGateway gateway, OutboundMessage msg) {
            System.out.println("Outbound handler called from Gateway: " + gateway.getGatewayId());
            System.out.println(msg);
        }
    }

    public class InboundNotification implements IInboundMessageNotification {
        public void process(AGateway gateway, Message.MessageTypes msgType, InboundMessage msg) {
            if (msgType == Message.MessageTypes.INBOUND)
                System.out.println(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
            else if (msgType == Message.MessageTypes.STATUSREPORT)
                System.out.println(">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
            System.out.println(msg);
        }
    }

    public class CallNotification implements ICallNotification {
        public void process(AGateway gateway, String callerId) {
            System.out.println(">>> New call detected from Gateway: " + gateway.getGatewayId() + " : " + callerId);
        }
    }

    public class GatewayStatusNotification implements IGatewayStatusNotification {
        public void process(AGateway gateway, AGateway.GatewayStatuses oldStatus, AGateway.GatewayStatuses newStatus) {
            System.out.println(">>> Gateway Status change for " + gateway.getGatewayId() + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
        }
    }

    public class OrphanedMessageNotification implements IOrphanedMessageNotification {
        public boolean process(AGateway gateway, InboundMessage msg) {
            System.out.println(">>> Orphaned message part detected from " + gateway.getGatewayId());
            System.out.println(msg);
            // Since we are just testing, return FALSE and keep the orphaned message part.
            return false;
        }
    }*/
}
