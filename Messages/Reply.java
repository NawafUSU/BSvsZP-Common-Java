package Messages;

import java.io.NotActiveException;
import java.net.UnknownHostException;

import org.omg.CORBA.portable.ApplicationException;

import Common.ByteList;
import Messages.Message.MESSAGE_CLASS_IDS;

public abstract class Reply extends Message {

    public enum PossibleTypes {

        AckNak(1),
        ReadyReply(2),
        ResourceReply(3),
        ConfigurationReply(4),
        PlayingFieldReply(5),
        AgentListReply(6),
        StatusReply(7);

        private int value;

        PossibleTypes(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static short getStringValueFromInt(int i) {
            for (PossibleTypes status : PossibleTypes.values()) {
                if (status.getValue() == i) {
                    return (short) status.value;
                }
            }

            throw new IllegalArgumentException("the given number doesn't match any Status.");
        }

        public static PossibleTypes convert(int value) {
            PossibleTypes temp = null;
            for (PossibleTypes dt : PossibleTypes.values()) {
                if (dt.value == value) {
                    temp = dt;
                }
            }
            return temp;

        }

    }

    public enum PossibleStatus {

        Success(1),
        Failure(2);

        private int value;

        PossibleStatus(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static short getStringValueFromInt(int i) {
            for (PossibleStatus status : PossibleStatus.values()) {
                if (status.getValue() == i) {
                    return (short) status.value;
                }
            }

            throw new IllegalArgumentException("the given number doesn't match any Status.");
        }

        public static PossibleStatus convert(int value) {
            PossibleStatus temp = null;
            for (PossibleStatus dt : PossibleStatus.values()) {
                if (dt.value == value) {
                    temp = dt;
                }
            }
            return temp;
        }
    }
    public PossibleTypes ReplyType;
    public PossibleStatus Status;
    public String Note;
    private static int MinimumEncodingLength;
    private static short ClassId;

    protected Reply() {
    }

    protected Reply(PossibleTypes type, PossibleStatus status, String note) {
        ReplyType = type;
        Status = status;
        Note = note;
    }

    public static Reply Create(ByteList bytes) throws Exception {
        Reply result = null;

        if (bytes == null || bytes.getRemainingToRead() < getMinimumEncodingLength()) {
            throw new ApplicationException("Invalid message byte array", null);
        }

        short msgType = bytes.PeekInt16();

        if (msgType == (short) MESSAGE_CLASS_IDS.AckNak.getValue()) 
            result = AckNak.Create(bytes);
        else if (msgType == (short) MESSAGE_CLASS_IDS.ReadyReply.getValue())
            result = ReadyReply.Create(bytes);
         else if (msgType == (short) MESSAGE_CLASS_IDS.ResourceReply.getValue()) 
            result = ResourceReply.Create(bytes);
         else if (msgType == (short) MESSAGE_CLASS_IDS.ConfigurationReply.getValue()) 
            result = ResourceReply.Create(bytes);
         else if (msgType == (short) MESSAGE_CLASS_IDS.PlayingFieldReply.getValue()) 
            result = ConfigurationReply.Create(bytes);
         else if (msgType == (short) MESSAGE_CLASS_IDS.AgentListReply.getValue()) 
            result = AgentListReply.Create(bytes);
         else if (msgType == (short) MESSAGE_CLASS_IDS.StatusReply.getValue()) 
            result = StatusReply.Create(bytes);
         else {
            throw new ApplicationException("Invalid Message Class Id", null);
        }
        return result;
    }

    @Override
    public void Encode(ByteList bytes) throws UnknownHostException, NotActiveException, Exception {
        bytes.Add((short) MESSAGE_CLASS_IDS.Reply.getValue());                            // Write out this class id first
        bytes.update();

        short lengthPos = bytes.getCurrentWritePosition();    // Get the current write position, so we
        // can write the length here later
        bytes.Add((short) 0);                             // Write out a place holder for the length
        bytes.update();

        super.Encode(bytes);                              // Encode stuff from base class

        bytes.Add((byte) ReplyType.getValue());            // Write out a place holder for the length
        bytes.update();

        bytes.Add((byte) Status.getValue());               // Write out a place holder for the length
        bytes.update();

        if (Note == null) Note = "";
        
        bytes.Add(Note);
        bytes.update();
        short length = (short) (bytes.getCurrentWritePosition() - lengthPos - 2);
        bytes.update();
        bytes.WriteInt16To(lengthPos, length);           // Write out the length of this object        
    }

    @Override
    public void Decode(ByteList bytes) throws Exception {
        short objType = bytes.GetInt16();
        bytes.update();
        short objLength = bytes.GetInt16();
        bytes.update();

        bytes.SetNewReadLimit(objLength);
        bytes.update();

        super.Decode(bytes);
        bytes.update();

        ReplyType = PossibleTypes.convert((int) bytes.GetByte());
        bytes.update();
        Status = PossibleStatus.convert((int) bytes.GetByte());
        bytes.update();
        Note = bytes.GetString();

        bytes.RestorePreviosReadLimit();
    }

    public PossibleTypes getReplyType() {
        return ReplyType;
    }

    public void setReplyType(PossibleTypes replyType) {
        ReplyType = replyType;
    }

    public PossibleStatus getStatus() {
        return Status;
    }

    public void setStatus(PossibleStatus status) {
        Status = status;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    @Override
    public short getClassId() {
        ClassId = (short) MESSAGE_CLASS_IDS.Reply.getValue();
        System.out.println("Reply.ClassId" + ClassId);
        return ClassId;
    }

    public static int getMinimumEncodingLength() {
        MinimumEncodingLength = 4		// Object header
        						+ 1 	// ReplyType
        						+ 1 	// Status
        						+ 2;    // Note
        System.out.println("Reply.MinimumEncodingLength" + MinimumEncodingLength);
        return MinimumEncodingLength;
    }
}
